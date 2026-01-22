package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.screens.ScreenManager;
import com.kursach.keynumadv.world.BusyManager;
import com.kursach.keynumadv.world.LocalRender;

public class Portal extends Entity {
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
    public void render(Batch batch) {
        batch.draw(texture, pixPos.x, pixPos.y);
        batch.setColor(1, 1, 1, 1);
    }
    @Override
    public boolean isStepable() {
        return true;
    }
    @Override
    public void onStep() {
        System.out.println("Going to leave");
        state = State.STEPPED;
    }

    @Override
    public void onFinish() {
        ScreenManager.ShowLevelCompleteScreen();
    }

    @Override
    public boolean isFinished() {
        return true;
    }
}
