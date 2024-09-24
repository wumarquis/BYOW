package core;

import edu.princeton.cs.algs4.StdDraw;
import tileengine.TERenderer;
import utils.FileUtils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class Main {
    private static boolean gameOver = false;
    private static boolean seedEntered = false;
    private static String worldGenNum = "";
    private static String ints = "";
    public static void endGame(String s) {
        FileUtils.writeFile("gameLoad.txt", s);
        UserInterface.gameOverScreen();
        StdDraw.pause(1000);
        System.exit(0);
    }
    public static void quitGame() {
        UserInterface.quitScreen();
        StdDraw.pause(1000);
        System.exit(0);
    }
    public static void iterateAndMove(String s, World w, TERenderer ter) {
        CharacterIterator keyIt = new StringCharacterIterator(s);
        while (keyIt.current() != CharacterIterator.DONE) {
            char curr = keyIt.current();
            String keyInput = String.valueOf(curr);
            w.loadMoves(keyInput);
            ter.renderFrame(w.getTiles());
            keyIt.next();
            StdDraw.pause(100);
        }
    }
    public static void main(String[] args) {
        UserInterface.mainMenu();
        while (!gameOver) {
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (Character.toLowerCase(key) == 'n') {
                    UserInterface.seedEnter(ints);
                    while (!seedEntered) {
                        if (StdDraw.hasNextKeyTyped()) {
                            char num = StdDraw.nextKeyTyped();
                            if (Character.toLowerCase(num) == 's') {
                                seedEntered = true;
                            } else if (Character.isDigit(num)) {
                                ints += num;
                                UserInterface.seedEnter(ints);
                            }
                        }
                    }
                    worldGenNum += ints;
                    World world = new World(Long.parseLong(ints));
                    world.runGame();
                    if (world.isGameOver()) {
                        gameOver = true;
                        worldGenNum += world.getCharMovements();
                        endGame(worldGenNum);
                    }
                }
                if (Character.toLowerCase(key) == 'l') {
                    String content = FileUtils.readFile("gameLoad.txt");
                    CharacterIterator it = new StringCharacterIterator(content);
                    String sSeed = "";
                    while (Character.isDigit(it.current())) {
                        sSeed += it.current();
                        it.next();
                    }
                    Long seed = Long.parseLong(sSeed);
                    World w = new World(seed);
                    String keyStrokes = content.substring(sSeed.length());
                    w.loadMoves(keyStrokes);
                    w.runGame();
                    if (w.isGameOver()) {
                        gameOver = true;
                        worldGenNum += content;
                        worldGenNum += w.getCharMovements();
                        endGame(worldGenNum);
                    }
                }
                if (Character.toLowerCase(key) == 'r') {
                    String content = FileUtils.readFile("gameLoad.txt");
                    CharacterIterator it = new StringCharacterIterator(content);
                    String sSeed = "";
                    while (Character.isDigit(it.current())) {
                        sSeed += it.current();
                        it.next();
                    }
                    World w = new World(Long.parseLong(sSeed));
                    String keyStrokes = content.substring(sSeed.length());
                    TERenderer ter = new TERenderer();
                    ter.initialize(World.W, World.H);
                    ter.renderFrame(w.getTiles());
                    iterateAndMove(keyStrokes, w, ter);
                    w.runGame();
                    if (w.isGameOver()) {
                        gameOver = true;
                        worldGenNum += content;
                        worldGenNum += w.getCharMovements();
                        endGame(worldGenNum);
                    }
                }
                if (Character.toLowerCase(key) == 'q') {
                    quitGame();
                }
            }
        }
    }
}
