/*
 * Decompiled with CFR 0.151.
 */
package managers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;

import helpz.ImgFix;
import helpz.LoadSave;
import objects.Tile;

public class TileManager {
    public Tile GRASS;
    public Tile WATER;
    public Tile BL_WATER_CORNER;
    public Tile TL_WATER_CORNER;
    public Tile TR_WATER_CORNER;
    public Tile BR_WATER_CORNER;
    public Tile BL_ISLAND;
    public Tile TL_ISLAND;
    public Tile TR_ISLAND;
    public Tile BR_ISLAND;
    public Tile B_WATER_BEACH;
    public Tile T_WATER_BEACH;
    public Tile R_WATER_BEACH;
    public Tile L_WATER_BEACH;
    public Tile TB_ROAD;
    public Tile LR_ROAD;
    public Tile ROAD_B_TO_R;
    public Tile ROAD_L_TO_B;
    public Tile ROAD_L_TO_T;
    public Tile ROAD_T_TO_R;
    public BufferedImage athlas;
    public ArrayList<Tile> tiles = new ArrayList();
    public ArrayList<Tile> roadsS = new ArrayList();
    public ArrayList<Tile> roadsC = new ArrayList();
    public ArrayList<Tile> waterC = new ArrayList();
    public ArrayList<Tile> beaches = new ArrayList();
    public ArrayList<Tile> islands = new ArrayList();

    public TileManager() {
        this.loadAthlas();
        this.createTiles();
    }

    private void createTiles() {
        int id = 0;
        this.GRASS = new Tile(this.getSprite(9, 0), id++, 1);
        this.tiles.add(this.GRASS);
        this.WATER = new Tile(this.getAniSprites(0, 0), id++, 0);
        this.tiles.add(this.WATER);
        this.LR_ROAD = new Tile(this.getSprite(8, 0), id++, 2);
        this.roadsS.add(this.LR_ROAD);
        this.TB_ROAD = new Tile(ImgFix.getRotImg(this.getSprite(8, 0), 90), id++, 2);
        this.roadsS.add(this.TB_ROAD);
        this.ROAD_B_TO_R = new Tile(this.getSprite(7, 0), id++, 2);
        this.roadsC.add(this.ROAD_B_TO_R);
        this.ROAD_L_TO_B = new Tile(ImgFix.getRotImg(this.getSprite(7, 0), 90), id++, 2);
        this.roadsC.add(this.ROAD_L_TO_B);
        this.ROAD_L_TO_T = new Tile(ImgFix.getRotImg(this.getSprite(7, 0), 180), id++, 2);
        this.roadsC.add(this.ROAD_L_TO_T);
        this.ROAD_T_TO_R = new Tile(ImgFix.getRotImg(this.getSprite(7, 0), 270), id++, 2);
        this.roadsC.add(this.ROAD_T_TO_R);
        this.BL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(this.getAniSprites(0, 0), this.getSprite(5, 0), 0), id++, 0);
        this.waterC.add(this.BL_WATER_CORNER);
        this.TL_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(this.getAniSprites(0, 0), this.getSprite(5, 0), 90), id++, 0);
        this.waterC.add(this.TL_WATER_CORNER);
        this.TR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(this.getAniSprites(0, 0), this.getSprite(5, 0), 180), id++, 0);
        this.waterC.add(this.TR_WATER_CORNER);
        this.BR_WATER_CORNER = new Tile(ImgFix.getBuildRotImg(this.getAniSprites(0, 0), this.getSprite(5, 0), 270), id++, 0);
        this.waterC.add(this.BR_WATER_CORNER);
        this.T_WATER_BEACH = new Tile(ImgFix.getBuildRotImg(this.getAniSprites(0, 0), this.getSprite(6, 0), 0), id++, 0);
        this.beaches.add(this.T_WATER_BEACH);
        this.R_WATER_BEACH = new Tile(ImgFix.getBuildRotImg(this.getAniSprites(0, 0), this.getSprite(6, 0), 90), id++, 0);
        this.beaches.add(this.R_WATER_BEACH);
        this.B_WATER_BEACH = new Tile(ImgFix.getBuildRotImg(this.getAniSprites(0, 0), this.getSprite(6, 0), 180), id++, 0);
        this.beaches.add(this.B_WATER_BEACH);
        this.L_WATER_BEACH = new Tile(ImgFix.getBuildRotImg(this.getAniSprites(0, 0), this.getSprite(6, 0), 270), id++, 0);
        this.beaches.add(this.L_WATER_BEACH);
        this.TL_ISLAND = new Tile(ImgFix.getBuildRotImg(this.getAniSprites(0, 0), this.getSprite(4, 0), 0), id++, 0);
        this.islands.add(this.TL_ISLAND);
        this.TR_ISLAND = new Tile(ImgFix.getBuildRotImg(this.getAniSprites(0, 0), this.getSprite(4, 0), 90), id++, 0);
        this.islands.add(this.TR_ISLAND);
        this.BR_ISLAND = new Tile(ImgFix.getBuildRotImg(this.getAniSprites(0, 0), this.getSprite(4, 0), 180), id++, 0);
        this.islands.add(this.BR_ISLAND);
        this.BL_ISLAND = new Tile(ImgFix.getBuildRotImg(this.getAniSprites(0, 0), this.getSprite(4, 0), 270), id++, 0);
        this.islands.add(this.BL_ISLAND);
        this.tiles.addAll(this.roadsS);
        this.tiles.addAll(this.roadsC);
        this.tiles.addAll(this.waterC);
        this.tiles.addAll(this.beaches);
        this.tiles.addAll(this.islands);
    }

    private BufferedImage[] getImgs(int firstX, int firstY, int secondX, int secondY) {
        return new BufferedImage[]{this.getSprite(firstX, firstY), this.getSprite(secondX, secondY)};
    }

    private void loadAthlas() {
        this.athlas = LoadSave.getSpriteAtlas();
    }

    public Tile getTile(int id) {
        return this.tiles.get(id);
    }

    public BufferedImage getSprite(int id) {
        return this.tiles.get(id).getSprite();
    }

    public BufferedImage getAniSprite(int id, int animationIndex) {
        return this.tiles.get(id).getSprite(animationIndex);
    }

    private BufferedImage[] getAniSprites(int xCord, int yCord) {
        BufferedImage[] arr = new BufferedImage[4];
        int i = 0;
        while (i < 4) {
            arr[i] = this.getSprite(xCord + i, yCord);
            ++i;
        }
        return arr;
    }

    private BufferedImage getSprite(int xCord, int yCord) {
        return this.athlas.getSubimage(xCord * 32, yCord * 32, 32, 32);
    }

    public boolean isSpriteAnimation(int sriteId) {
        return this.tiles.get(sriteId).isAnimation();
    }

    public ArrayList<Tile> getRoadsS() {
        return this.roadsS;
    }

    public ArrayList<Tile> getRoadsC() {
        return this.roadsC;
    }

    public ArrayList<Tile> getWaterC() {
        return this.waterC;
    }

    public ArrayList<Tile> getBeaches() {
        return this.beaches;
    }

    public ArrayList<Tile> getIslands() {
        return this.islands;
    }
}

