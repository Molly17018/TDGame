/*
 * Decompiled with CFR 0.151.
 */
package main;

import javax.swing.JFrame;

import helpz.LoadSave;
import managers.TileManager;
import scenes.Editing;
import scenes.GameOver;
import scenes.Menu;
import scenes.Playing;
import scenes.Settings;

public class Game
extends JFrame
implements Runnable {
    private GameScreen gameScreen;
    private int updates;
    private long lastTimeUPS;
    private Thread gameThread;
    private final double FPS_SET = 120.0;
    private final double UPS_SET = 60.0;
    private Render render;
    private Menu menu;
    private Playing playing;
    private Settings settings;
    private Editing editing;
    private GameOver gameOver;
    private TileManager tileManager;

    public Game() {
        LoadSave.CreateFolder();
        this.createDefaultLevel();
        this.initClasses();
        this.setDefaultCloseOperation(3);
        this.setLocationRelativeTo(null);
        this.setResizable(false);
        this.setTitle("2D Tower Defence");
        this.add(this.gameScreen);
        this.pack();
        this.setVisible(true);
    }

    private void createDefaultLevel() {
        int[] arr = new int[400];
        int i = 0;
        while (i < arr.length) {
            arr[i] = 0;
            ++i;
        }
        LoadSave.CreateLevel(arr);
    }

    private void initClasses() {
        this.tileManager = new TileManager();
        this.render = new Render(this);
        this.gameScreen = new GameScreen(this);
        this.menu = new Menu(this);
        this.playing = new Playing(this);
        this.settings = new Settings(this);
        this.editing = new Editing(this);
        this.gameOver = new GameOver(this);
    }

    private void start() {
        this.gameThread = new Thread(this){};
        this.gameThread.start();
    }

    private void callUPS() {
        if (System.currentTimeMillis() - this.lastTimeUPS >= 1000L) {
            System.out.println("UPS: " + this.updates);
            this.updates = 0;
            this.lastTimeUPS = System.currentTimeMillis();
        }
    }

    private void updateGame() {
        switch (GameStates.gameState) {
            case EDIT: {
                this.editing.update();
                break;
            }
            case MENU: {
                break;
            }
            case PLAYING: {
                this.playing.update();
                break;
            }
            case SETTINGS: {
                break;
            }
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();
    }

    @Override
    public void run() {
        long lastFrame = System.nanoTime();
        long lastUpdate = System.nanoTime();
        double timePerFrame = 8333333.333333333;
        double timePerUpdate = 1.6666666666666666E7;
        int frames = 0;
        int updates = 0;
        long lastTimeCheck = System.currentTimeMillis();
        while (true) {
            long now;
            if ((now = System.nanoTime()) - lastFrame >= timePerFrame) {
                this.repaint();
                lastFrame = now;
                ++frames;
            }
            if (now - lastUpdate >= timePerUpdate) {
                this.updateGame();
                lastUpdate = now;
                ++updates;
            }
            if (System.currentTimeMillis() - lastTimeCheck < 1000L) {
				continue;
			}
            System.out.println("FPS: " + frames + " | UPS: " + updates);
            frames = 0;
            updates = 0;
            lastTimeCheck = System.currentTimeMillis();
        }
    }

    public Render getRender() {
        return this.render;
    }

    public Menu getMenu() {
        return this.menu;
    }

    public Playing getPlaying() {
        return this.playing;
    }

    public Settings getSettings() {
        return this.settings;
    }

    public Editing getEditor() {
        return this.editing;
    }

    public GameOver getGameOver() {
        return this.gameOver;
    }

    public TileManager getTileManager() {
        return this.tileManager;
    }
}

