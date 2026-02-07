package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.world.BusyManager;
import com.kursach.keynumadv.world.map.GameMap;

import java.util.ArrayList;

import static com.kursach.keynumadv.world.LocalRender.TileToPixel;

public class Player {
    private GridPoint2 tilePos;
    private Vector2 pixelPos;
    private final int OFFSET_X  = 64;
    private final int OFFSET_TX = 0;
    private final int OFFSET_Y = 96;
    private final int OFFSET_TY = -1;

    private boolean isMoving = false;
    private float moveProgress = 0f;
    private GridPoint2 targetTile = null;
    private Vector2 startPixel;
    private Vector2 targetPixel;

    private BusyManager busyManager;
    private float VALUE;

    private BitmapFont playerFont;
    private Texture playerSheet;
    private TextureRegion[][] sprites;
    private int currentDirection = 0;
    private TextureRegion currentFrame;
    private float animTime = 0f;
    private static final float FRAME_DURATION = 0.15f;
    private static final int[][] DIRECTIONS = {{0, 3, 0}, {4, 0, 2}, {0, 1, 0}};
    private float spriteScale = 2.0f;

    public Player(GridPoint2 spawnTile, BusyManager busyManager) {
        this.tilePos = spawnTile;
        this.pixelPos = TileToPixel(tilePos.x, tilePos.y);
        this.startPixel = new Vector2();
        this.targetPixel = new Vector2();
        this.busyManager = busyManager;
        this.playerFont = new BitmapFont(Gdx.files.internal("commodore64/raw/commodore-64.fnt"));
        this.playerFont.setColor(Color.BLACK);
        this.VALUE = 1;

        playerSheet = new Texture(Gdx.files.internal("sprites/character/SpriteSheet.png"));
        playerSheet.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Nearest);

        int spriteWidth = 23;
        int spriteHeight = 36;
        int offsetX = 2;
        int offsetY = 2;
        sprites = new TextureRegion[5][9];
        for (int col = 0; col < 9; col++) {
            sprites[0][col] = new TextureRegion(playerSheet, offsetX, offsetY, spriteWidth, spriteHeight);
        }
        for (int row = 0; row < 4; row ++) {
            for (int col = 0; col < 9; col++) {
                int x = offsetX + col * (spriteWidth);
                int y = offsetY + (1 + row * 2) * (spriteHeight);
                sprites[row+1][col] = new TextureRegion(playerSheet, x, y, spriteWidth, spriteHeight);
            }
        }

        currentFrame = sprites[currentDirection][0]; // idle
        animTime = 0f;
    }

    public float getVALUE() {
        return VALUE;
    }

    public void update(float delta) {
        if (busyManager.isActive()) {
            busyManager.Update();
            return;
        }
        if (isMoving) {
            animTime += delta;
            int frameIndex = (int) (animTime / FRAME_DURATION) % 3 + 1; // кадры 1,2,3
            currentFrame = sprites[currentDirection][frameIndex];
            moveProgress += delta * 4.0f;
            if (moveProgress >= 1f) {
                finishMove();
            } else {
                pixelPos.x = startPixel.x + (targetPixel.x - startPixel.x) * moveProgress;
                pixelPos.y = startPixel.y + (targetPixel.y - startPixel.y) * moveProgress;
            }
        }
        else {
            currentFrame = sprites[currentDirection][0]; // idle
            animTime = 0f;
        }
    }

    private void finishMove() {
        tilePos.set(targetTile);
        pixelPos.set(targetPixel);
        isMoving = false;
        moveProgress = 0f;
        targetTile = null;
        startPixel.set(pixelPos);
        targetPixel.set(pixelPos);

        busyManager.Start();
    }

    public boolean tryMove(int dx, int dy, GameMap map) {
        if (isMoving || busyManager.isActive()) return false;

        currentDirection = DIRECTIONS[dx+1][dy+1];

        int newCol = tilePos.x + dx;
        int newRow = tilePos.y + dy;

        if (newCol < 0 || newCol >= map.mapWidth || newRow < 0 || newRow >= map.mapHeight) {
            return false;
        }

        GridPoint2 newTile = new GridPoint2(newCol, newRow);
        System.out.println("Going to " + newTile);
        if (!canMoveTo(newTile, map)) {
            return false;
        }

        startPixel.set(pixelPos);

        targetPixel.set(TileToPixel(newTile.x, newTile.y));
        targetTile = newTile;
        isMoving = true;
        moveProgress = 0f;
        busyManager.CreateQueue(map.getEntitiesOnTile(newTile));
        return true;
    }

    private boolean canMoveTo(GridPoint2 newTile, GameMap map) {
        ArrayList<Entity> entities = map.getEntitiesOnTile(newTile);
        if (entities == null) return true;
        for (Entity entity : entities) {
            if (!entity.isStepable()) {
                return false;
            }
        }
        return true;
    }
    public Vector2 getVisualPixelPosition() {
        return new Vector2(pixelPos.x+OFFSET_X, pixelPos.y+OFFSET_Y);
    }
    public GridPoint2 getVisualTilePosition() {
        return new GridPoint2(tilePos.x+OFFSET_TX, tilePos.y+OFFSET_TY);
    }
    public Vector2 getPixelPosition() {
        return new Vector2(pixelPos.x, pixelPos.y);
    }
    public GridPoint2 getTilePosition() {
        return new GridPoint2(tilePos.x, tilePos.y);
    }
    public void render(SpriteBatch batch) {
        Vector2 visualPos = getVisualPixelPosition();

        float spriteWidth = currentFrame.getRegionWidth() * spriteScale;
        float spriteHeight = currentFrame.getRegionHeight() * spriteScale;

        float drawX = visualPos.x - spriteWidth / 2f;
        float drawY = visualPos.y - spriteHeight;

        batch.draw(currentFrame, drawX, drawY, spriteWidth, spriteHeight);
    }
}
