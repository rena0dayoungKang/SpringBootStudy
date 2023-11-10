package hello.hellospring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeController {

    @GetMapping("/")
    //그냥 로컬호스트 8080으로 들어오면 호출.
    public String home() {
        return "home";
        //home.html이 호출되겟죠
    }
}
