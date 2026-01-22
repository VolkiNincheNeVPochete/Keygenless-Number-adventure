package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.world.BusyManager;
import com.kursach.keynumadv.world.LocalRender;

public class Wall extends Entity {
    static Texture texture = new Texture(Gdx.files.internal("sprites/wall.png"));
    enum State {};

    public Wall() {
        super();
    }
    public Wall(GridPoint2 tilePos) {
        super(tilePos);
    }
    @Override
    public void render(Batch batch) {
        batch.draw(this.texture, pixPos.x, pixPos.y);
    }

    public static void dispose() {
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }

    @Override
    public boolean isStepable() {
        return false;
    }

    @Override
    public void onStep() {
    }

    @Override
    public void onFinish() {
    }

    @Override
    public boolean isFinished() {
        return false;
    }
}
