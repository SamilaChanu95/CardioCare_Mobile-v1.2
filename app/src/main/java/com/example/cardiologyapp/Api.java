package com.example.cardiologyapp;

public class Api {

    //private static final String ROOT_URL = "http://192.168.1.100/ConsultantApi/v1/Api.php?apicall=";
    private static final String ROOT_URL = "http://192.168.43.215/ConsultantApi/v1/Api.php?apicall=";
    //private static final String ROOT_URL = "http://10.0.2.2/ConsultantApi/v1/Api.php?apicall=";

    public static final String URL_CREATE_CONSULTANT = ROOT_URL + "createconsultant";
    public static final String URL_READ_CONSULTANT = ROOT_URL + "getconsultant";
    public static final String URL_UPDATE_CONSULTANT = ROOT_URL + "updateconsultant";
    public static final String URL_DELETE_CONSULTANT = ROOT_URL + "deleteconsultant&id=";
}
