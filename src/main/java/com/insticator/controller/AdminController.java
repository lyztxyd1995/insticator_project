package com.insticator.controller;

import com.insticator.entity.Administrator;
import com.insticator.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody
;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class AdminController {
    @Autowired
    AdminService adminService;

    @RequestMapping(value = "/admin", method = RequestMethod.GET)
    private String adminLogin(){
        return "adminLogin";
    }
    @RequestMapping(value = "/manageQuestions", method = RequestMethod.GET)
    private String manageQuestions(){
        return "manageQuestions";
    }

    @RequestMapping(value = "/adminLogin",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> adminLogin(HttpServletRequest request) {
        Map<String,Object>modelMap = new HashMap<>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (username == null || password == null) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "password or username is empty");
            return modelMap;
        }
        if (!adminService.isValidAdmin(username,password)) {
            modelMap.put("success",false);
            modelMap.put("errMsg", "invalid password or username");
            return modelMap;
        } else {
            modelMap.put("success",true);
            request.getSession().setAttribute("Admin", adminService.getAdmin(username));
            return modelMap;
        }
    }

    @RequestMapping(value = "getAdmin", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getAdmin(HttpServletRequest request) {
        Map<String,Object>modelMap = new HashMap<>();
        Administrator admin = (Administrator) request.getSession().getAttribute("Admin");
        if(admin != null){
            modelMap.put("success",true);
            modelMap.put("Admin",admin);
            return modelMap;
        } else {
            modelMap.put("success",false);
            modelMap.put("errMsg","could not load the administrator's information");
        }
        return modelMap;
    }
}
