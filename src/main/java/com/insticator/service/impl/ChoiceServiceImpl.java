package com.insticator.service.impl;

import com.insticator.dao.ChoiceDao;
import com.insticator.entity.Choice;
import com.insticator.service.ChoiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChoiceServiceImpl implements ChoiceService {
    @Autowired
    ChoiceDao choiceDao;
    @Override
    public boolean updateChoice(Choice choice) {
        if (choice == null || choice.getChoiceId() == null) {
            return false;
        }
        choiceDao.updateContent(choice.getChoiceId(), choice.getContent());
        return true;
    }
}
