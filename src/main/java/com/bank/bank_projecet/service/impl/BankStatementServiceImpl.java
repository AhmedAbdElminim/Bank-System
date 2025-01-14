package com.bank.bank_projecet.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.bank_projecet.dto.EmailDetails;
import com.bank.bank_projecet.entity.Transaction;
import com.bank.bank_projecet.entity.User;
import com.bank.bank_projecet.repository.TransactionRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

@Service
@RequiredArgsConstructor
@Log4j2
public class BankStatementServiceImpl {

    private final TransactionRepository transactionRepository;
    private final UserServiceImpl userService;
    private final EmailServiceImpl emailService;

    private static String FILE="D:\\Bank Pdfs\\MyStatement.pdf";





    public List<Transaction>generateBankStatement(String accountNumber,String startDate,String endDate) throws FileNotFoundException, DocumentException{
        LocalDate start=LocalDate.parse(startDate,DateTimeFormatter.ISO_DATE);
        LocalDate end=LocalDate.parse(endDate,DateTimeFormatter.ISO_DATE);




        List<Transaction>transactionList=transactionRepository.findAll()
        .stream()
        .filter(transaction->transaction.getAccountNumber().equals(accountNumber))
        .filter(
            transaction -> {
                LocalDate createdDate = transaction.getCreatedDate();
                return (createdDate.isAfter(start) || createdDate.isEqual(start)) &&
                       (createdDate.isBefore(end) || createdDate.isEqual(end));
            }
        )
        .toList(); 
        
        designStatement(transactionList, accountNumber, startDate, endDate);
      


        return transactionList;
    }


    private void designStatement(List<Transaction>transactions,String accountNumber,String startDate,String endDate) throws FileNotFoundException, DocumentException{


        User user=userService.findUserByAccountNumber(accountNumber);

      Rectangle statementSize=new Rectangle(PageSize.A4);
      Document document=new Document(statementSize);
      OutputStream outputStream=new FileOutputStream(FILE);
      PdfWriter.getInstance(document, outputStream);
      document.open();
      PdfPTable bankInfoTable=new PdfPTable(1);
      PdfPCell bankName=new PdfPCell(new Phrase("CIB Bank"));
      bankName.setBorder(0);
      bankName.setBackgroundColor(BaseColor.BLUE);
      bankName.setPadding(20f);
      PdfPCell bankAddress=new PdfPCell(new Phrase("CAIRO, EGYPT"));
      bankAddress.setBorder(0);
      bankInfoTable.addCell(bankName);
      bankInfoTable.addCell(bankAddress);

      PdfPTable statementInfo=new PdfPTable(2);
      PdfPCell userInfo=new PdfPCell(new Phrase("Start Date : "+startDate));
      userInfo.setBorder(0);
      PdfPCell statement=new PdfPCell(new Phrase("STATEMENT OF ACCOUNT"));
      statement.setBorder(0);
      PdfPCell stopDate=new PdfPCell(new Phrase("End Date : "+endDate));
      stopDate.setBorder(0);
      PdfPCell userName=new PdfPCell(new Phrase("User Name : "+user.getF_Name()+" "+user.getL_Name()));
      userName.setBorder(0);
      PdfPCell space=new PdfPCell();
      space.setBorder(0);
      PdfPCell userAddress=new PdfPCell(new Phrase("User Address : "+user.getAddress()));
      userAddress.setBorder(0);

      //CREATE TRANSACTION TABLE
      PdfPTable transactionTable=new PdfPTable(4);
      PdfPCell date=new PdfPCell(new Phrase("DATE"));
      date.setBackgroundColor(BaseColor.BLUE);
      date.setBorder(0);

      PdfPCell transactionType=new PdfPCell(new Phrase("TRANSACTION TYPE"));
      transactionType.setBackgroundColor(BaseColor.BLUE);
      transactionType.setBorder(0);
      PdfPCell transactionAmount=new PdfPCell(new Phrase("TRANSACTION AMOUNT"));
      transactionAmount.setBackgroundColor(BaseColor.BLUE);
      transactionAmount.setBorder(0);
      PdfPCell status=new PdfPCell(new Phrase("STATUS"));
      status.setBackgroundColor(BaseColor.BLUE);
      status.setBorder(0);
      transactionTable.addCell(date);
      transactionTable.addCell(transactionType);

      transactionTable.addCell(transactionAmount);

      transactionTable.addCell(status);


      transactions.forEach(transaction->{

        transactionTable.addCell(new Phrase(transaction.getCreatedDate().toString()));
        transactionTable.addCell(new Phrase(transaction.getTransactionType().toString()));
        transactionTable.addCell(new Phrase(transaction.getAmount().toString()));
        transactionTable.addCell(new Phrase(transaction.getStatus().toString()));
      });
      statementInfo.addCell(userInfo);
      statementInfo.addCell(statement);
      statementInfo.addCell(stopDate);
      statementInfo.addCell(userName);
      statementInfo.addCell(space);
      statementInfo.addCell(userAddress);


      document.add(bankInfoTable);
      document.add(statementInfo);
      document.add(transactionTable);
      document.close();

      EmailDetails emailDetails=EmailDetails.builder()
      .recipient(user.getEmail())
      .messageBody("Kindly find your requested account transaction from \n"+startDate+"\n"+"to"+"\n"+endDate+"\n"+"attached!")
      .subject("TRANSACTIONS REPORT")
      .attachment(FILE)
      .build();
      emailService.sendEmailWithAttachment(emailDetails);




      













    }
}
