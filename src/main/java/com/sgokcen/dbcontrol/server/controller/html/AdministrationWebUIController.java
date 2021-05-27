package com.sgokcen.dbcontrol.server.controller.html;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

import com.sgokcen.dbcontrol.server.security.auth.AuthenticationConstants;

@Controller
@RequestMapping(path = "/ui")
@SessionAttributes(AuthenticationConstants.AUTH_TOKEN_HEADER_KEY)
public class AdministrationWebUIController {

    @GetMapping
    public ModelAndView index(Model model) {
        ModelAndView mv = new ModelAndView();
        mv.setViewName("index");
        
        return mv;
    }
    
//    @GetMapping("/login")
//    public String LoginPage(Model model) {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        
//        if (authentication.isAuthenticated() && authentication.getPrincipal() instanceof User) {
//            return "redirect:/html";
//        }
//        
//        return "login";
//    }
    
}
