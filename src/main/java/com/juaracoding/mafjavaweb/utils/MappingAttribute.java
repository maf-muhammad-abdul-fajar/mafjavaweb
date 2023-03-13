package com.juaracoding.mafjavaweb.utils;

import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;

import java.util.Date;
import java.util.Map;

public class MappingAttribute {

    public void setAttribute(Model model,Map<String,Object> mapz)
    {
        model.addAttribute("message", mapz.get("message"));
        model.addAttribute("status", mapz.get("status"));
        model.addAttribute("data", mapz.get("responseObj")==null?"":mapz.get("responseObj"));
        model.addAttribute("timestamp", new Date());
        model.addAttribute("success",mapz.get("success"));
        if(mapz.get("errorCode") != null)
        {
            model.addAttribute("errorCode",mapz.get("errorCode"));
            model.addAttribute("path",mapz.get("path"));
        }
    }

    public BindingResult setErrorMessage(BindingResult br, String  strErrorMessage)
    {
        br.addError(new ObjectError("globalError",strErrorMessage));
        return br;
    }
}
