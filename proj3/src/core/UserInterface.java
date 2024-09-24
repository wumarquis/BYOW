package core;

import edu.princeton.cs.algs4.StdDraw;

import java.awt.*;

public class UserInterface {
    public UserInterface() {

    }

    public static void mainMenu() {
        StdDraw.setCanvasSize(World.W * 16, World.H * 16);
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.clear(Color.BLACK);

        Font font = new Font("Arial", Font.BOLD, 80);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.6, "CS61B: THE GAME");

        Font font2 = new Font("Arial", Font.BOLD, 30);
        StdDraw.setFont(font2);
        StdDraw.text(0.5, 0.45, "NEW GAME (N)");
        StdDraw.text(0.5, 0.40, "LOAD GAME (L)");
        StdDraw.text(0.5, 0.35, "REPLAY (R)");
        StdDraw.text(0.5, 0.30, "QUIT GAME (Q)");

        StdDraw.show();
    }
    public static void seedEnter(String seed) {
        StdDraw.clear(Color.BLACK);

        Font font = new Font("Arial", Font.BOLD, 80);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.55, "PLEASE ENTER SEED");

        Font font2 = new Font("Arial", Font.BOLD, 50);
        StdDraw.setFont(font2);
        StdDraw.setPenColor(Color.GREEN);
        StdDraw.text(0.5, 0.45, seed);
        StdDraw.setPenColor(Color.WHITE);

        StdDraw.show();
    }

    public static void gameOverScreen() {
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.clear(Color.BLACK);

        Font font = new Font("Arial", Font.BOLD, 80);
        StdDraw.setFont(font);
        StdDraw.text(45, 25, "GAME OVER!");

        StdDraw.show();
    }
    public static void quitScreen() {
        StdDraw.clear(Color.BLACK);

        Font font = new Font("Arial", Font.BOLD, 80);
        StdDraw.setFont(font);
        StdDraw.text(0.5, 0.45, "YOU QUIT :(");

        StdDraw.show();
    }
}
