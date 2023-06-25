package com.losung.cms.repository;

import com.losung.cms.entity.ApiKeys;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ApiKeysRepository extends JpaRepository<ApiKeys, Long> {
    @Query("select ak from ApiKeys ak where ak.clientId = :clientId and ak.isDeleted = false")
    ApiKeys getApiKeysByClientId(@Param("clientId") String clientId);
}
