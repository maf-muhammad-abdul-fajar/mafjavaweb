package com.juaracoding.mafjavaweb.controller;

import cn.apiclub.captcha.Captcha;
import com.juaracoding.mafjavaweb.dto.ForgetPasswordDTO;
import com.juaracoding.mafjavaweb.dto.UserDTO;
import com.juaracoding.mafjavaweb.model.Userz;
import com.juaracoding.mafjavaweb.utils.CaptchaUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("api/check")
public class CheckPageController {


    @GetMapping("/signin")
    public String pageOne(Model model)
    {

        Captcha captcha = CaptchaUtils.createCaptcha(150, 60);
        Userz users = new Userz();
        users.setHidden(captcha.getAnswer());
        users.setCaptcha("");
        users.setImage(CaptchaUtils.encodeBase64(captcha));
        model.addAttribute("usr",users);
        return "authz_signin";
    }

    @GetMapping("/register")
    public String pageTwo(Model model)
    {
        UserDTO users = new UserDTO();
        model.addAttribute("usr",users);
        return "authz_register";
    }

    @GetMapping("/verify")
    public String pageThree(Model model)
    {
        model.addAttribute("usr",new Userz());
        return "authz_verifikasi";
    }

    @GetMapping("/index1")
    public String pageFour()
    {
        return "index_1";

    }
    @GetMapping("/styledPage")
    public String pageFive(Model model)
    {
        model.addAttribute("name", "Paulo");
        return "styledPage";
    }

    @GetMapping("/index")
    public String pageSix()
    {
        return "index";
    }

    @GetMapping("/forgetpwd")
    public String pageSeven(Model model)
    {

        ForgetPasswordDTO forgetPasswordDTO = new ForgetPasswordDTO();
        model.addAttribute("forgetpwd",forgetPasswordDTO);
        return "authz_forget_pwd_email";
    }
}
