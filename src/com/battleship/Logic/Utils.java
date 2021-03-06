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

public class Utils {
    private Utils(){}
    public static Map<String, ShipType> typeMap;
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
            case "UP_RIGHT":
                return 2;
            case "RIGHT_DOWN":
                return 3;
            case "DOWN_RIGHT":
                return 4;
            case "RIGHT_UP":
                return 5;
            default:
                return -1;
        }
    }
}