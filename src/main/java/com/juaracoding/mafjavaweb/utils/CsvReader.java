package com.juaracoding.mafjavaweb.utils;

import org.springframework.web.multipart.MultipartFile;

public class CsvReader {


    public static boolean isCsv(MultipartFile multipartFile)
    {
        if(!ConstantMessage.CONTENT_TYPE_CSV.equals(multipartFile.getContentType()))
        {
            return false;
        }
        return true;
    }
}