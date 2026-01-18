package com.kursach.keynumadv.world;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.world.Entities.Entity;

import java.util.ArrayList;

public class Player {
    private GridPoint2 tilePos;
    private Vector2 pixelPos;
    private boolean isMoving = false;
    private float moveProgress = 0f;
    private GridPoint2 targetTile = null;
    private Vector2 startPixel;
    private Vector2 targetPixel;
    private BattleSystem battleSystem;
    private float VALUE;

    public Player(GridPoint2 spawnTile, BattleSystem battleSystem) {
        this.tilePos = new GridPoint2(spawnTile.x, spawnTile.y);
        this.pixelPos = LocalRender.TileToPixel(tilePos.x, tilePos.y);
        this.startPixel = new Vector2(pixelPos);
        this.targetPixel = new Vector2(pixelPos);
        this.battleSystem = battleSystem;
    }

    public float getVALUE() {
        return VALUE;
    }

    public void update(float delta) {
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
        if (isMoving) return false;

        int newCol = tilePos.x + dx;
        int newRow = tilePos.y + dy;

        if (newCol < 0 || newCol >= map.mapWidth || newRow < 0 || newRow >= map.mapHeight) {
            return false;
        }

        GridPoint2 newTile = new GridPoint2(newCol, newRow);
        if (!canMoveTo(newTile, map)) {
            return false;
        }

        startPixel.set(pixelPos);

        targetPixel.set(LocalRender.TileToPixel(newTile.x, newTile.y));
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
        return pixelPos;
    }

    public GridPoint2 getTilePosition() {
        return tilePos;
    }
}
