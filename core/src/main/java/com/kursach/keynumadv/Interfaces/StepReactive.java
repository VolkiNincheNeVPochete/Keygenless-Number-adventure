package com.kursach.keynumadv.Interfaces;

import com.kursach.keynumadv.world.Player;

public interface StepReactive {
    boolean isStepable();

    void onStep(Player stepper);
}
