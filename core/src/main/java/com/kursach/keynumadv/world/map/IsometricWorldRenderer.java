package com.kursach.keynumadv.world.map;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.world.Entities.Entity;
import com.kursach.keynumadv.world.Entities.Player;
import com.kursach.keynumadv.world.LocalRender;

import java.util.ArrayList;
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
        Map<GridPoint2, ArrayList<Entity>> entities,
        Player player
    ) {
        float camX = camera.position.x;
        float camY = camera.position.y;
        float halfH = camera.viewportHeight / 2;
        float halfW = camera.viewportWidth / 2;

        Vector2 tl = new Vector2(camX - halfW, camY - halfH);
        Vector2 br = new Vector2(camX + halfW, camY + halfH);

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

        int rCol;
        int rRow;
        for (int row = mapTHeight; row >= 0; row--) {
            for (int cc = mapTHeight - row; cc >= 0; cc--) {

                rCol = cc;
                rRow = row + cc;

                GridPoint2 tile = new GridPoint2(rCol, rRow);
                var list = entities.get(tile);
                if (list != null) {
                    for (Entity e : list) {
                        e.render(batch);
                        batch.setColor(1, 1, 1, 1);
                    }
                }

                if (rCol == player.getTilePosition().x && rRow == player.getTilePosition().y) player.render(batch);
            }
        }
        for (int col = 0; col <= mapTWidth; col++) {
            for (int cc = mapTWidth - col; cc >= 0; cc--) {

                rCol = col + cc;
                rRow = cc;

                GridPoint2 tile = new GridPoint2(rCol, rRow);
                var list = entities.get(tile);
                if (list != null) {
                    for (Entity e : list) {
                        e.render(batch);
                        batch.setColor(1, 1, 1, 1);
                    }
                }

                if (rCol == player.getTilePosition().x && rRow == player.getTilePosition().y) player.render(batch);
            }
        }

        batch.end();
    }

    public void dispose() {
        if (batch != null) batch.dispose();
        if (tileTexture != null) tileTexture.dispose();
    }
}
