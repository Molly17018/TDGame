/*
 * Decompiled with CFR 0.151.
 */
package main;

import java.awt.Graphics;
import main.Game;
import main.GameStates;

public class Render {
    private Game game;

    public Render(Game game) {
        this.game = game;
    }

    public void render(Graphics g) {
        switch (GameStates.gameState) {
            case MENU: {
                this.game.getMenu().render(g);
                break;
            }
            case PLAYING: {
                this.game.getPlaying().render(g);
                break;
            }
            case SETTINGS: {
                this.game.getSettings().render(g);
                break;
            }
            case EDIT: {
                this.game.getEditor().render(g);
                break;
            }
            case GAME_OVER: {
                this.game.getGameOver().render(g);
                break;
            }
        }
    }
}

