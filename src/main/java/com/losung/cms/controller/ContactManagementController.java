package com.losung.cms.controller;

import com.losung.cms.dto.User;
import com.losung.cms.entity.Contacts;
import com.losung.cms.exception.BusinessException;
import com.losung.cms.service.ContactManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/cms")
@Tag(name = "Contact Management System")
public class ContactManagementController {

    @Autowired
    private ContactManagementService contactManagementService;

    @PostMapping("/user")
    @Operation(description = "Creates user contact in DB")
    public ResponseEntity<Contacts> createUser(@Valid @RequestBody User user) {
        return ResponseEntity.ok(contactManagementService.createUser(user));
    }

    @DeleteMapping("/user")
    @Operation(description = "Delete user using id.")
    public ResponseEntity<?> deleteUser(@RequestParam Long id) {
        contactManagementService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/user/{id}")
    @Operation(description = "Update user using id and updated data")
    public ResponseEntity updateUser(@Valid @RequestBody User user, @PathVariable("id") Long Id) {
        contactManagementService.updateUser(user, Id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/user")
    @Operation(description = "Find users using any combination of firstName, lastName or email.")
    public ResponseEntity<List<Contacts>> searchUser(@RequestParam(value = "firstName", required = false) String firstName, @RequestParam(value = "lastName", required = false) String lastName, @RequestParam(value = "email", required = false) String email) {
        return ResponseEntity.ok(contactManagementService.findUser(firstName, lastName, email));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BusinessException> handleException(Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_ACCEPTABLE).body(BusinessException.builder()
                .message(e.getMessage())
                .build());
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<BusinessException> handleException(BusinessException e) {
        return ResponseEntity.status(e.getStatusCode()).body(e);
    }
}
