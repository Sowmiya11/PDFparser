package com.invoiceApplication.invoiceRepository;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import static org.mockito.Mockito.*;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class InvoiceDataBaseTest {

    public Connection connection;
    public InvoiceDataBase invoiceDataBase;
    public Statement statement;
    public ResultSet resultSet;

    @Before
    public void setBefore() throws Exception{
         invoiceDataBase=new InvoiceDataBase();
         connection= mock(Connection.class);
         statement =mock(Statement.class);
         resultSet =mock(ResultSet.class);
    }

    @Test(expected = Exception.class)
    public void displayInvoiceDetailsTest() throws SQLException{
        when(connection.createStatement()).thenReturn(statement);
        when(statement.executeQuery(anyString())).thenReturn(resultSet);
        when(resultSet.next()).thenReturn(true).thenReturn(false);
        when(resultSet.getString(anyInt())).thenReturn("13367").thenReturn("12/6/56").thenReturn("ShipTo").thenReturn("SoldTo ").thenReturn("RemitTo ").thenReturn(" Status").thenReturn("Mail");
        invoiceDataBase.displayInvoiceDetails();
    }

    @Test
    public void updatingStatusForInvoiceNumberTest() throws Exception{
        invoiceDataBase.updatingStatusForInvoiceNumber("124");
    }
}
