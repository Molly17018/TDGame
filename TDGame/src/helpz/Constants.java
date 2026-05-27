/*
 * Decompiled with CFR 0.151.
 */
package helpz;

public class Constants {

    public static class Direction {
        public static final int LEFT = 0;
        public static final int UP = 1;
        public static final int RIGHT = 2;
        public static final int DOWN = 3;
    }

    public static class Enemies {
        public static final int ORC = 0;
        public static final int BAT = 1;
        public static final int KNIGHT = 2;
        public static final int WOLF = 3;

        public static float GetSpeed(int enemyType) {
            switch (enemyType) {
                case 0: { 
                    return 0.5f;
                }
                case 1: {
                    return 0.6f;
                }
                case 2: {
                    return 0.3f;
                }
                case 3: {
                    return 1.0f;
                }
            }
            return 0.0f;
        }

        public static int GetDefaultHealth(int enemyType) {
            switch (enemyType) {
                case 0: {
                    return 100;
                }
                case 1: {
                    return 70;
                }
                case 2: {
                    return 250;
                }
                case 3: {
                    return 50;
                }
            }
            return 0;
        }

        public static int GetReward(int enemyType) {
            switch (enemyType) {
                case 0: {
                    return 10;
                }
                case 1: {
                    return 15;
                }
                case 2: {
                    return 25;
                }
                case 3: {
                    return 15;
                }
            }
            return 0;
        }
    }

    public static class Projectiles {
        public static final int BOMB = 2;
        public static final int ARROW = 0;
        public static final int CHAINS = 1;
        public static final int COIN = 3;

        public static float GetSpeed(int type) {
            switch (type) {
                case 2: {
                    return 1.0f;
                }
                case 0: {
                    return 5.0f;
                }
                case 1: {
                    return 2.0f;
                }
            }
            return 0.0f;
        }
    }

    public static class Tiles {
        public static final int WATER_TILE = 0;
        public static final int GRASS_TILE = 1;
        public static final int ROAD_TILE = 2;
    }

    public static class Towers {
        public static final int CANNON = 0;
        public static final int ARCHER = 1;
        public static final int WIZARD = 2;
        public static final int TREE = 3;

        public static String GetName(int towerType) {
            switch (towerType) {
                case 0: {
                    return "Cannon";
                }
                case 1: {
                    return "Archer";
                }
                case 2: {
                    return "Wizard";
                }
                case 3: {
                	return "Tree";
                }
            }
            return "";
        }

        public static int GetTowerCost(int towerType) {
            switch (towerType) {
                case 0: {
                    return 250;
                }
                case 1: {
                    return 100;
                }
                case 2: {
                    return 150;
                }
                case 3: {
                	return 300;
                }
            }
            return 0;
        }

        public static int GetDefaultDamage(int towerType) {
            switch (towerType) {
                case 0: {
                    return 30;
                }
                case 1: {
                    return 10;
                }
                case 2: {
                    return 1;
                }
            }
            return 0;
        }

        public static float GetDefaultRange(int towerType) {
            switch (towerType) {
                case 0: {
                    return 50.0f;
                }
                case 1: {
                    return 75.0f;
                }
                case 2: {
                    return 50.0f;
                }
                case 3: {
                	return 10.0f;
                }
            }
            return 0.0f;
        }

        public static float GetDefaultCooldown(int towerType) {
            switch (towerType) {
                case 0: {
                    return 50.0f;
                }
                case 1: {
                    return 25.0f;
                }
                case 2: {
                    return 42.5f;
                }
                case 3: {
                	return 500.0f;
                }
            }
            return 0.0f;
        }
    }
}

