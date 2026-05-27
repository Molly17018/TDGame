/*
 * Decompiled with CFR 0.151.
 */
package enemies;

import enemies.Enemy;
import managers.EnemyManager;

public class Knight
extends Enemy {
    public Knight(float x, float y, int ID, EnemyManager enemyManager) {
        super(x, y, ID, 2, enemyManager);
    }
}

