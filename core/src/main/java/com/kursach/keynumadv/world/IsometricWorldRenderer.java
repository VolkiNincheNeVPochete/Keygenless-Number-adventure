package com.kursach.keynumadv.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.world.Entities.Entity;

public class IsometricWorldRenderer {
    private SpriteBatch batch;
    private Texture tileTexture;
    private int mapWidth, mapHeight;

    public IsometricWorldRenderer(int mapWidth, int mapHeight) {
        this.mapWidth = mapWidth;
        this.mapHeight = mapHeight;
        this.batch = new SpriteBatch();
        this.tileTexture = new Texture(Gdx.files.internal("sprites/tile.png"));
    }

    public void render(OrthographicCamera camera, GameMap gameMap) {
        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        for (int row = 0; row <= mapHeight; row++) {
            for (int col = 0; col <= mapWidth; col++) {
                Vector2 pos = LocalRender.TileToPixel(col, row);
                batch.draw(tileTexture, pos.x, pos.y);
            }
        }
        for (GridPoint2 tile : gameMap.entities.keySet()) {
            for (Entity e : gameMap.entities.get(tile)) {
                e.render(batch, new GridPoint2(tile.x - 1, tile.y));
            }
        }
        batch.end();
    }

    public void dispose() {
        if (batch != null) batch.dispose();
        if (tileTexture != null) tileTexture.dispose();
    }
}
