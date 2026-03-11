package com.kursach.keynumadv;

import com.badlogic.gdx.Game;
import com.kursach.keynumadv.screens.MainMenuScreen;
import com.kursach.keynumadv.screens.ScreenManager;
import com.kursach.keynumadv.world.Entities.Enemy;
import com.kursach.keynumadv.world.Entities.Portal;
import com.kursach.keynumadv.world.Entities.Wall;

public class MyGame extends Game {

    @Override
    public void create() {
        new ScreenManager(this);
        ScreenManager.pushScreen(new MainMenuScreen(this));
    }

    @Override
    public void dispose() {
        Enemy.dispose();
        Wall.dispose();
        Portal.dispose();
        super.dispose();
    }
}
