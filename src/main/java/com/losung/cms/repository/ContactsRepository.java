package com.losung.cms.repository;

import com.losung.cms.entity.Contacts;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactsRepository extends JpaRepository<Contacts, Long>, ContactsRepositoryCustom {
    @Modifying
    @Query("Update Contacts cnts set cnts.firstName = :firstName, cnts.lastName = :lastName, cnts.phone = :phone, cnts.email = :email where cnts.id = :id")
    int updateContact(@Param("id") Long id, @Param("firstName") String firstName, @Param("lastName") String lastName, @Param("phone") String phone, @Param("email") String email);

    @Modifying
    @Query("Update Contacts cnts set cnts.isDeleted = true where cnts.id = :id")
    int deleteUser(@Param("id") Long id);
}
