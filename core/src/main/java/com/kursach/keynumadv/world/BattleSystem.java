package com.kursach.keynumadv.world;

import com.badlogic.gdx.utils.Array;
import com.kursach.keynumadv.Interfaces.StepReactive;

public class BattleSystem {
    private Array<StepReactive> queue;
    private boolean isActive = false;
    private Player currentPlayer;

    public BattleSystem() {
        this.queue = new Array<>();
    }

    public void startBattle(Player player, Array<StepReactive> entities) {
        if (isActive) return;
        this.queue.clear();
        this.queue.addAll(entities);
        this.currentPlayer = player;
        this.isActive = true;
        processNext();
    }

    private void processNext() {
        if (queue.size == 0) {
            finishBattle();
            return;
        }

        StepReactive current = queue.first();
        current.onStep(currentPlayer, this);
    }

    public void onEntityFinished() {
        if (queue.size > 0) {
            queue.removeIndex(0);
        }
        processNext();
    }

    private void finishBattle() {
        isActive = false;
        // Можно вызвать callback, если нужно
    }

    public boolean isActive() {
        return isActive;
    }
}
