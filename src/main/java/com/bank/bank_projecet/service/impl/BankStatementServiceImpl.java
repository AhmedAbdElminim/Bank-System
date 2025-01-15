package com.bank.bank_projecet.service.impl;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.springframework.stereotype.Service;

import com.bank.bank_projecet.dto.EmailDetails;
import com.bank.bank_projecet.entity.Transaction;
import com.bank.bank_projecet.entity.User;
import com.bank.bank_projecet.repository.TransactionRepository;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
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

    private static String FILE = "D:\\Bank Pdfs\\MyStatement.pdf";

    public List<Transaction> generateBankStatement(String accountNumber, String startDate, String endDate)
            throws FileNotFoundException, DocumentException {
        LocalDate start = LocalDate.parse(startDate, DateTimeFormatter.ISO_DATE);
        LocalDate end = LocalDate.parse(endDate, DateTimeFormatter.ISO_DATE);

        List<Transaction> transactionList = transactionRepository.findAll()
                .stream()
                .filter(transaction -> transaction.getAccountNumber().equals(accountNumber))
                .filter(
                        transaction -> {
                            LocalDate createdDate = transaction.getCreatedDate();
                            return (createdDate.isAfter(start) || createdDate.isEqual(start)) &&
                                    (createdDate.isBefore(end) || createdDate.isEqual(end));
                        })
                .toList();
        designStatement(transactionList, accountNumber, startDate, endDate);
        return transactionList;
    }

    private void designStatement(List<Transaction> transactions, String accountNumber, String startDate, String endDate)
            throws FileNotFoundException, DocumentException {

        User user = userService.findUserByAccountNumber(accountNumber);

        Rectangle statementSize = new Rectangle(PageSize.A4);
        Document document = new Document(statementSize);
        OutputStream outputStream = new FileOutputStream(FILE);
        PdfWriter.getInstance(document, outputStream);
        document.open();

        // Bank Info Table
        PdfPTable bankInfoTable = new PdfPTable(1);
        PdfPCell bankName = new PdfPCell(new Phrase("CIB Bank", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 16)));
        bankName.setBorder(0);
        bankName.setBackgroundColor(BaseColor.CYAN);
        bankName.setPadding(15f);
        bankName.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell bankAddress = new PdfPCell(new Phrase("CAIRO, EGYPT", FontFactory.getFont(FontFactory.HELVETICA, 12)));
        bankAddress.setBorder(0);
        bankAddress.setPadding(10f);
        bankAddress.setHorizontalAlignment(Element.ALIGN_CENTER);

        bankInfoTable.addCell(bankName);
        bankInfoTable.addCell(bankAddress);

        // Statement Info Table
        PdfPTable statementInfo = new PdfPTable(2);
        statementInfo.setWidthPercentage(100);

        PdfPCell beginDate = new PdfPCell(new Phrase("Start Date: " + startDate));
        beginDate.setBorder(0);
        beginDate.setHorizontalAlignment(Element.ALIGN_RIGHT);
        PdfPCell statement = new PdfPCell(
                new Phrase("STATEMENT OF ACCOUNT", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 14)));
        statement.setBorder(0);
        statement.setHorizontalAlignment(Element.ALIGN_LEFT);

        PdfPCell stopDate = new PdfPCell(new Phrase("End Date: " + endDate));
        stopDate.setBorder(0);
        stopDate.setHorizontalAlignment(Element.ALIGN_RIGHT);
        PdfPCell userName = new PdfPCell(new Phrase("Client Name: " + user.getF_Name() + " " + user.getL_Name()));
        userName.setBorder(0);
        userName.setHorizontalAlignment(Element.ALIGN_LEFT);
        PdfPCell space = new PdfPCell();
        space.setBorder(0);
        space.setHorizontalAlignment(Element.ALIGN_RIGHT);

        PdfPCell userAddress = new PdfPCell(new Phrase("Client Address: " + user.getAddress()));
        userAddress.setBorder(0);
        userAddress.setHorizontalAlignment(Element.ALIGN_LEFT);
        statementInfo.addCell(statement);
        statementInfo.addCell(beginDate);
        statementInfo.addCell(userName);
        statementInfo.addCell(stopDate);
        statementInfo.addCell(userAddress);
        statementInfo.addCell(space);

        // Transaction Table
        PdfPTable transactionTable = new PdfPTable(4);
        transactionTable.setWidthPercentage(100);
        transactionTable.setSpacingBefore(10f);
        transactionTable.setSpacingAfter(10f);

        PdfPCell date = new PdfPCell(new Phrase("DATE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        date.setBackgroundColor(BaseColor.CYAN);
        date.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell transactionType = new PdfPCell(
                new Phrase("TRANSACTION TYPE", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        transactionType.setBackgroundColor(BaseColor.CYAN);
        transactionType.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell transactionAmount = new PdfPCell(
                new Phrase("TRANSACTION AMOUNT", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        transactionAmount.setBackgroundColor(BaseColor.CYAN);
        transactionAmount.setHorizontalAlignment(Element.ALIGN_CENTER);

        PdfPCell status = new PdfPCell(new Phrase("STATUS", FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12)));
        status.setBackgroundColor(BaseColor.CYAN);
        status.setHorizontalAlignment(Element.ALIGN_CENTER);

        transactionTable.addCell(date);
        transactionTable.addCell(transactionType);
        transactionTable.addCell(transactionAmount);
        transactionTable.addCell(status);

        // Add transaction rows with alternating row colors
        boolean isAlternateRow = false;
        for (Transaction transaction : transactions) {
            BaseColor rowColor = isAlternateRow ? BaseColor.CYAN : BaseColor.WHITE;
            isAlternateRow = !isAlternateRow;

            PdfPCell dateCell = new PdfPCell(new Phrase(transaction.getCreatedDate().toString()));
            dateCell.setBackgroundColor(rowColor);
            transactionTable.addCell(dateCell);

            PdfPCell typeCell = new PdfPCell(new Phrase(transaction.getTransactionType().toString()));
            typeCell.setBackgroundColor(rowColor);
            transactionTable.addCell(typeCell);

            PdfPCell amountCell = new PdfPCell(new Phrase(transaction.getAmount().toString()));
            amountCell.setBackgroundColor(rowColor);
            transactionTable.addCell(amountCell);

            PdfPCell statusCell = new PdfPCell(new Phrase(transaction.getStatus().toString()));
            statusCell.setBackgroundColor(rowColor);
            transactionTable.addCell(statusCell);
        }

        // Footer
        Paragraph footer = new Paragraph("Thank you for banking with CIB Bank.",
                FontFactory.getFont(FontFactory.HELVETICA_BOLD, 12, BaseColor.CYAN));
        footer.setAlignment(Element.ALIGN_CENTER);

        // Add all sections to the document
        document.add(bankInfoTable);
        document.add(statementInfo);
        document.add(transactionTable);
        document.add(footer);

        document.close();

        EmailDetails emailDetails = EmailDetails.builder()
                .recipient(user.getEmail())
                .messageBody("Kindly find your requested account transaction from \n" + startDate + "\n" + "to" + "\n"
                        + endDate + "\n" + "attached!")
                .subject("TRANSACTIONS REPORT")
                .attachment(FILE)
                .build();
        emailService.sendEmailWithAttachment(emailDetails);

    }
}
