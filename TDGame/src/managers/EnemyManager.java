/*
 * Decompiled with CFR 0.151.
 */
package managers;

import enemies.Bat;
import enemies.Enemy;
import enemies.Knight;
import enemies.Orc;
import enemies.Wolf;
import helpz.Constants;
import helpz.LoadSave;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import objects.PathPoint;
import scenes.Playing;

public class EnemyManager {
    private Playing playing;
    private BufferedImage[] enemyImgs;
    private BufferedImage slowEffect;
    private ArrayList<Enemy> enemies = new ArrayList();
    private PathPoint start;
    private PathPoint end;
    private int HPbarWidth = 30;

    public EnemyManager(Playing playing, PathPoint start, PathPoint end) {
        this.playing = playing;
        this.enemyImgs = new BufferedImage[4];
        this.start = start;
        this.end = end;
        this.loadEnemyImgs();
        this.loadEffectImg();
    }

    private void loadEffectImg() {
        this.slowEffect = LoadSave.getSpriteAtlas().getSubimage(288, 64, 32, 32);
    }

    private void loadEnemyImgs() {
        BufferedImage atlas = LoadSave.getSpriteAtlas();
        int i = 0;
        while (i < 4) {
            this.enemyImgs[i] = atlas.getSubimage(i * 32, 32, 32, 32);
            ++i;
        }
    }

    public void update() {
        for (Enemy e : this.enemies) {
            if (!e.isAlive()) continue;
            this.updateEnemyMove(e);
        }
    }

    public int getAmountOfAliveEnemies() {
        int size = 0;
        for (Enemy e : this.enemies) {
            if (!e.isAlive()) continue;
            ++size;
        }
        return size;
    }

    public void updateEnemyMove(Enemy e) {
        int newY;
        int newX;
        if (e.getLastDir() == -1) {
            this.setNewDirAndMove(e);
        }
        if (this.getTileType(newX = (int)(e.getX() + this.getSpeedAndWight(e.getLastDir(), e.getEnemyType())), newY = (int)(e.getY() + this.getSpeedAndHeight(e.getLastDir(), e.getEnemyType()))) == 2) {
            e.move(Constants.Enemies.GetSpeed(e.getEnemyType()), e.getLastDir());
        } else if (this.isAtEnd(e)) {
            System.out.println("Lives lost!");
            e.kill();
            this.playing.removeOneLive();
        } else {
            this.setNewDirAndMove(e);
        }
    }

    private void setNewDirAndMove(Enemy e) {
        int dir = e.getLastDir();
        int xCord = (int)(e.getX() / 32.0f);
        int yCord = (int)(e.getY() / 32.0f);
        this.fixEnemyOffset(e, dir, xCord, yCord);
        if (this.isAtEnd(e)) {
            return;
        }
        if (dir == 0 || dir == 2) {
            int newY = (int)(e.getY() + this.getSpeedAndHeight(1, e.getEnemyType()));
            if (this.getTileType((int)e.getX(), newY) == 2) {
                e.move(Constants.Enemies.GetSpeed(e.getEnemyType()), 1);
            } else {
                e.move(Constants.Enemies.GetSpeed(e.getEnemyType()), 3);
            }
        } else {
            int newX = (int)(e.getX() + this.getSpeedAndWight(2, e.getEnemyType()));
            if (this.getTileType(newX, (int)e.getY()) == 2) {
                e.move(Constants.Enemies.GetSpeed(e.getEnemyType()), 2);
            } else {
                e.move(Constants.Enemies.GetSpeed(e.getEnemyType()), 0);
            }
        }
    }

    private void fixEnemyOffset(Enemy e, int dir, int xCord, int yCord) {
        switch (dir) {
            case 2: {
                if (xCord >= 19) break;
                ++xCord;
                break;
            }
            case 3: {
                if (yCord >= 19) break;
                ++yCord;
            }
        }
        e.setPos(xCord * 32, yCord * 32);
    }

    private boolean isAtEnd(Enemy e) {
        return e.getX() == (float)(this.end.getxCord() * 32) && e.getY() == (float)(this.end.getyCord() * 32);
    }

    private int getTileType(int x, int y) {
        return this.playing.getTileType(x, y);
    }

    private float getSpeedAndHeight(int dir, int enemyType) {
        if (dir == 1) {
            return -Constants.Enemies.GetSpeed(enemyType);
        }
        if (dir == 3) {
            return Constants.Enemies.GetSpeed(enemyType) + 32.0f;
        }
        return 0.0f;
    }

    private float getSpeedAndWight(int dir, int enemyType) {
        if (dir == 0) {
            return -Constants.Enemies.GetSpeed(enemyType);
        }
        if (dir == 2) {
            return Constants.Enemies.GetSpeed(enemyType) + 32.0f;
        }
        return 0.0f;
    }

    public void addEnemy(int enemyType) {
        int x = this.start.getxCord() * 32;
        int y = this.start.getyCord() * 32;
        switch (enemyType) {
            case 0: {
                this.enemies.add(new Orc(x, y, 0, this));
                break;
            }
            case 1: {
                this.enemies.add(new Bat(x, y, 0, this));
                break;
            }
            case 2: {
                this.enemies.add(new Knight(x, y, 0, this));
                break;
            }
            case 3: {
                this.enemies.add(new Wolf(x, y, 0, this));
            }
        }
    }

    public void spawEnemy(int nextEnemy) {
        this.addEnemy(nextEnemy);
    }

    public void draw(Graphics g) {
        for (Enemy e : this.enemies) {
            if (!e.isAlive()) continue;
            this.drawEnemy(e, g);
            this.drawHealthBar(e, g);
            this.drawEffects(e, g);
        }
    }

    private void drawEffects(Enemy e, Graphics g) {
        if (e.isSlowed()) {
            g.drawImage(this.slowEffect, (int)e.getX(), (int)e.getY(), null);
        }
    }

    private void drawHealthBar(Enemy e, Graphics g) {
        g.setColor(Color.RED);
        g.fillRect((int)e.getX() + 2, (int)e.getY() - 3, this.getNewBarWidth(e), 2);
    }

    private int getNewBarWidth(Enemy e) {
        return (int)((float)this.HPbarWidth * e.getHealthBarFloat());
    }

    private void drawEnemy(Enemy e, Graphics g) {
        g.drawImage(this.enemyImgs[e.getEnemyType()], (int)e.getX(), (int)e.getY(), null);
    }

    public void rewardPlayer(int enemyType) {
        this.playing.rewardPlayer(enemyType);
    }

    public ArrayList<Enemy> getEnemies() {
        return this.enemies;
    }

    public void reset() {
        this.enemies.clear();
    }
}

