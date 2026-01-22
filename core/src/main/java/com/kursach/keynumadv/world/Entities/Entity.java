package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.Interfaces.StepReactive;
import com.kursach.keynumadv.world.BusyManager;
import com.kursach.keynumadv.world.LocalRender;

public abstract class Entity implements StepReactive {
    protected GridPoint2 tilePos;
    protected Vector2 pixPos;
    protected Texture texture;
    protected static Player player;
    protected static BusyManager busyManager;
    public enum State{};
    public Entity() {}
    public Entity(GridPoint2 tilePos) {
        this.tilePos = tilePos;
        this.pixPos = LocalRender.TileToPixel(tilePos);
    }
    public void SetPos(GridPoint2 tilePos) {
        this.tilePos = tilePos;
        this.pixPos = LocalRender.TileToPixel(tilePos);
    }
    public void SetPos(Vector2 pixPos) {
        this.pixPos = pixPos;
        this.tilePos = LocalRender.PixelToTile(pixPos);
    }
    public Vector2 GetPixPos() {
        return pixPos;
    }
    public GridPoint2 GetTilePos() {
        return tilePos;
    }
    public void render(Batch batch, GridPoint2 tilePos) {
    }
    public void render(Batch batch) {
        Vector2 pos = LocalRender.TileToPixel(tilePos.x, tilePos.y);
        batch.draw(this.texture, pos.x, pos.y);
    }
    public static void setPlayer(Player player) {
        Entity.player = player;
    }
    public static void setBusyManager(BusyManager busyManager) {
        Entity.busyManager = busyManager;
    }
}
