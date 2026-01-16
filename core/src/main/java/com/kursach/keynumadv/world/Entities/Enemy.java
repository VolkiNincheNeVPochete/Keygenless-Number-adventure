package com.kursach.keynumadv.world.Entities;

import com.kursach.keynumadv.world.Player;

public class Enemy extends Entity {

    @Override
    public boolean isStepable() {
        return true;
    }

    @Override
    public void onStep(Player stepper) {
        StartFight();

    }

    private void StartFight() {

    }

    private float GetReward() {
        return 0f;
    }
}
