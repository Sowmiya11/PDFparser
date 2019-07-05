package com.invoiceApplication.invoiceService;

import org.junit.Test;

public class ReceiveEmailWithInvoiceAttachmentTest {

    @Test
    public void testReceiveMail() throws Exception {
        new ReceiveEmailWithInvoiceAttachment().receiveEmail("pop",
                "mailStoreType", "userName", "password");
    }
}
