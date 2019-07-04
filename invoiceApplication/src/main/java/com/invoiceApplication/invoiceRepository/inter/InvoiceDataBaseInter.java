package com.invoiceApplication.invoiceRepository.inter;

import com.invoiceApplication.invoiceModel.InvoiceData;

import java.util.ArrayList;

public interface InvoiceDataBaseInter {
    public void displayInvoiceDetails();
    public void insertInvoiceData(ArrayList<InvoiceData> invoiceData);
    public int updatingStatusForInvoiceNumber(String invoiceNo);
}
