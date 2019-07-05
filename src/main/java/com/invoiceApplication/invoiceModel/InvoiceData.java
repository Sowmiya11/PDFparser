package com.invoiceApplication.invoiceModel;

public class InvoiceData {

    private String invoiceNo;

    private String invoiceDate;

    private String customerPO;

    private InvoiceAddress address;

    private String amount;

    private String status;

    private String email;

    public InvoiceData(String invoiceNo, String invoiceDate, String customerPO, InvoiceAddress address, String amount, String status, String mailId) {
        this.invoiceNo = invoiceNo;
        this.invoiceDate = invoiceDate;
        this.customerPO = customerPO;
        this.address = address;
        this.amount = amount;
        this.status = status;
        this.email = mailId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setInvoiceNo(String invoiceNo) {
        this.invoiceNo = invoiceNo;
    }

    public void setInvoiceDate(String invoiceDate) {
        this.invoiceDate = invoiceDate;
    }

    public void setCustomerPO(String customerPO) {
        this.customerPO = customerPO;
    }

    public void setAddress(InvoiceAddress address) {
        this.address = address;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getInvoiceNo() {
        return invoiceNo;
    }

    public String getInvoiceDate() {
        return invoiceDate;
    }

    public String getCustomerPO() {
        return customerPO;
    }

    public InvoiceAddress getAddress() {
        return address;
    }

    public String getAmount() {
        return amount;
    }

    @Override
    public String toString() {
        return "InvoiceData[" +
                "invoiceNo='" + invoiceNo + '\'' +
                ", invoiceDate='" + invoiceDate + '\'' +
                ", customerPO='" + customerPO + '\'' +
                ", address=" + address +
                ", amount='" + amount + '\'' +
                ']';
    }
}
