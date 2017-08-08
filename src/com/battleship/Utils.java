package com.battleship;

import java.io.File;
import java.io.IOException;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.Node;

import org.xml.sax.SAXException;

class Utils {
    boolean checkXML(String xmlPath) {
        return schemaIsValid(xmlPath) && dataIsValid(xmlPath);
    }

    private boolean dataIsValid(String xmlPath) {
        try {
            File fXmlFile = new File(xmlPath);
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(fXmlFile);
            NodeList boards = doc.getElementsByTagName("board");
            int boardSize = Integer.parseInt(doc.getElementsByTagName("boardSize").item(0).getTextContent());
            isBoardValid(boards.item(0), boardSize);
            isBoardValid(boards.item(1), boardSize);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
    private boolean isBoardValid(Node board, int boardSize) {

        System.out.println(boardSize);
        return false;
    }
    private boolean schemaIsValid(String xmlPath){
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(new File("src/resources/BattleShipGame.xsd"));
            Validator validator = schema.newValidator();
            validator.validate(new StreamSource(new File(xmlPath)));
        } catch (IOException e){
            System.out.println("Exception: "+e.getMessage());
            return false;
        }catch(SAXException e1){
            System.out.println("SAX Exception: "+e1.getMessage());
            return false;
        }
        return true;
    }
}