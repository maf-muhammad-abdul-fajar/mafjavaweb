package com.juaracoding.mafjavaweb.service;


import com.juaracoding.mafjavaweb.configuration.OtherConfig;
import com.juaracoding.mafjavaweb.core.BcryptImpl;
import com.juaracoding.mafjavaweb.dto.ForgetPasswordDTO;
import com.juaracoding.mafjavaweb.handler.ResponseHandler;
import com.juaracoding.mafjavaweb.model.Userz;
import com.juaracoding.mafjavaweb.repo.UserRepo;
import com.juaracoding.mafjavaweb.utils.ConstantMessage;
import com.juaracoding.mafjavaweb.utils.LoggingFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Random;

@Service
@Transactional
public class UserService {

    private UserRepo userRepo;

    private String [] strExceptionArr = new String[2];

    @Autowired
    public UserService(UserRepo userService) {
        strExceptionArr[0] = "UserService";
        this.userRepo = userService;
    }

    public Map<String,Object> checkRegis(Userz userz, WebRequest request) {
        int intVerification = new Random().nextInt(100000,999999);
        List<Userz> listUserResult = userRepo.findByEmailOrNoHPOrUsername(userz.getEmail(),userz.getNoHP(),userz.getUsername());//INI VALIDASI USER IS EXISTS
        try
        {
            if(listUserResult.size()!=0)//kondisi mengecek apakah user terdaftar
            {
                Userz nextUser = listUserResult.get(0);
                if(nextUser.getIsDelete()!=0)//sudah terdaftar dan aktif
                {
                    //PEMBERITAHUAN SAAT REGISTRASI BAGIAN MANA YANG SUDAH TERDAFTAR (USERNAME, EMAIL ATAU NOHP)
                    if(nextUser.getEmail().equals(userz.getEmail()))
                    {
                        return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_EMAIL_ISEXIST,
                                HttpStatus.NOT_ACCEPTABLE,null,"FV01001",request);//EMAIL SUDAH TERDAFTAR DAN AKTIF
                    } else if (nextUser.getNoHP().equals(userz.getNoHP())) {//FV = FAILED VALIDATION
                        return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_NOHP_ISEXIST,
                                HttpStatus.NOT_ACCEPTABLE,null,"FV01002",request);//NO HP SUDAH TERDAFTAR DAN AKTIF
                    } else if (nextUser.getUsername().equals(userz.getUsername())) {
                        return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_USERNAME_ISEXIST,
                                HttpStatus.NOT_ACCEPTABLE,null,"FV01003",request);//USERNAME SUDAH TERDAFTAR DAN AKTIF
                    } else {
                        return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_USER_ISACTIVE,
                                HttpStatus.NOT_ACCEPTABLE,null,"FV01004",request);//KARENA YANG DIAMBIL DATA YANG PERTAMA JADI ANGGAPAN NYA SUDAH TERDAFTAR SAJA
                    }
                }
                else
                {
                    nextUser.setPassword(BcryptImpl.hash(userz.getPassword()));
                    nextUser.setToken(BcryptImpl.hash(String.valueOf(intVerification)));
                    nextUser.setTokenCounter(nextUser.getTokenCounter()+1);//setiap kali mencoba ditambah 1
                    nextUser.setModifiedBy(Integer.parseInt(nextUser.getIdUser().toString()));
                    nextUser.setModifiedDate(new Date());
                }
            }
            else//belum terdaftar
            {
                userz.setPassword(BcryptImpl.hash(userz.getPassword()));
                userz.setToken(BcryptImpl.hash(String.valueOf(intVerification)));
                userRepo.save(userz);
            }
//            String [] strEmail = {userz.getEmail()};
//            SMTPCore sc = new SMTPCore();
//            ConfigProperties.getEmailPassword();
//            System.out.println(sc.sendMailWithAttachment(strEmail,
//                    "DEMO REGISTRATION -- TOKEN : "+intVerification,
//                    new ReadTextFileSB("\\data\\template-BCAF.html").getContentFile(),
//                    "SSL",
//                    new String[] {ResourceUtils.getFile("classpath:\\data\\sample.docx").getAbsolutePath()}));
            System.out.println("VERIFIKASI -> "+intVerification);
        }catch (Exception e)
        {
            strExceptionArr[1]="checkRegis(Userz userz) --- LINE 70";
            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_REGIS_FAILED,
                    HttpStatus.NOT_FOUND,null,"FE01001",request);
        }

        return new ResponseHandler().generateModelAttribut(ConstantMessage.SUCCESS_CHECK_REGIS,
                HttpStatus.CREATED,null,null,request);
    }

    public Map<String,Object> confirmRegis(Userz userz, String emails, WebRequest request) {
        List<Userz> listUserResult = userRepo.findByEmail(emails);
        try
        {
            if(listUserResult.size()!=0)
            {
                Userz nextUser = listUserResult.get(0);
                if(!BcryptImpl.verifyHash(userz.getToken(),nextUser.getToken()))
                {
                    return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_TOKEN_NOT_VALID,
                            HttpStatus.NOT_ACCEPTABLE,null,"FV01005",request);
                }
                nextUser.setIsDelete((byte) 1);//SET REGISTRASI BERHASIL
            }
            else
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_USER_NOT_EXISTS,
                    HttpStatus.NOT_FOUND,null,"FV01006",request);
            }
        }
        catch (Exception e)
        {
            strExceptionArr[1]="confirmRegis(Userz userz)  --- LINE 103";
            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_REGIS_FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"FE01002",request);
        }

        return new ResponseHandler().generateModelAttribut(ConstantMessage.SUCCESS_CHECK_REGIS,
                HttpStatus.OK,null,null,request);
    }
    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> doLogin(Userz userz, WebRequest request) {
        userz.setUsername(userz.getEmail());
        userz.setNoHP(userz.getNoHP());
        List<Userz> listUserResult = userRepo.findByEmailOrNoHPOrUsername(userz.getEmail(),userz.getNoHP(),userz.getUsername());//DATANYA PASTI HANYA 1
        try
        {
            if(listUserResult.size()!=0)
            {
                Userz nextUser = listUserResult.get(0);
                if(!BcryptImpl.verifyHash(userz.getPassword(),nextUser.getPassword()))
                {
                    return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_LOGIN_FAILED,
                            HttpStatus.NOT_ACCEPTABLE,null,"FV01007",request);
                }
                nextUser.setLastLoginDate(new Date());
            }
            else
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_USER_NOT_EXISTS,
                        HttpStatus.NOT_ACCEPTABLE,null,"FV01008",request);
            }
        }
        catch (Exception e)
        {
            strExceptionArr[1]="doLogin(Userz userz,WebRequest request)  --- LINE 132";
            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_LOGIN_FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"FE01003",request);
        }

        return new ResponseHandler().generateModelAttribut(ConstantMessage.SUCCESS_LOGIN,
                HttpStatus.OK,null,null,request);
    }

    public Map<String,Object> getNewToken(String emailz, WebRequest request) {
        List<Userz> listUserResult = userRepo.findByEmail(emailz);//DATANYA PASTI HANYA 1
        try
        {
            if(listUserResult.size()!=0)
            {
                int intVerification = new Random().nextInt(100000,999999);
                Userz userz = listUserResult.get(0);
                userz.setToken(BcryptImpl.hash(String.valueOf(intVerification)));
                userz.setModifiedDate(new Date());
                userz.setModifiedBy(Integer.parseInt(userz.getIdUser().toString()));
                System.out.println("New Token -> "+intVerification);
            }
            else
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_NOT_VALID,
                        HttpStatus.NOT_ACCEPTABLE,null,"FV01009",request);
            }
        }
        catch (Exception e)
        {
            strExceptionArr[1]="getNewToken(String emailz, WebRequest request)  --- LINE 185";
            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_NOT_VALID,
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"FE01004",request);
        }
        return new ResponseHandler().generateModelAttribut(ConstantMessage.SUCCESS_LOGIN,
                HttpStatus.OK,null,null,request);
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> sendMailForgetPwd(String email,WebRequest request)
    {

        List<Userz> listUserResults = userRepo.findByEmail(email);
        try
        {
            if(listUserResults.size()==0)
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_USER_NOT_EXISTS,
                        HttpStatus.NOT_FOUND,null,"FV01010",request);
            }
            int intVerification = new Random().nextInt(100000,999999);
            Userz userz = listUserResults.get(0);
            userz.setToken(BcryptImpl.hash(String.valueOf(intVerification)));
            userz.setModifiedDate(new Date());
            userz.setModifiedBy(Integer.parseInt(userz.getIdUser().toString()));
            System.out.println("New Forget Password Token -> "+intVerification);
        }
        catch (Exception e)
        {
            strExceptionArr[1]="sendMailForgetPwd(String email)  --- LINE 214";
            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_REGIS_FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"FE01005",request);
        }
        return new ResponseHandler().generateModelAttribut(ConstantMessage.SUCCESS_SEND_NEW_TOKEN,
                HttpStatus.OK,null,null,request);
    }

    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> confirmTokenForgotPwd(ForgetPasswordDTO forgetPasswordDTO, WebRequest request)
    {
        String emailz = forgetPasswordDTO.getEmail();
        String token = forgetPasswordDTO.getToken();

        List<Userz> listUserResults = userRepo.findByEmail(emailz);
        try
        {
            if(listUserResults.size()==0)
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_USER_NOT_EXISTS,
                        HttpStatus.NOT_FOUND,null,"FV01011",request);
            }

            Userz userz = listUserResults.get(0);

            if(!BcryptImpl.verifyHash(token,userz.getToken()))//VALIDASI TOKEN
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_TOKEN_FORGOTPWD_NOT_SAME,
                        HttpStatus.NOT_FOUND,null,"FV01012",request);
            }
        }
        catch (Exception e)
        {
            strExceptionArr[1]="confirmTokenForgotPwd(ForgetPasswordDTO forgetPasswordDTO, WebRequest request)  --- LINE 250";
            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_NOT_VALID,
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"FE01006",request);
        }
        return new ResponseHandler().generateModelAttribut(ConstantMessage.SUCCESS_TOKEN_MATCH,
                HttpStatus.OK,null,null,request);
    }


    @Transactional(rollbackFor = Exception.class)
    public Map<String,Object> confirmPasswordChange(ForgetPasswordDTO forgetPasswordDTO, WebRequest request)
    {
        String emailz = forgetPasswordDTO.getEmail();
        String newPassword = forgetPasswordDTO.getNewPassword();
        String oldPassword = forgetPasswordDTO.getOldPassword();
        String confirmPassword = forgetPasswordDTO.getConfirmPassword();

        List<Userz> listUserResults = userRepo.findByEmail(emailz);
        try
        {
            if(listUserResults.size()==0)
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_FLOW_NOT_VALID,
                        HttpStatus.NOT_FOUND,null,"FV01012",request);
            }

            Userz userz = listUserResults.get(0);
            if(!BcryptImpl.verifyHash(oldPassword,userz.getPassword()))//kalau password lama tidak sama dengan yang diinput
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_PASSWORD_NOT_SAME,
                        HttpStatus.NOT_FOUND,null,"FV01013",request);
            }
            if(oldPassword.equals(newPassword))//PASSWORD BARU SAMA DENGAN PASSWORD LAMA
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_PASSWORD_IS_SAME,
                        HttpStatus.NOT_FOUND,null,"FV01014",request);
            }
            if(!confirmPassword.equals(newPassword))//PASSWORD BARU DENGAN PASSWORD KONFIRMASI TIDAK SAMA
            {
                return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_PASSWORD_CONFIRM_FAILED,
                        HttpStatus.NOT_FOUND,null,"FV01014",request);
            }


            userz.setPassword(BcryptImpl.hash(String.valueOf(newPassword)));
            userz.setModifiedDate(new Date());
            userz.setModifiedBy(Integer.parseInt(userz.getIdUser().toString()));
            System.out.println("New Forget Password -> "+newPassword);
        }
        catch (Exception e)
        {
            strExceptionArr[1]="confirmPasswordChange(ForgetPasswordDTO forgetPasswordDTO, WebRequest request)  --- LINE 297";
            LoggingFile.exceptionStringz(strExceptionArr,e, OtherConfig.getFlagLogging());
            return new ResponseHandler().generateModelAttribut(ConstantMessage.ERROR_REGIS_FAILED,
                    HttpStatus.INTERNAL_SERVER_ERROR,null,"FE01006",request);
        }
        return new ResponseHandler().generateModelAttribut(ConstantMessage.SUCCESS_CHANGE_PWD,
                HttpStatus.OK,null,null,request);
    }


}
