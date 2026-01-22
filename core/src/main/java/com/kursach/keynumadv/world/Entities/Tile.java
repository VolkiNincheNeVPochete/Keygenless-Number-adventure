package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.kursach.keynumadv.Interfaces.StepReactive;
import com.kursach.keynumadv.world.BusyManager;

public class Tile implements StepReactive {
    private SpriteBatch batch;
    private Texture texture = new Texture(Gdx.files.internal("sprites/tile.png"));
    private Sprite sprite = new Sprite(texture, 0, 0, 128, 64);

    public void Tile(SpriteBatch batch) {
        this.batch = batch;
    }

    public void render(float delta) {
        batch.begin();
        sprite.draw(batch);
        batch.end();
    }

    @Override
    public boolean isStepable() {
        return true;
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
