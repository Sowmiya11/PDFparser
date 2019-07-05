package com.invoiceApplication.invoiceConstants;

public class DBConstants {

    //Driver Constants
    public static final String OracleDriver = "oracle.jdbc.driver.OracleDriver";
    public static final String Driver_URL = "jdbc:oracle:thin:@localhost:1521:xe";
    public static final String Driver_UserName = "system";
    public static final String Driver_Password = "root";

   //DB Operation Constants
   public static final String Select_InvoiceData="SELECT invoiceData.invoiceNumber, invoiceData.invoiceDate,invoiceAddressDB.soldTo,invoiceAddressDB.shipTo,invoiceAddressDB.remitTo, invoiceData.customerPO,invoiceData.totalAmount FROM invoiceData INNER JOIN invoiceAddressDB ON invoiceData.invoiceNumber=invoiceAddressDB.invoiceNumber";

   public static final String Update_Status="UPDATE invoiceData SET status='approved' where invoiceNumber=";

   public static final String Getting_Retuen_MailID="SELECT invoiceData.emailID FROM invoiceData where invoiceData.invoiceNumber=";
}
