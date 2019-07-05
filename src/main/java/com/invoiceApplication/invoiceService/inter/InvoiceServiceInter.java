package com.invoiceApplication.invoiceService.inter;

import java.io.File;
import java.sql.SQLException;

public interface InvoiceServiceInter {
    public void insertInvoiceDataToDB(File invoiceFile) throws SQLException;
    public void statusUpdationForRequestedInvoiceNo(String userName, String password, String invoiceNo) throws SQLException;
}
