/*
 * Decompiled with CFR 0.151.
 */
package enemies;

import enemies.Enemy;
import managers.EnemyManager;

public class Wolf
extends Enemy {
    public Wolf(float x, float y, int ID, EnemyManager enemyManager) {
        super(x, y, ID, 3, enemyManager);
    }
}

