package ru.geekfactory.homefinance.dao.parsers.xml;

import org.xml.sax.SAXException;
import ru.geekfactory.homefinance.dao.model.TransactionModel;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.io.IOException;
import java.util.List;

public class SaxParser implements Parser<TransactionModel> {

    @Override
    public List<TransactionModel> parserEntity(String xmlFile) {

        SaxHandler saxHandler = new SaxHandler();

        try {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser parser = factory.newSAXParser();
            parser.parse(ClassLoader.getSystemResourceAsStream(xmlFile), saxHandler);
        } catch (SAXException | IOException | ParserConfigurationException e) {
            e.printStackTrace();
        }

        return saxHandler.transactionsList;
    }
}
