package com.kursach.keynumadv.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.GridPoint2;

public class Level {
    private static GridPoint2 spawn;
    public GameMap gameMap;
    private IsometricWorldRenderer worldRenderer;

    public Level(String mapPath) {
        gameMap = new GameMap(mapPath);
        worldRenderer = new IsometricWorldRenderer(gameMap.mapWidth, gameMap.mapHeight);
        spawn = gameMap.getSpawnTile();
    }

    public GridPoint2 getSpawn() {
        return spawn;
    }

    //реализовать изстеппабля

    public void render(OrthographicCamera camera) {
        worldRenderer.render(camera, gameMap);
    }

    public void update(float delta) {
    }

    public float getWidth() {
        return GameMap.getWidth();
    }

    public float getHeight() {
        return GameMap.getHeight();
    }

    public void dispose() {
        if (worldRenderer != null) worldRenderer.dispose();
        if (gameMap != null) gameMap.dispose();
    }
}
