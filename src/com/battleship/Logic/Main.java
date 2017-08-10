package com.battleship.Logic;

import java.util.ArrayList;

public class Main {
    public ArrayList<Object> Data1 = new ArrayList();
    public int depth = 0;
    private String[] NODE_TYPES = {
            "", "ELEMENT", "ATTRIBUTE", "TEXT", "CDATA",
            "ENTITY_REF", "ENTITY", "PROCESSING_INST",
            "COMMENT", "DOCUMENT", "DOCUMENT_TYPE",
            "DOCUMENT_FRAG", "NOTATION"};

    public static void main(String[] args) throws Exception {
        try {
            BattleShipParser parser = new BattleShipParser("src/resources/battleShip_5_basic.xml");
            System.out.println(parser.getBoardSize());
            /*int[] x = {0, 3, 0};
            int[] y = {1, 3, 5};
            int[] len = {3, 1, 5};
            int[] dir = {1, 0, 1};
            Ship[] s = new Ship[3];
            s[0] = new Ship(len[0], 10, "1");
            s[1] = new Ship(len[1], 10, "2");
            s[2] = new Ship(len[2], 10, "3");
            Board b = new Board(6, x, y, len, dir);
            boolean res = b.isValidBoard(9);
            System.out.println(res);
            Player p1 = new Player(b, s);
            Player p2 = new Player(b, s);
            Game g = new Game(p1, p2);
            Game.startTurnClock();
            g.makeTurn(1, 5, 3);
            g.endTurnClock(1);
            System.out.println(g.makeTurn(0, 0, 1));
            System.out.println(g.getNumberOfHits(0));
            System.out.println("Schema is " + (Utils.isSchemaValid("src/resources/battleShip_5_basic.xml") ? "Valid" : "not Valid"));
            System.out.println(g.makeTurn(0, 1, 1));
            System.out.println(g.getNumberOfHits(0));

            System.out.println(g.makeTurn(0, 2, 1));
            System.out.println(g.getNumberOfHits(0));
            Game.startTurnClock();
            Scanner keyboard = new Scanner(System.in);
            System.out.println("enter an integer");
            int n = keyboard.nextInt();
            g.putMine(1, 5, n);
            g.endTurnClock(1);
            System.out.println(g.makeTurn(1, 3, 3));
            System.out.println(g.getNumberOfHits(0));
            System.out.print("time:");
            System.out.println(g.getAvgTime(1));
            System.out.print("time:start");
            long[] a = g.GetTimePass();
            System.out.print(a[0]);
            System.out.print(":");
            System.out.println(a[1]);
            System.out.println(g.isGameFinished(0));*/
        } catch (Exception ex) {
            System.out.println("out of bounds mannnn");
        }
    }
}


