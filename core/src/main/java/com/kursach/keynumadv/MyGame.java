package com.kursach.keynumadv;

import com.badlogic.gdx.Game;
import com.kursach.keynumadv.screens.GameScreen;
import com.kursach.keynumadv.screens.ScreenManager;

public class MyGame extends Game {

    @Override
    public void create() {
        new ScreenManager(this);
        ScreenManager.pushScreen(new GameScreen(this));
    }
}
