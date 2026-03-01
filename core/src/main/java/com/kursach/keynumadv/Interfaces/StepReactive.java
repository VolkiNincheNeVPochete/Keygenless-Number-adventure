package com.kursach.keynumadv.Interfaces;

import com.kursach.keynumadv.world.Entities.Player;

public interface StepReactive {
    boolean isStepable();
    void onStep(Player currentPlayer);
    void onFinish(Player currentPlayer);
    boolean isFinished();
    boolean isFinalized();
    default void Reward(Player player) {
        player.updateValue(0f);
    }
}
