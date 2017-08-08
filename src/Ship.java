public class Ship {
    private int count;
    private int points;
    private String shipType;

    public Ship(int length, int score, String type) {
        this.count = length;
        this.points = score;
        this.shipType = type;
    }

    public int getPoints() {
        return points;
    }

    public int getCount() {
        return count;
    }

    public void decCount() {
        this.count--;
    }

}
