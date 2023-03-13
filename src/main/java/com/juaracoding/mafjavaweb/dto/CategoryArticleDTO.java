package com.juaracoding.mafjavaweb.dto;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 3/8/2023 8:00 PM
@Last Modified 3/8/2023 8:00 PM
Version 1.0
*/
public class CategoryArticleDTO {
    private Long idCategory;
    private String nameCategory;
    private String strDescCategoryArticle;


    public Long getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(Long idCategory) {
        this.idCategory = idCategory;
    }

    public String getNameCategory() {
        return nameCategory;
    }

    public void setNameCategory(String nameCategory) {
        this.nameCategory = nameCategory;
    }

    public String getStrDescCategoryArticle() {
        return strDescCategoryArticle;
    }

    public void setStrDescCategoryArticle(String strDescCategoryArticle) {
        this.strDescCategoryArticle = strDescCategoryArticle;
    }
}