package com.insticator.service;

import com.insticator.entity.Administrator;

public interface AdminService {
    boolean isValidAdmin(String username, String password);
    Administrator getAdmin(String username);
}
