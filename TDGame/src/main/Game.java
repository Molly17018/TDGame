/**
 * Main Class of the Project.
 * @author Molly17018
 * @version 0.1.0
 * 
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

/** 
 * Main class of this Game
 */
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

    /**
     * Constructor for the Game Class.
     */
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

    /**
     * Creates the default Level by initilizing an arry and filling it with Ones (grass tiles).
     */
    private void createDefaultLevel() {
        int[] arr = new int[400];
        int i = 0;
        while (i < arr.length) {
            arr[i] = 1;
            ++i;
        }
        LoadSave.CreateLevel(arr);
    }

    /**
     * Initates all Classes that are used in Game.
     * @see managers.TileManager
     * @see main.Render
     * @see main.GameScreen
     * @see scenes.Menu
     * @see scenes.Playing
     * @see scenes.Settings
     * @see scenes.Editing
     * @see scenes.GameOver
     */
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

    /**
     * Starts the Game thread, which is the thread the whole game runs in.
     */
    private void start() {
        this.gameThread = new Thread(this){};
        this.gameThread.start();
    }

    /**
     * Debug feature, that showes the UPS in the console.
     */
    private void callUPS() {
        if (System.currentTimeMillis() - this.lastTimeUPS >= 1000L) {
            System.out.println("UPS: " + this.updates);
            this.updates = 0;
            this.lastTimeUPS = System.currentTimeMillis();
        }
    }

    /**
     * Updates the Game, by calling the update() methods in the current gamestate.
     */
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

    /**
     * Main method. Inizilises the Game class.
     * @param args
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.gameScreen.initInputs();
        game.start();
    }

    /**
     * Run loop of the game. This is the Game loop.
     */
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

