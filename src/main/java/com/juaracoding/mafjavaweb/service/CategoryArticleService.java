package com.juaracoding.mafjavaweb.service;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 3/8/2023 8:10 PM
@Last Modified 3/8/2023 8:10 PM
Version 1.0
*/


import com.juaracoding.mafjavaweb.handler.ResourceNotFoundException;
import com.juaracoding.mafjavaweb.model.CategoryArticle;
import com.juaracoding.mafjavaweb.repo.CategoryArticleRepo;
import com.juaracoding.mafjavaweb.utils.ConstantMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOError;
import java.io.IOException;
import java.sql.SQLDataException;
import java.util.Date;
import java.util.List;
import java.util.Optional;
@Service
@Transactional
public class CategoryArticleService {
    private CategoryArticleRepo categoryArticleRepo;


    @Autowired
    public CategoryArticleService(CategoryArticleRepo categoryArticleRepo) {
        this.categoryArticleRepo = categoryArticleRepo;
    }


    public void saveDataCategory(CategoryArticle categoryArticle){
        categoryArticleRepo.save(categoryArticle);
    }

    @Transactional(rollbackFor = Exception.class)
    public void saveAllCategory(List<CategoryArticle> listCategoryArticle){
        categoryArticleRepo.saveAll(listCategoryArticle);
    }

    @Transactional(rollbackFor = Exception.class)
    public List<CategoryArticle> saveUploadFile(List<CategoryArticle> listCategoryArticle){
        return categoryArticleRepo.saveAll(listCategoryArticle);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updateCategory(CategoryArticle categoryArticle,Long id) throws  Exception
    {
        CategoryArticle cArticle = categoryArticleRepo.findById(id).orElseThrow (
                ()->  new ResourceNotFoundException("Data tidak ditemukan")
        );

        /*
            SELECT * FROM MstCategoryProduct WHERE IDCategoryProduct = ?
            cProduct.getNameCategoryProduct();//ALAT ELEKTRONIK
            cProduct.getStrDescCategoryProduct();//seluruh peralatan yang disentuh nanti nyetrum
         */
        if(cArticle!=null){
            cArticle.setNameCategoryArticle(categoryArticle.getNameCategoryArticle());
            cArticle.setModifiedBy(categoryArticle.getModifiedBy());
            cArticle.setModifiedDate(new Date());
            cArticle.setStrDescCategoryArticle(categoryArticle.getStrDescCategoryArticle());
        }

    }

    public List<CategoryArticle> findAllCategory()
    {
        return categoryArticleRepo.findAll();
        /*
            SELECT * FROM MstCategoryProduct
         */
    }
    public Page<CategoryArticle> findAllCategoryByPage(Pageable pageable)
    {
        return categoryArticleRepo.findAll(pageable);
        /*
            SELECT * FROM MstCategoryProduct Page = ? , Sort = ? , Record = ?

            totalRecord = 100
            page = 0
            Record = 10
            data balikan = dari index ke 0 s.d index ke 9

            page = 1
            record = 10
            data balikan = dari index 10 s.d index 19

            page = 2
            record = 10
            data balikan = dari index 20 s.d index 19

         */
    }

    public Optional<CategoryArticle> findById(Long id)
    {
        return categoryArticleRepo.findById(id);

        /*
            SELECT * FROM MstCategoryProduct WHERE IDCategoryProduct = ?
         */
    }
}


