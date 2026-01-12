package com.kursach.keynumadv.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.GridPoint2;

public class Level {
    public GameMap gameMap;
    private OrthogonalTiledMapRenderer renderer;
    private GridPoint2 spawn;

    public Level(String mapPath) {
        TmxMapLoader loader = new TmxMapLoader();
        this.gameMap = new GameMap(mapPath);
        this.renderer = new OrthogonalTiledMapRenderer(gameMap.map);
        this.spawn = gameMap.getSpawnTile();
    }

    public GridPoint2 getSpawn() {
        return spawn;
    }

    //реализовать изстеппабля


    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public void update(float delta) {
    }

    public float getWidth() {
        return GameMap.widthInPixels;
    }

    public float getHeight() {
        return GameMap.heightInPixels;
    }

    public void dispose() {
        gameMap.dispose();
        renderer.dispose();
    }
}
