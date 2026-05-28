/*
 * Decompiled with CFR 0.151.
 */
package towers;

import java.awt.geom.Point2D;

public class Projectile {
    private Point2D.Float pos;
    private int id;
    private int projectileType;
    private int damage;
    private boolean active = true;
    private float xSpeed;
    private float ySpeed;
    private float rotation;
    private float time;

    public Projectile(float x, float y, float xSpeed, float ySpeed, float rotation, int id, int projectileType, int damage) {
        this.pos = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.rotation = rotation;
        this.id = id;
        this.projectileType = projectileType;
        this.damage = damage;
    }
    
    public Projectile(float x, float y, float xSpeed, float ySpeed, float rotation, int id, int projectileType, float time) {
        this.pos = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.rotation = rotation;
        this.id = id;
        this.projectileType = projectileType;
        this.time = time;
    }

    public void move() {
        this.pos.x += this.xSpeed;
        this.pos.y += this.ySpeed;
    }

    public void reuse(int x, int y, float xSpeed, float ySpeed, float rotate, int dmg) {
        this.pos = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.rotation = rotate;
        this.damage = dmg;
        this.active = true;
    }
    
    public void reuse(int x, int y, float xSpeed, float ySpeed, float rotate, float time) {
        this.pos = new Point2D.Float(x, y);
        this.xSpeed = xSpeed;
        this.ySpeed = ySpeed;
        this.rotation = rotate;
        this.time = time;
        this.active = true;
    }

    public Point2D.Float getPos() {
        return this.pos;
    }

    public int getId() {
        return this.id;
    }

    public int getProjectileType() {
        return this.projectileType;
    }

    public int getDamage() {
        return this.damage;
    }

    public float getRotation() {
        return this.rotation;
    }
    
    public float getTime() {
    	return this.time;
    }
    
    public void countdownTime() {
    	this.time--;
    }

    public boolean isActive() {
        return this.active;
    }

    public void setPos(Point2D.Float pos) {
        this.pos = pos;
    }

    public void setActive(boolean active) {
        this.active = active;
    }
}

