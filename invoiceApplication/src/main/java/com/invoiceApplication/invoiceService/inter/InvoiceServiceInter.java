package com.invoiceApplication.invoiceService.inter;

import java.io.File;

public interface InvoiceServiceInter {
    public void insertInvoiceDataToDB(File invoiceFile);
    public void statusUpdationForRequestedInvoiceNo(String userName, String password, String invoiceNo);
}
