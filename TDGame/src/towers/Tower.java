/*
 * Decompiled with CFR 0.151.
 */
package towers;

import helpz.Constants;

public class Tower {
    private int x;
    private int y;
    private int dmg;
    private int id;
    private int towerType;
    private int cdTick;
    private float range;
    private float cooldown;
    private int tier = 1;

    public Tower(int x, int y, int id, int towerType) {
        this.x = x;
        this.y = y;
        this.id = id;
        this.towerType = towerType;
        this.setDefaultDamage();
        this.setDefaultRange();
        this.setDefaultCooldown();
    }

    public void update() {
        ++this.cdTick;
    }

    public void upgradeTower() {
        ++this.tier;
        switch (this.towerType) {
            case 0: {
                this.dmg += 10;
                this.range += 5.0f;
                this.cooldown -= 2.5f;
                break;
            }
            case 1: {
                this.dmg += 5;
                this.range += 10.0f;
                this.cooldown -= 5.0f;
                break;
            }
            case 2: {
                ++this.dmg;
                this.range += 7.5f;
                this.cooldown -= 10.0f;
            }
        }
    }

    public boolean isCooldownOver() {
        return this.cdTick >= this.cooldown;
    }

    public void resteCooldown() {
        this.cdTick = 0;
    }

    private void setDefaultCooldown() {
        this.cooldown = Constants.Towers.GetDefaultCooldown(this.towerType);
    }

    private void setDefaultRange() {
        this.range = Constants.Towers.GetDefaultRange(this.towerType);
    }

    private void setDefaultDamage() {
        this.dmg = Constants.Towers.GetDefaultDamage(this.towerType);
    }

    public int getX() {
        return this.x;
    }

    public int getY() {
        return this.y;
    }

    public int getId() {
        return this.id;
    }

    public int getTowerType() {
        return this.towerType;
    }

    public int getDmg() {
        return this.dmg;
    }

    public float getRange() {
        return this.range;
    }

    public float getCooldown() {
        return this.cooldown;
    }

    public int getTier() {
        return this.tier;
    }

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setTowerType(int towerType) {
        this.towerType = towerType;
    }
}

