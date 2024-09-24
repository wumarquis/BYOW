package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import tileengine.TETile;
import tileengine.Tileset;

import java.awt.*;
import java.text.CharacterIterator;
import java.text.StringCharacterIterator;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class World {
    TETile[][] world;
    List<Room> rooms = new ArrayList<>();
    public static final int W = 90;
    public static final int H = 50;
    private boolean avatarCreated = false;
    Avatar avatar;
    private int avatarX;
    private int avatarY;
    private int floorAmount;
    private String charMovements = "";
    private boolean quitGame;

    // build your own world!
    public World(Long seed) {
        world = new TETile[W][H]; // create new world of TETiles

        // create blank world
        for (int col = 0; col < W; col++) {
            for (int row = 0; row < H; row++) {
                world[col][row] = Tileset.NOTHING; // fill world with nothing tiles
            }
        }
        generateWorld(seed); // generate world
        // find amount of floors
        for (int col = 0; col < W; col++) {
            for (int row = 0; row < H; row++) {
                if (world[col][row] == Tileset.FLOOR) {
                    floorAmount++;
                }
            }
        }
    }

    public void runGame() {
        TERenderer ter = new TERenderer();
        ter.initialize(World.W, World.H);

        // runs game
        while (!isGameOver()) {
            moveAvatar();
            renderTileTypeAndDate();
            ter.drawTiles(world);
        }
    }

    public String tileType() {
        int mouseX = (int) StdDraw.mouseX();
        int mouseY = (int) StdDraw.mouseY();

        if (mouseX < W && mouseY < H) {
            if (world[mouseX][mouseY] == Tileset.FLOOR) {
                return "Floor";
            }
            if (world[mouseX][mouseY] == Tileset.WALL) {
                return "Wall";
            }
            if (world[mouseX][mouseY] == Tileset.NOTHING) {
                return "Nothing";
            }
            if (world[mouseX][mouseY] == Tileset.TREE) {
                return "Tree";
            }
            if (world[mouseX][mouseY] == Tileset.WATER) {
                return "Water";
            }
            if (world[mouseX][mouseY] == Tileset.MOUNTAIN) {
                return "Mountain";
            }
            if (world[mouseX][mouseY] == Tileset.AVATAR) {
                return "You";
            }
        }
        return "";
    }

    public void renderTileTypeAndDate() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
        LocalDateTime dt = LocalDateTime.now();

        StdDraw.setPenColor(Color.WHITE);
        Font font = new Font("Arial", Font.PLAIN, 18);
        StdDraw.setFont(font);
        StdDraw.text(84, 49, dtf.format(dt));
        StdDraw.text(4, 49, "Tile: " + tileType());
        StdDraw.show();
    }

    public boolean isGameOver() {
        // check if game is over
        return floorAmount == 0 || quitGame;
    }


    public boolean isValidMove(int newX, int newY) {
        // checks if the next input move for avatar is valid
        return world[newX][newY] != Tileset.WALL && world[newX][newY] != Tileset.WATER
                && world[newX][newY] != Tileset.TREE && world[newX][newY] != Tileset.MOUNTAIN;
    }

    public void loadMoves(String keyStrokes) {
        CharacterIterator charIterator = new StringCharacterIterator(keyStrokes);

        while (charIterator.current() != StringCharacterIterator.DONE) {
            if (charIterator.current() == 'w') {
                if (isValidMove(avatarX, avatarY + 1)) {
                    moveUp();
                }
                charIterator.next();
            }
            if (charIterator.current() == 'a') {
                if (isValidMove(avatarX - 1, avatarY)) {
                    moveLeft();
                }
                charIterator.next();
            }
            if (charIterator.current() == 's') {
                if (isValidMove(avatarX, avatarY - 1)) {
                    moveDown();
                }
                charIterator.next();
            }
            if (charIterator.current() == 'd') {
                if (isValidMove(avatarX + 1, avatarY)) {
                    moveRight();
                }
                charIterator.next();
            }
        }
    }

    public void moveAvatar() {
        // move avatar
        if (StdDraw.hasNextKeyTyped()) {
            char key = StdDraw.nextKeyTyped();
            if (key == ':') {
                boolean qPress = false;
                while (!qPress) {
                    if (StdDraw.hasNextKeyTyped()) {
                        char nextKey = StdDraw.nextKeyTyped();
                        if (Character.toLowerCase(nextKey) == 'q') {
                            qPress = true;
                            quitGame = true;
                        } else {
                            break;
                        }
                    }
                }
            }
            if (Character.toLowerCase(key) == 'w') {
                if (isValidMove(avatarX, avatarY + 1)) {
                    charMovements += key;
                    moveUp();
                }
            }
            if (Character.toLowerCase(key) == 'a') {
                if (isValidMove(avatarX - 1, avatarY)) {
                    charMovements += key;
                    moveLeft();
                }
            }
            if (Character.toLowerCase(key) == 's') {
                if (isValidMove(avatarX, avatarY - 1)) {
                    charMovements += key;
                    moveDown();
                }
            }
            if (Character.toLowerCase(key) == 'd') {
                if (isValidMove(avatarX + 1, avatarY)) {
                    charMovements += key;
                    moveRight();
                }
            }
        }
    }

    public void moveUp() {
        if (world[avatarX][avatarY + 1] == Tileset.FLOOR) {
            floorAmount--;
        }
        world[avatarX][avatarY] = Tileset.NOTHING;
        world[avatarX][avatarY + 1] = Tileset.AVATAR;
        avatarY = avatarY + 1;
        avatar.changeCurrentY(avatarY);
    }
    public void moveDown() {
        if (world[avatarX][avatarY - 1] == Tileset.FLOOR) {
            floorAmount--;
        }
        world[avatarX][avatarY] = Tileset.NOTHING;
        world[avatarX][avatarY - 1] = Tileset.AVATAR;
        avatarY = avatarY - 1;
        avatar.changeCurrentY(avatarY);
    }
    public void moveLeft() {
        if (world[avatarX - 1][avatarY] == Tileset.FLOOR) {
            floorAmount--;
        }
        world[avatarX][avatarY] = Tileset.NOTHING;
        world[avatarX - 1][avatarY] = Tileset.AVATAR;
        avatarX = avatarX - 1;
        avatar.changeCurrentX(avatarX);
    }
    public void moveRight() {
        if (world[avatarX + 1][avatarY] == Tileset.FLOOR) {
            floorAmount--;
        }
        world[avatarX][avatarY] = Tileset.NOTHING;
        world[avatarX + 1][avatarY] = Tileset.AVATAR;
        avatarX = avatarX + 1;
        avatar.changeCurrentX(avatarX);
    }

    public void generateWorld(Long seed) {
        Random random = new Random(seed);

        int ranRooms = random.nextInt(W) + 4;

        int ranWall = random.nextInt(4) + 1;

        for (int i = 0; i < ranRooms; i++) {
            int width = random.nextInt(10) + 3;
            int height = random.nextInt(10) + 3;

            int x = Math.max(1, random.nextInt(W - width));
            int y = Math.max(1, random.nextInt(H - height));

            Room room = new Room(x, y, width, height);

            if (validRoom(room)) {
                drawRoom(room);
                rooms.add(room);
                if (!avatarCreated) {
                    avatarCreated = true;
                    avatarX = x;
                    avatarY = y;
                }
            }
        }
        connectRoomsFromOrigin();
        wrapRoomsAndHallways(ranWall);
        createAvatar(avatarX, avatarY);
    }

    public void createAvatar(int x, int y) {
        avatar = new Avatar(x, y);
        world[x][y] = Tileset.AVATAR;
    }

    public void connectRoomsFromOrigin() {
        Room origin = new Room(0, 0, 0, 0);
        rooms.sort(new RoomComparator(origin));
        Room currRoom = rooms.get(0);
        for (Room r : rooms) {
            connectRooms(currRoom, r);
            currRoom = r;
        }
    }

    public boolean validRoom(Room room) {
        for (int x = room.getxCoord() - 2; x <= room.getxCoord() + room.getWidth() + 1; x++) {
            for (int y = room.getyCoord() - 2; y <= room.getyCoord() + room.getHeight() + 1; y++) {
                if (x >= room.getxCoord() && x < room.getxCoord() + room.getWidth()) {
                    if (y >= room.getyCoord() && y < room.getyCoord() + room.getHeight()) {
                        continue;
                    }
                }
                // check if the tile is outside the room and is a floor tile
                if (x >= 0 && x < W && y >= 0 && y < H && world[x][y] == Tileset.FLOOR) {
                    return false;
                }
            }
        }
        return true;
    }

    public void drawRoom(Room room) {
        int xStart = room.getxCoord();
        int xEnd = room.getxCoord() + room.getWidth() - 1;
        int yStart = room.getyCoord();
        int yEnd = room.getyCoord() + room.getHeight() - 1;

        for (int x = xStart; x <= xEnd; x++) {
            for (int y = yStart; y <= yEnd; y++) {
                world[x][y] = Tileset.FLOOR;
            }
        }
    }
    public void connectRooms(Room r1, Room r2) {
        int x1 = r1.xCoord;
        int y1 = r1.yCoord;
        int x2 = r2.xCoord;
        int y2 = r2.yCoord;

        drawHallway(x1, y1, x2, y2);
    }

    public void drawHallway(int xS, int yS, int xE, int yE) {
        int currX = xS;
        int currY = yS;

        while (currX != xE || currY != yE) {
            world[currX][currY] = Tileset.FLOOR;

            if (currX < xE) {
                currX++;
            } else if (currX > xE) {
                currX--;
            } else if (currY < yE) {
                currY++;
            } else if (currY > yE) {
                currY--;
            }
        }
    }

    public void wrapWithTile(int tileNum) {
        if (tileNum == 1) {
            wrap(Tileset.WALL);
        }
        if (tileNum == 2) {
            wrap(Tileset.WATER);
        }
        if (tileNum == 3) {
            wrap(Tileset.MOUNTAIN);
        }
        if (tileNum == 4) {
            wrap(Tileset.TREE);
        }
    }
    public void wrap(TETile tile) {
        for (int x = 1; x < W - 1; x++) {
            for (int y = 1; y < H - 1; y++) {

                if (world[x][y] == Tileset.FLOOR) { // stop at floor tiles

                    if (world[x - 1][y] != Tileset.FLOOR) { // check left tile
                        world[x - 1][y] = tile;
                    }
                    if (world[x + 1][y] != Tileset.FLOOR) { // check right tile
                        world[x + 1][y] = tile;
                    }
                    if (world[x][y - 1] != Tileset.FLOOR) { // check bottom tile
                        world[x][y - 1] = tile;
                    }
                    if (world[x][y + 1] != Tileset.FLOOR) { // check top tile
                        world[x][y + 1] = tile;
                    }
                    if (world[x + 1][y + 1] != Tileset.FLOOR) { // check top right tile
                        world[x + 1][y + 1] = tile;
                    }
                    if (world[x - 1][y + 1] != Tileset.FLOOR) { // check top left tile
                        world[x - 1][y + 1] = tile;
                    }
                    if (world[x - 1][y - 1] != Tileset.FLOOR) { // check bottom left tile
                        world[x - 1][y - 1] = tile;
                    }
                    if (world[x + 1][y - 1] != Tileset.FLOOR) { // check bottom right tile
                        world[x + 1][y - 1] = tile;
                    }
                }
            }
        }
    }

    public void wrapRoomsAndHallways(int tileNum) {
        wrapWithTile(tileNum);
    }

    public TETile[][] getTiles() {
        return world; // return tiles
    }
    public String getCharMovements() {
        return charMovements;
    }
}
