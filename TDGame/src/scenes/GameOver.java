/*
 * Decompiled with CFR 0.151.
 */
package scenes;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

import main.Game;
import main.GameStates;
import ui.MyButton;

public class GameOver
extends GameScene
implements SceneMethods {
    private MyButton bReplay;
    private MyButton bMenu;

    public GameOver(Game game) {
        super(game);
        this.initButtons();
    }

    private void initButtons() {
        int w = 150;
        int h = w / 3;
        int x = 320 - w / 2;
        int y = 300;
        int yOffset = 100;
        this.bMenu = new MyButton("Menu", x, y, w, h);
        this.bReplay = new MyButton("Replay", x, y + yOffset, w, h);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.RED);
        g.setFont(new Font("LucidaSans", 1, 90));
        g.drawString("GAME OVER", 45, 100);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Comic Sans MS", 1, 20));
        g.drawString("Waves: " + this.game.getPlaying().getWaveManager().getWaveIndex() + 1 + " / " + this.game.getPlaying().getWaveManager().getWaves().size(), 250, 150);
        g.setFont(new Font("LucidaSans", 1, 15));
        this.bMenu.draw(g);
        this.bReplay.draw(g);
    }

    private void replayGame() {
        this.resetAll();
        GameStates.SetGameState(GameStates.PLAYING);
    }

    private void resetAll() {
        this.game.getPlaying().resetEverything();
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (this.bMenu.getBounds().contains(x, y)) {
            GameStates.SetGameState(GameStates.MENU);
            this.resetAll();
        } else if (this.bReplay.getBounds().contains(x, y)) {
            this.replayGame();
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        this.bMenu.setMouseOver(false);
        this.bReplay.setMouseOver(false);
        if (this.bMenu.getBounds().contains(x, y)) {
            this.bMenu.setMouseOver(true);
        } else if (this.bReplay.getBounds().contains(x, y)) {
            this.bReplay.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (this.bMenu.getBounds().contains(x, y)) {
            this.bMenu.setMousePressed(true);
        } else if (this.bReplay.getBounds().contains(x, y)) {
            this.bReplay.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        this.bMenu.resetBooleans();
        this.bReplay.resetBooleans();
    }

    @Override
    public void mouseDraged(int x, int y) {
    }
}

