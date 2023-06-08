package org.poolc.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;

@Entity //@Data가 들어왔을때 전체 coverage 가능한 테스트는 어떻게 할까?
public class Member {

    //this is PK
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "USERID")
    @NotEmpty
    private String userId;

    @Column(name = "NAME")
    @NotEmpty
    private String name;

    @Column(name = "PASSWORD")
    @NotEmpty
    private String passWord;

    @Column(name = "EMAIL")
    @NotEmpty
    private String email;

    @Column(name = "PHONENUM")
    @NotEmpty
    private String phoneNum;

    @Column(name = "DEPARTMENT")
    @NotEmpty
    private String department;

    @Column(name = "STUDENTID")
    @NotEmpty
    private String studentId;

    @Column(name = "ROLE")
    //default는 EnumType.ORDINAL이지만(int로 db에 저장), 추후에 새로운 등급이 추가 될 경우
    //등급이 밀리거나 숫자에 할당된 role이 변경될 위험 매우 높음(큰 장애 발생 가능) 따라서 EnumType.String으로 변경
    @Enumerated(EnumType.STRING)
    private MEMBER_ROLE role;


    public Member() {
    }

    public Member(String userId, String name, String passWord,
        String email, String phoneNum, String department, String studentId, MEMBER_ROLE role) {
        this.userId = userId;
        this.name = name;
        this.passWord = passWord;
        this.email = email;
        this.phoneNum = phoneNum;
        this.department = department;
        this.studentId = studentId;
        this.role = role;
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

    public MEMBER_ROLE getRole() {
        return role;
    }

    public void setRole(MEMBER_ROLE role) {
        this.role = role;
    }
}
