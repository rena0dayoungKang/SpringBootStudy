package hello.hellospring.repository;

import hello.hellospring.domain.Member;
import org.springframework.jdbc.datasource.DataSourceUtils;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;


public class JdbcMemberRepository implements MemberRepository {

    private final DataSource dataSource;

    public JdbcMemberRepository(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    @Override
    public Member save(Member member) {
        String sql = "insert into member(name) values(?)";  //파라미터 바인딩 위해 물음표 처리

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null; //결과를 받는 것
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            //RETURN_GENERATED_KEYS는 1번, 2번 id값을 얻을 수 있는 것

            pstmt.setString(1, member.getName());
            //setString에서 파라미터 인덱스 1번 하면 물음표에 1이 들어가게 된다.


            pstmt.executeUpdate(); //DB의 실제 query가 날라간다.
            rs = pstmt.getGeneratedKeys();  //방금 키를 생성했는데 만약 1번이면 1번을 반환해주고, 2번이면 2번을 반환
            if (rs.next()) {
            //result set 에서 뭔가 값을 가지고 있으면 rs.next() 해서 값을 꺼낸다
                member.setId(rs.getLong(1));
                //rs값이 있다면 getLong으로 값을 꺼내고 setId로 세팅한다.
            } else {
                throw new SQLException("id 조회 실패");
                //실패 시 나오는 문구
            }
            return member;
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
            //DB커넥션 같은 것들은 외부 네트워크 연결과 관련되어 있기 때문에 끝나고 나면 바로 자원을 끊어야 한다.
            //DB커넥션 계속 쌓이다가 대장애가 날 수도 있다.
            //클로즈 리소스 하는 것도 굉장히 복잡
        }
    }

    //스프링프레임워크를 통해서 데이터베이스 커넥션을 쓸 때, DataSourceUtils를 통해서 커넥션을 획득
    private Connection getConnection() {
        return DataSourceUtils.getConnection(dataSource);
    }

    //닫을 때도 connection은 DataSourceUtils를 통해서 close 해줘야한다.
    private void close(Connection conn) throws SQLException {
        DataSourceUtils.releaseConnection(conn, dataSource);
    }


    private void close(Connection conn, PreparedStatement pstmt, ResultSet rs) { //클로즈 리소스
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (pstmt != null) {
                pstmt.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (conn != null) {
                close(conn);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Optional<Member> findById(Long id) {
        String sql = "select * from member where id = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setLong(1, id);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            } else {
                return Optional.empty();
            }
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public Optional<Member> findByName(String name) {
        String sql = "select * from member where name = ?";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            pstmt.setString(1, name);
            rs = pstmt.executeQuery();
            if(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                return Optional.of(member);
            }
            return Optional.empty();  //없으면 Optional.empty()해서 넘김.
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }

    @Override
    public List<Member> findAll() {
        String sql = "select * from member";

        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        try {
            conn = getConnection();
            pstmt = conn.prepareStatement(sql);
            rs = pstmt.executeQuery();
            List<Member> members = new ArrayList<>();
            while(rs.next()) {
                Member member = new Member();
                member.setId(rs.getLong("id"));
                member.setName(rs.getString("name"));
                members.add(member);
                //while 반복하면서 .add해서 리스트에 멤버를 담는다.
            }
            return members; //멤버 반환
        } catch (Exception e) {
            throw new IllegalStateException(e);
        } finally {
            close(conn, pstmt, rs);
        }
    }
}
