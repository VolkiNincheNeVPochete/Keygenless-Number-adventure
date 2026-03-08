package com.kursach.keynumadv.world;

import com.kursach.keynumadv.screens.ScreenManager;

public class GeneralEvents {
    private static String levelPath;
    private static float playerScore;

    public static void setLevelPath(String levelPath) {
        GeneralEvents.levelPath = levelPath;
    }

    public static void setPlayerScore(float playerScore) {
        GeneralEvents.playerScore = playerScore;
    }

    public static void ShowGameOver() {
        ScreenManager.ShowGameOverScreen(playerScore, levelPath);
    }

    public static void ShowLevelComplete() {
        ScreenManager.ShowLevelCompleteScreen(playerScore, levelPath);
    }
}
