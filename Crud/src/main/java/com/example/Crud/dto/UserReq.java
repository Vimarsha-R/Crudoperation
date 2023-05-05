package com.example.Crud.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.*;

@Data
@AllArgsConstructor(staticName = "build")
@NoArgsConstructor
public class UserReq {
    @NotBlank(message = "username shouldn't be null")
    private String name;

    @Email(message = "invalid email id..!")
    private String email;

    @NotBlank
    @Pattern(regexp = "^\\d{10}$",message = "invalid mobile number entered")
    private String mobile;

    @Min(18)
    @Max(60)
    private String age;
}
