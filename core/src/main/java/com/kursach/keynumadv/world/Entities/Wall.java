package com.kursach.keynumadv.world.Entities;

import com.kursach.keynumadv.world.Player;

public class Wall extends Entity {

    @Override
    public boolean isStepable() {
        return false;
    }

    @Override
    public void onStep(Player stepper) {

    }
}
