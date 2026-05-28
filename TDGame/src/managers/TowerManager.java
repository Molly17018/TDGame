/*
 * Decompiled with CFR 0.151.
 */
package managers;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import enemies.Enemy;
import helpz.LoadSave;
import helpz.Utilz;
import scenes.Playing;
import towers.Tower;
import ui.ActionBar;

public class TowerManager {
    private Playing playing;
    private ActionBar actionBar;
    private BufferedImage[] towerImgs;
    private ArrayList<Tower> towers = new ArrayList();
    private int towerAmount = 0;
    private int tickCount = 0;

    public TowerManager(Playing playing, ActionBar actionBar) {
        this.playing = playing;
        this.actionBar = actionBar;
        this.loadTowerImgs();
    }

    private void loadTowerImgs() {
        BufferedImage athlas = LoadSave.getSpriteAtlas();
        this.towerImgs = new BufferedImage[5];
        int i = 0;
        while (i < 3) {
            this.towerImgs[i] = athlas.getSubimage((4 + i) * 32, 32, 32, 32);
            ++i;
        }
        towerImgs[3] = athlas.getSubimage(2 * 32, 3 * 32, 32, 32);
        towerImgs[4] = athlas.getSubimage(4 * 32, 3 * 32, 32, 32);
    }

    public void addTower(Tower selectedTower, int xPos, int yPos) {
        this.towers.add(new Tower(xPos, yPos, this.towerAmount++, selectedTower.getTowerType()));
    }

    public void removeTower(Tower displayedTower) {
        int i = 0;
        while (i < this.towers.size()) {
            if (this.towers.get(i).getId() == displayedTower.getId()) {
                this.towers.remove(i);
            }
            ++i;
        }
    }

    public void upgradeTower(Tower displayedTower) {
        for (Tower t : this.towers) {
            if (t.getId() != displayedTower.getId()) {
				continue;
			}
            this.playing.getActionBar().removeUpgradeCost();
            t.upgradeTower();
        }
    }

    public void update() {
        for (Tower t : this.towers) {
            t.update();
            this.attackEnemyIfClose(t);
            if (t.getTowerType() == 3 || t.getTowerType() == 4) {
                updateTreeDrop(t);
            }
        }
    }

    private void updateTreeDrop(Tower t) {
		if (t.isCooldownOver()) {
			this.playing.drop(t);
			if (t.getTowerType() == 3) {
				actionBar.addCoins(10);
			} else {
				actionBar.addLives(1);
			}
			t.resteCooldown();
		}
	}

	private void attackEnemyIfClose(Tower t) {
        for (Enemy e : this.playing.getEnemyManager().getEnemies()) {
            if (!e.isAlive() || !this.isEnemyInRange(t, e) || !t.isCooldownOver()) {
				continue;
			}
            this.playing.shootEnemy(t, e);
            t.resteCooldown();
        }
    }

    private boolean isEnemyInRange(Tower t, Enemy e) {
        int range = Utilz.GetHypoDistance(t.getX(), t.getY(), e.getX(), e.getY());
        return range < t.getRange();
    }

    public void draw(Graphics g) {
        for (Tower t : this.towers) {
            g.drawImage(this.towerImgs[t.getTowerType()], t.getX(), t.getY(), null);
        }
    }
    
    public Tower getTowerAt(int x, int y) {
        for (Tower t : this.towers) {
            if (t.getX() != x || t.getY() != y) {
				continue;
			}
            return t;
        }
        return null;
    }

    public BufferedImage[] getTowerImgs() {
        return this.towerImgs;
    }

    public void reset() {
        this.towers.clear();
        this.towerAmount = 0;
    }
}

