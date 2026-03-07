package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;

public class Wall extends Entity {
    static Texture texture = new Texture(Gdx.files.internal("sprites/wall.png"));

    public Wall() {
        super();
    }

    ;

    public Wall(GridPoint2 tilePos) {
        super(tilePos);
    }

    public static void dispose() {
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }

    @Override
    public void render(Batch batch) {
        batch.draw(this.texture, pixPos.x, pixPos.y);
    }

    @Override
    public boolean isStepable() {
        return false;
    }

    @Override
    public void onStep(Player currentPlayer) {
    }

    @Override
    public void onFinish(Player currentPlayer) {
    }

    @Override
    public boolean isFinished() {
        return false;
    }

    @Override
    public boolean isFinalized() {
        return false;
    }

    enum State {}
}
