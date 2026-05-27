/*
 * Decompiled with CFR 0.151.
 */
package ui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import helpz.LoadSave;
import main.GameStates;
import objects.Tile;
import scenes.Editing;

public class ToolBar
extends Bar {
    private Editing editing;
    private MyButton bMenu;
    private MyButton bSave;
    private MyButton bPathStart;
    private MyButton bPathEnd;
    private BufferedImage pathStart;
    private BufferedImage pathEnd;
    private Tile selectedTile;
    private Map<MyButton, ArrayList<Tile>> map = new HashMap<>();
    private MyButton bGrass;
    private MyButton bWater;
    private MyButton bRoad;
    private MyButton bCorner;
    private MyButton bIsland;
    private MyButton bBeach;
    private MyButton bWaterC;
    private MyButton currendButton;
    private int currendIndex = 0;

    public ToolBar(int x, int y, int width, int height, Editing editing) {
        super(x, y, width, height);
        this.editing = editing;
        this.initPathImgs();
        this.initButtons();
    }

    private void initPathImgs() {
        this.pathStart = LoadSave.getSpriteAtlas().getSubimage(224, 64, 32, 32);
        this.pathEnd = LoadSave.getSpriteAtlas().getSubimage(256, 64, 32, 32);
    }

    private void initButtons() {
        this.bMenu = new MyButton("Menu", 2, 642, 100, 30);
        this.bSave = new MyButton("Save", 2, 674, 100, 30);
        int w = 50;
        int h = 50;
        int xStart = 110;
        int yStart = 650;
        int xOffset = (int)(w * 1.1f);
        int i = 0;
        this.bGrass = new MyButton("Grass", xStart, yStart, w, h, i++);
        this.bWater = new MyButton("Water", xStart + xOffset, yStart, w, h, i++);
        this.initMapButton(this.bRoad, this.editing.getGame().getTileManager().getRoadsS(), xStart, yStart, xOffset, w, h, i++);
        this.initMapButton(this.bCorner, this.editing.getGame().getTileManager().getRoadsC(), xStart, yStart, xOffset, w, h, i++);
        this.initMapButton(this.bWaterC, this.editing.getGame().getTileManager().getWaterC(), xStart, yStart, xOffset, w, h, i++);
        this.initMapButton(this.bBeach, this.editing.getGame().getTileManager().getBeaches(), xStart, yStart, xOffset, w, h, i++);
        this.initMapButton(this.bIsland, this.editing.getGame().getTileManager().getIslands(), xStart, yStart, xOffset, w, h, i++);
        this.bPathStart = new MyButton("Path_Start", xStart, yStart + xOffset, w, h, i++);
        this.bPathEnd = new MyButton("Path_End", xStart + xOffset, yStart + xOffset, w, h, i++);
    }

    private void initMapButton(MyButton b, ArrayList<Tile> list, int x, int y, int xOff, int w, int h, int id) {
        b = new MyButton("", x + xOff * id, y, w, h, id);
        this.map.put(b, list);
    }

    private void saveLevel() {
        this.editing.saveLevel();
    }

    public void rotateSprite() {
        ++this.currendIndex;
        if (this.currendIndex >= this.map.get(this.currendButton).size()) {
            this.currendIndex = 0;
        }
        this.selectedTile = this.map.get(this.currendButton).get(this.currendIndex);
        this.editing.setSelectedTile(this.selectedTile);
    }

    public void draw(Graphics g) {
        g.setColor(new Color(55, 55, 55));
        g.fillRect(this.x, this.y, this.width, this.height);
        this.drawButtons(g);
    }

    private void drawButtons(Graphics g) {
        this.bMenu.draw(g);
        this.bSave.draw(g);
        this.drawNormalButton(g, this.bGrass);
        this.drawNormalButton(g, this.bWater);
        this.drawSelectedTile(g);
        this.drawMapButtons(g);
        this.drawPathButtons(g, this.bPathStart, this.pathStart);
        this.drawPathButtons(g, this.bPathEnd, this.pathEnd);
    }

    private void drawPathButtons(Graphics g, MyButton b, BufferedImage img) {
        g.drawImage(img, b.x, b.y, b.width, b.height, null);
        this.drawButtonFeedback(g, b);
    }

    private void drawNormalButton(Graphics g, MyButton b) {
        g.drawImage(this.getButtImg(b.getId()), b.x, b.y, b.width, b.height, null);
        this.drawButtonFeedback(g, b);
    }

    private void drawMapButtons(Graphics g) {
        for (Map.Entry<MyButton, ArrayList<Tile>> entry : this.map.entrySet()) {
            MyButton b = entry.getKey();
            BufferedImage img = entry.getValue().get(0).getSprite();
            g.drawImage(img, b.x, b.y, b.width, b.height, null);
            this.drawButtonFeedback(g, b);
        }
    }

    private void drawSelectedTile(Graphics g) {
        if (this.selectedTile != null) {
            g.drawImage(this.selectedTile.getSprite(), 550, 650, 50, 50, null);
            g.setColor(Color.BLACK);
            g.drawRect(550, 650, 50, 50);
        }
    }

    public BufferedImage getButtImg(int id) {
        return this.editing.getGame().getTileManager().getSprite(id);
    }

    public void mouseClicked(int x, int y) {
        if (this.bMenu.getBounds().contains(x, y)) {
            GameStates.SetGameState(GameStates.MENU);
        } else if (this.bSave.getBounds().contains(x, y)) {
            this.saveLevel();
        } else if (this.bWater.getBounds().contains(x, y)) {
            this.selectedTile = this.editing.getGame().getTileManager().getTile(this.bWater.getId());
            this.editing.setSelectedTile(this.selectedTile);
        } else if (this.bGrass.getBounds().contains(x, y)) {
            this.selectedTile = this.editing.getGame().getTileManager().getTile(this.bGrass.getId());
            this.editing.setSelectedTile(this.selectedTile);
        } else if (this.bPathStart.getBounds().contains(x, y)) {
            this.selectedTile = new Tile(this.pathStart, -1, -1);
            this.editing.setSelectedTile(this.selectedTile);
        } else if (this.bPathEnd.getBounds().contains(x, y)) {
            this.selectedTile = new Tile(this.pathEnd, -2, -2);
            this.editing.setSelectedTile(this.selectedTile);
        } else {
            for (MyButton b : this.map.keySet()) {
                if (!b.getBounds().contains(x, y)) {
					continue;
				}
                this.selectedTile = this.map.get(b).get(0);
                this.editing.setSelectedTile(this.selectedTile);
                this.currendButton = b;
                this.currendIndex = 0;
                return;
            }
        }
    }

    public void mouseMoved(int x, int y) {
        this.bMenu.setMouseOver(false);
        this.bSave.setMouseOver(false);
        this.bWater.setMouseOver(false);
        this.bGrass.setMouseOver(false);
        this.bPathStart.setMouseOver(false);
        this.bPathEnd.setMouseOver(false);
        for (MyButton b : this.map.keySet()) {
            b.setMouseOver(false);
        }
        if (this.bMenu.getBounds().contains(x, y)) {
            this.bMenu.setMouseOver(true);
        } else if (this.bSave.getBounds().contains(x, y)) {
            this.bSave.setMouseOver(true);
        } else if (this.bWater.getBounds().contains(x, y)) {
            this.bWater.setMouseOver(true);
        } else if (this.bGrass.getBounds().contains(x, y)) {
            this.bGrass.setMouseOver(true);
        } else if (this.bPathStart.getBounds().contains(x, y)) {
            this.bPathStart.setMouseOver(true);
        } else if (this.bPathEnd.getBounds().contains(x, y)) {
            this.bPathEnd.setMouseOver(true);
        } else {
            for (MyButton b : this.map.keySet()) {
                if (!b.getBounds().contains(x, y)) {
					continue;
				}
                b.setMouseOver(true);
                return;
            }
        }
    }

    public void mousePressed(int x, int y) {
        if (this.bMenu.getBounds().contains(x, y)) {
            this.bMenu.setMousePressed(true);
        } else if (this.bSave.getBounds().contains(x, y)) {
            this.bSave.setMousePressed(true);
        } else if (this.bWater.getBounds().contains(x, y)) {
            this.bWater.setMousePressed(true);
        } else if (this.bGrass.getBounds().contains(x, y)) {
            this.bGrass.setMousePressed(true);
        } else if (this.bPathStart.getBounds().contains(x, y)) {
            this.bPathStart.setMousePressed(true);
        } else if (this.bPathEnd.getBounds().contains(x, y)) {
            this.bPathEnd.setMousePressed(true);
        } else {
            for (MyButton b : this.map.keySet()) {
                if (!b.getBounds().contains(x, y)) {
					continue;
				}
                b.setMousePressed(true);
                return;
            }
        }
    }

    public void mouseReleased(int x, int y) {
        this.bMenu.resetBooleans();
        this.bSave.resetBooleans();
        this.bWater.resetBooleans();
        this.bGrass.resetBooleans();
        this.bPathStart.resetBooleans();
        this.bPathEnd.resetBooleans();
        for (MyButton b : this.map.keySet()) {
            b.resetBooleans();
        }
    }

    public BufferedImage getStartPathImg() {
        return this.pathStart;
    }

    public BufferedImage getEndPathImg() {
        return this.pathEnd;
    }
}

