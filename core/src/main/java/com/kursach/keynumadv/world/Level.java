package com.kursach.keynumadv.world;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.MapProperties;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;

public class Level {
    private final TmxMapLoader mapLoader;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer renderer;
    private final float width;
    private final float height;

    public Level(String mapPath) {
        mapLoader = new TmxMapLoader();
        map = mapLoader.load(mapPath);
        renderer = new OrthogonalTiledMapRenderer(map);

        MapProperties props = map.getProperties();
        width = props.get("width", Integer.class) * props.get("tilewidth", Integer.class);
        height = props.get("height", Integer.class) * props.get("tileheight", Integer.class);
    }

    public void render(OrthographicCamera camera) {
        renderer.setView(camera);
        renderer.render();
    }

    public void update(float delta) {

    }

    public float getWidth() { return width; }

    public float getHeight() { return height; }

    public void dispose() {
        map.dispose();
        renderer.dispose();
    }
}
