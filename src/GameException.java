public class GameException extends Exception {
    String msg;
    int x = 0;
    int y = 0;

    GameException(){}

    GameException(String msg){
        this.msg = msg;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String ms) {
        msg = ms;
    }

    public void setMsg(String ms, int index1 , int index2) {
        x = index1;
        y = index2;
        setMsg(ms);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
