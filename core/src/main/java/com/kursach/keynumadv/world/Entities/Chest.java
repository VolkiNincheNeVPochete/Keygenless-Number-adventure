package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.math.GridPoint2;

public class Chest extends Entity {


    public Chest(GridPoint2 tilePos) {
        super(tilePos);
    }

    @Override
    public boolean isStepable() {
        return true;
    }

    @Override
    public void onStep(Player currentPlayer) {

    }

    @Override
    public void onFinish(Player currentPlayer) {

    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isFinalized() {
        return false;
    }
}
