package ru.geekfactory.homefinance.dao.parsers.xml;

import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;
import java.util.List;

public interface Parser<E> {

    List<E> parserEntity(String xmlFile) throws ParserConfigurationException, IOException, SAXException, XMLStreamException;

}
