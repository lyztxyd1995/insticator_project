package com.insticator.controller;

import com.insticator.entity.Question;
import com.insticator.entity.User;
import com.insticator.service.UserService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@Controller
public class UserController {
    @Autowired
    UserService userService;
    @RequestMapping(value = "/userRegister", method = RequestMethod.GET)
    private String adminLogin(){
        return "userRegister";
    }
    @RequestMapping(value = "/userLogin", method = RequestMethod.GET)
    private String manageQuestions(){
        return "userLogin";
    }
    @RequestMapping(value = "/index", method = RequestMethod.GET)
    private String index(){
        return "index";
    }

    @RequestMapping(value = "/handleUserRegister", method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> handleUserRegister(@RequestBody String param, HttpServletRequest request) {
        Map<String,Object>modelMap = new HashMap<>();
        JSONObject obj = new JSONObject(param);
        String username = obj.getString("username");
        if (!validStr(username)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "username could not be empty");
            return modelMap;
        }
        String password = obj.getString("password");
        String password1 = obj.getString("password1");
        if (!validStr(password) || !validStr(password1)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "password could not be empty");
            return modelMap;
        }
        if (!password.equals(password1)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "passwords are not equal");
            return modelMap;
        }
        try {
            boolean res = userService.addUser(username,password);
            if (res == false){
                modelMap.put("success", false);
                modelMap.put("errMsg", "user already exists, please change your username");
                return modelMap;
            }
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;
    }

    @RequestMapping(value = "/handleUserLogin", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> handleUserLogin(HttpServletRequest request) {
        Map<String,Object>modelMap = new HashMap<>();
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        if (!validStr(username) || !validStr(password)) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "password or username could not be empty");
            return modelMap;
        }
        User user = userService.checkUser(username,password);
        if (user == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "username or password is wrong");
            return modelMap;
        }
        request.getSession().setAttribute("user", user);
        modelMap.put("success",true);
        return modelMap;
    }

    @RequestMapping(value = "/getUser", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getUser(HttpServletRequest request) {
        Map<String,Object>modelMap = new HashMap<>();
        User user = (User) request.getSession().getAttribute("user");
        if (user == null) {
            modelMap.put("success", false);
            return modelMap;
        }
        modelMap.put("user", user);
        modelMap.put("success", true);
        return modelMap;
    }

    @RequestMapping(value="/getUserQuestion", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getQuestion(HttpServletRequest request) {
        Map<String,Object>modelMap = new HashMap<>();
        User user = (User)request.getSession().getAttribute("user");
        try {
            Question question = userService.getQuestion(user.getUsername());
            if (question == null) {
                modelMap.put("success", false);
                modelMap.put("errMsg", "You have asked all the questions");
                return modelMap;
            }
            modelMap.put("question", question);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;
    }

    private boolean validStr(String str) {
        if (str == null || str.trim().length() == 0) {
            return false;
        }
        return true;
    }

    @RequestMapping(value="/userAddRecord", method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> userAddRecord(@RequestBody String param, HttpServletRequest request) {
        Map<String,Object>modelMap = new HashMap<>();
        JSONObject obj = new JSONObject(param);
        try {
            String username = obj.getString("username");
            Integer questionId = obj.getInt("questionId");
            userService.addRecord(questionId,username);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;
    }


    @RequestMapping(value="/userAddPoints", method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> userAddPoints (@RequestBody String param, HttpServletRequest request) {
        Map<String,Object>modelMap = new HashMap<>();
        JSONObject obj = new JSONObject(param);
        try {
            String username = obj.getString("username");
            Integer points = obj.getInt("points");
            System.out.println("current points: " + points);
            userService.updateUserPoints(username,points + 5);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;
    }

    @RequestMapping(value = "/updateUser", method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> updateUser (HttpServletRequest request) {
        Map<String,Object>modelMap = new HashMap<>();
        try {
            String username = request.getParameter("username");
            User user = userService.getUser(username);
            request.getSession().setAttribute("user", user);
            modelMap.put("user", user);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;
    }

}
