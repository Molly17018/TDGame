/*
 * Decompiled with CFR 0.151.
 */
package objects;

public class PathPoint {
    private int xCord;
    private int yCord;

    public PathPoint(int xCord, int yCord) {
        this.xCord = xCord;
        this.yCord = yCord;
    }

    public int getxCord() {
        return this.xCord;
    }

    public int getyCord() {
        return this.yCord;
    }

    public void setxCord(int xCord) {
        this.xCord = xCord;
    }

    public void setyCord(int yCord) {
        this.yCord = yCord;
    }
}

