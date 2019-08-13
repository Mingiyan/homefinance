package ru.geekfactory.homefinance.dao.parsers.xml;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;
import ru.geekfactory.homefinance.dao.model.AccountModel;
import ru.geekfactory.homefinance.dao.model.CategoryTransactionModel;
import ru.geekfactory.homefinance.dao.model.TransactionModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SaxHandler extends DefaultHandler {

    List<TransactionModel> transactionsList = new ArrayList<>();
    TransactionModel transaction = null;
    String content = null;
    Collection<CategoryTransactionModel> categories = null;
    CategoryTransactionModel categoryTransactionModel = null;

    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
        switch (qName) {
            case "transaction":
                transaction = new TransactionModel();
                transaction.setId(Long.valueOf(attributes.getValue("id")));
                categories = new ArrayList<>();
                break;
            case "category":
                categoryTransactionModel = new CategoryTransactionModel();
                categoryTransactionModel.setId(Long.valueOf(attributes.getValue("id")));
                break;
        }
    }

    @Override
    public void endElement(String uri, String localName, String qName) throws SAXException {
        switch (qName) {
            case "transactionName":
                transaction.setName(content);
                break;
            case "time":
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                transaction.setDateTime(LocalDateTime.parse(content, formatter));
                break;
            case "account":
                AccountModel account = new AccountModel();
                account.setId(Long.valueOf(content));
                transaction.setAccount(account);
                break;
            case "categoryName":
                categoryTransactionModel.setName(content);
                break;
            case "transaction":
                transaction.setCategoryTransaction(categories);
                transactionsList.add(transaction);
                break;
            case "category":
                categories.add(categoryTransactionModel);
        }
    }

    @Override
    public void characters(char[] ch, int start, int length) throws SAXException {
        content = String.copyValueOf(ch, start, length).trim();
    }
}
