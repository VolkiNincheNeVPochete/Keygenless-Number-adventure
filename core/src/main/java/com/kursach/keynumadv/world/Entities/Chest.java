package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.math.GridPoint2;
import com.kursach.keynumadv.world.BusyManager;
import com.kursach.keynumadv.world.Player;

public class Chest extends Entity {


    public Chest(GridPoint2 tilePos) {
        super(tilePos);
    }

    @Override
    public boolean isStepable() {
        return true;
    }

    @Override
    public void onStep(Player stepper, BusyManager busyManager) {

    }
}
