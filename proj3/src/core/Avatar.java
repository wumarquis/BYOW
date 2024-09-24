package core;

public class Avatar {
    private int xPosition;
    private int yPosition; //[y][x]

    public Avatar(int x, int y) {
        xPosition = x;
        yPosition = y;
    }
    public void changeCurrentX(int x) {
        xPosition = x;
    }
    public void changeCurrentY(int y) {
        yPosition = y;
    }

    public int getCurrentX() {
        return xPosition;
    }

    public int getCurrentY() {
        return yPosition;
    }

}
