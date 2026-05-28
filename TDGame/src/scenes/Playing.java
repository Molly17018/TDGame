/*
 * Decompiled with CFR 0.151.
 */
package scenes;

import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.Constants;
import helpz.LoadSave;
import main.Game;
import managers.EnemyManager;
import managers.ProjectileManager;
import managers.TowerManager;
import managers.WaveManager;
import objects.PathPoint;
import towers.Tower;
import ui.ActionBar;

public class Playing
extends GameScene
implements SceneMethods {
    private int[][] lvl;
    private ActionBar actionBar;
    private EnemyManager enemyManager;
    private TowerManager towerManager;
    private ProjectileManager projectileManager;
    private WaveManager waveManager;
    private int mouseX;
    private int mouseY;
    private PathPoint start;
    private PathPoint end;
    private Enemy e;
    private Tower selectedTower;
    private int coinsTick;

    /**
     * Constructor for the class Playing
     * @param game Instance of the class game
     */
    public Playing(Game game) {
        super(game);
        this.loadDefaultLevel();
        this.actionBar = new ActionBar(0, 640, 640, 160, this);
        this.enemyManager = new EnemyManager(this, this.start, this.end);
        this.towerManager = new TowerManager(this, actionBar);
        this.projectileManager = new ProjectileManager(this);
        this.waveManager = new WaveManager(this);
    }

    /**
     * Loads the default Level from LoadSave, and sets the start and end points to spawn enemies
     * @see helpz.LoadSave
     */
    private void loadDefaultLevel() {
        this.lvl = LoadSave.GetLevelData();
        ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints();
        this.start = points.get(0);
        this.end = points.get(1);
    }

    public void update() {
        this.updateTick();
        this.enemyManager.update();
        this.towerManager.update();
        this.projectileManager.update();
        this.waveManager.update();
        ++this.coinsTick;
        if (this.coinsTick % 3600 == 0) {
            this.actionBar.addCoins(10);
        }
        if (this.isTimeForNewEnemy()) {
            this.spawnEnemy();
        }
        if (this.isAllEnemiesDead() && this.isThereMoreWaves()) {
            this.waveManager.startWaveTimer();
            if (this.isWaveTimerOver()) {
                this.waveManager.increaseWaveIndex();
                this.enemyManager.getEnemies().clear();
                this.waveManager.resetEnemyIndex();
            }
        }
    }

    private boolean isWaveTimerOver() {
        return this.waveManager.isWaveTimerOver();
    }

    private boolean isThereMoreWaves() {
        return this.waveManager.isThereMoreWaves();
    }

    private boolean isAllEnemiesDead() {
        if (this.waveManager.isThereMoreEnemiesInWave()) {
            return false;
        }
        for (Enemy e : this.enemyManager.getEnemies()) {
            if (!e.isAlive()) {
				continue;
			}
            return false;
        }
        return true;
    }

    private void spawnEnemy() {
        this.enemyManager.spawEnemy(this.waveManager.getNextEnemy());
    }

    private boolean isTimeForNewEnemy() {
        return this.waveManager.isTimeForNewEnemy() && this.waveManager.isThereMoreEnemiesInWave();
    }

    public void setSelectedTower(Tower selectedTower) {
        this.selectedTower = selectedTower;
    }

    @Override
    public void render(Graphics g) {
        this.drawLevel(g);
        this.actionBar.draw(g);
        this.enemyManager.draw(g);
        this.towerManager.draw(g);
        this.projectileManager.draw(g);
        this.drawSelectedTower(g);
    }

    private void drawSelectedTower(Graphics g) {
        if (this.selectedTower != null) {
            g.drawImage(this.towerManager.getTowerImgs()[this.selectedTower.getTowerType()], this.mouseX, this.mouseY, null);
        }
    }

    private void drawLevel(Graphics g) {
        int y = 0;
        while (y < this.lvl.length) {
            int x = 0;
            while (x < this.lvl[y].length) {
                int id = this.lvl[y][x];
                if (this.isAnimation(id)) {
                    g.drawImage(this.getSprite(id, this.animationIndex), x * 32, y * 32, null);
                } else {
                    g.drawImage(this.getSprite(id), x * 32, y * 32, null);
                }
                ++x;
            }
            ++y;
        }
    }

    private boolean isAnimation(int spriteId) {
        return this.game.getTileManager().isSpriteAnimation(spriteId);
    }

    public BufferedImage getSprite(int spriteID) {
        return this.game.getTileManager().getSprite(spriteID);
    }

    public BufferedImage getSprite(int spriteID, int animationIndex) {
        return this.game.getTileManager().getAniSprite(spriteID, animationIndex);
    }

    public int getTileType(int x, int y) {
        int xCord = x / 32;
        int yCord = y / 32;
        if (xCord < 0 || xCord > 19 || yCord < 0 || yCord > 19) {
            return 0;
        }
        int id = this.lvl[y / 32][x / 32];
        return this.game.getTileManager().getTile(id).getTileType();
    }

    public void shootEnemy(Tower t, Enemy e) {
        this.projectileManager.newProjectile(t, e);
    }
    
    public void drop(Tower t) {
    	this.projectileManager.newProjectile(t);
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            this.actionBar.mouseClicked(x, y);
        } else if (this.selectedTower != null) {
            if (this.isTileGrass(this.mouseX, this.mouseY) && this.getTowerAt(this.mouseX, this.mouseY) == null) {
                this.towerManager.addTower(this.selectedTower, this.mouseX, this.mouseY);
                this.removeCost(this.selectedTower.getTowerType());
                this.selectedTower = null;
            }
        } else {
            Tower t = this.getTowerAt(this.mouseX, this.mouseY);
            this.actionBar.displayTower(t);
        }
    }

    private void removeCost(int towerType) {
        this.actionBar.payForTower(towerType);
    }

    public void rewardPlayer(int enemyType) {
        this.actionBar.addCoins(Constants.Enemies.GetReward(enemyType));
    }

    private Tower getTowerAt(int x, int y) {
        return this.towerManager.getTowerAt(x, y);
    }

    public void removeTower(Tower displayedTower) {
        this.towerManager.removeTower(displayedTower);
    }

    public void upgadeTower(Tower displayedTower) {
        this.towerManager.upgradeTower(displayedTower);
    }

    private boolean isTileGrass(int x, int y) {
        int id = this.lvl[y / 32][x / 32];
        int tileType = this.game.getTileManager().getTile(id).getTileType();
        return tileType == 1;
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640) {
            this.actionBar.mouseMoved(x, y);
        } else {
            this.mouseX = x / 32 * 32;
            this.mouseY = y / 32 * 32;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 640) {
            this.actionBar.mousePressed(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        this.actionBar.mouseReleased(x, y);
    }

    @Override
    public void mouseDraged(int x, int y) {
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 27) {
            System.out.println("Tower deselected!");
            this.selectedTower = null;
        }
    }

    public void removeOneLive() {
        this.actionBar.remoweOneLive();
    }

    public TowerManager getTowerManager() {
        return this.towerManager;
    }

    public EnemyManager getEnemyManager() {
        return this.enemyManager;
    }

    public WaveManager getWaveManager() {
        return this.waveManager;
    }

    public ActionBar getActionBar() {
        return this.actionBar;
    }
    
    public void setLevel(int[][] lvl) {
        this.lvl = lvl;
    }

    public void resetEverything() {
        this.actionBar.resetEverything();
        this.enemyManager.reset();
        this.towerManager.reset();
        this.projectileManager.reset();
        this.waveManager.reset();
        this.mouseX = 0;
        this.mouseY = 0;
        this.selectedTower = null;
        this.coinsTick = 0;
    }
}

