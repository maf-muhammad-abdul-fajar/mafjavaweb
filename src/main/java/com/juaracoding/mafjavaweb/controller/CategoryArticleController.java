package com.juaracoding.mafjavaweb.controller;

/*
Created By IntelliJ IDEA 2022.2.3 (Community Edition)
Build #IU-222.4345.14, built on October 5, 2022
@Author Asus a.k.a. muhammad abdul fajar
Java Developer
Created on 3/8/2023 7:54 PM
@Last Modified 3/8/2023 7:54 PM
Version 1.0
*/

import com.juaracoding.mafjavaweb.dto.CategoryArticleDTO;
import com.juaracoding.mafjavaweb.handler.ResourceNotFoundException;
import com.juaracoding.mafjavaweb.handler.ResponseHandler;
import com.juaracoding.mafjavaweb.model.CategoryArticle;
import com.juaracoding.mafjavaweb.service.CategoryArticleService;
import com.juaracoding.mafjavaweb.utils.ConstantMessage;
import com.juaracoding.mafjavaweb.utils.CsvReader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping("/api/mgmnt")
public class CategoryArticleController {

    private CategoryArticleService categoryArticleService;

    @Autowired
    private ModelMapper modelMapper;

    private Map<String,Object> objectMapper = new HashMap<String,Object>();
    List<CategoryArticle> lsCPUpload = new ArrayList<CategoryArticle>();

    @Autowired
    public void categoryArticleService(CategoryArticleService categoryArticleService) {
        this.categoryArticleService = categoryArticleService;
    }

    @PostMapping("/v1/s")
    public ResponseEntity<Object> saveCategory(@Valid
                                               @RequestBody CategoryArticle categoryArticle
    ){

        categoryArticleService.saveDataCategory(categoryArticle);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY, HttpStatus.OK,null,null,null);
        //FI01001 --> F = FAILED , I = INSERT, 01 = MODUL, 001 = LENGTH NAME MAX 40
        //FI01002 --> F = FAILED , I = INSERT, 01 = MODUL, 002 = LENGTH DESC MAX 500
        //FI01003 --> F = FAILED , I = INSERT, 01 = MODUL, 003 = DATETIME FORMAT
        //SI01001 --> S = SUCCESS , I = INSERT , 01 = MODUL,

    }

    @PostMapping("/v1/sl")
    public ResponseEntity<Object> saveCategoryList(@Valid
                                                   @RequestBody List<CategoryArticle> listCategoryArticle
    ){

        categoryArticleService.saveAllCategory(listCategoryArticle);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_SAVE, HttpStatus.CREATED,null,null,null);

    }
    @PostMapping("/v1/upl/batch")
    public ResponseEntity<Object> uploadCsvMaster(@Valid
                                                  @RequestParam("fileDemo")
                                                  @RequestHeader  MultipartFile multipartFile
    ) throws Exception {


        try{
            if(CsvReader.isCsv(multipartFile))
            {
                List<CategoryArticle> lsCP =  categoryArticleService.saveUploadFile(
                        csvToCategoryArticle(
                                multipartFile.getInputStream()));

                if(lsCP.size()==0)
                {
                    throw new ResourceNotFoundException(ConstantMessage.ERROR_UPLOAD_CSV+" -- "+multipartFile.getOriginalFilename());
                }
            }
            else
            {
                throw new ResourceNotFoundException(ConstantMessage.ERROR_NOT_CSV_FILE+" -- "+multipartFile.getOriginalFilename());
            }
        }catch (Exception e)
        {
            throw new Exception(ConstantMessage.ERROR_UPLOAD_CSV+multipartFile.getOriginalFilename());
        }
        return new ResponseHandler().generateResponse(ConstantMessage.SUCCESS_SAVE,
                HttpStatus.CREATED,null,null,null);


    }

    @PutMapping("/v1/sl/{id}")
    public ResponseEntity<Object> updateCategoryById(@Valid
                                                     @RequestBody CategoryArticle categoryArticle,
                                                     @PathVariable Long id
    ) throws Exception {

        categoryArticleService.updateCategory(categoryArticle,id);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY, HttpStatus.OK,null,null,null);
    }

    @GetMapping("/v1/f")
    public ResponseEntity<Object> findAll(){

        List<CategoryArticle> ls = categoryArticleService.findAllCategory();

        if(ls.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            categoryArticleService.findAllCategory(),
                            null,
                            null);
        }

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        ls,
                        null,
                        null);


    }

    @GetMapping("/v1/fp/{size}/{page}/{sort}")
    public ResponseEntity<Object> findAllPagination(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz
    ){

        Pageable pageable = null;
        if(sortz.equalsIgnoreCase("desc"))
        {
            pageable = PageRequest.of(pagez,sizez, Sort.by("idCategoryArticle").descending());
        }
        else
        {
            pageable = PageRequest.of(pagez,sizez);
        }
        Page<CategoryArticle> page = categoryArticleService.findAllCategoryByPage(pageable);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        page,
                        null,
                        null);


    }


    /*
       PAGINATION SORTING DEFAULT by ID TANPA PARAMETER SORT BY YANG SPESIFIK DAN RESPONSE SUDAH MENGGUNAKAN LIST
       Sudah ada validasi semisal data kosong dan informasi di response lebih dinamis
       Problem :
          1. Data content sudah lebih baik namun informasi Paging tidak diikutsertakan, sehingga menghambat front end nantinya
          2. belum menggunakan DTO data content untuk response masih belum rapih
    */
    @GetMapping("/v1/fp2/{size}/{page}/{sort}")
    public ResponseEntity<Object> findAllPagination2(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz
    ){

        Pageable pageable = null;
        if(sortz.equalsIgnoreCase("desc"))
        {
            pageable = PageRequest.of(pagez,sizez, Sort.by("idCategoryArticle").descending());
        }
        else
        {
            pageable = PageRequest.of(pagez,sizez);

        }
        Page<CategoryArticle> page = categoryArticleService.findAllCategoryByPage(pageable);
        List<CategoryArticle> listCategoryArticle = page.getContent();
        if(listCategoryArticle.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            null,
                            null,
                            null);
        }

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        listCategoryArticle,
                        null,
                        null);
    }

    /*
       PAGINATION DENGAN SORT BY DAN RESPONSE SUDAH MENGGUNAKAN LIST NAMUN VALIDASI SORT BY MASIH DI EMBED DI METHOD REQUEST
       Problem :
       1. Data content sudah lebih baik namun Data Paging tidak diikutsertakan, sehingga menghambat front end nantinya
       2. belum menggunakan DTO data content untuk response masih belum rapih
    */
    @GetMapping("/v1/fpsb1/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findPaginationSortBy1(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy
    ){

        Pageable pageable = null;
        String strSortBy = "";

        if(sortzBy.equalsIgnoreCase("id"))
        {
            strSortBy = "idCategoryArticle";
        }
        else if(sortzBy.equalsIgnoreCase("name"))
        {
            strSortBy = "nameCategoryArticle";
        }
        else if(sortzBy.equalsIgnoreCase("description"))
        {
            strSortBy = "strDescCategoryArticle";
        }
        else
        {
            strSortBy = "idCategoryArticle";
        }

        if(sortz.equalsIgnoreCase("desc"))
        {
            pageable = PageRequest.of(pagez,sizez, Sort.by(strSortBy).descending());
        }
        else
        {
            pageable = PageRequest.of(pagez,sizez, Sort.by(strSortBy).ascending());
        }

        Page<CategoryArticle> page = categoryArticleService.findAllCategoryByPage(pageable);
        List<CategoryArticle> listCategoryArticle = page.getContent();

        if(listCategoryArticle.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            categoryArticleService.findAllCategory(),
                            null,
                            null);
        }

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        listCategoryArticle,
                        null,
                        null);
    }

    /*
       PAGINATION DENGAN SORT BY DAN RESPONSE SUDAH MENGGUNAKAN LIST SERTA VALIDASI SUDAH DIBUAT METHOD SENDIRI
       Problem :
       1. belum menggunakan DTO data content untuk response masih belum rapih
    */
    @GetMapping("/v1/fpsb2/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findPaginationSortBy2(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy
    ){

        Pageable pageable = filterPagination(pagez,sizez,sortz,sortzBy);
        Page<CategoryArticle> page = categoryArticleService.findAllCategoryByPage(pageable);
        List<CategoryArticle> listCategoryArticle = page.getContent();

        if(listCategoryArticle.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            categoryArticleService.findAllCategory(),
                            null,
                            null);
        }

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        listCategoryArticle,
                        null,
                        null);
    }

    /*
       PAGINATION DENGAN SORT BY DAN RESPONSE SUDAH MENGGUNAKAN LIST SERTA VALIDASI SUDAH DIBUAT METHOD SENDIRI + DTO di embed di method request
       Problem :
       1. kurang rapih karena DTO nya masih diembed di method request
    */
    @GetMapping("/v1/fpsbd1/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findPaginationSortByDTO1(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy
    ){

        Pageable pageable = filterPagination(pagez,sizez,sortz,sortzBy);
        Page<CategoryArticle> page = categoryArticleService.findAllCategoryByPage(pageable);
        List<CategoryArticle> listCategoryArticle = page.getContent();

        /*
            CATATAN PENTING!!
            VALIDASI INI DILETAKKAN DISINI AGAR TIDAK PERLU MELAKUKAN PROSES APAPUN (PROSES MAPPING ATAU TRANSFORM DTO) JIKA DATA YANG DICARI KOSONG
         */
        if(listCategoryArticle.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            null,
                            null,
                            null);
        }

        List<CategoryArticleDTO> listCategoryArticleDTO = modelMapper.map(listCategoryArticle, new TypeToken<List<CategoryArticleDTO>>() {}.getType());
        objectMapper.put("data",listCategoryArticleDTO);
        objectMapper.put("currentPage",page.getNumber());
        objectMapper.put("totalItems",page.getTotalElements());
        objectMapper.put("totalPages",page.getTotalPages());
        objectMapper.put("sort",page.getSort());
        objectMapper.put("numberOfElements",page.getNumberOfElements());

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        objectMapper,
                        null,
                        null);
    }

    /*
       PAGINATION DENGAN SORT BY DAN RESPONSE SUDAH MENGGUNAKAN LIST SERTA VALIDASI SUDAH DIBUAT METHOD SENDIRI + DTO sudah dibuat method sendiri
    */
    @GetMapping("/v1/fpsbd2/{size}/{page}/{sort}/{sortby}")
    public ResponseEntity<Object> findPaginationSortByDTO2(
            @PathVariable("size") Integer sizez,
            @PathVariable("page") Integer pagez,
            @PathVariable("sort") String sortz,
            @PathVariable("sortby") String sortzBy
    ){

        Pageable pageable = filterPagination(pagez,sizez,sortz,sortzBy);
        Page<CategoryArticle> page = categoryArticleService.findAllCategoryByPage(pageable);
        List<CategoryArticle> listCategoryArticle = page.getContent();

        /*
            CATATAN PENTING!!
            VALIDASI INI DILETAKKAN DISINI AGAR TIDAK PERLU MELAKUKAN PROSES APAPUN (PROSES MAPPING ATAU TRANSFORM DTO) JIKA DATA YANG DICARI KOSONG
         */
        if(listCategoryArticle.size()==0)
        {
            return new ResponseHandler().
                    generateResponse(ConstantMessage.WARNING_DATA_EMPTY,
                            HttpStatus.NOT_FOUND,
                            null,
                            null,
                            null);
        }

        List<CategoryArticle> listCategoryArticleDTO = modelMapper.map(listCategoryArticle, new TypeToken<List<CategoryArticle>>() {}.getType());
        Map<String, Object> mapResult = transformObject(objectMapper,listCategoryArticleDTO,page);

        return new ResponseHandler().
                generateResponse(ConstantMessage.SUCCESS_FIND_BY,
                        HttpStatus.OK,
                        mapResult,
                        null,
                        null);
    }

    private Map<String,Object> transformObject(Map<String,Object> mapz, List ls, Page page)
    {
        mapz.put("data",ls);
        mapz.put("currentPage",page.getNumber());
        mapz.put("totalItems",page.getTotalElements());
        mapz.put("totalPages",page.getTotalPages());
        mapz.put("sort",page.getSort());
        mapz.put("numberOfElements",page.getNumberOfElements());

        return mapz;
    }

    private Pageable filterPagination(Integer page, Integer size, String sorts , String sortsBy)
    {
        Pageable pageable;
        String strSortBy = "";

        if(sortsBy.equalsIgnoreCase("id"))
        {
            strSortBy = "idCategoryArticle";
        }
        else if(sortsBy.equalsIgnoreCase("name"))
        {
            strSortBy = "nameCategoryArticle";
        }
        else if(sortsBy.equalsIgnoreCase("description"))
        {
            strSortBy = "strDescCategoryArticle";
        }
        else
        {
            strSortBy = "idCategoryArticle";
        }

        if(sorts.equalsIgnoreCase("desc"))
        {
            pageable = PageRequest.of(page,size, Sort.by(strSortBy).descending());
        }
        else
        {
            pageable = PageRequest.of(page,size, Sort.by(strSortBy).ascending());
        }

        return pageable;
    }

    public List<CategoryArticle> csvToCategoryArticle(InputStream inputStream) throws Exception {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        CSVParser csvParser = new CSVParser(bufferedReader,
                CSVFormat.DEFAULT.withFirstRecordAsHeader().
                        withIgnoreHeaderCase().
                        withTrim()
        );
        lsCPUpload.clear();
        try {

            Iterable<CSVRecord> iterRecords = csvParser.getRecords();

            for (CSVRecord record : iterRecords) {
                CategoryArticle cArticles = new CategoryArticle();
                cArticles.setNameCategoryArticle(record.get("NameCategoryArticle"));
                cArticles.setStrDescCategoryArticle(record.get("DescCategoryArticle"));
                cArticles.setCreatedBy(Integer.parseInt(record.get("CreatedBy")));
                lsCPUpload.add(cArticles);
            }

        } catch (Exception ex) {
            throw new Exception(ex.getMessage());
        } finally {

            if (!csvParser.isClosed()) {
                csvParser.close();
            }
            return lsCPUpload;
        }
    }
}
