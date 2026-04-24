/*
 * Decompiled with CFR 0.151.
 */
package enemies;

import enemies.Enemy;
import managers.EnemyManager;

public class Bat
extends Enemy {
    public Bat(float x, float y, int ID, EnemyManager enemyManager) {
        super(x, y, ID, 1, enemyManager);
    }
}

