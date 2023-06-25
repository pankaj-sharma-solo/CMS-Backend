package com.losung.cms.dao.impl;

import com.losung.cms.dao.ContactsDao;
import com.losung.cms.dto.User;
import com.losung.cms.entity.Contacts;
import com.losung.cms.exception.BusinessException;
import com.losung.cms.repository.ContactsRepository;
import jakarta.transaction.Transactional;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.time.Instant;
import java.util.List;

@Service
@Transactional
public class ContactsDaoImpl implements ContactsDao {

    @Autowired
    private ContactsRepository contactsRepository;

    @Override
    public Contacts createContact(User user) {
        return contactsRepository.save(Contacts.builder()
                .firstName(user.getFirstName().toLowerCase())
                .lastName(StringUtils.isNotEmpty(user.getLastName()) ? user.getLastName().toLowerCase() : null)
                .phone(user.getPhoneNumber())
                .email(user.getEmail().toLowerCase())
                .build());
    }

    @Override
    public void deleteContact(Long Id) {
        contactsRepository.deleteUser(Id);
    }

    @Override
    public void updateContact(User user, Long Id) {
        int updateCount = contactsRepository.updateContact(Id, user.getFirstName(), user.getLastName(), user.getPhoneNumber(), user.getEmail());
        if (updateCount != 1) {
            throw BusinessException.builder()
                    .message("Update failed!")
                    .statusCode(HttpStatus.NOT_ACCEPTABLE)
                    .build();
        }
    }

    @Override
    public List<Contacts> searchUser(String firstName, String lastName, String email) {
        return contactsRepository.findUser(firstName, lastName, email);
    }
}
