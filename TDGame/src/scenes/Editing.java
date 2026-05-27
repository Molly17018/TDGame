/*
 * Decompiled with CFR 0.151.
 */
package scenes;

import helpz.LoadSave;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import main.Game;
import objects.PathPoint;
import objects.Tile;
import scenes.GameScene;
import scenes.SceneMethods;
import ui.ToolBar;

public class Editing
extends GameScene
implements SceneMethods {
    private int[][] lvl;
    private ToolBar toolBar;
    private PathPoint start;
    private PathPoint end;
    private Tile selectedTile;
    private int mouseX;
    private int mouseY;
    private int lastTileX;
    private int lastTileY;
    private int lastTileId;
    private boolean drawSelect;

    public Editing(Game game) {
        super(game);
        this.loadDefaultLevel();
        this.toolBar = new ToolBar(0, 640, 640, 160, this);
    }

    private void loadDefaultLevel() {
        this.lvl = LoadSave.GetLevelData();
        ArrayList<PathPoint> points = LoadSave.GetLevelPathPoints();
        this.start = points.get(0);
        this.end = points.get(1);
    }

    public void update() {
    }

    @Override
    public void render(Graphics g) {
        this.drawLevel(g);
        this.toolBar.draw(g);
        this.drawSelectedTile(g);
        this.drawPathPoints(g);
    }

    private void drawPathPoints(Graphics g) {
        if (this.start != null) {
            g.drawImage(this.toolBar.getStartPathImg(), this.start.getxCord() * 32, this.start.getyCord() * 32, 32, 32, null);
        }
        if (this.end != null) {
            g.drawImage(this.toolBar.getEndPathImg(), this.end.getxCord() * 32, this.end.getyCord() * 32, 32, 32, null);
        }
    }

    private void drawLevel(Graphics g) {
        int y = 0;
        while (y < this.lvl.length) {
            int x = 0;
            while (x < this.lvl[y].length) {
                int id = this.lvl[y][x];
                g.drawImage(this.getSprite(id), x * 32, y * 32, null);
                ++x;
            }
            ++y;
        }
    }

    public BufferedImage getSprite(int spriteID) {
        return this.game.getTileManager().getSprite(spriteID);
    }

    private void drawSelectedTile(Graphics g) {
        if (this.selectedTile != null && this.drawSelect) {
            g.drawImage(this.selectedTile.getSprite(), this.mouseX, this.mouseY, 32, 32, null);
        }
    }

    public void saveLevel() {
        LoadSave.SaveLevel(this.lvl, this.start, this.end);
        this.getGame().getPlaying().setLevel(this.lvl);
    }

    public void setSelectedTile(Tile tile) {
        this.selectedTile = tile;
        this.drawSelect = true;
    }

    private void changeTile(int x, int y) {
        if (this.selectedTile != null) {
            int tileX = x / 32;
            int tileY = y / 32;
            if (this.selectedTile.getId() >= 0) {
                if (this.lastTileX == tileX && this.lastTileY == tileY && this.lastTileId == this.selectedTile.getId()) {
                    return;
                }
                this.lastTileX = tileX;
                this.lastTileY = tileY;
                this.lvl[tileY][tileX] = this.selectedTile.getId();
            } else {
                int id = this.lvl[tileY][tileX];
                if (this.game.getTileManager().getTile(id).getTileType() == 2) {
                    if (this.selectedTile.getId() == -1) {
                        this.start = new PathPoint(tileX, tileY);
                    } else {
                        this.end = new PathPoint(tileX, tileY);
                    }
                }
            }
        }
    }

    @Override
    public void mouseClicked(int x, int y) {
        if (y >= 640) {
            this.toolBar.mouseClicked(x, y);
        } else {
            this.changeTile(this.mouseX, this.mouseY);
        }
    }

    @Override
    public void mouseMoved(int x, int y) {
        if (y >= 640) {
            this.toolBar.mouseMoved(x, y);
            this.drawSelect = false;
        } else {
            this.mouseX = x / 32 * 32;
            this.mouseY = y / 32 * 32;
            this.drawSelect = true;
        }
    }

    @Override
    public void mousePressed(int x, int y) {
        if (y >= 640) {
            this.toolBar.mouseMoved(x, y);
        }
    }

    @Override
    public void mouseReleased(int x, int y) {
        if (y >= 640) {
            this.toolBar.mouseReleased(x, y);
        }
    }

    @Override
    public void mouseDraged(int x, int y) {
        if (y < 640) {
            this.changeTile(x, y);
        }
    }

    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == 82) {
            this.toolBar.rotateSprite();
        }
    }
}

