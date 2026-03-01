package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.kursach.keynumadv.screens.ScreenManager;

import java.util.*;

public class Portal extends Entity {
    private float count;
    static Texture texture = new Texture(Gdx.files.internal("sprites/portal.png"));
    enum State {
        UNACTIVE,
        STEPPED;
    }
    private State state = State.UNACTIVE;
    public Portal() {
        super();
    }
    public Portal(GridPoint2 tilePos) {
        super(tilePos);
    }

    @Override
    protected void init() {
        this.isFinished = false;
        count = 255f;
    }

    @Override
    public void render(Batch batch) {
        batch.setColor((255-count)/255, (255-count)/255, (count)/255, 1);
        batch.draw(texture, pixPos.x, pixPos.y);
    }
    @Override
    public boolean isStepable() {
        return true;
    }
    @Override
    public void onStep(Player currentPlayer) {
        System.out.println("Going to leave");
        state = State.STEPPED;
        timer = new Timer();
        timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    count--;
                    if (count <= 0) {
                        isFinished = true;
                        timer.cancel();
                        timer = null;
                    }
                }
            }, 0, 10);
    }

    @Override
    public void onFinish(Player currentPlayer) {
        ScreenManager.ShowLevelCompleteScreen(currentPlayer.getVALUE());
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public boolean isFinalized() {
        return false;
    }
}
