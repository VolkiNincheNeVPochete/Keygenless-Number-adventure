package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.math.GridPoint2;
import com.kursach.keynumadv.world.BattleSystem;
import com.kursach.keynumadv.world.Player;

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
    public void onStep(Player player, BattleSystem battleSystem) {
        if (player.getVALUE() > VALUE) {
            System.out.println("Player wins!");
        } else {
            System.out.println("Enemy wins!");
        }

        battleSystem.onEntityFinished();
    }

    private void StartFight() {

    }

    private float GetReward() {
        return 0f;
    }
}
