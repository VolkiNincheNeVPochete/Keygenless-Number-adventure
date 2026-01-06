package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;

import java.util.ArrayList;

public class ScreenManager {
    private static Game myGame;
    private static Screen createdScreen;
    public ArrayList<Screen> screenStack = new ArrayList<>();

    public ScreenManager(Game myGame) {
        this.myGame = myGame;
    }

    public void pushScreen(Screen newScreen) {
        Screen current = myGame.getScreen();
        if (current != newScreen && current != null) {
            screenStack.add(current);
        }
        myGame.setScreen(newScreen);
        System.out.println(screenStack.toString() + "\n\n");
    }

    public void popScreen() {
        if (screenStack.size() > 0) {
            System.out.println("pop " + myGame.getScreen());
            myGame.setScreen(screenStack.remove(screenStack.size() - 1));
            System.out.println("\nand screenStack is " + screenStack + "\n\n");
        } else {
            ShowMainMenu();
        }
    }

    public void ShowMainMenu() {
        createdScreen = new MainMenuScreen();
        screenStack.clear();
        myGame.setScreen(createdScreen);
    }

    public void ShowGame() {
        screenStack.clear();
        createdScreen = new GameScreen();
        screenStack.add(createdScreen);
        myGame.setScreen(createdScreen);
    }

    public void ShowSettings() {
        screenStack.clear();
        createdScreen = new SettingsScreen();
        screenStack.add(createdScreen);
        myGame.setScreen(createdScreen);
    }

    public void ShowPauseScreen() {
        screenStack.clear();
        createdScreen = new PauseScreen();
        screenStack.add(createdScreen);
        myGame.setScreen(createdScreen);
    }

    public void ShowLevelSelectScreen() {}

    public void ShowLevelCompleteScreen() {}

    public void ShowLevelGameOverScreen() {}


    public void exit() {
        Gdx.app.exit();
    }
}
