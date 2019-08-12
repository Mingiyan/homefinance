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
                        if (cNode.hasChildNodes()) {
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
                                    account = new AccountModel();
                                    account.setId(Long.valueOf(content));
                                    transaction.setAccount(account);
                                    break;

                            }
                        } else {
                            NodeList categoryList = cNode.getChildNodes();
                            Collection<CategoryTransactionModel> collection = new ArrayList<>();

                            for (int c = 0; c < categoryList.getLength(); c++) {
                                Node categoryNode = categoryList.item(c);
                                if (categoryNode instanceof Element) {
                                    CategoryTransactionModel categoryTransactionModel = new CategoryTransactionModel();
                                    categoryTransactionModel.setId(Long.valueOf(categoryNode.getAttributes().getNamedItem("id").getNodeValue()));
                                    String categoryContext = categoryNode.getLastChild().getTextContent().trim();
                                    categoryTransactionModel.setName(categoryContext);
                                    collection.add(categoryTransactionModel);
                                }
                            }
                            transaction.setCategoryTransaction(collection);
                        }
                    }
                }
                transactionList.add(transaction);
            }
        }
        return transactionList;
    }
}
