package com.sgokcen.dbcontrol.server.controller.html;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class JWController {

    @Autowired
    private HttpServletResponse response;
    
    @GetMapping("/jw.html")
    public String jw() {
//        response.setHeader("Access-Control-Allow-Origin", "https://admin.planmijnsport.nl");
//        response.setHeader("Access-Control-Allow-Methods", "GET, POST, OPTIONS, HEAD");
//        response.setHeader("Access-Control-Allow-Headers", "Accept, Origin, Content-Type, X-Requested-With, SessionId, Set-Cookie");
        return "jw";
    }
    
}
