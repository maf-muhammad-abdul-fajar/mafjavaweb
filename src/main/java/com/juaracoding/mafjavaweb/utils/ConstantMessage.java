package com.juaracoding.mafjavaweb.utils;

public class ConstantMessage {

    /*
    Memperbolehkan nilai numerik dari 0 hingga 9.
    Memperbolehkan Huruf besar dan huruf kecil dari a sampai z.
    Yang diperbolehkan hanya garis bawah “_”, tanda hubung “-“ dan titik “.”
    Titik tidak diperbolehkan di awal dan akhir local part (sebelum tanda @).
    Titik berurutan tidak diperbolehkan.
    Local part, maksimal 64 karakter.
     */
//    public final static String REGEX_EMAIL_STRICT = "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\\\.[A-Za-z0-9_-]+)*@[^-][A-Za-z0-9-]+(\\\\.[A-Za-z0-9-]+)*(\\\\.[A-Za-z]{2,})$";

    /*CONTENT TYPE*/
    public final static String CONTENT_TYPE_CSV = "text/csv";
    public final static String CONTENT_TYPE_XLS = "application/vnd.ms-excel";
    public final static String CONTENT_TYPE_XLSX = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";

    /*REGEX*/
    public final static String REGEX_PHONE = "^[\\+]?[(]?[0-9]{3}[)]?[-\\s\\.]?[0-9]{3}[-\\s\\.]?[0-9]{4,6}$";

    /*
    * Tidak memperbolehkan tanda | (pipa) dan ' (petik 1)
    */
    public final static String REGEX_EMAIL_STANDARD_RFC5322  = "^[a-zA-Z0-9_!#$%&'*+/=?`{|}~^.-]+@[a-zA-Z0-9.-]+$";
    public final static String REGEX_DATE_YYYYMMDD  = "^([0-9][0-9])?[0-9][0-9]-(0[0-9]||1[0-2])-([0-2][0-9]||3[0-1])$";

    public final static String REGEX_DATE_DDMMYYYY  = "^([0-2][0-9]||3[0-1])-(0[0-9]||1[0-2])-([0-9][0-9])?[0-9][0-9]$";

    /*Global*/
    public final static String SUCCESS_SAVE = "DATA BERHASIL DIBUAT";
    public final static String SUCCESS_UPDATE = "DATA BERHASIL DIPERBAHARUI";
    public final static String ERROR_UPLOAD_CSV = "UPLOAD FILE GAGAL ";
    public final static String ERROR_EMPTY_FILE = "FILE KOSONG , TIDAK ADA DATA !! ";
    public final static String ERROR_UPDATE_BY_ID = "UPDATE GAGAL, DATA TIDAK DITEMUKAN";
    public final static String ERROR_NOT_CSV_FILE = "FORMAT FILE HARUS CSV ";
    public final static String ERROR_NOT_EXCEL_FILE = "FORMAT FILE HARUS .XLS ATAU .XLSX ";
    public final static String SUCCESS_SEND_EMAIL = "SILAHKAN CEK EMAIL YANG TELAH ANDA DAFTARKAN";
    public final static String SUCCESS_FIND_BY = "OK";
    public final static String SUCCESS_TRANSFER = "TRANSFER BERHASIL";
    public final static String WARNING_NOT_FOUND = "DATA TIDAK DITEMUKAN";
    public final static String WARNING_DATA_EMPTY = "DATA TIDAK ADA";

    public final static String ERROR_DATA_INVALID = "DATA TIDAK VALID";
    public final static String ERROR_SAVE_FAILED = "DATA GAGAL DISIMPAN";
    public final static String ERROR_CH_PWD_FAILED = "GANTI PASSWORD GAGAL";
    public final static String ERROR_INTERNAL_SERVER = "INTERNAL SERVER ERROR";
    public final static String ERROR_MAIL_FORM_JSON = "Malformed JSON request";
    public final static String ERROR_EMAIL_FORMAT_INVALID = "FORMAT EMAIL TIDAK SESUAI (Nomor/Karakter@Nomor/Karakter Tanpa tanda | atau ')";
    public final static String ERROR_PHONE_NUMBER_FORMAT_INVALID = "FORMAT NOMOR HANDPHONE TIDAK SESUAI (+628XX-xxx) ex : +62813-24";
    public final static String ERROR_DATE_FORMAT_YYYYMMDD = "FORMAT TANGGAL TIDAK SESUAI (YYYY-mm-dd)";
//    public final static String ERROR_DATE_FORMAT_DDMMYYYY = "FORMAT TANGGAL TIDAK SESUAI (dd-MM-YYYY)";
    public final static String ERROR_DATE_FORMAT_DDMMYYYY = "FORMAT TANGGAL TIDAK SESUAI (Tanggal(dd)-Bulan(MM)-Tahun(YYYY)) contoh : 01-01-2001";
    public final static String ERROR_UNEXPECTED = "UNEXPECTED ERROR";
    public final static String ERROR_UNPROCCESSABLE = "Validation error. Check 'errors' field for details.";
    public final static String ERROR_NO_CONTENT = "PERMINTAAN TIDAK DAPAT DIPROSES";

    /*
        AUTH
     */

    public final static String ERROR_REGIS_FAILED = "REGISTRASI GAGAL";
    public final static String ERROR_TOKEN_NOT_VALID = "TOKEN TIDAK SESUAI";
    public final static String SUCCESS_CHECK_REGIS = "PROSES REGISTRASI AMAN";
    public final static String SUCCESS_LOGIN = "LOGIN BERHASIL";
    public final static String SUCCESS_SEND_NEW_TOKEN = "TOKEN BARU BERHASIL DIKIRIM KE EMAIL";
    public final static String SUCCESS_CHANGE_PWD = "PASSWORD BERHASIL DIUBAH";
    public final static String SUCCESS_TOKEN_MATCH = "TOKEN SUDAH SESUAI";
    public final static String ERROR_EMAIL_ISEXIST = "REGISTRASI GAGAL! EMAIL SUDAH TERDAFTAR";
    public final static String ERROR_USER_ISACTIVE = "REGISTRASI GAGAL! EMAIL SUDAH TERDAFTAR";
    public final static String ERROR_NOHP_ISEXIST = "REGISTRASI GAGAL! NO HP SUDAH TERDAFTAR";
    public final static String ERROR_USERNAME_ISEXIST = "REGISTRASI GAGAL! USERNAME SUDAH TERDAFTAR";
    public final static String ERROR_EMAIL_MAX_MIN_LENGTH = "PANJANG EMAIL MIN 15 MAKS 50 !!";
    public final static String ERROR_EMAIL_IS_NULL = "EMAIL TIDAK BOLEH NULL!!";
    public final static String ERROR_EMAIL_IS_EMPTY = "EMAIL TIDAK BOLEH EMPTY!!";
    public final static String ERROR_USER_NOT_EXISTS = "USER TIDAK TERDAFTAR / USERNAME DAN PASSWORD SALAH !!";
    public final static String ERROR_LOGIN_FAILED = "USERNAME DAN PASSWORD SALAH !!";
    public final static String ERROR_FLOW_NOT_VALID = "PROSES TIDAK SESUAI DENGAN PROSEDUR";

    public final static String ERROR_USRNAME_MAX_MIN_LENGTH = "USERNAME MIN 10 MAKS 30  KARAKTER!!";
    public final static String ERROR_USRNAME_IS_NULL = "USERNAME TIDAK BOLEH NULL!!";
    public final static String ERROR_USRNAME_IS_EMPTY = "USERNAME TIDAK BOLEH KOSONG!!";
    public final static String ERROR_PASSWORD_MAX_MIN_LENGTH = "PASSWORD MIN 8 MAKS 25  KARAKTER!!";
    public final static String ERROR_PASSWORD_IS_NULL = "PASSWORD TIDAK BOLEH NULL!!";
    public final static String ERROR_PASSWORD_IS_EMPTY = "PASSWORD TIDAK BOLEH KOSONG!!";
    public final static String ERROR_NAMALENGKAP_MAX_MIN_LENGTH = "NAMA LENGKAP MIN 8 MAKS 40  KARAKTER!!";
    public final static String ERROR_NAMALENGKAP_IS_NULL = "NAMA LENGKAP TIDAK BOLEH NULL!!";
    public final static String ERROR_NAMALENGKAP_IS_EMPTY = "NAMA LENGKAP TIDAK BOLEH KOSONG!!";

    public final static String ERROR_NOHP_MAX_MIN_LENGTH = "NO HANDPHONE MIN 11 MAKS 18  KARAKTER!!";
    public final static String ERROR_NOHP_IS_NULL = "NO HANDPHONE TIDAK BOLEH NULL!!";
    public final static String ERROR_NOHP_IS_EMPTY = "NO HANDPHONE TIDAK BOLEH KOSONG!!";
    public final static String ERROR_TOKEN_IS_EMPTY = "TOKEN TIDAK BOLEH KOSONG!!";
    public final static String ERROR_TOKEN_IS_NULL = "TOKEN TIDAK BOLEH BERNILAI NULL!!";

    public final static String ERROR_NEW_PASSWORD_MAX_MIN_LENGTH = "PASSWORD BARU MIN 8 MAKS 25  KARAKTER!!";
    public final static String ERROR_NEW_PASSWORD_IS_NULL = "PASSWORD BARU TIDAK BOLEH NULL!!";
    public final static String ERROR_NEW_PASSWORD_IS_EMPTY = "PASSWORD BARU TIDAK BOLEH KOSONG!!";

    public final static String ERROR_CONFIRM_PASSWORD_MAX_MIN_LENGTH = "KONFIRMASI PASSWORD BARU MIN 8 MAKS 25  KARAKTER!!";
    public final static String ERROR_CONFIRM_PASSWORD_IS_NULL = "KONFIRMASI PASSWORD BARU TIDAK BOLEH NULL!!";
    public final static String ERROR_CONFIRM_PASSWORD_IS_EMPTY = "KONFIRMASI PASSWORD BARU TIDAK BOLEH KOSONG!!";

    public final static String ERROR_PASSWORD_NOT_SAME = "PASSWORD LAMA SALAH !!";
    public final static String ERROR_TOKEN_FORGOTPWD_NOT_SAME = "TOKEN SALAH !!";
    public final static String ERROR_PASSWORD_CONFIRM_FAILED = "PASSWORD KONFIRMASI TIDAK COCOK DENGAN PASSWORD BARU !!";
    public final static String ERROR_PASSWORD_IS_SAME = "PASSWORD BARU TIDAK BOLEH SAMA DENGAN PASSWORD LAMA !!";


//    Category Article

    public final static String WARNING_CATPROD_MAX_LENGTH_NAME  = "MAKSIMAL NAMA KATEGORI PRODUK ADALAH 40 KARAKTER";
    public final static String WARNING_CATPROD_NAME_CANNOT_EMPTY  = "NAMA KATEGORI PRODUK TIDAK BOLEH KOSONG";
    public final static String WARNING_CATPROD_DESC_CANNOT_EMPTY  = "DESKRIPSI KATEGORI PRODUK TIDAK BOLEH KOSONG";
    public final static String WARNING_CATPROD_MAX_LENGTH_DESC  = "MAKSIMAL DESKRIPSI KATEGORI PRODUK ADALAH 500 KARAKTER !";

    public final static String WARNING_TUGAS_A_MAX_LENGTH_NAME = "MAKSIMAL NAMA ADALAH 40 KARAKTER";
    public final static String WARNING_TUGAS_A_NAME_CANNOT_EMPTY = "NAMA TIDAK BOLEH KOSONG";
    public final static String WARNING_TUGAS_A_MAX_LENGTH_ALAMAT = "MAKKSIMAL ALAMAT ADALAH 500 KARAKTER";
    public final static String WARNING_TUGAS_A_ALAMAT_CANNOT_EMPTY = "ALAMAT TIDAK BOLEH KOSONG";






}
