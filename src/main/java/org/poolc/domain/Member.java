package org.poolc.domain;

import lombok.Data;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;

@Entity //@Data가 들어왔을때 전체 coverage 가능한 테스트는 어떻게 할까?
public class Member {

    //this is PK
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERID") @NotEmpty
    private String userId;

    @Column(name = "NAME") @NotEmpty
    private String name;

    @Column(name = "PASSWORD") @NotEmpty
    private String passWord;

    @Column(name = "EMAIL") @NotEmpty
    private String email;

    @Column(name = "PHONENUM") @NotEmpty
    private String phoneNum;

    @Column(name = "DEPARTMENT") @NotEmpty
    private String department;

    @Column(name = "STUDENTID") @NotEmpty
    private String studentId;

    public Member(){}

    public Member( String userId, String name, String passWord,
                  String email, String phoneNum, String department, String studentId) {
        this.userId = userId;
        this.name = name;
        this.passWord = passWord;
        this.email = email;
        this.phoneNum = phoneNum;
        this.department = department;
        this.studentId = studentId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNum() {
        return phoneNum;
    }

    public void setPhoneNum(String phoneNum) {
        this.phoneNum = phoneNum;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getStudentId() {
        return studentId;
    }

    public void setStudentId(String studentId) {
        this.studentId = studentId;
    }

}
