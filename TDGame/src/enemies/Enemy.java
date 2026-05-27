/*
 * Decompiled with CFR 0.151.
 */
package enemies;

import helpz.Constants;
import java.awt.Rectangle;
import managers.EnemyManager;

public abstract class Enemy {
    protected EnemyManager enemyManager;
    protected float x;
    protected float y;
    protected Rectangle bounds;
    protected int health;
    protected int maxHealth;
    protected int ID;
    protected int enemyType;
    protected int lastDir;
    protected int slowTickLimit;
    protected int slowTick;
    protected boolean alive;

    public Enemy(float x, float y, int ID, int enemyType, EnemyManager enemyManager) {
        this.slowTick = this.slowTickLimit = 120;
        this.alive = true;
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.enemyType = enemyType;
        this.enemyManager = enemyManager;
        this.bounds = new Rectangle((int)x, (int)x, 32, 32);
        this.setDefaultHealth();
        this.lastDir = -1;
    }

    private void setDefaultHealth() {
        this.maxHealth = this.health = Constants.Enemies.GetDefaultHealth(this.enemyType);
    }

    public void hurt(int dmg) {
        this.health -= dmg;
        if (this.health <= 0) {
            this.alive = false;
            this.enemyManager.rewardPlayer(this.enemyType);
        }
    }

    public void kill() {
        this.health = 0;
        this.alive = false;
    }

    public void slow() {
        this.slowTick = 0;
    }

    public void move(float speed, int dir) {
        this.lastDir = dir;
        if (this.slowTick < this.slowTickLimit) {
            ++this.slowTick;
            speed *= 0.25f;
        }
        switch (dir) {
            case 0: {
                this.x -= speed;
                break;
            }
            case 1: {
                this.y -= speed;
                break;
            }
            case 2: {
                this.x += speed;
                break;
            }
            case 3: {
                this.y += speed;
            }
        }
        this.updateHitbox();
    }

    private void updateHitbox() {
        this.bounds.x = (int)this.x;
        this.bounds.y = (int)this.y;
    }

    public void setPos(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public float getHealthBarFloat() {
        return (float)this.health / (float)this.maxHealth;
    }

    public float getX() {
        return this.x;
    }

    public float getY() {
        return this.y;
    }

    public Rectangle getBounds() {
        return this.bounds;
    }

    public int getHealth() {
        return this.health;
    }

    public int getID() {
        return this.ID;
    }

    public int getEnemyType() {
        return this.enemyType;
    }

    public int getLastDir() {
        return this.lastDir;
    }

    public boolean isAlive() {
        return this.alive;
    }

    public boolean isSlowed() {
        return this.slowTick < this.slowTickLimit;
    }
}

