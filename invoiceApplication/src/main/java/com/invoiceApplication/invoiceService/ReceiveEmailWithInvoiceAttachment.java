package com.invoiceApplication.invoiceService;

import com.invoiceApplication.invoiceConstants.POP3MailConstants;
import com.invoiceApplication.invoiceConstants.SMTPMailConstants;

import javax.mail.*;
import java.io.File;
import java.io.InputStream;
import java.nio.file.StandardCopyOption;
import java.util.Properties;

/**
 * This class is used to receive email with attachment.
 *
 * @author sowmiyaNataraj
 */
public class ReceiveEmailWithInvoiceAttachment {


    public static Address from = null;
    public static Address to[] = new Address[12345];

    public static File receiveEmail(String pop3Host,
                                    String mailStoreType, String userName, String password) throws Exception {

        File targetFile = null;

        //Set properties
        Properties props = new Properties();
        props.put(POP3MailConstants.POP3_MAIL_PROTOCOL, POP3MailConstants.POP3);
        props.put(POP3MailConstants.POP3_HOST, pop3Host);
        props.put(POP3MailConstants.POP3_PORT, POP3MailConstants.POP3_PORT_ID);
        props.put(POP3MailConstants.POP3_STARTTTLS, SMTPMailConstants.ENABLE_TRUE);


        // Get the Session object.
        Session session = Session.getInstance(props);

        try {
            //Create the POP3 store object and connect to the pop store.
            Store store = session.getStore("pop3s");
            store.connect(pop3Host, userName, password);

            //Create the folder object and open it in your mailbox.
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);

            //Retrieve the messages from the folder object.
            Message[] messages = emailFolder.getMessages();
            System.out.println("Total Message" + messages.length);

            //Iterate the messages
            for (int i = 0; i < messages.length; i++) {
                Message message = messages[i];
                Address[] toAddress =
                        message.getRecipients(Message.RecipientType.TO);
                System.out.println("---------------------------------");
                System.out.println("Details of Email Message "
                        + (i + 1) + " :");
                System.out.println("Subject: " + message.getSubject());

                System.out.println("From: " + message.getFrom()[0]);
                from = message.getFrom()[0];

                //Iterate recipients
                System.out.println("To: ");
                to = new Address[toAddress.length];
                for (int j = 0; j < toAddress.length; j++) {
                    System.out.println(toAddress[j].toString());
                    to[j] = toAddress[j];
                }
                //Iterate multiparts
                Object multiPartObj = message.getContent();
                if (multiPartObj instanceof Multipart) {
                    Multipart multipart = (Multipart) multiPartObj;
                    for (int k = 0; k < multipart.getCount(); k++) {
                        BodyPart bodyPart = multipart.getBodyPart(k);
                        if (bodyPart.getDisposition() != null && bodyPart.getDisposition().equalsIgnoreCase(Part.ATTACHMENT)) {
                            System.out.println("file name " + bodyPart.getFileName());
                            System.out.println("size " + bodyPart.getSize());
                            System.out.println("content type " + bodyPart.getContentType());
                            InputStream stream = (InputStream) bodyPart.getInputStream();
                            targetFile = new File("D:\\Project\\SamplePDF\\" + bodyPart.getFileName());
                            java.nio.file.Files.copy(
                                    stream,
                                    targetFile.toPath(),
                                    StandardCopyOption.REPLACE_EXISTING);
                            //  IOUtils.closeQuietly(stream);
                        }
                    }
                }

            }

            //close the folder and store objects
            emailFolder.close(false);
            store.close();
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return targetFile;
    }
}