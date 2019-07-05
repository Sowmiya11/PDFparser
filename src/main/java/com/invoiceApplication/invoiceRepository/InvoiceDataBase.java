package com.invoiceApplication.invoiceRepository;


import com.invoiceApplication.invoiceConstants.DBConstants;
import com.invoiceApplication.invoiceConstants.InvoiceConstant;
import com.invoiceApplication.invoiceConstants.InvoiceExceptionConstants;
import com.invoiceApplication.invoiceException.InvoiceExceptionHandler;
import com.invoiceApplication.invoiceModel.InvoiceData;
import com.invoiceApplication.invoiceRepository.inter.InvoiceDataBaseInter;

import java.sql.*;
import java.util.ArrayList;


public class InvoiceDataBase implements InvoiceDataBaseInter {

    public Connection con;

    @Override
    public void displayInvoiceDetails() throws SQLException {
        try {
            con = DriverManager.getConnection(
                    DBConstants.Driver_URL, DBConstants.Driver_UserName, DBConstants.Driver_Password);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(DBConstants.Select_InvoiceData);
            while (resultSet.next())
                System.out.println(resultSet.getString(1) + "  " + resultSet.getString(2) + " " + resultSet.getString(3) + "  " + resultSet.getString(4) + "  " + resultSet.getString(5) + "  " + resultSet.getString(6) + "  " + resultSet.getString(7));
        } catch (Exception sqlException) {
            new InvoiceExceptionHandler(InvoiceExceptionConstants.DB_RELATED_ERROR, sqlException);
        } finally {
            con.close();
        }
    }

    @Override
    public void insertInvoiceData(ArrayList<InvoiceData> invoiceData) throws SQLException {
        try {
            Class.forName(DBConstants.OracleDriver);
            con = DriverManager.getConnection(
                    DBConstants.Driver_URL, DBConstants.Driver_UserName, DBConstants.Driver_Password);

            for (int i = 0; i < invoiceData.size(); i++) {
                String sql = "INSERT INTO invoiceData (invoiceNumber, invoiceDate, customerPo,totalAmount,status,emailId) VALUES (?, ?, ?, ?,?,?)";
                PreparedStatement statement = con.prepareStatement(sql);
                statement.setString(1, invoiceData.get(i).getInvoiceNo());
                statement.setString(2, invoiceData.get(i).getInvoiceDate());
                statement.setString(3, invoiceData.get(i).getCustomerPO());
                statement.setString(4, invoiceData.get(i).getAmount());
                statement.setString(5, invoiceData.get(i).getStatus());
                statement.setString(6, invoiceData.get(i).getEmail());

                String sqlQueryForAddress = "INSERT INTO invoiceAddressDB (invoiceNumber, soldTo, shipTo,remitTo) VALUES (?, ?, ?, ?)";
                PreparedStatement preparedStatement = con.prepareStatement(sqlQueryForAddress);
                preparedStatement.setString(1, invoiceData.get(i).getInvoiceNo());
                preparedStatement.setString(2, invoiceData.get(i).getAddress().getSoldTo());
                preparedStatement.setString(3, invoiceData.get(i).getAddress().getShipTo());
                preparedStatement.setString(4, invoiceData.get(i).getAddress().getRemitTo());

                int rowsInserted = statement.executeUpdate();
                int row = preparedStatement.executeUpdate();
                if (rowsInserted > 0 && row > 0) {
                    System.out.println(InvoiceConstant.INVOICE_DATA_INSERTION);
                }
            }
        } catch (Exception e) {
            new InvoiceExceptionHandler(InvoiceExceptionConstants.DB_RELATED_ERROR, e);
        } finally {
            con.close();
        }
    }

    @Override
    public int updatingStatusForInvoiceNumber(String invoiceNo) throws SQLException {
        int n = 0;
        try {
            Class.forName(DBConstants.OracleDriver);
            con = DriverManager.getConnection(
                    DBConstants.Driver_URL, DBConstants.Driver_UserName, DBConstants.Driver_Password);

            String sql = DBConstants.Update_Status + invoiceNo;
            Statement statement = con.createStatement();
            n = statement.executeUpdate(sql);
            if (n > 0)
                System.out.println(InvoiceConstant.INVOICE_DATA_UPDATION);
            return n;
        } catch (Exception sqlException) {
            new InvoiceExceptionHandler(InvoiceExceptionConstants.DB_UPDATION_ERROR, sqlException);
        } finally {
            con.close();
        }
        return n;
    }

    @Override
    public String replyMailID(String invoiceNo) throws SQLException {
        String mailId = null;
        try {
            Class.forName(DBConstants.OracleDriver);
            con = DriverManager.getConnection(
                    DBConstants.Driver_URL, DBConstants.Driver_UserName, DBConstants.Driver_Password);
            Statement statement = con.createStatement();
            ResultSet resultSet = statement.executeQuery(DBConstants.Getting_Retuen_MailID + invoiceNo);
            while (resultSet.next())
                mailId = resultSet.getString(1);
        } catch (Exception sqlException) {
            new InvoiceExceptionHandler(InvoiceExceptionConstants.DB_RELATED_ERROR, sqlException);
        } finally {
            con.close();
        }
        return mailId;

    }
}
