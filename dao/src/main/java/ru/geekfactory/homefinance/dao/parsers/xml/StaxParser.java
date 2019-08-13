package ru.geekfactory.homefinance.dao.parsers.xml;

import org.xml.sax.SAXException;
import ru.geekfactory.homefinance.dao.model.TransactionModel;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.IOException;
import java.util.List;

public class StaxParser implements Parser<TransactionModel> {

    @Override
    public List<TransactionModel> parserEntity(String xmlFile) throws ParserConfigurationException, IOException, SAXException, XMLStreamException {

        String content = null;
        TransactionModel transaction = null;
        List<TransactionModel> transactions = null;

        XMLInputFactory factory = XMLInputFactory.newInstance();
        XMLStreamReader reader = factory.createXMLStreamReader(ClassLoader.getSystemResourceAsStream(xmlFile));

        while (reader.hasNext()) {
            int event = reader.next();
        }
        return null;
    }
}
