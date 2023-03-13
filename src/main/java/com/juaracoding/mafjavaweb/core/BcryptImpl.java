package com.juaracoding.mafjavaweb.core;

import java.util.function.Function;

public class BcryptImpl {

    private static final BcryptCustom bcrypt = new BcryptCustom(11);

    public static String hash(String password) {
        return bcrypt.hash(password);
    }

    public static boolean verifyAndUpdateHash(String password, String hash, Function<String, Boolean> updateFunc) {
        return bcrypt.verifyAndUpdateHash(password, hash, updateFunc);
    }

    public static boolean verifyHash(String password , String hash)
    {
        return bcrypt.verifyHash(password,hash);
    }
    
    public static void main(String[] args) {
//        String[] mutableHash = new String[1];
//        Function<String, Boolean> update = hash -> { mutableHash[0] = hash; return true; };

        String strUserName = "Paulo";
        String strPwd = "Paul123";
        String strAfterEncrypt1 = hash(strUserName+strPwd);
        String strAfterEncrypt2 = hash(strPwd);
//        System.out.println("After Encrypt-1 : "+strAfterEncrypt1);
//        System.out.println("After Encrypt-2 : "+strAfterEncrypt2);

//        $2a$11$jT60LlDg4AH2IPpIT6uh8uNCnJQ5CSN6mtI2cbcLNnsQG9DIn/4GC --> Encrypt 1-1
//        $2a$11$MD1a8ZhAenYn0S7wu.OKkeDs7FX4tmu5oTZ0Ak5IUDQjI1MhTBFfq --> Encrypt 1-2
//        $2a$11$I5.3bj0UD7k6W8awV/LNvOlWeaeWrNjEx.QInrEedLUaa54sh9WIe --> Encrypt 1-3

//        $2a$11$iCGsQTIpe3O/bDMgEEHo6ehL4YQHpx8mqGSHvYzdCnso.ESvj9TA2 --> Encrypt 2-1
//        $2a$11$52hfsJP7mTntGlSEitXq3O.yvvZCrOSC6rsHEXDgqH0yH6er8LClW --> Encrypt 2-2
//        $2a$11$60JK7Mb2uZ4lgQjveXhNp.g6obq9wpobbiY93A/bw/lcseKhsu7oi --> Encrypt 2-3

        System.out.println(verifyHash(strUserName+strPwd,"$2a$11$jT60LlDg4AH2IPpIT6uh8uNCnJQ5CSN6mtI2cbcLNnsQG9DIn/4GC"));

//        strPwd = strUserName+strPwd;
//        String strAfterEncrypt = hash(strPwd);
//        System.out.println("After Encrypt : "+strAfterEncrypt);
//        $2a$11$LpIv7eoy6O93oig3PU8TxeJJphzB7lyJlD59tLvzEZPJ83K/JgZmK --> Encrypt 1
//        $2a$11$269MfMETohZocznxwjDqZ.cdbR9ga9maeLm6QsGoP4XUyOgYsViVy --> Encrypt 2
//        $2a$11$nU0UUYkG95Dw3EWkR2Bu6eTZPclxyopqRHsneLkxBF85NSG4qdEpm --> Encrypt 3
//$2a$07$oCaZyblkMXcR5KX7161iIuyQfcwfCPaHpPj9ZGFgb -> $2a$07$14GFHpIdM5L9FFQbzm0DMuP9CL9d12s6js9GU6Gw94yyikeWj
//        System.out.println(verifyHash(strPwd,"$2a$07$14GFHpIdM5L9FFQbzm0DMuP9CL9d12s6js9GU6Gw94yyikeWj.lKS"));
//        System.out.println(verifyHash(strPwd,"$2a$07$oCaZyblkMXcR5KX7161iIuyQfcwfCPaHpPj9ZGFgb/A2MJfE8vICq"));
//        System.out.println(verifyHash(strPwd,"$2a$07$y7C9mU/u4pffYW8gW0htCeUPcdW5AUwra4yz7IJ1E7//ycU3PICSW"));
//        System.out.println(verifyHash(strPwd,"$2a$11$LpIv7eoy6O93oig3PU8TxeJJphzB7lyJlD59tLvzEZPJ83K/JgZmK"));
//        System.out.println(verifyHash(strPwd,"$2a$11$269MfMETohZocznxwjDqZ.cdbR9ga9maeLm6QsGoP4XUyOgYsViVy"));
//        System.out.println(verifyHash(strPwd,"$2a$11$nU0UUYkG95Dw3EWkR2Bu6eTZPclxyopqRHsneLkxBF85NSG4qdEpm"));

//        System.out.println(verifyAndUpdateHash(strPwd,strAfterEncrypt,update));
//
//        System.out.println("After Encrypt : "+strAfterEncrypt);
//        System.out.println(verifyAndUpdateHash(strPwd,strAfterEncrypt,update));

//        System.out.println(hash(strPwd));
//        System.out.println(hash(strPwd));
//        System.out.println(hash(strPwd));
//        System.out.println(verifyAndUpdateHash(strPwd,"$2a$07$14GFHpIdM5L9FFQbzm0DMuP9CL9d12s6js9GU6Gw94yyikeWj.lKS",update));
//        System.out.println(verifyAndUpdateHash(strPwd,"$2a$07$oCaZyblkMXcR5KX7161iIuyQfcwfCPaHpPj9ZGFgb/A2MJfE8vICq",update));
//        System.out.println(verifyAndUpdateHash(strPwd,"$2a$07$y7C9mU/u4pffYW8gW0htCeUPcdW5AUwra4yz7IJ1E7//ycU3PICSW",update));
//        System.out.println(verifyAndUpdateHash(strPwd,"$2a$07$B7s.iWLTut2boPoCSvUoMOZbNsLsjPDU1EpXdWkJACNPl5eTCJU7e",update));

//        System.out.println(bcrypt.verifyHash(strPwd,"$2a$07$14GFHpIdM5L9FFQbzm0DMuP9CL9d12s6js9GU6Gw94yyikeWj.lKS"));
//        System.out.println(bcrypt.verifyHash(strPwd,"$2a$07$oCaZyblkMXcR5KX7161iIuyQfcwfCPaHpPj9ZGFgb/A2MJfE8vICq"));
//        System.out.println(bcrypt.verifyHash(strPwd,"$2a$07$y7C9mU/u4pffYW8gW0htCeUPcdW5AUwra4yz7IJ1E7//ycU3PICSW"));
//        System.out.println(bcrypt.verifyHash(strPwd,"$2a$07$B7s.iWLTut2boPoCSvUoMOZbNsLsjPDU1EpXdWkJACNPl5eTCJU7e"));
//        $2a$07$oCaZyblkMXcR5KX7161iIuyQfcwfCPaHpPj9ZGFgb/A2MJfE8vICq
//        $2a$07$y7C9mU/u4pffYW8gW0htCeUPcdW5AUwra4yz7IJ1E7//ycU3PICSW
//        $2a$07$B7s.iWLTut2boPoCSvUoMOZbNsLsjPDU1EpXdWkJACNPl5eTCJU7e
    }
}