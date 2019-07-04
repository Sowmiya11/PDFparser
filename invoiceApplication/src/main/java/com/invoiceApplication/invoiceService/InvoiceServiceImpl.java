package com.invoiceApplication.invoiceService;


import com.invoiceApplication.invoiceConstants.InvoiceConstant;
import com.invoiceApplication.invoiceConstants.InvoiceExceptionConstants;
import com.invoiceApplication.invoiceConstants.SMTPMailConstants;
import com.invoiceApplication.invoiceException.InvoiceExceptionHandler;
import com.invoiceApplication.invoiceModel.InvoiceAddress;
import com.invoiceApplication.invoiceModel.InvoiceData;
import com.invoiceApplication.invoiceRepository.InvoiceDataBase;
import com.invoiceApplication.invoiceService.inter.InvoiceServiceInter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;


public class InvoiceServiceImpl implements InvoiceServiceInter {

    InvoiceDataBase invoiceDataBase;
    String to = null;

    public InvoiceServiceImpl() {
        invoiceDataBase = new InvoiceDataBase();
    }


    @Override
    //Insert invoiceData into DB
    public void insertInvoiceDataToDB(File invoiceFile) {
        invoiceDataBase.insertInvoiceData(invoiceDataProcess(invoiceFile));
        invoiceDataBase.displayInvoiceDetails();
    }

    /**
     * method to send confirmation email
     *
     * @param userName  userName of the compose mail
     * @param password
     * @param invoiceNo
     */
    @Override
    public void statusUpdationForRequestedInvoiceNo(String userName, String password, String invoiceNo) {
        if (invoiceDataBase.updatingStatusForInvoiceNumber(invoiceNo) > 0) {
            final String user = userName;
            final String pass = password;

            String to = null;
            if (ReceiveEmailWithInvoiceAttachment.from != null) {
                to = ReceiveEmailWithInvoiceAttachment.from.toString();
            } else
                to = userName;

            //Get the session object
            Properties properties = new Properties();
            properties.put(SMTPMailConstants.SMTP_MAIL_HOST, SMTPMailConstants.SMTP_MAIL_HOST_ID);
            properties.put(SMTPMailConstants.SMTP_MAIL_PORT, SMTPMailConstants.SMTP_PORT_NO);
            properties.put(SMTPMailConstants.MAIL_AUTHORITY, SMTPMailConstants.ENABLE_TRUE);
            properties.put(SMTPMailConstants.ENABLE_STARTTLS, SMTPMailConstants.ENABLE_TRUE);


            Session session = Session.getDefaultInstance(properties,
                    new javax.mail.Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(user, pass);
                        }
                    });

            //Compose the message
            try {
                MimeMessage message = new MimeMessage(session);
                message.setFrom(new InternetAddress(user));
                message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
                message.setSubject(InvoiceConstant.INVOICE_STATUS_UPDATION);
                message.setText("Invoice no :" + invoiceNo + " got Approved");

                //send the message
                Transport.send(message);

                System.out.println("message sent successfully...");

            } catch (MessagingException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("Invalid invoice Number Found");
        }
    }

    private ArrayList<InvoiceData> invoiceDataProcess(File invoiceFile) {

        if (ReceiveEmailWithInvoiceAttachment.from != null) {
            to = ReceiveEmailWithInvoiceAttachment.from.toString();
        } else
            to = InvoiceConstant.USER_NAME;
        //list of invoiceData
        ArrayList<InvoiceData> invoiceDataList = new ArrayList<InvoiceData>();

        if (invoiceFile != null) {
            try (PDDocument document = PDDocument.load(invoiceFile)) {
                document.getClass();
                if (!document.isEncrypted()) {
                    PDFTextStripper tStripper = new PDFTextStripper();
                    String pdfFileInText = tStripper.getText(document);

                    //variables for invoiceData and invoiceAddressData
                    String invoiceNo = null;
                    String invoiceDate = null;
                    String customerPO = null;
                    String totalInvoice = null;
                    String soldTo = "";
                    String shipTo = "";
                    String remitTo = "";
                    InvoiceAddress invoiceAddress = null;

                    //Fetching invoiceNo,invoiceDate,CustomerPo,TotalInvoice and Address Details
                    String lines[] = pdfFileInText.split("\\r?\\n");
                    for (int i = 0; i < lines.length; ) {
                        if (lines.length - 1 < lines.length) {
                            if (lines[i].equals("Invoice No")) {
                                invoiceNo = lines[++i];
                            } else if (lines[i].equals("Invoice Date"))
                                invoiceDate = lines[++i];
                            else if (lines[i].equals("Customer P.O."))
                                customerPO = lines[++i];
                            else if (lines[i].equals("Total Invoice") || lines.equals("CREDIT")) {
                                totalInvoice = lines[++i];
                            } else if (lines[i].equals("Sold To")) {
                                while (!(lines[i + 1].equals("Ship To")))
                                    soldTo += lines[++i] + "\n";
                            } else if (lines[i].equals("Ship To")) {
                                while (!(lines[i + 1].equals("Remit To")))
                                    shipTo += lines[++i] + "\n";
                            } else if (lines[i].equals("Remit To")) {
                                while (!((lines[i + 1].startsWith("Payment"))))
                                    remitTo += lines[++i] + "\n";
                                i++;
                            } else
                                i++;
                        }

                        //Checking Address details & Creating InvoiceAddress
                        if (!shipTo.isEmpty() && !soldTo.isEmpty() && !remitTo.isEmpty()) {
                            invoiceAddress = new InvoiceAddress(soldTo, shipTo, remitTo);
                            soldTo = "";
                            shipTo = "";
                            remitTo = "";
                        }

                        to = ReceiveEmailWithInvoiceAttachment.from.toString();
                        //Checking InvoiceData details & Creating InvoiceData
                        if (invoiceAddress != null && invoiceNo != null && invoiceDate != null && totalInvoice != null && to != null) {
                            invoiceDataList.add(new InvoiceData(invoiceNo, invoiceDate, customerPO, invoiceAddress, totalInvoice, "Not Approved", to));
                            invoiceNo = null;
                            invoiceDate = null;
                            invoiceAddress = null;
                            totalInvoice = null;
                            System.out.println(invoiceDataList.get(invoiceDataList.size() - 1));
                        }
                    }

                }
            } catch (Exception exception) {
                new InvoiceExceptionHandler(InvoiceExceptionConstants.FILE_READING_ERROR, exception);
            }
        }
        return invoiceDataList;

    }

}
        

