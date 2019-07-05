package com.invoiceApplication;

import com.invoiceApplication.invoiceConstants.InvoiceConstant;
import com.invoiceApplication.invoiceConstants.POP3MailConstants;
import com.invoiceApplication.invoiceService.InvoiceServiceImpl;
import com.invoiceApplication.invoiceService.ReceiveEmailWithInvoiceAttachment;

import java.io.File;
import java.util.Scanner;

public class InvoiceApplication {
    public static void main(String[] args) throws Exception {
        String pop3Host = POP3MailConstants.POP3_GMAIL_HOST;
        String mailStoreType = POP3MailConstants.POP3;
        final String userName = InvoiceConstant.USER_NAME;
        final String password = InvoiceConstant.PASSWORD;

        InvoiceServiceImpl invoiceService = new InvoiceServiceImpl();

        //call receiveEmail
        File file = new ReceiveEmailWithInvoiceAttachment().receiveEmail(pop3Host, mailStoreType, userName, password);

        //list All data
        invoiceService.insertInvoiceDataToDB(file);

        System.out.println("******Status Updation******");
        System.out.println("Enter Invoice No:");
        Scanner scanner = new Scanner(System.in);
        String invoiceNo = scanner.nextLine();

        //Fetching the particular invoiceNo and Changing the status and replying the confirmation Mail
        invoiceService.statusUpdationForRequestedInvoiceNo(userName, password, invoiceNo);

    }

}
