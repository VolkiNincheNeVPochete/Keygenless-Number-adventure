package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.math.GridPoint2;

public class Enemy extends Entity {
    private float VALUE;

    public Enemy(GridPoint2 tilePos, float thisValue) {
        super(tilePos);
        this.VALUE = thisValue;
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

    private void StartFight() {

    }

    private float GetReward() {
        return 0f;
    }
}
