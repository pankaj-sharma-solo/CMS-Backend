package com.losung.cms.repository.impl;

import com.losung.cms.entity.Contacts;
import com.losung.cms.exception.BusinessException;
import com.losung.cms.repository.ContactsRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;

public class ContactsRepositoryCustomImpl implements ContactsRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Contacts> findUser(String firstName, String lastName, String email) {
        if (StringUtils.isNotEmpty(firstName) && StringUtils.isNotEmpty(lastName) && StringUtils.isNotEmpty(email)){
            throw BusinessException.builder()
                    .statusCode(HttpStatus.NOT_ACCEPTABLE)
                    .message("All three searching parameters can not be empty!")
                    .build();
        }
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Contacts> query = cb.createQuery(Contacts.class);
        Root<Contacts> root = query.from(Contacts.class);
        Path<String> fN = root.get("firstName");
        Path<String> lN = root.get("lastName");
        Path<String> em = root.get("email");
        Path<Boolean> isDeleted = root.get("isDeleted");

        List<Predicate> predicates = new ArrayList<>();
        if(StringUtils.isNotEmpty(firstName)){
            predicates.add(cb.equal(fN, firstName.toLowerCase()));
        }
        if(StringUtils.isNotEmpty(lastName)){
            predicates.add(cb.equal(lN, lastName.toLowerCase()));
        }
        if(StringUtils.isNotEmpty(email)) {
            predicates.add(cb.equal(em, email.toLowerCase()));
        }

        predicates.add(cb.equal(isDeleted, false));

        query.select(root).where(cb.and(predicates.toArray(new Predicate[0])));
        return entityManager.createQuery(query).getResultList();
    }
}
