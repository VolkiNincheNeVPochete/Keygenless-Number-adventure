package com.kursach.keynumadv.world.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.world.Entities.Entity;
import com.kursach.keynumadv.world.LocalRender;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class IsometricWorldRenderer {
    private SpriteBatch batch;
    private Texture tileTexture;
    private int mapWidth, mapHeight;
    private int mapTWidth, mapTHeight;


    public IsometricWorldRenderer(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.mapTWidth = (int) (mapWidth / LocalRender.TILE_WIDTH);
        this.mapTHeight = (int) (mapHeight / LocalRender.TILE_HEIGHT);
        this.batch = new SpriteBatch();
        this.tileTexture = new Texture(Gdx.files.internal("sprites/tile.png"));
    }

    public void render(
        OrthographicCamera camera,
        Map<GridPoint2, ArrayList<Entity>> entities
    ) {
        float camX = camera.position.x;
        float camY = camera.position.y;
        float halfH = camera.viewportHeight / 2;
        float halfW = camera.viewportWidth / 2;

        Vector2 tl = new Vector2(camX - halfW, camY - halfH);
        Vector2 br = new Vector2(camX + halfW, camY + halfH );

        GridPoint2 topLeft = LocalRender.PixelToTile(tl);
        GridPoint2 bottomRight = LocalRender.PixelToTile(br);

        int minCol = Math.max(-mapTWidth, topLeft.x);
        int minRow = Math.max(-mapTHeight, topLeft.y);
        int maxCol = Math.min(mapTWidth, bottomRight.x);
        int maxRow = Math.min(mapTHeight, bottomRight.y);

        int minSum = minCol + minRow;
        int maxSum = maxCol + maxRow;

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
//
//        for (int col = minCol; col <= maxCol; col++) {
//            for (int s = minSum; s <= maxSum; s++) {
//                var row = s - col;
//                if (col >= mapWidth || row >= mapHeight) continue;
//
//                GridPoint2 tile = new GridPoint2(col, row);
//                var list = entities.get(tile);
//                if (list != null) {
//                    for (Entity e : list) {
//                        e.render(batch);
//                    }
//                }
//            }
//        }
        for (int col = -mapTWidth; col <= mapTWidth; col++) {
            for (int row = -mapTHeight; row <= mapTHeight; row++) {
                if (col >= mapWidth || row >= mapHeight) continue;

                GridPoint2 tile = new GridPoint2(col, row);
                var list = entities.get(tile);
                if (list != null) {
                    for (Entity e : list) {
                        e.render(batch);
                        batch.setColor(1,1,1,1);
                    }
                }
            }
        }

        batch.end();
    }
    public void dispose() {
        if (batch != null) batch.dispose();
        if (tileTexture != null) tileTexture.dispose();
    }
}
