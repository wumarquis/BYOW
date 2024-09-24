package core;

import tileengine.TETile;
import tileengine.Tileset;
import utils.FileUtils;

import java.text.CharacterIterator;
import java.text.StringCharacterIterator;

public class AutograderBuddy {

    /**
     * Simulates a game, but doesn't render anything or call any StdDraw
     * methods. Instead, returns the world that would result if the input string
     * had been typed on the keyboard.
     *
     * Recall that strings ending in ":q" should cause the game to quit and
     * save. To "quit" in this method, save the game to a file, then just return
     * the TETile[][]. Do not call System.exit(0) in this method.
     *
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public static TETile[][] getWorldFromInput(String input) {
        String in = input.substring(1);
        String nOrL = input.substring(0, 1);
        CharacterIterator it = new StringCharacterIterator(in);
        String seed = "";
        String keyStrokes = "";
        String saveString = "";

        if (nOrL.equals("n") || nOrL.equals("N")) {
            while (it.current() != StringCharacterIterator.DONE) {
                char curr = it.current();
                if (Character.isDigit(it.current())) {
                    seed += curr;
                }
                if (Character.isLetter(it.current())) {
                    keyStrokes += curr;
                }
                if (it.current() == ':') {
                    it.next();
                    char q = it.current();
                    if (Character.toLowerCase(q) == 'q') {
                        saveString += seed;
                        saveString += keyStrokes;
                        FileUtils.writeFile("gameLoad.txt", saveString);
                    }
                }
                it.next();
            }
        }
        if (nOrL.equals("l") || nOrL.equals("L")) {
            String content = FileUtils.readFile("gameLoad.txt");
            CharacterIterator contIt = new StringCharacterIterator(content);

            while (contIt.current() != StringCharacterIterator.DONE) {
                char curr = contIt.current();
                if (Character.isDigit(contIt.current())) {
                    seed += curr;
                }
                if (Character.isLetter(contIt.current())) {
                    keyStrokes += curr;
                }
                contIt.next();
            }
            while (it.current() != StringCharacterIterator.DONE) {
                char curr = it.current();
                if (Character.isLetter(curr)) {
                    keyStrokes += curr;
                }
                if (curr == ':') {
                    it.next();
                    char q = it.current();
                    if (Character.toLowerCase(q) == 'q') {
                        saveString += content;
                        saveString += keyStrokes;
                        FileUtils.writeFile("gameLoad.txt", saveString);
                    }
                }
                it.next();
            }
        }

        Long lSeed = Long.parseLong(seed);
        World world = new World(lSeed);

        world.loadMoves(keyStrokes);

        return world.getTiles();
    }


    /**
     * Used to tell the autograder which tiles are the floor/ground (including
     * any lights/items resting on the ground). Change this
     * method if you add additional tiles.
     */
    public static boolean isGroundTile(TETile t) {
        return t.character() == Tileset.FLOOR.character()
                || t.character() == Tileset.AVATAR.character()
                || t.character() == Tileset.FLOWER.character();
    }

    /**
     * Used to tell the autograder while tiles are the walls/boundaries. Change
     * this method if you add additional tiles.
     */
    public static boolean isBoundaryTile(TETile t) {
        return t.character() == Tileset.WALL.character()
                || t.character() == Tileset.LOCKED_DOOR.character()
                || t.character() == Tileset.UNLOCKED_DOOR.character();
    }
}
