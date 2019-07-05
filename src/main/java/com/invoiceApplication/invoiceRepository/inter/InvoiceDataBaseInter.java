package com.invoiceApplication.invoiceRepository.inter;

import com.invoiceApplication.invoiceModel.InvoiceData;

import java.sql.SQLException;
import java.util.ArrayList;

public interface InvoiceDataBaseInter {
    public void displayInvoiceDetails() throws SQLException;
    public void insertInvoiceData(ArrayList<InvoiceData> invoiceData) throws SQLException;
    public int updatingStatusForInvoiceNumber(String invoiceNo) throws SQLException;
    public String replyMailID(String invoiceNo) throws SQLException;
}
