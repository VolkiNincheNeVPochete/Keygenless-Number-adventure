package com.kursach.keynumadv.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public final class LocalRender {
    public final static float TILE_SIDE = 64f;
    public final static float TILE_HEIGHT = 64f;
    public final static float TILE_WIDTH = 128f;
    public final static float CAM_WIDTH = Gdx.graphics.getWidth()/2;
    public final static float CAM_HEIGHT = Gdx.graphics.getHeight()/2;
    public final static boolean Y_DOWN = false;

    public static GridPoint2 GridPixelToTile(float px, float py) {

        float col = px / TILE_SIDE;
        float row = py / TILE_SIDE;

        return new GridPoint2((int) col, (int) row);
    }
    public static GridPoint2 PixelToTile(float px, float py) {

        float col = px / TILE_WIDTH  - py / TILE_HEIGHT ;
        float row = px / TILE_WIDTH  + py / TILE_HEIGHT ;

        return new GridPoint2((int) col, (int) row);
    }

    public static GridPoint2 PixelToTile(Vector2 pixelPos) {

        float px = pixelPos.x;
        float py = pixelPos.y;

        float col = px / TILE_WIDTH  - py / TILE_HEIGHT ;
        float row = px / TILE_WIDTH  + py / TILE_HEIGHT ;

        return new GridPoint2((int) col, (int) row);
    }

    public static Vector2 TileToPixel(int col, int row) {

        float x = (col + row ) * TILE_WIDTH / 2;
        float y = (-col + row ) * TILE_HEIGHT / 2;

        return new Vector2(x, y);
    }

    public static Vector2 TileToPixel(GridPoint2 tilePos) {

        int col = tilePos.x;
        int row = tilePos.y;

        float x = (col + row ) * TILE_WIDTH / 2;
        float y = (-col + row ) * TILE_HEIGHT / 2;

        return new Vector2(x, y);
    }
    public static GridPoint2 PlayerPixelToTile(float px, float py) {

        float col = px / TILE_WIDTH + 1 - py / TILE_HEIGHT - 1;
        float row = px / TILE_WIDTH - 1 + py / TILE_HEIGHT - 1;

        return new GridPoint2((int) col, (int) row);
    }

    public static GridPoint2 PlayerPixelToTile(Vector2 pixelPos) {

        float px = pixelPos.x;
        float py = pixelPos.y;

        float col = px / TILE_WIDTH + 1 - py / TILE_HEIGHT - 1;
        float row = px / TILE_WIDTH - 1 + py / TILE_HEIGHT - 1;

        return new GridPoint2((int) col, (int) row);
    }

    public static Vector2 PlayerTileToPixel(int col, int row) {

        float x = (col + row + 1) * TILE_WIDTH / 2;
        float y = (-col + row + 1) * TILE_HEIGHT / 2;

        return new Vector2(x, y);
    }

    public static Vector2 PlayerTileToPixel(GridPoint2 tilePos) {

        int col = tilePos.x;
        int row = tilePos.y;

        float x = (col + row + 1) * TILE_WIDTH / 2;
        float y = (-col + row + 1) * TILE_HEIGHT / 2;

        return new Vector2(x, y);
    }
}
