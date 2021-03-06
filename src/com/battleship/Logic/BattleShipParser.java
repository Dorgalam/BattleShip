package com.battleship.Logic;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import javax.rmi.CORBA.Util;
import javax.xml.XMLConstants;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;
import javax.xml.validation.Validator;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import static java.lang.System.in;


class BattleShipParser {
    private Element dom;
    private File xmlFile;
    BattleShipParser(String xmlPath) throws GameException {
        try {
            if (!xmlPath.endsWith(".xml")) {
                throw new GameException("File extension must be .xml");
            }
            isSchemaValid(xmlPath);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            this.dom = db.parse(xmlFile).getDocumentElement();
            setShipTypesUtil();
        } catch(IOException e) {
            throw new GameException(e.getMessage());
        } catch (SAXException e) {
            throw new GameException("Invalid XML (possible)?");
        } catch (ParserConfigurationException e) {
            throw new GameException("Cannot parse XML");
        }
    }
    private void setShipTypesUtil() throws GameException {
        NodeList shipTypesList = dom.getElementsByTagName("shipType");
        int numTypes = shipTypesList.getLength();
        Utils.typeMap = new HashMap<>();
        for (int i = 0; i < numTypes; ++i) {
            Element shipType = (Element)shipTypesList.item(i);
            String type = shipType.getAttributes().getNamedItem("id").getNodeValue();
            String category = Utils.getFirstChildText(shipType, "category");
            int amount = Utils.getFirstChildInt(shipType, "amount");
            int length = Utils.getFirstChildInt(shipType, "length");
            int score = Utils.getFirstChildInt(shipType, "score");
            if (!getGameType().equals("ADVANCE") &&  (type.equals("RIGHT_UP") || type.equals("UP_RIGHT") || type.equals("RIGHT_DOWN") || type.equals("DOWN_RIGHT"))) {
                throw new GameException("Game is basic but ships are advanced");
            }
            Utils.typeMap.put(type, new ShipType(length, score, amount, category));
        }
        Utils.typeMap = Collections.unmodifiableMap(Utils.typeMap);
    }

    Ship[] getBoardAShips() throws GameException {
        Element boardA = (Element) dom.getElementsByTagName("board").item(0);
        return getBoardShips(boardA);
    }
    Ship[] getBoardBShips() throws GameException {
        Element boardB = (Element) dom.getElementsByTagName("board").item(1);
        return getBoardShips(boardB);
    }

    int getNumMines() {
        Element mines = (Element)dom.getElementsByTagName("mine").item(0);
        return Utils.getFirstChildInt(mines, "amount");
    }

    private Ship[] getBoardShips(Element board) throws GameException {
        NodeList shipList = board.getElementsByTagName("ship");
        int numShips = shipList.getLength();
        Ship[] boardShips = new Ship[numShips];
        Map<String, Integer> shipCount = new HashMap<>();
        Utils.typeMap.forEach((key, value)-> shipCount.put(key, value.getAmount()));
        for (int i = 0; i < numShips; ++i) {
            Element ship = (Element)shipList.item(i);
            String type = Utils.getFirstChildText(ship, "shipTypeId");
            int x, y;
            Element position = (Element) ship.getElementsByTagName("position").item(0);
            x = Integer.parseInt(position.getAttributes().getNamedItem("x").getNodeValue());
            y = Integer.parseInt(position.getAttributes().getNamedItem("y").getNodeValue());
            String direction = Utils.getFirstChildText(ship, "direction");
            if (!Utils.typeMap.containsKey(type)) {
                throw new GameException("Error loading ships: Ship of type " + type + " wasn't declared in the XML");
            }
            Integer typeAmount = shipCount.get(type);
            shipCount.put(type, typeAmount - 1);
            boardShips[i] = new Ship(
                    Utils.typeMap.get(type).getLength(),
                    Utils.getDirectionFromString(direction),
                    x, y,
                    Utils.typeMap.get(type).getScore(),
                    type
                    );
        }
        for (Map.Entry<String, Integer> entry : shipCount.entrySet()) {
            Integer value = entry.getValue();
            String key = entry.getKey();
            Integer amount = Utils.typeMap.get(key).getAmount();
            if (entry.getValue() != 0) {
                throw new GameException("Shiptype " + key + " specified " + amount + " but found " + (amount - value));
            }
        }

        return boardShips;
    }
    int getBoardSize() {
        return Utils.getFirstChildInt(dom, "boardSize");
    }
    String getGameType() {
        return Utils.getFirstChildText(dom, "GameType");
    }
    private void isSchemaValid(String xmlPath) throws GameException{
        try {
            SchemaFactory factory = SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI);
            Schema schema = factory.newSchema(getClass().getResource("/resources/BattleShipGame.xsd"));
            Validator validator = schema.newValidator();
            xmlFile = new File(xmlPath);
            validator.validate(new StreamSource(xmlFile));
        } catch(IOException e) {
             throw new GameException(e.getMessage());
        } catch (Exception e) {
            throw new GameException("Error: XML is not built correctly");
        }
    }

}
