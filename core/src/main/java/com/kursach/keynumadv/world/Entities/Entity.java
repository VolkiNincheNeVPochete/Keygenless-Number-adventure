package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.Interfaces.StepReactive;

public abstract class Entity implements StepReactive {
    protected GridPoint2 tilePos;
    protected Vector2 pixPos;
    protected Texture texture;

    public Vector2 getPixPos() {
        return pixPos;
    }

    public GridPoint2 getTilePos() {
        return tilePos;
    }

    public void render(Batch batch, GridPoint2 tilePos) {
    }

    ;
}
