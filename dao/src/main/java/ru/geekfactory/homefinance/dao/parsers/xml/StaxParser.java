package ru.geekfactory.homefinance.dao.parsers.xml;

import org.xml.sax.SAXException;
import ru.geekfactory.homefinance.dao.model.AccountModel;
import ru.geekfactory.homefinance.dao.model.CategoryTransactionModel;
import ru.geekfactory.homefinance.dao.model.TransactionModel;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamConstants;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StaxParser implements Parser<TransactionModel> {

    @Override
    public List<TransactionModel> parserEntity(String xmlFile) throws ParserConfigurationException, IOException, SAXException, XMLStreamException {

        String content = null;
        TransactionModel transaction = null;
        List<TransactionModel> transactions = null;
        CategoryTransactionModel category = null;
        Collection<CategoryTransactionModel> categories = null;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(xmlFile));

        while (reader.hasNext()) {
            int event = reader.next();

            switch (event) {
                case XMLStreamConstants.START_ELEMENT:
                    if ("transaction".equals(reader.getLocalName())) {
                        transaction = new TransactionModel();
                        transaction.setId(Long.valueOf(reader.getAttributeValue(0)));
                        categories = new ArrayList<>();
                    }

                    if ("transactions".equals(reader.getLocalName())) {
                        transactions = new ArrayList<>();
                    }

                    if ("category".equals(reader.getLocalName())) {
                        category = new CategoryTransactionModel();
                        category.setId(Long.valueOf(reader.getAttributeValue(0)));
                    }

                    break;

                case XMLStreamConstants.CHARACTERS:
                    content = reader.getText().trim();
                    break;

                case XMLStreamConstants.END_ELEMENT:
                    switch (reader.getLocalName()) {
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
                        case "transaction":
                            transaction.setCategoryTransaction(categories);
                            transactions.add(transaction);
                            break;
                        case "categoryName":
                            category.setName(content);
                            break;
                        case "category":
                            categories.add(category);
                            break;
                    }
                    break;
            }
        }
        return transactions;
    }
}
