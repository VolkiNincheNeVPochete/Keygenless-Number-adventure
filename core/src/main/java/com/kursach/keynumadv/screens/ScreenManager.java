package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;

public class ScreenManager {
    private final Game myGame;

    public ScreenManager(Game myGame) {
        this.myGame = myGame;
    }

    public void showMainMenu() {
        myGame.setScreen(new MainMenuScreen(myGame));
    }

    public void showGame() {
        myGame.setScreen(new GameScreen(myGame));
    }

    public void showSettings() {
        myGame.setScreen(new SettingsScreen(myGame));
    }

    public void exit() {
        Gdx.app.exit();
    }
}
