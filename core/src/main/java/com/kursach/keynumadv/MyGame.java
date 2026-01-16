package com.kursach.keynumadv;

import com.badlogic.gdx.Game;
import com.kursach.keynumadv.screens.GameScreen;
import com.kursach.keynumadv.screens.ScreenManager;

public class MyGame extends Game {

    public ScreenManager screMan = new ScreenManager(this);

    @Override
    public void create() {
        screMan.pushScreen(new GameScreen(this, screMan));
    }
}
