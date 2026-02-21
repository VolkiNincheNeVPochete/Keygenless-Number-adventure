package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.Interfaces.StepReactive;
import com.kursach.keynumadv.world.BusyManager;
import com.kursach.keynumadv.world.LocalRender;

import java.util.Timer;
import java.util.TimerTask;

public abstract class Entity implements StepReactive {
    protected GridPoint2 tilePos;
    protected Vector2 pixPos;
    protected Texture texture;
    protected static Player player;
    protected static BusyManager busyManager;
    public enum State{};
    protected boolean isFinished;
    protected Timer timer;
    protected TimerTask timerTask;
    protected ShaderProgram shaderProgram;
    protected int OFFSET_X  = 0;
    protected int OFFSET_TX = 0;
    protected int OFFSET_Y = 0;
    protected int OFFSET_TY = 0;
    public Entity() {
        this.tilePos = new GridPoint2(0, 0);
        this.pixPos = LocalRender.TileToPixel(tilePos);
        init();
    }
    public Entity(GridPoint2 tilePos) {
        this.tilePos = tilePos;
        this.pixPos = LocalRender.TileToPixel(tilePos);
        init();
    }
    protected void init() {
        this.isFinished = false;
        timer = new Timer();
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
    public Vector2 getVisualPixelPosition() {
        return new Vector2(pixPos.x+OFFSET_X, pixPos.y+OFFSET_Y);
    }
    public GridPoint2 getVisualTilePosition() {
        return new GridPoint2(tilePos.x+OFFSET_TX, tilePos.y+OFFSET_TY);
    }
    public void render(Batch batch, GridPoint2 tilePos) {
    }
    public void render(Batch batch) {
    }
    public static void setPlayer(Player player) {
        Entity.player = player;
    }
    public static void setBusyManager(BusyManager busyManager) {
        Entity.busyManager = busyManager;
    }
}
