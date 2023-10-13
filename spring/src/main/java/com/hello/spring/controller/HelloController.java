package com.hello.spring.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")    //  /hello 경로로 들어오는 GET요청을 이 메소드가 처리한다
    public String hello(Model model) {
    // String 타입의 값을 반환. 매개변수로 Model 객체를 받으며, 데이터를 뷰에 전달한다.
        model.addAttribute("data", "hello!");
        //"data"라는 이름으로 "hello!"라는 값을 모델에 추가한다. 모델에 데이터를 추가하면 해당 데이터를 뷰에서 사용한다.
        return "hello";
        //문자열 hello를 반환. 스프링프레임워크에서 뷰의 이름으로 사용된다.
    }


}
