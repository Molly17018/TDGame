/*
 * Decompiled with CFR 0.151.
 */
package scenes;

import java.awt.Color;
import java.awt.Graphics;

import main.Game;
import main.GameStates;
import ui.MyButton;

public class Settings
extends GameScene
implements SceneMethods {
    private MyButton bMenu;

    public Settings(Game game) {
        super(game);
        this.initButtons();
    }

    private void initButtons() {
        this.bMenu = new MyButton("Menu", 2, 2, 100, 30);
    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect(0, 0, 640, 640);
        this.drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        this.bMenu.draw(g);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (this.bMenu.getBounds().contains(x, y)) {
            GameStates.SetGameState(GameStates.MENU);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        this.bMenu.setMouseOver(false);
        if (this.bMenu.getBounds().contains(x, y)) {
            this.bMenu.setMouseOver(true);
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (this.bMenu.getBounds().contains(x, y)) {
            this.bMenu.setMousePressed(true);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        this.resetButtons();
    }

    private void resetButtons() {
        this.bMenu.resetBooleans();
    }

    @Override
    public void mouseDraged(int x, int y) {
    }
}

