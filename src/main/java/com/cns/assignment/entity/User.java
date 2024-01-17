package com.cns.assignment.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.Table;
import java.util.Date;

@Entity
@Getter
@Setter
@Table(name = "APP_USERS")
public class User extends AbstractBaseEntity{
    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String mobileNumber;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private Date dateOfBirth;

    @Column(nullable = false)
    private String nationality;

    @Column(nullable = false)
    private String passportNumber;

    @Column(nullable = false)
    private String passportExpiryDate;

    @Column(nullable = false)
    @Lob
    private String passportFile;

    @Column(nullable = false)
    @Lob
    private String userPhoto;

    @Column(nullable = false)
    private String passportFilePath;

    @Column(nullable = false)
    private String userPhotoPath;
}
