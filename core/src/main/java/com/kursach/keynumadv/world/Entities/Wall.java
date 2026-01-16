package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.kursach.keynumadv.world.LocalRender;
import com.kursach.keynumadv.world.Player;

public class Wall extends Entity {
    static Texture texture = new Texture(Gdx.files.internal("sprites/wall.png"));
    ;

    public Wall() {

    }

    public static void dispose() {
        if (texture != null) {
            texture.dispose();
            texture = null;
        }
    }

    public void render(Batch batch, GridPoint2 tilePos) {
        if (texture != null) {
            var pos = LocalRender.TileToPixel(tilePos);
            batch.draw(texture, pos.x, pos.y);
        }
    }

    @Override
    public boolean isStepable() {
        return false;
    }

    @Override
    public void onStep(Player stepper) {
    }
}
