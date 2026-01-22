package com.kursach.keynumadv.world.map;

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

import static com.kursach.keynumadv.world.LocalRender.*;

public class GameMap {
    public static int widthInPixels;
    public static int heightInPixels;
    public static int tileWidth;
    public static int tileHeight;
    private static float offsetX;
    private static float offsetY;
    public int mapWidth;
    public int mapHeight;
    public Map<GridPoint2, ArrayList<Entity>> entities = new HashMap<>();
    public GridPoint2 spawn = null;
    public TiledMap map;

    public GameMap(String tmxPath) {
        map = new TemplateTmxMapLoader().load(tmxPath);
        MapLayer layer = map.getLayers().get("Entities");
        offsetX = layer.getOffsetX();
        offsetY = layer.getOffsetY();

        mapWidth = map.getProperties().get("width", Integer.class);
        mapHeight = map.getProperties().get("height", Integer.class);
        widthInPixels = (int) (mapWidth * TILE_WIDTH);
        heightInPixels = (int) (mapHeight * TILE_HEIGHT);

        if (layer == null) {
            System.err.println("Слой 'Entities' не найден в " + tmxPath);
            return;
        }

        MapObjects objects = layer.getObjects();

        for (MapObject obj : objects) {
            String type = null;
            if (!(obj instanceof RectangleMapObject)) continue;

            var rect = ((RectangleMapObject) obj).getRectangle();
            float worldX = ((rect.x + offsetX));
            float worldY = ((rect.y + offsetY));

            type = obj.getProperties().get("type", String.class);

            Entity entity = switch (type) {
                case "wall" -> new Wall();
                case "portal" -> new Portal();
                default -> null;
            };

            if (entity != null) {
                GridPoint2 tilePos = GridPixelToTile(worldX, worldY);
                entity.SetPos(tilePos);
                entities.computeIfAbsent(tilePos, k -> new ArrayList<>()).add(entity);
                continue;
            }

            if ("spawn".equals(type)) {
                GridPoint2 tilePos = GridPixelToTile(worldX, worldY);
                spawn = tilePos;
            }
        }
    }

    public static float getWidth() {
        return widthInPixels + offsetX;
    }

    public static float getHeight() {
        return heightInPixels - tileHeight + offsetY;
    }

    public GridPoint2 getSpawnTile() {
        if (spawn == null) return new GridPoint2(0, 0);
        return spawn;
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
