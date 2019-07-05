package com.invoiceApplication.invoiceModel;

public class InvoiceAddress {

    private String soldTo;

    private String shipTo;

    private String remitTo;

    public InvoiceAddress(String soldTo, String shipTo, String remitTo) {
        this.soldTo = soldTo;
        this.shipTo = shipTo;
        this.remitTo = remitTo;
    }

    public String getSoldTo() {
        return soldTo;
    }

    public void setSoldTo(String soldTo) {
        this.soldTo = soldTo;
    }

    public String getShipTo() {
        return shipTo;
    }

    public void setShipTo(String shipTo) {
        this.shipTo = shipTo;
    }

    public String getRemitTo() {
        return remitTo;
    }

    public void setRemitTo(String remitTo) {
        this.remitTo = remitTo;
    }

    @Override
    public String toString() {
        return "InvoiceAddress{" + "\n" +
                "soldTo='" + "\n" + soldTo + '\'' +
                ", shipTo='" + "\n" + shipTo + '\'' +
                ", remitTo='" + "\n" + remitTo + '\'' +
                '}';
    }
}
