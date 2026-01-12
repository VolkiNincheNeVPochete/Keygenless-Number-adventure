package com.kursach.keynumadv.world;

import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.objects.RectangleMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.GridPoint2;
import com.kursach.keynumadv.world.Entities.Entity;
import com.kursach.keynumadv.world.Entities.Portal;
import com.kursach.keynumadv.world.Entities.Wall;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class GameMap {
    private static final float TILE_WIDTH = 128f;
    private static final float TILE_HEIGHT = 64f;
    public static float widthInPixels;
    public static float heightInPixels;
    private static float offsetX;
    private static float offsetY;
    public int mapWidth;
    public int mapHeight;
    public int tileWidth;
    public int tileHeight;
    public Map<GridPoint2, ArrayList<Entity>> entities = new HashMap<>();
    public GridPoint2 spawn = null;
    TiledMap map;

    public GameMap(String tmxPath) {
        map = new TemplateTmxMapLoader().load(tmxPath);
        MapLayer layer = map.getLayers().get("Entities");
        offsetX = layer.getOffsetX();
        offsetY = layer.getOffsetY();

        int mapWidth = map.getProperties().get("width", Integer.class);
        int mapHeight = map.getProperties().get("height", Integer.class);
        int tileWidth = map.getProperties().get("tilewidth", Integer.class);
        int tileHeight = map.getProperties().get("tileheight", Integer.class);
        widthInPixels = mapWidth * tileWidth;
        heightInPixels = mapHeight * tileHeight;

        if (layer == null) {
            System.err.println("Слой 'Entities' не найден в " + tmxPath);
            return;
        }

        MapObjects objects = layer.getObjects();
        String type = null;

        for (MapObject obj : objects) {
            if (!(obj instanceof RectangleMapObject)) continue;

            var rect = ((RectangleMapObject) obj).getRectangle();
            int worldX = (int) (rect.x + offsetX);
            int worldY = (int) (rect.y + offsetY);

            type = obj.getProperties().get("type", String.class);

            Entity entity = switch (type) {
                case "wall" -> new Wall();
                case "portal" -> new Portal();
                default -> null;
            };

            if (entity != null) {
                GridPoint2 tilePos = pixelToTile(worldX, worldY);
                entities.computeIfAbsent(tilePos, k -> new ArrayList<>()).add(entity);
                System.out.println(entities);
            }
        }
    }

    public static GridPoint2 pixelToTile(int px, int py) {
        int col = px / (64);
        int row = py / (64);
        return new GridPoint2(col, row);
    }

    public static float getWidth() {
        return widthInPixels;
    }

    public static float getHeight() {
        return heightInPixels;
    }

    public GridPoint2 getSpawnTile() {
        if (spawn == null) return new GridPoint2(0, 0);
        GridPoint2 tile = pixelToTile(spawn.x, spawn.y);
        return tile;
    }

    public ArrayList<Entity> getEntitiesOnTile(GridPoint2 tile) {
        return entities.get(tile);
    }

    public void dispose() {
        if (map != null) {
            map.dispose();
        }
    }
}
