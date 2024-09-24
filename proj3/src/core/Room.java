package core;

public class Room {
    int xCoord;
    int yCoord;
    int width;
    int height;
    public Room(int x, int y, int w, int h) {
        xCoord = x;
        yCoord = y;
        width = w;
        height = h;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }

    public int getxCoord() {
        return xCoord;
    }

    public int getyCoord() {
        return yCoord;
    }
}
