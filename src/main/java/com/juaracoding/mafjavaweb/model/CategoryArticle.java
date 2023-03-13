package com.juaracoding.mafjavaweb.model;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 3/8/2023 8:09 PM
@Last Modified 3/8/2023 8:09 PM
Version 1.0
*/

import com.juaracoding.mafjavaweb.utils.ConstantMessage;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.Date;

@Entity
@Table(name = "MstCategoryArticle")
public class CategoryArticle {
    @Column(name = "CreatedDate",nullable = false)
    private Date createdDate = new Date();
    @Column(name = "CreatedBy",nullable = false)
    private int createdBy ;
    @Column(name = "ModifiedDate")
    private Date modifiedDate;
    @Column(name = "ModifiedBy")
    private Integer modifiedBy;

    @Column(name = "IsDelete")
    private Byte isDelete = 1;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IDCategoryArticle")
    private Long idCategoryArticle;

    @Length(message = ConstantMessage.WARNING_CATPROD_MAX_LENGTH_NAME, max = 40)
    @NotEmpty(message = ConstantMessage.WARNING_CATPROD_NAME_CANNOT_EMPTY)
    @Column(name = "NameCategoryArticle", nullable = false, length = 40)
    private String nameCategoryArticle;

    @NotEmpty(message = ConstantMessage.WARNING_CATPROD_DESC_CANNOT_EMPTY)
    @Length(message = ConstantMessage.WARNING_CATPROD_MAX_LENGTH_DESC, max = 500)
    @Column(name = "DescCategoryArticle", nullable = false, length = 500)
    private String strDescCategoryArticle;


    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public int getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(int createdBy) {
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

    public Long getIdCategoryArticle() {
        return idCategoryArticle;
    }

    public void setIdCategoryArticle(Long idCategoryArticle) {
        this.idCategoryArticle = idCategoryArticle;
    }

    public String getNameCategoryArticle() {
        return nameCategoryArticle;
    }

    public void setNameCategoryArticle(String nameCategoryArticle) {
        this.nameCategoryArticle = nameCategoryArticle;
    }

    public String getStrDescCategoryArticle() {
        return strDescCategoryArticle;
    }

    public void setStrDescCategoryArticle(String strDescCategoryArticle) {
        this.strDescCategoryArticle = strDescCategoryArticle;
    }
}