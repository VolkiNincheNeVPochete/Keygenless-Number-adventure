package com.kursach.keynumadv.world;

import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

public final class LocalRender {
    public final static float TILE_SIDE = 64f;
    public final static float TILE_HEIGHT = 64f;
    public final static float TILE_WIDTH = 128f;

    public static GridPoint2 GridPixelToTile(float px, float py) {

        float col = px / TILE_SIDE;
        float row = py / TILE_SIDE;

        return new GridPoint2((int) col, (int) row);
    }

    public static GridPoint2 PixelToTile(float px, float py) {

        float col = py / TILE_HEIGHT + px / TILE_WIDTH;
        float row = py / TILE_HEIGHT - px / TILE_WIDTH;

        return new GridPoint2((int) col, (int) row);
    }

    public static GridPoint2 PixelToTile(Vector2 pixelPos) {

        float px = pixelPos.x;
        float py = pixelPos.y;

        float col = py / TILE_HEIGHT + px / TILE_WIDTH;
        float row = py / TILE_HEIGHT - px / TILE_WIDTH;

        return new GridPoint2((int) col, (int) row);
    }

    public static Vector2 TileToPixel(int col, int row) {

        float x = (col - row) * TILE_WIDTH / 2;
        float y = (col + row) * TILE_HEIGHT / 2;

        return new Vector2(x, y);
    }

    public static Vector2 TileToPixel(GridPoint2 tilePos) {

        int col = tilePos.x;
        int row = tilePos.y;

        float x = (col - row) * TILE_WIDTH / 2;
        float y = (col + row) * TILE_HEIGHT / 2;

        return new Vector2(x, y);
    }
}
