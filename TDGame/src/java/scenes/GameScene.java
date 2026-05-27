/*
 * Decompiled with CFR 0.151.
 */
package scenes;

import main.Game;

public class GameScene {
    protected Game game;
    protected int animationIndex;
    protected int ANIMATION_SPEED = 40;
    protected int tick;

    public GameScene(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return this.game;
    }

    protected void updateTick() {
        ++this.tick;
        if (this.tick >= this.ANIMATION_SPEED) {
            this.tick = 0;
            ++this.animationIndex;
            if (this.animationIndex >= 4) {
                this.animationIndex = 0;
            }
        }
    }
}

