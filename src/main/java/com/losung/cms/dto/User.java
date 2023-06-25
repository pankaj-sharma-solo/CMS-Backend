package com.losung.cms.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class User {
    @NotEmpty(message = "First Name Cannot Be Empty")
    private String firstName;
    private String lastName;

    @NotEmpty(message = "Please Provide 10 Digit Mobile Number")
    @Pattern(regexp = "^[\\d]{10}$", message = "Please Provide 10 Digit Mobile Number")
    @Size(min = 10, max = 10, message = "Please Provide 10 Digit Mobile Number")
    private String phoneNumber;

    @NotEmpty(message = "Email address can not be empty")
    @Pattern(regexp = "^[\\w][\\w-\\.]+@([\\w-]+\\.)+[\\w-]*[\\w]+$", message = "Invalid Email Address Format")
    private String email;
}
