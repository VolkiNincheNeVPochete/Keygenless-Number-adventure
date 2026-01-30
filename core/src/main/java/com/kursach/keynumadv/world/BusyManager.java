package com.kursach.keynumadv.world;

import com.kursach.keynumadv.world.Entities.Entity;
import com.kursach.keynumadv.world.Entities.Player;

import java.util.ArrayList;

public class BusyManager {
    private ArrayList<Entity> queue;
    private boolean isActive = false;
    private boolean isRealize = false;
    private Player currentPlayer;

    public BusyManager() {
        this.queue = new ArrayList<>();
    }

    public void CreateQueue (ArrayList<Entity> entities) {
        if (entities != null) {
            queue = entities;
        }

    }
    public void Start() {
        if (!queue.isEmpty()) {
            isActive = true;
        }
    }
    public void Update () {
        if (queue.isEmpty()){
            return;
        }

        if (!isRealize) {
            RealiseEntity();
        }

        if (GetFinished()) {
            FinalEntity();
        }
    }
    public void RealiseEntity () {
        GetCurrentEntity().onStep();
        isRealize = true;
    }
    public void FinalEntity () {
        GetCurrentEntity().onFinish();
        isRealize = false;
        queue.removeFirst();
    }
    public boolean GetFinished() {
        return GetCurrentEntity().isFinished();
    }
    public Entity GetCurrentEntity() {
        return queue.getFirst();
    }
    public boolean isActive() {
        return isActive;
    }
}
