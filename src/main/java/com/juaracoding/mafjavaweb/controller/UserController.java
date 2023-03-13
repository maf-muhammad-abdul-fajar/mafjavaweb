package com.juaracoding.mafjavaweb.controller;

import com.juaracoding.mafjavaweb.dto.ForgetPasswordDTO;
import com.juaracoding.mafjavaweb.dto.UserDTO;
import com.juaracoding.mafjavaweb.handler.FormatValidation;
import com.juaracoding.mafjavaweb.model.Userz;
import com.juaracoding.mafjavaweb.service.UserService;
import com.juaracoding.mafjavaweb.utils.ConstantMessage;
import com.juaracoding.mafjavaweb.utils.MappingAttribute;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api/authz")
public class UserController {

    private UserService userService;

    @Autowired
    private ModelMapper modelMapper;
    private Map<String,Object> objectMapper = new HashMap<String,Object>();

    private List<Userz> lsCPUpload = new ArrayList<Userz>();

    private String [] strExceptionArr = new String[2];

    private MappingAttribute mappingAttribute = new MappingAttribute();

    @Autowired
    public UserController(UserService userService) {
        strExceptionArr[0]="UserController";
        this.userService = userService;
    }

    @PostMapping("/v1/register")
    public String doRegis(@ModelAttribute("usr")
                          @Valid UserDTO userz
                          ,BindingResult bindingResult
                          ,Model model
                          ,WebRequest request
    )
    {
        /* START VALIDATION */
        if(bindingResult.hasErrors())
        {
            model.addAttribute("usr",userz);
            return "authz_register";
        }
        Boolean isValid = true;
        if(!FormatValidation.phoneNumberFormatValidation(userz.getNoHP(),null))
        {
            isValid = false;
            mappingAttribute.setErrorMessage(bindingResult, ConstantMessage.ERROR_PHONE_NUMBER_FORMAT_INVALID);
        }
//        if(!FormatValidation.dateFormatYYYYMMDDValidation(userz.getTanggalLahir().toString(),null))
//        {
//            isValid = false;
//            mappingAttribute.setErrorMessage(bindingResult, ConstantMessage.ERROR_DATE_FORMAT_YYYYMMDD);
//        }
        if(!FormatValidation.emailFormatValidation(userz.getEmail(),null))
        {
            isValid = false;
            mappingAttribute.setErrorMessage(bindingResult, ConstantMessage.ERROR_EMAIL_FORMAT_INVALID);
        }
        if(!isValid)
        {
            model.addAttribute("users",userz);
            return "authz_register";
        }
        /* END OF VALIDATION */

        Userz users = modelMapper.map(userz, new TypeToken<Userz>() {}.getType());
        objectMapper = userService.checkRegis(users,request);

        if((Boolean) objectMapper.get("success"))
        {
            mappingAttribute.setAttribute(model,objectMapper);
            model.addAttribute("verifyEmail",userz.getEmail());
//            model.addAttribute("sorting","contohSorting");
//            model.addAttribute("paging","contohPaging");
            model.addAttribute("users",new Userz());

            return "authz_verifikasi";
        }
        else
        {
            mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
            model.addAttribute("users",users);
            return "authz_register";
        }
    }

    @GetMapping("/v1/taketoken")
    public String requestTokenz(@ModelAttribute("usr")
                                    @Valid UserDTO userz,
                                    BindingResult bindingResult,Model model,@RequestParam String email, WebRequest request)
    {
        if(email == null || email.equals(""))
        {
            mappingAttribute.setErrorMessage(bindingResult,ConstantMessage.ERROR_REGIS_FAILED);
            return "authz_signin";
        }

        objectMapper = userService.getNewToken(email,request);
        Boolean isSuccess = (Boolean) objectMapper.get("success");
        if(isSuccess)
        {
            model.addAttribute("verifyEmail",email);
            model.addAttribute("usr",new UserDTO());
            mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
            return "authz_verifikasi";
        }
        else
        {
            model.addAttribute("usr",new UserDTO());
            return "authz_signin";
        }
    }

    @PostMapping("/v1/verify")
    public String verifyRegis(@ModelAttribute("usr")
                              @Valid Userz userz,
                              BindingResult bindingResult,
                              Model model,
                              @RequestParam String email,
                              WebRequest request)
    {
        //tidak ada bindingResult karena tidak memerlukan validasi di masing-masing field

        String verToken = userz.getToken();
        int lengthToken = verToken.length();
        if(email== null || email.equals(""))
        {
            mappingAttribute.setErrorMessage(bindingResult,ConstantMessage.ERROR_FLOW_NOT_VALID);
            model.addAttribute("verifyEmail",email);
            model.addAttribute("usr",new UserDTO());
            return "authz_signin";
        }
        if(verToken.equals(""))//token tidak boleh kosong
        {
            mappingAttribute.setErrorMessage(bindingResult,ConstantMessage.ERROR_TOKEN_IS_EMPTY);
            model.addAttribute("verifyEmail",email);
            return "authz_verifikasi";
        }
        else if(lengthToken!=6)//token HARUS 6 DIGIT
        {
            mappingAttribute.setErrorMessage(bindingResult,ConstantMessage.ERROR_TOKEN_NOT_VALID);
            model.addAttribute("verifyEmail",email);
            return "authz_verifikasi";
        }

        objectMapper = userService.confirmRegis(userz,email,request);

        if((Boolean) objectMapper.get("success"))
        {
            mappingAttribute.setErrorMessage(bindingResult,"REGISTRASI BERHASIL SILAHKAN LOGIN");
            model.addAttribute("users",new Userz());//agar field kosong
            return "authz_signin";
        }
        else
        {
            model.addAttribute("verifyEmail",email);
            mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
            return "authz_verifikasi";
        }
    }

    @PostMapping("/v1/login")
    public String login(@ModelAttribute("usr")
                        @Valid Userz userz,
                        BindingResult bindingResult,
                        Model model,
                        WebRequest request)
    {
        if(bindingResult.hasErrors())
        {
            return "authz_signin";
        }

        objectMapper = userService.doLogin(userz,request);
        Boolean isSuccess = (Boolean) objectMapper.get("success");
        if(isSuccess)
        {
            mappingAttribute.setAttribute(model,objectMapper);
            model.addAttribute("verifyEmail",userz.getEmail());
            return "index_1";
        }
        else
        {
            mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
            return "authz_signin";
        }
    }

    @PostMapping("/v1/forgetpwd")
    public String sendMailForgetPwd(@ModelAttribute("forgetpwd")
                                    @Valid ForgetPasswordDTO forgetPasswordDTO,
                                    BindingResult bindingResult
                                    ,Model model
                                    ,WebRequest request

    )
    {
        String emailz = forgetPasswordDTO.getEmail();
        if(emailz== null || emailz.equals(""))
        {
            mappingAttribute.setErrorMessage(bindingResult,ConstantMessage.ERROR_EMAIL_IS_EMPTY);
            return "authz_forget_pwd_email";
        }
        if(!FormatValidation.emailFormatValidation(emailz,null))
        {
            mappingAttribute.setErrorMessage(bindingResult, ConstantMessage.ERROR_EMAIL_FORMAT_INVALID);
            return "authz_forget_pwd_email";
        }

        objectMapper = userService.sendMailForgetPwd(emailz,request);
        Boolean isSuccess = (Boolean) objectMapper.get("success");
        ForgetPasswordDTO nextForgetPasswordDTO = new ForgetPasswordDTO();
        if(isSuccess)
        {
            mappingAttribute.setAttribute(model,objectMapper);
            nextForgetPasswordDTO.setEmail(emailz);
            model.addAttribute("forgetpwd",nextForgetPasswordDTO);
            return "authz_forget_pwd_verifikasi";
        }
        else
        {
            model.addAttribute("forgetpwd",nextForgetPasswordDTO);
            mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
            return "authz_forget_pwd_email";
        }

    }

    @PostMapping("/v1/vertokenfp")
    public String verifyTokenForgetPwd(@ModelAttribute("forgetpwd")
                                           @Valid ForgetPasswordDTO forgetPasswordDTO,
                                       BindingResult bindingResult
                                        ,Model model
                                        ,WebRequest request
    )
    {

        String emailz = forgetPasswordDTO.getEmail();
        String tokenz = forgetPasswordDTO.getToken();
        int intTokenLength = tokenz.length();
        Boolean isValid = true;

        /*START VALIDATION*/
        if(intTokenLength!=6)
        {
            mappingAttribute.setErrorMessage(bindingResult,ConstantMessage.ERROR_TOKEN_NOT_VALID);
            isValid = false;
        }
        if(tokenz==null || tokenz.equals(""))
        {
            mappingAttribute.setErrorMessage(bindingResult,ConstantMessage.ERROR_TOKEN_IS_EMPTY);
            isValid = false;
        }
        if(emailz== null || emailz.equals(""))
        {
            mappingAttribute.setErrorMessage(bindingResult,ConstantMessage.ERROR_FLOW_NOT_VALID);
            isValid = false;
        }
        if(!FormatValidation.emailFormatValidation(emailz,null))
        {
            mappingAttribute.setErrorMessage(bindingResult, ConstantMessage.ERROR_FLOW_NOT_VALID+" -- "+ConstantMessage.ERROR_EMAIL_FORMAT_INVALID);
            isValid = false;
        }

        if(!isValid)
        {
            model.addAttribute("forgetpwd",forgetPasswordDTO);
            return "authz_forget_pwd_verifikasi";
        }/*END OF VALIDATION*/

        objectMapper = userService.confirmTokenForgotPwd(forgetPasswordDTO,request);
        Boolean isSuccess = (Boolean) objectMapper.get("success");
        if(isSuccess)
        {
            ForgetPasswordDTO nextForgetPasswordDTO = new ForgetPasswordDTO();
            mappingAttribute.setAttribute(model,objectMapper);
            nextForgetPasswordDTO.setEmail(emailz);
            model.addAttribute("forgetpwd",nextForgetPasswordDTO);
            return "authz_forget_password";
        }
        else
        {
            model.addAttribute("forgetpwd",forgetPasswordDTO);
            mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
            return "authz_forget_pwd_verifikasi";
        }

    }

    @PostMapping("/v1/cfpwd")
    public String verifyForgetPwd(@ModelAttribute("forgetpwd")
                                   @Valid ForgetPasswordDTO forgetPasswordDTO,
                                   BindingResult bindingResult
                                   ,Model model
                                  ,WebRequest request
    )
    {
        if(bindingResult.hasErrors())
        {
            model.addAttribute("forgetpwd",forgetPasswordDTO);
            return "authz_forget_password";
        }

        String emailz = forgetPasswordDTO.getEmail();

        if(emailz== null || emailz.equals(""))
        {
            mappingAttribute.setErrorMessage(bindingResult,ConstantMessage.ERROR_FLOW_NOT_VALID);
            model.addAttribute("forgetpwd",forgetPasswordDTO);
            return "authz_forget_password";
        }

        objectMapper = userService.confirmPasswordChange(forgetPasswordDTO,request);
        Boolean isSuccess = (Boolean) objectMapper.get("success");

        if(isSuccess)
        {
            mappingAttribute.setAttribute(model,objectMapper);
            model.addAttribute("usr",new UserDTO());
            return "authz_signin";
        }
        else
        {
            model.addAttribute("forgetpwd",forgetPasswordDTO);
            mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
            return "authz_forget_password";
        }
    }

    @GetMapping("/v1/ntverfp")
    public String requestTokenzForgetPwd(@ModelAttribute("forgetpwd")
                                @Valid ForgetPasswordDTO forgetPasswordDTO,
                                BindingResult bindingResult,
                                 Model model,
                                 @RequestParam String emailz,
                                 WebRequest request)
    {
        forgetPasswordDTO.setToken("");//DIKOSONGKAN UNTUK MENGHILANGKAN INPUTAN USER DI FIELD TOKEN
        forgetPasswordDTO.setEmail(emailz);

        String email = forgetPasswordDTO.getEmail();

        if(email == null || email.equals(""))
        {
            mappingAttribute.setErrorMessage(bindingResult,ConstantMessage.ERROR_FLOW_NOT_VALID);
            return "authz_forget_pwd_verifikasi";
        }

        objectMapper = userService.getNewToken(email,request);
        Boolean isSuccess = (Boolean) objectMapper.get("success");
        if(isSuccess)
        {
            model.addAttribute("forgetPwd",forgetPasswordDTO);
            mappingAttribute.setAttribute(model,objectMapper);
            return "authz_forget_pwd_verifikasi";
        }
        else
        {
            mappingAttribute.setErrorMessage(bindingResult,objectMapper.get("message").toString());
            model.addAttribute("forgetPwd",forgetPasswordDTO);
            return "authz_signin";
        }
    }
}