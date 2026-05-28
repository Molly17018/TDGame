/*
 * Decompiled with CFR 0.151.
 */
package ui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.text.DecimalFormat;

import helpz.Constants;
import main.GameStates;
import scenes.Playing;
import towers.Tower;

public class ActionBar
extends Bar {
    private Playing playing;
    private MyButton bMenu;
    private MyButton[] towerButtons;
    private Tower selectedTower;
    private Tower displayedTower;
    private MyButton sellTower;
    private MyButton upgradeTower;
    private DecimalFormat formatter;
    private int coins = 2000; //TODO set back to 200
    private boolean showTowerCost;
    private int towerCostType;
    private int cost;
    private int lives = 25;

    public ActionBar(int x, int y, int width, int height, Playing playing) {
        super(x, y, width, height);
        this.playing = playing;
        this.formatter = new DecimalFormat("0.0");
        this.initButtons();
    }

    private void initButtons() {
        this.bMenu = new MyButton("Menu", 2, 642, 100, 30);
        this.towerButtons = new MyButton[5];
        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int)(w * 1.1f);
        int i = 0;
        while (i < this.towerButtons.length) {
            this.towerButtons[i] = new MyButton("", xStart + xOffset * i, yStart, w, h, i);
            ++i;
        }
        this.sellTower = new MyButton("Sell", 430, 730, 60, 30);
        this.upgradeTower = new MyButton("Upgrade", 540, 730, 60, 30);
    }

    public void remoweOneLive() {
        --this.lives;
        if (this.lives <= 0) {
            System.out.println("Game over!");
            GameStates.SetGameState(GameStates.GAME_OVER);
        }
    }

    private void drawButtons(Graphics g) {
        this.bMenu.draw(g);
        MyButton[] myButtonArray = this.towerButtons;
        int n = this.towerButtons.length;
        int n2 = 0;
        while (n2 < n) {
            MyButton b = myButtonArray[n2];
            g.setColor(Color.GRAY);
            g.fillRect(b.x, b.y, b.width, b.height);
            g.drawImage(this.playing.getTowerManager().getTowerImgs()[b.getId()], b.x, b.y, b.width, b.height, null);
            this.drawButtonFeedback(g, b);
            ++n2;
        }
    }

    public void draw(Graphics g) {
        g.setColor(new Color(55, 55, 55));
        g.fillRect(this.x, this.y, this.width, this.height);
        this.drawButtons(g);
        this.drawDisplayedTower(g);
        this.drawWaveInfo(g);
        this.drawCoinInfo(g);
        if (this.showTowerCost) {
            this.drawTowerCost(g);
        }
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", 1, 12));
        g.drawString("Lives: " + this.lives, 20, 710);
    }

    private void drawTowerCost(Graphics g) {
        g.setColor(Color.LIGHT_GRAY);
        g.fillRect(165, 710, 105, 35);
        g.setColor(Color.BLACK);
        g.drawRect(165, 710, 105, 35);
        g.setColor(Color.BLACK);
        g.setFont(new Font("Comic Sans MS", 1, 15));
        g.drawString(this.getTowerCostName(), 170, 725);
        g.drawString("Cost: " + this.getTowerCost(), 170, 740);
        if (this.isTowerCostMoreThanCurrentCoins()) {
            g.setColor(Color.LIGHT_GRAY);
            g.fillRect(165, 750, 105, 35);
            g.setColor(Color.BLACK);
            g.drawRect(165, 750, 105, 35);
            g.setColor(Color.RED);
            g.setFont(new Font("Comic Sans MS", 1, 17));
            g.drawString("Not enough", 170, 765);
            g.drawString(" coins", 190, 781);
        }
    }

    private boolean isTowerCostMoreThanCurrentCoins() {
        return this.getTowerCost() > this.coins;
    }

    private int getTowerCost() {
        this.cost = Constants.Towers.GetTowerCost(this.towerCostType);
        return this.cost;
    }

    private String getTowerCostName() {
        String name = Constants.Towers.GetName(this.towerCostType);
        return name;
    }

    private void drawCoinInfo(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", 1, 12));
        g.drawString("Coins: " + this.coins, 20, 725);
    }

    private void drawWaveInfo(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Comic Sans MS", 1, 12));
        this.drawWaveTimerInfo(g);
        this.drawWavesLeftInfo(g);
        this.drawEnemiesLeftInfo(g);
    }

    private void drawWaveTimerInfo(Graphics g) {
        if (this.playing.getWaveManager().isWaveTimerStarted()) {
            float timeLeft = this.playing.getWaveManager().getTimeLeft();
            String formatedText = this.formatter.format(timeLeft);
            g.drawString("Next Wave: " + formatedText, 20, 740);
        }
    }

    private void drawWavesLeftInfo(Graphics g) {
        int current = this.playing.getWaveManager().getWaveIndex();
        int size = this.playing.getWaveManager().getWaves().size();
        g.drawString("Wave: " + (current + 1) + " / " + size, 20, 755);
    }

    private void drawEnemiesLeftInfo(Graphics g) {
        int current = this.playing.getEnemyManager().getAmountOfAliveEnemies();
        int size = this.playing.getEnemyManager().getEnemies().size();
        g.drawString("Enemies: " + current + " / " + size, 20, 770);
    }

    private void drawDisplayedTower(Graphics g) {
        if (this.displayedTower != null) {
            g.setColor(Color.GRAY);
            g.fillRect(415, 645, 200, 80);
            g.setColor(Color.BLACK);
            g.drawRect(415, 645, 200, 80);
            g.drawImage(this.playing.getTowerManager().getTowerImgs()[this.displayedTower.getTowerType()], 420, 650, 50, 50, null);
            g.setFont(new Font("Comic Sans MS", 1, 15));
            g.drawString("Type: " + Constants.Towers.GetName(this.displayedTower.getTowerType()), 480, 660);
            g.setFont(new Font("Comic Sans MS", 0, 14));
            g.drawString("ID: " + this.displayedTower.getId(), 480, 677);
            g.drawString("Tier: " + this.displayedTower.getTier(), 530, 677);
            g.drawString("Damage: " + this.displayedTower.getDmg(), 480, 690);
            g.drawString("Cooldown: " + this.displayedTower.getCooldown(), 480, 703);
            g.drawString("Range: " + this.displayedTower.getRange(), 480, 716);
            this.drawDisplayedTowerBorder(g);
            this.drawDisplayedTowerRange(g);
            this.sellTower.draw(g);
            this.drawButtonFeedback(g, this.sellTower);
            if (this.displayedTower.getTier() < 3 && this.coins >= this.getUpgradeAmount(this.displayedTower)) {
                this.upgradeTower.draw(g);
                this.drawButtonFeedback(g, this.upgradeTower);
            }
            if (this.sellTower.isMouseOver()) {
                int amount = this.getSellAmount(this.displayedTower);
                g.setColor(Color.RED);
                g.drawString("Sell for: " + amount, 430, 780);
            } else if (this.upgradeTower.isMouseOver() && this.coins >= this.getUpgradeAmount(this.displayedTower)) {
                int amount = this.getUpgradeAmount(this.displayedTower);
                g.setColor(Color.GREEN);
                g.drawString("Upgrade: " + amount, 540, 780);
            }
        }
    }

    private int getSellAmount(Tower displayedTower) {
        int upgradeCost = (displayedTower.getTier() - 1) * this.getUpgradeAmount(displayedTower);
        upgradeCost = (int)(upgradeCost * 0.5f);
        return Constants.Towers.GetTowerCost(displayedTower.getTowerType()) / 2 + upgradeCost;
    }

    private int getUpgradeAmount(Tower displayedTower) {
        int amount = (int)(Constants.Towers.GetTowerCost(displayedTower.getTowerType()) * 0.5f);
        return amount;
    }

    public void removeUpgradeCost() {
        this.coins -= this.getUpgradeAmount(this.displayedTower);
    }

    private void drawDisplayedTowerRange(Graphics g) {
        g.setColor(new Color(0, 188, 255));
        g.drawOval(this.displayedTower.getX() + 16 - (int)this.displayedTower.getRange(), this.displayedTower.getY() + 16 - (int)this.displayedTower.getRange(), (int)this.displayedTower.getRange() * 2, (int)this.displayedTower.getRange() * 2);
    }

    private void drawDisplayedTowerBorder(Graphics g) {
        g.setColor(new Color(255, 88, 88));
        g.drawRect(this.displayedTower.getX(), this.displayedTower.getY(), 32, 32);
    }

    public void displayTower(Tower t) {
        this.displayedTower = t;
    }

    public void payForTower(int towerType) {
        this.coins -= Constants.Towers.GetTowerCost(towerType);
    }

    private void sellTowerClicked() {
        this.playing.removeTower(this.displayedTower);
        this.coins += Constants.Towers.GetTowerCost(this.displayedTower.getTowerType()) / 2;
        int upgradeCost = (this.displayedTower.getTier() - 1) * this.getUpgradeAmount(this.displayedTower);
        upgradeCost = (int)(upgradeCost * 0.5f);
        this.coins += upgradeCost;
        this.displayedTower = null;
    }

    private void upgradeTowerClicked() {
        this.playing.upgadeTower(this.displayedTower);
    }

    public void mouseClicked(int x, int y) {
        if (this.bMenu.getBounds().contains(x, y)) {
            GameStates.SetGameState(GameStates.MENU);
        } else {
            if (this.displayedTower != null) {
                if (this.sellTower.getBounds().contains(x, y)) {
                    this.sellTowerClicked();
                    return;
                }
                if (this.upgradeTower.getBounds().contains(x, y) && this.displayedTower.getTier() < 3 && this.coins >= this.getUpgradeAmount(this.displayedTower)) {
                    this.upgradeTowerClicked();
                    return;
                }
            }
            MyButton[] myButtonArray = this.towerButtons;
            int n = this.towerButtons.length;
            int n2 = 0;
            while (n2 < n) {
                MyButton b = myButtonArray[n2];
                if (b.getBounds().contains(x, y)) {
                    if (!this.isCoinsEnough(b.getId())) {
                        return;
                    }
                    this.selectedTower = new Tower(0, 0, -1, b.getId());
                    this.playing.setSelectedTower(this.selectedTower);
                    return;
                }
                ++n2;
            }
        }
    }

    private boolean isCoinsEnough(int towerType) {
        return this.coins >= Constants.Towers.GetTowerCost(towerType);
    }

    public void mouseMoved(int x, int y) {
        MyButton b;
        this.bMenu.setMouseOver(false);
        this.sellTower.setMouseOver(false);
        this.upgradeTower.setMouseOver(false);
        this.showTowerCost = false;
        MyButton[] myButtonArray = this.towerButtons;
        int n = this.towerButtons.length;
        int n2 = 0;
        while (n2 < n) {
            b = myButtonArray[n2];
            b.setMouseOver(false);
            ++n2;
        }
        if (this.bMenu.getBounds().contains(x, y)) {
            this.bMenu.setMouseOver(true);
        } else {
            if (this.displayedTower != null) {
                if (this.sellTower.getBounds().contains(x, y)) {
                    this.sellTower.setMouseOver(true);
                    return;
                }
                if (this.upgradeTower.getBounds().contains(x, y) && this.displayedTower.getTier() < 3) {
                    this.upgradeTower.setMouseOver(true);
                    return;
                }
            }
            myButtonArray = this.towerButtons;
            n = this.towerButtons.length;
            n2 = 0;
            while (n2 < n) {
                b = myButtonArray[n2];
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                    this.showTowerCost = true;
                    this.towerCostType = b.getId();
                    return;
                }
                ++n2;
            }
        }
    }

    public void mousePressed(int x, int y) {
        if (this.bMenu.getBounds().contains(x, y)) {
            this.bMenu.setMousePressed(true);
        } else {
            if (this.displayedTower != null) {
                if (this.sellTower.getBounds().contains(x, y)) {
                    this.sellTower.setMousePressed(true);
                    return;
                }
                if (this.upgradeTower.getBounds().contains(x, y) && this.displayedTower.getTier() < 3) {
                    this.upgradeTower.setMousePressed(true);
                    return;
                }
            }
            MyButton[] myButtonArray = this.towerButtons;
            int n = this.towerButtons.length;
            int n2 = 0;
            while (n2 < n) {
                MyButton b = myButtonArray[n2];
                if (b.getBounds().contains(x, y)) {
                    b.setMouseOver(true);
                }
                ++n2;
            }
        }
    }

    public void mouseReleased(int x, int y) {
        this.bMenu.resetBooleans();
        MyButton[] myButtonArray = this.towerButtons;
        int n = this.towerButtons.length;
        int n2 = 0;
        while (n2 < n) {
            MyButton b = myButtonArray[n2];
            b.resetBooleans();
            ++n2;
        }
        this.sellTower.resetBooleans();
        this.upgradeTower.resetBooleans();
    }

    public void addCoins(int getReward) {
        this.coins += getReward;
    }

    public int getLives() {
        return this.lives;
    }
    
	public void addLives(int amount) {
		this.lives += amount;
	}

    public void resetEverything() {
        this.coins = 200;
        this.showTowerCost = false;
        this.towerCostType = 0;
        this.lives = 25;
        this.selectedTower = null;
        this.displayedTower = null;
    }

}

