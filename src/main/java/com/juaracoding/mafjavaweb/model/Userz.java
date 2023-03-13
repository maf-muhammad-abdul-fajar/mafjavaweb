package com.juaracoding.mafjavaweb.model;


import com.juaracoding.mafjavaweb.utils.ConstantMessage;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.Period;
import java.util.Date;

@Entity
@Table(name = "MstUser", uniqueConstraints = @UniqueConstraint(columnNames = "Email"))
public class Userz {

    @Id
    @Column(name = "IDUser")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idUser;

    @NotEmpty(message = ConstantMessage.ERROR_EMAIL_IS_EMPTY)
    @Length(message = ConstantMessage.ERROR_EMAIL_MAX_MIN_LENGTH ,min = 15,max = 50)
    @NotNull(message = ConstantMessage.ERROR_EMAIL_IS_NULL)
    @Column(name = "Email",unique = true,length = 50)
    private String email;

    @Column(name = "UserName")
    private String username;

    @Column(name = "Password")
    private String password;

    @Column(name = "NamaLengkap")
    private String namaLengkap;

    @Column(name = "Token")
    private String token;

    @Column(name = "TokenCounter")
    private Integer tokenCounter=0;

    @Column(name = "PasswordCounter")
    private Integer passwordCounter=0;

    @Column(name ="CreatedDate" , nullable = false)
    private Date createdDate = new Date();

    @Column(name = "CreatedBy", nullable = false)
    private Integer createdBy=1;

    @Column(name = "ModifiedDate")
    private Date modifiedDate;
    @Column(name = "ModifiedBy")
    private Integer modifiedBy;

    @Column(name = "IsDelete", nullable = false)
    private Byte isDelete = 0;//khusus disini default 0 karena setelah verifikasi baru di update menjadi 1
    /*
        end audit trails
     */

    @Transient
    private String captcha;

    @Transient
    private String hidden;

    @Transient
    private String image;


    @Column(name = "LastLoginDate")
    private Date lastLoginDate;
    @Transient
    private Integer umur;

    @Column(name = "TanggalLahir")
    private LocalDate tanggalLahir;

    @Column(name = "NoHP")
    private String noHP;

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }

    public Integer getUmur() {
        return Period.
                between(this.tanggalLahir,LocalDate.now())
                .getYears();
    }

    public void setUmur(Integer umur) {
        this.umur = umur;
    }

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public Date getLastLoginDate() {
        return lastLoginDate;
    }

    public void setLastLoginDate(Date lastLoginDate) {
        this.lastLoginDate = lastLoginDate;
    }

    public String getCaptcha() {
        return captcha;
    }

    public void setCaptcha(String captcha) {
        this.captcha = captcha;
    }

    public String getHidden() {
        return hidden;
    }

    public void setHidden(String hidden) {
        this.hidden = hidden;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getIdUser() {
        return idUser;
    }

    public void setIdUser(Long idUser) {
        this.idUser = idUser;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getNamaLengkap() {
        return namaLengkap;
    }

    public void setNamaLengkap(String namaLengkap) {
        this.namaLengkap = namaLengkap;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Integer getTokenCounter() {
        return tokenCounter;
    }

    public void setTokenCounter(Integer tokenCounter) {
        this.tokenCounter = tokenCounter;
    }

    public Integer getPasswordCounter() {
        return passwordCounter;
    }

    public void setPasswordCounter(Integer passwordCounter) {
        this.passwordCounter = passwordCounter;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public Integer getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    public Date getModifiedDate() {
        return modifiedDate;
    }

    public void setModifiedDate(Date modifiedDate) {
        this.modifiedDate = modifiedDate;
    }

    public Integer getModifiedBy() {
        return modifiedBy;
    }

    public void setModifiedBy(Integer modifiedBy) {
        this.modifiedBy = modifiedBy;
    }

    public Byte getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Byte isDelete) {
        this.isDelete = isDelete;
    }
}
