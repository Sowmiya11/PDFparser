package com.invoiceApplication.invoiceException;

//import oracle.jdbc.replay.driver.ReplayLoggerFactory;

import java.util.logging.Logger;

public class InvoiceExceptionHandler extends Exception {

//    public static Logger logger = ReplayLoggerFactory.getLogger("Exception");

    public InvoiceExceptionHandler() {

    }

    public InvoiceExceptionHandler(String message, Exception exception) {
        System.out.println(message + " ::: " + exception.toString());
    }
}
