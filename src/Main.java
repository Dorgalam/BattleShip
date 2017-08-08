import java.util.ArrayList;
import java.util.Scanner;
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
            int[] x = {0, 3, 0};
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
            g.putMine(0,6,1);
        } catch (GameException ex) {
            System.out.println(ex.getMsg());
        }
    }
}


