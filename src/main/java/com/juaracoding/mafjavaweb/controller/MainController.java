package com.juaracoding.mafjavaweb.controller;

import cn.apiclub.captcha.Captcha;
import com.juaracoding.mafjavaweb.model.Userz;
import com.juaracoding.mafjavaweb.utils.CaptchaUtils;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/")
public class MainController {
    @GetMapping("/")
    public String pageTwo(Model model)
    {
        Captcha captcha = CaptchaUtils.createCaptcha(150, 60);

//        UserDTO users = new UserDTO()O();
        Userz users = new Userz();
        users.setHidden(captcha.getAnswer());
        users.setCaptcha("");
        users.setImage(CaptchaUtils.encodeBase64(captcha));
        model.addAttribute("usr",users);
        return "authz_signin";
    }

}
