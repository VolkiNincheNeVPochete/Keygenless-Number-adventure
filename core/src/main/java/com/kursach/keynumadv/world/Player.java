package com.kursach.keynumadv.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.world.Entities.Entity;

import java.util.ArrayList;

import static com.kursach.keynumadv.world.LocalRender.*;

public class Player {
    private GridPoint2 tilePos;
    private Vector2 pixelPos;
    private boolean isMoving = false;
    private boolean isBusy = false;
    private float moveProgress = 0f;
    private GridPoint2 targetTile = null;
    private Vector2 startPixel;
    private Vector2 targetPixel;
    private BusyManager busyManager;
    private float VALUE;
    private BitmapFont playerFont;
    private SpriteBatch uiBatch;
    private final int OFFSET_X  = 64;
    private final int OFFSET_TX = 0;
    private final int OFFSET_Y = 32;
    private final int OFFSET_TY = -1;

    public Player(GridPoint2 spawnTile, BusyManager busyManager) {
        this.tilePos = spawnTile;
        this.pixelPos = TileToPixel(tilePos.x, tilePos.y);
        this.startPixel = new Vector2();
        this.targetPixel = new Vector2();
        this.busyManager = busyManager;
        playerFont = new BitmapFont(Gdx.files.internal("commodore64/raw/commodore-64.fnt"));
        playerFont.setColor(Color.BLACK);
        uiBatch = new SpriteBatch();
        VALUE = 1;
    }

    public float getVALUE() {
        return VALUE;
    }

    public void update(float delta) {
        if (isBusy) {

        }
        if (isMoving) {
            moveProgress += delta * 4.0f;
            if (moveProgress >= 1f) {
                finishMove();
            } else {
                pixelPos.x = startPixel.x + (targetPixel.x - startPixel.x) * moveProgress;
                pixelPos.y = startPixel.y + (targetPixel.y - startPixel.y) * moveProgress;
            }
        }
    }

    private void finishMove() {
        tilePos.set(targetTile);
        pixelPos.set(targetPixel);
        isMoving = false;
        moveProgress = 0f;
        targetTile = null;
        startPixel.set(pixelPos);
        targetPixel.set(pixelPos);
    }

    public boolean tryMove(int dx, int dy, GameMap map) {
        if (isMoving || isBusy) return false;

        int newCol = tilePos.x + dx;
        int newRow = tilePos.y + dy;

        if (newCol < 0 || newCol >= map.mapWidth || newRow < 0 || newRow >= map.mapHeight) {
            return false;
        }

        GridPoint2 newTile = new GridPoint2(newCol, newRow);
        System.out.println("Going to " + newTile);
        if (!canMoveTo(newTile, map)) {
            return false;
        }

        startPixel.set(pixelPos);

        targetPixel.set(TileToPixel(newTile.x, newTile.y));
        targetTile = newTile;
        isMoving = true;
        moveProgress = 0f;
        return true;
    }

    private boolean canMoveTo(GridPoint2 newTile, GameMap map) {
        ArrayList<Entity> entities = map.getEntitiesOnTile(newTile);
        if (entities == null) return true;
        for (Entity entity : entities) {
            if (!entity.isStepable()) {
                return false;
            }
        }
        return true;
    }

    public Vector2 getPixelPosition() {
        return new Vector2(pixelPos.x+OFFSET_X, pixelPos.y+OFFSET_Y);
    }

    public GridPoint2 getTilePosition() {
        return new GridPoint2(tilePos.x+OFFSET_TX, tilePos.y+OFFSET_TY);
    }

    public void render() {
        uiBatch.begin();
        playerFont.draw(uiBatch, "F(x) = " + VALUE, pixelPos.x, pixelPos.y);
        uiBatch.end();
    }
}
