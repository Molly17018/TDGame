/*
 * Decompiled with CFR 0.151.
 */
package main;

public enum GameStates {
    PLAYING,
    MENU,
    SETTINGS,
    EDIT,
    GAME_OVER;

    public static GameStates gameState;

    static {
        gameState = MENU;
    }

    public static void SetGameState(GameStates state) {
        gameState = state;
    }
}

