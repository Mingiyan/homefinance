package ru.geekfactory.homefinance.dao.parsers.xml;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import ru.geekfactory.homefinance.dao.model.AccountModel;
import ru.geekfactory.homefinance.dao.model.CategoryTransactionModel;
import ru.geekfactory.homefinance.dao.model.TransactionModel;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class DOMParser implements Parser<TransactionModel> {

    @Override
    public List<TransactionModel> parserEntity(String xmlFile) throws ParserConfigurationException, IOException, SAXException, XMLStreamException {
        List<TransactionModel> transactionList = new ArrayList<>();

        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();

        Document document = builder.parse(ClassLoader.getSystemResourceAsStream(xmlFile));
        NodeList nodeList = document.getDocumentElement().getChildNodes();

        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);

            if (node instanceof Element) {
                TransactionModel transaction = new TransactionModel();
                transaction.setId(Long.valueOf(node.getAttributes().getNamedItem("id").getNodeValue()));
                NodeList childNodes = node.getChildNodes();

                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node cNode = childNodes.item(j);

                    if (cNode instanceof Element) {
                        String content = cNode.getLastChild().getTextContent().trim();
                        switch (cNode.getNodeName()) {
                            case "name":
                                transaction.setName(content);
                                break;
                            case "time":
                                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
                                transaction.setDateTime(LocalDateTime.parse(content, formatter));
                                break;
                            case "account":
                                AccountModel account = null;
                                if (!content.isEmpty()) {
                                    account = new AccountModel();
                                    account.setId(Long.valueOf(content));
                                    transaction.setAccount(account);
                                }
                                break;
                            case "categories":
                                Collection<CategoryTransactionModel> categoryList = null;
                                if (!content.isEmpty()) {
                                    categoryList = getCategories(content);
                                }
                                transaction.setCategoryTransaction(categoryList);
                                break;
                        }
                    }
                }
                transactionList.add(transaction);
            }
        }
        return transactionList;
    }

    private Collection<CategoryTransactionModel> getCategories(String content) throws ParserConfigurationException, IOException, SAXException {
        Collection<CategoryTransactionModel> categoryTransactionModels = new ArrayList<>();
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        Document document = builder.parse(content);
        NodeList nodeList = document.getDocumentElement().getChildNodes();
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node instanceof Element) {
                CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
                categoryTransactionModel.setId(Long.valueOf(node.getAttributes().getNamedItem("id").getNodeValue()));
                NodeList childNodes = node.getChildNodes();
                for (int j = 0; j < childNodes.getLength(); j++) {
                    Node cNode = childNodes.item(j);
                    if (cNode instanceof Element) {
                        String category = cNode.getLastChild().getTextContent().trim();
                        categoryTransactionModel.setName(category);
                    }
                }
                categoryTransactionModels.add(categoryTransactionModel);
            }
        }
        return categoryTransactionModels;
    }
}
