package com.losung.cms.service;

import com.losung.cms.dto.User;
import com.losung.cms.entity.Contacts;

import java.util.List;

public interface ContactManagementService {
    Contacts createUser(User user);

    void deleteUser(Long id);

    void updateUser(User user, Long Id);

    List<Contacts> findUser(String firstName, String lastName, String email);
}
