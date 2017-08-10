package com.battleship.Logic;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;

import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Element;
import org.xml.sax.SAXException;

class Utils {
    private Utils(){}
    static Map<String, ShipType> typeMap;
    static boolean isSchemaValid(String xmlPath){
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
    static String getFirstChildText(Element item, String tagName) {
        return item.getElementsByTagName(tagName).item(0).getTextContent();
    }
    static int getFirstChildInt(Element item, String tagName) {
        return Integer.parseInt(getFirstChildText(item, tagName));
    }
    static int getDirectionFromString(String direction) {
        switch(direction) {
            case "ROW":
                return 0;
            case "COLUMN":
                return 1;
            default:
                return -1;
        }
    }
}