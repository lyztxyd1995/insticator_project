package com.insticator.service.impl;

import com.insticator.dao.AdministratorDao;
import com.insticator.entity.Administrator;
import com.insticator.service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AdminServiceImpl implements AdminService {
    @Autowired
    AdministratorDao administratorDao;
    @Override
    public boolean isValidAdmin(String username, String password) {
        Administrator administrator = administratorDao.select(username);
        if (administrator == null || !administrator.getPassword().equals(password)) {
            return false;
        }
        return true;
    }

    @Override
    public Administrator getAdmin(String username) {
        return administratorDao.select(username);
    }
}
