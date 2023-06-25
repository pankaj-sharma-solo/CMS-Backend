package com.losung.cms.service.impl;

import com.losung.cms.dao.ContactsDao;
import com.losung.cms.dto.User;
import com.losung.cms.entity.Contacts;
import com.losung.cms.exception.BusinessException;
import com.losung.cms.service.ContactManagementService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactManagementServiceImpl implements ContactManagementService {

    @Autowired
    private ContactsDao contactsDao;

    @Override
    public Contacts createUser(User user) {
        return contactsDao.createContact(user);
    }

    @Override
    public void deleteUser(Long id) {
        contactsDao.deleteContact(id);
    }

    @Override
    public void updateUser(User user, Long Id) {
        contactsDao.updateContact(user, Id);
    }

    @Override
    public List<Contacts> findUser(String firstName, String lastName, String email) {
        if (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName) && StringUtils.isNotEmpty(email)){
            throw BusinessException.builder()
                    .statusCode(HttpStatus.NOT_ACCEPTABLE)
                    .message("All three searching parameters can not be empty!")
                    .build();
        }
        return contactsDao.searchUser(firstName, lastName, email);
    }
}
