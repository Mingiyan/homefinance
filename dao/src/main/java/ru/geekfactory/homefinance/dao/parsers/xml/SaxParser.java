package ru.geekfactory.homefinance.dao.parsers.xml;

import org.xml.sax.SAXException;
import ru.geekfactory.homefinance.dao.model.TransactionModel;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public class SaxParser implements Parser<TransactionModel> {

    @Override
    public List<TransactionModel> parserEntity(String xmlFile) throws ParserConfigurationException, IOException, SAXException, XMLStreamException {
        SAXParserFactory factory = SAXParserFactory.newInstance();
        SAXParser parser = factory.newSAXParser();
        SaxHandler saxHandler = new SaxHandler();

        parser.parse(ClassLoader.getSystemResourceAsStream(xmlFile), saxHandler);

        return saxHandler.transactionsList;
    }
}
