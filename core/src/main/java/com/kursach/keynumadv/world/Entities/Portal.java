package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.kursach.keynumadv.screens.ScreenManager;

import java.util.Timer;
import java.util.TimerTask;

public class Portal extends Entity {
    static Texture texture = new Texture(Gdx.files.internal("sprites/portal.png"));
    enum State {
        UNACTIVE,
        STEPPED;
    }
    private State state = State.UNACTIVE;
    private float count;
    public Portal() {
        super();
    }
    public Portal(GridPoint2 tilePos) {
        super(tilePos);
    }

    @Override
    protected void init() {
        this.isFinished = false;
        timer = new Timer();
        count = 255f;
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                count--;
                if (count <= 0) {
                    isFinished = true;
                    timer.cancel();
                }
            }
        };
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
    public void onStep() {
        System.out.println("Going to leave");
        state = State.STEPPED;
        timer.schedule(timerTask, 0, 10);
    }

    @Override
    public void onFinish() {
        ScreenManager.ShowLevelCompleteScreen();
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }
}
