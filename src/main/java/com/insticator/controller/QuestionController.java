package com.insticator.controller;

import com.insticator.entity.Choice;
import com.insticator.entity.MatricItem;
import com.insticator.entity.Question;
import com.insticator.service.QuestionService;
import com.insticator.util.QuestionType;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @RequestMapping(value = "/getQuestions",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getQuestions(HttpServletRequest request) {
        Map<String,Object>modelMap = new HashMap<>();
        try {
            modelMap.put("success", true);
            modelMap.put("questions", questionService.selectAll());
        } catch (Exception e) {
            modelMap.put("errMsg", e.getMessage());
            modelMap.put("success", false);
        }
        return modelMap;
    }

    @RequestMapping(value = "/getQuestionByType",method = RequestMethod.GET)
    @ResponseBody
    private Map<String,Object> getQuestionByType(HttpServletRequest request){
        String type = request.getParameter("type");
        Map<String,Object>modelMap = new HashMap<>();
        try {
            List<Question> questionList = new ArrayList<>();
            if (type.equals("trivia")) {
                questionList = questionService.selectByType(QuestionType.Trivial);
            }
            else if(type.equals("poll")) {
                questionList = questionService.selectByType(QuestionType.Poll);
            }
            else if (type.equals("checkbox")){
                questionList = questionService.selectByType(QuestionType.CheckBox);
            }
            else if(type.equals("matric")){
                questionList = questionService.selectByType(QuestionType.Matric);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "could not find corresponding question type");
                return modelMap;
            }
            System.out.println(questionList);
            modelMap.put("success", true);
            modelMap.put("questionList", questionList);
        } catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
        }
        return modelMap;
    }

    @RequestMapping(value="/deleteQuestion",method = RequestMethod.GET)
    private String deleteQuestion(HttpServletRequest request) {
        System.out.println("enter delete");
        if (request.getSession().getAttribute("Admin") == null){
            return "manageQuestions";
        }
        Integer questionId = Integer.parseInt(request.getParameter("id"));
        questionService.deleteQuestion(questionId);
        System.out.println("delete successfully");
        return "manageQuestions";
    }

    @RequestMapping(value = "/addQuestion", method = RequestMethod.GET)
    private String addQuestion(HttpServletRequest request) {
        return "addQuestion";
    }

    @RequestMapping(value = "/handleAddQuestion", method = RequestMethod.POST)
    @ResponseBody
    private Map<String,Object> handleAddQuestion(@RequestBody String param, HttpServletRequest request){
        Map<String,Object>modelMap = new HashMap<>();
        System.out.println(param);
        JSONObject obj = new JSONObject(param);
        //get question content
        String content = obj.getString("content");
        if (content == null || content.trim().length() == 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "content of question is empty");
            return modelMap;
        }
        //get question type
        String type = obj.getString("questionType");
        QuestionType questionType = QuestionType.getByName(type);
        if (questionType == null) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "question type is not valid");
            return modelMap;
        }
        if (questionType == QuestionType.Matric) {
            return addMatric(obj, content);
        } else {
            return addQuetion(obj, content, questionType);
        }
    }

    private Map<String,Object> addQuetion(JSONObject obj, String content, QuestionType questionType) {
        Map<String,Object>modelMap = new HashMap<>();
        List<Choice>choiceList = new ArrayList<>();
        JSONArray arr = obj.getJSONArray("choices");
        if (arr == null || arr.length() < 2 || arr.length() > 10) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "not enough choice items to add");
            return modelMap;
        }
        int rightAnswer = 0;
        if (questionType != QuestionType.CheckBox && arr.length() > 4) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "the number of choices is not valid");
            return modelMap;
        }
        for (int i = 0; i < arr.length(); i++) {
            String choice_content = arr.getJSONObject(i).getString("content");
            String isAnswer = arr.getJSONObject(i).getString("isAnswer");
            Choice choice = new Choice();
            choice.setContent(choice_content);
            if (isAnswer.equals("Yes")) {
                choice.setAnswer(true);
                rightAnswer++;
            }
            else if(isAnswer.equals("No")){
                choice.setAnswer(false);
            } else {
                modelMap.put("success", false);
                modelMap.put("errMsg", "undefined answer type");
                return modelMap;
            }
            choiceList.add(choice);
        }
        if (rightAnswer > 1) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "too many right answers");
            return modelMap;
        }
        if (rightAnswer > 0 && questionType != QuestionType.Trivial) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "Only trivia question can add right answer");
            return modelMap;
        }
        if (questionType == QuestionType.Trivial && rightAnswer == 0) {
            modelMap.put("success", false);
            modelMap.put("errMsg", "trivia question must have one right answer");
            return modelMap;
        }
        try{
            questionService.addQuestion(questionType, content, choiceList);
        } catch (Exception e){
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;
    }
    private Map<String,Object> addMatric(JSONObject obj, String content) {
        System.out.println("add matric");
        Map<String,Object>modelMap = new HashMap<>();
        try{
            List<MatricItem>matricItemList = new ArrayList<>();
            JSONArray arr = obj.getJSONArray("maricItemList");
            for (int i = 0; i  < arr.length(); i++) {
                JSONObject jsonObject = arr.getJSONObject(i);
                String itemContent = jsonObject.getString("content");
                if (itemContent == null || itemContent.trim().length() == 0) {
                    System.out.println("item conetent is null");
                    modelMap.put("success", false);
                    modelMap.put("errMsg", "matric item could not be empty");
                    return modelMap;
                }
                MatricItem matricItem = new MatricItem(itemContent);
                JSONArray curArr = null;
                try {
                    curArr = jsonObject.getJSONArray("defaultChoices");
                } catch (Exception e){
                    matricItemList.add(matricItem);
                    continue;
                }
                List<String>defaultChoices = new ArrayList<>();
                for (int j = 0; j < curArr.length(); j++) {
                    defaultChoices.add(curArr.getString(j));
                }
                matricItem.setDefaultChoices(defaultChoices);
                matricItemList.add(matricItem);
            }
            System.out.println("finish matricItemList");
            System.out.println(matricItemList);
            System.out.println(content);
            System.out.println("addm matric");
            questionService.addMatric(content,matricItemList);
        } catch (Exception e) {
            modelMap.put("success", false);
            modelMap.put("errMsg", e.getMessage());
            return modelMap;
        }
        modelMap.put("success", true);
        return modelMap;
    }
}

