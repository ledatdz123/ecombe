package com.example.ecombe;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;

@Controller
public class HomeController {
    @RequestMapping("/test")
    @ResponseBody
    public HashMap<String, String> test(){
        HashMap<String, String> ob=new HashMap<>();
        ob.put("a", "student a");
        ob.put("b", "student b");
        return ob;
    }
}
