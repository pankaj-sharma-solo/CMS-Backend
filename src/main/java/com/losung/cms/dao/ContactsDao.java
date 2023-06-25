package com.losung.cms.dao;

import com.losung.cms.dto.User;
import com.losung.cms.entity.Contacts;

import java.util.List;

public interface ContactsDao {

    /**
     * Create user contact in table.
     */
    Contacts createContact(User user);

    /**
     * Delete contact by id.
     */
    void deleteContact(Long Id);

    /**
     * Update existing contact.
     */
    void updateContact(User user, Long Id);

    /**
     * find user by any of the following parameters.
     */
    List<Contacts> searchUser(String firstName, String lastName, String email);
}
