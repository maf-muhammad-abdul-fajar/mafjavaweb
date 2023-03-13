package com.juaracoding.mafjavaweb.dto;

import com.juaracoding.mafjavaweb.utils.ConstantMessage;
import org.hibernate.validator.constraints.Length;
import org.springframework.format.annotation.DateTimeFormat;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

public class UserDTO {

    @NotEmpty(message = ConstantMessage.ERROR_EMAIL_IS_EMPTY)
    @Length(message = ConstantMessage.ERROR_EMAIL_MAX_MIN_LENGTH ,min = 15,max = 50)
    @NotNull(message = ConstantMessage.ERROR_EMAIL_IS_NULL)
    private String email;

    @NotEmpty(message = ConstantMessage.ERROR_USRNAME_IS_EMPTY)
    @Length(message = ConstantMessage.ERROR_USRNAME_MAX_MIN_LENGTH ,min = 10,max = 30)
    @NotNull(message = ConstantMessage.ERROR_USRNAME_IS_NULL)
    private String username;

    @NotEmpty(message = ConstantMessage.ERROR_PASSWORD_IS_EMPTY)
    @Length(message = ConstantMessage.ERROR_PASSWORD_MAX_MIN_LENGTH ,min = 8,max = 25)
    @NotNull(message = ConstantMessage.ERROR_PASSWORD_IS_NULL)
    private String password;


    @NotEmpty(message = ConstantMessage.ERROR_NAMALENGKAP_IS_EMPTY)
    @Length(message = ConstantMessage.ERROR_NAMALENGKAP_MAX_MIN_LENGTH ,min = 8,max = 40)
    @NotNull(message = ConstantMessage.ERROR_NAMALENGKAP_IS_NULL)
    private String namaLengkap;

    @DateTimeFormat(pattern = "yyyy-MM-dd")//masih miss validasi nya
    private LocalDate tanggalLahir;

    @NotEmpty(message = ConstantMessage.ERROR_NOHP_IS_EMPTY)
    @NotNull(message = ConstantMessage.ERROR_NOHP_IS_NULL)
    private String noHP;

    private String token;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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

    public LocalDate getTanggalLahir() {
        return tanggalLahir;
    }

    public void setTanggalLahir(LocalDate tanggalLahir) {
        this.tanggalLahir = tanggalLahir;
    }

    public String getNoHP() {
        return noHP;
    }

    public void setNoHP(String noHP) {
        this.noHP = noHP;
    }
}
