package com.kursach.keynumadv.Interfaces;

import com.kursach.keynumadv.world.BusyManager;
import com.kursach.keynumadv.world.Player;

public interface StepReactive {
    boolean isStepable();

    void onStep(Player player, BusyManager busyManager);
}
