package com.example.cardiologyapp;

public class Api {

    //private static final String ROOT_URL  = "http://192.168.1.100/CardioCare/";
    private static final String ROOT_URL = "http://192.168.43.215/CardioCare/";
    //private static final String ROOT_URL = "http://10.0.2.2/CardioCare/";

    public static final String URL_READ_CONSULTANT = ROOT_URL  + "ConsultantApi/v1/Api.php?apicall=getconsultant";
    public static final String URL_READ_DOCTOR = ROOT_URL + "DoctorApi/v1/Api.php?apicall=getdoctor";
    public static final String URL_READ_NURSE = ROOT_URL  + "NurseApi/v1/Api.php?apicall=getnurse";
    public static final String URL_READ_TECHNICIAN = ROOT_URL  + "TechnicianApi/v1/Api.php?apicall=gettechnician";
    public static final String URL_READ_PATIENT = ROOT_URL  + "PatientApi/v1/Api.php?apicall=getpatient";
    public static final String URL_READ_ICUREPORT = ROOT_URL + "ICUReportApi/v1/Api.php?apicall=geticureport";

}
