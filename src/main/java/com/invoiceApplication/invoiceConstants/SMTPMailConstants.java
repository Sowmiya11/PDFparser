package com.invoiceApplication.invoiceConstants;

public class SMTPMailConstants {

    //SMTP Host
    public static String SMTP_MAIL_HOST = "mail.smtp.host";
    public static String SMTP_MAIL_HOST_ID = "smtp.gmail.com";

    //TLS Port
    public static String SMTP_MAIL_PORT = "mail.smtp.port";
    public static String SMTP_PORT_NO = "587";

    //enable authentication
    public static String MAIL_AUTHORITY = "mail.smtp.auth";
    public static String ENABLE_TRUE = "true";

    //enable STARTTLS
    public static String ENABLE_STARTTLS = "mail.smtp.starttls.enable";

    //Message Values
    public static String SEND_SUCCESSFULL="message sent successfully...";
    public static String INVALID_NO_FOUND="Invalid invoice Number Found";
}
