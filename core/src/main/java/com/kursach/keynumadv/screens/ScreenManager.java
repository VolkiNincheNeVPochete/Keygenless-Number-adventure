package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import java.util.ArrayList;

public class ScreenManager {
    private static Game myGame;
    private static Screen createdScreen;
    public static ArrayList<Screen> screenStack = new ArrayList<>();

    public ScreenManager(Game myGame) {
        ScreenManager.myGame = myGame;
    }

    public static void pushScreen(Screen newScreen) {
        Screen current = myGame.getScreen();
        System.out.println(screenStack);
        if (current != newScreen && current != null) {
            screenStack.add(current);
        }
        myGame.setScreen(newScreen);
    }

    public static void popScreen() {
        if (screenStack.size() > 0) {
            myGame.setScreen(screenStack.remove(screenStack.size() - 1));
        } else {
            ShowMainMenu();
        }
    }

    public static void ShowMainMenu() {
        createdScreen = new MainMenuScreen();
        screenStack.clear();
        myGame.setScreen(createdScreen);
    }

    public static void ShowGame() {
        screenStack.clear();
        createdScreen = new GameScreen(myGame);
        screenStack.add(createdScreen);
        myGame.setScreen(createdScreen);
    }

    public static void ShowSettings() {
        screenStack.clear();
        createdScreen = new SettingsScreen();
        screenStack.add(createdScreen);
        myGame.setScreen(createdScreen);
    }

    public static void ShowPauseScreen() {
        screenStack.clear();
        createdScreen = new PauseScreen();
        screenStack.add(createdScreen);
        myGame.setScreen(createdScreen);
    }

    public static void ShowLevelSelectScreen() {
    }

    public static void ShowLevelCompleteScreen() {
        screenStack.clear();
        createdScreen = new LevelCompleteScreen();
        screenStack.add(createdScreen);
        myGame.setScreen(createdScreen);
    }

    public static void ShowLevelGameOverScreen() {
    }


    public static void exit() {
        Gdx.app.exit();
    }
}
