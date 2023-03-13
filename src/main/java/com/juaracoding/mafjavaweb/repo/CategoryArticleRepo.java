package com.juaracoding.mafjavaweb.repo;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 3/8/2023 8:32 PM
@Last Modified 3/8/2023 8:32 PM
Version 1.0
*/


import com.juaracoding.mafjavaweb.model.CategoryArticle;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
public interface CategoryArticleRepo extends JpaRepository<CategoryArticle,Long> {

//    List<CategoryProduct> findByNameCategoryProduct(String value);
}