package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.math.GridPoint2;
import com.kursach.keynumadv.world.BusyManager;

public class Chest extends Entity {


    public Chest(GridPoint2 tilePos) {
        super(tilePos);
    }

    @Override
    public boolean isStepable() {
        return true;
    }

    @Override
    public void onStep() {

    }

    @Override
    public void onFinish() {

    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
