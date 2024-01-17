package com.cns.assignment.DTO;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Setter
@Getter
public class UserDTO {
    @NotNull
    private String name;
    @NotNull
    private String mobileNumber;
    @NotNull
    private String email;
    @NotNull
    private Date dateOfBirth;
    @NotNull
    private String nationality;
    @NotNull
    private String passportNumber;
    @NotNull
    private String passportExpiryDate;
    @NotNull
    private String passportFile;
    @NotNull
    private String userPhoto;

}
