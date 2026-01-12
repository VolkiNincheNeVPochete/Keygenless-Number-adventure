package com.kursach.keynumadv.world;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.world.Entities.Entity;

import java.util.ArrayList;

public class Player {
    public static final float TILE_WIDTH = 128f;
    public static final float TILE_HEIGHT = 64f;
    private GridPoint2 tilePos; // текущая позиция в тайлах
    private Vector2 pixelPos;   // позиция в пикселях (для отрисовки)
    private boolean isMoving = false;
    private float moveProgress = 0f; // 0.0 → 1.0
    private GridPoint2 targetTile = null;

    public Player(GridPoint2 spawnTile) {
        this.tilePos = new GridPoint2(spawnTile);
        this.pixelPos = new Vector2();
        updatePixelPosition();
    }

    public static Vector2 tileToPixel(int col, int row) {
        float x = (col - row) * (TILE_WIDTH / 2f);
        float y = (col + row) * (TILE_HEIGHT / 2f);
        return new Vector2(x, y);
    }

    public void update(float delta) {
        if (isMoving) {
            moveProgress += delta * 4.0f;
            if (moveProgress >= 1f) {
                moveProgress = 1f;
                finishMove();
            }
            interpolatePosition();
        }
    }

    private void finishMove() {
        tilePos.set(targetTile);
        isMoving = false;
        moveProgress = 0f;
        targetTile = null;
    }

    private void interpolatePosition() {
        Vector2 start = tileToPixel(tilePos.x, tilePos.y);
        Vector2 end = tileToPixel(targetTile.x, targetTile.y);
        pixelPos.x = start.x + (end.x - start.x) * moveProgress;
        pixelPos.y = start.y + (end.y - start.y) * moveProgress;
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

        targetTile = new GridPoint2(newCol, newRow);
        isMoving = true;
        moveProgress = 0f;
        return true;
    }

    private boolean canMoveTo(GridPoint2 newTile, GameMap map) {
        ArrayList<Entity> entities = map.getEntitiesOnTile(newTile);
        boolean flag = true;
        for (Entity entity : entities) {
            flag &= entity.isStepable();
        }
        return flag;
    }

    public void render() {
    }

    public Vector2 getPixelPosition() {
        return pixelPos;
    }

    public GridPoint2 getTilePosition() {
        return tilePos;
    }

    private void updatePixelPosition() {
        Vector2 pos = tileToPixel(tilePos.x, tilePos.y);
        pixelPos.set(pos);
    }
}
