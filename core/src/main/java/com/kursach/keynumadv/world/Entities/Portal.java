package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.world.BusyManager;
import com.kursach.keynumadv.world.LocalRender;
import com.kursach.keynumadv.world.Player;

public class Portal extends Entity {
    static Texture texture = new Texture(Gdx.files.internal("sprites/portal.png"));

    public Portal() {
        super();
    }
    public Portal(GridPoint2 tilePos) {
        super(tilePos);
    }
    @Override
    public void render(Batch batch) {
        Vector2 pos = LocalRender.TileToPixel(tilePos.x, tilePos.y);
        batch.draw(this.texture, pos.x, pos.y);
    }
    @Override
    public boolean isStepable() {
        return true;
    }

    @Override
    public void onStep(Player stepper, BusyManager busyManager) {
        System.out.println("leave");
    }
}
