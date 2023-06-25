package com.losung.cms.repository;

import com.losung.cms.entity.Contacts;

import java.util.List;

public interface ContactsRepositoryCustom {
    List<Contacts> findUser(String firstName, String lastName, String email);
}
