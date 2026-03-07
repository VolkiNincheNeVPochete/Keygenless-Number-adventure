package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.kursach.keynumadv.screens.GameOverScreen;
import com.kursach.keynumadv.screens.ScreenManager;

import java.util.Timer;
import java.util.TimerTask;

public class Enemy extends Entity {
    static Texture texture = new Texture(Gdx.files.internal("sprites/enemies/Centipede/Centipede_enemy.png"));
    private static BitmapFont valueFont;
    private static GlyphLayout glyphLayout;

    static {
        valueFont = new BitmapFont();
        valueFont.getData().setScale(1.2f);
        valueFont.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        glyphLayout = new GlyphLayout();
    }

    private float VALUE;
    private float osCount;
    private float ofCount;
    private State state = State.UNACTIVE;

    public Enemy(GridPoint2 tilePos, float thisValue) {
        super(tilePos);
        this.VALUE = thisValue;
    }

    public Enemy(float thisValue) {
        super();
        this.VALUE = thisValue;
    }

    @Override
    protected void init() {
        this.isFinished = false;
        osCount = 255f;
        ofCount = 0f;
    }

    @Override
    public boolean isStepable() {
        return true;
    }

    @Override
    public void onStep(Player currentPlayer) {
        timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                Gdx.app.postRunnable(() -> {
                    osCount--;
                    if (osCount <= 0) {
                        StartFight(currentPlayer);
                        isFinished = true;
                        timer.cancel();
                    }
                });
            }
        }, 0, 1);
    }

    @Override
    public void onFinish(Player currentPlayer) {
        if (state == State.DEFEATED) {
            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Gdx.app.postRunnable(() -> {
                        ofCount++;
                        if (ofCount >= 255) {
                            isFinalized = true;
                            timer.cancel();
                        }
                    });
                }
            }, 0, 10);
        } else if (state == State.WINNING) {
            ScreenManager.pushScreen(new GameOverScreen(currentPlayer.getVALUE()));
        }
    }

    @Override
    public boolean isFinished() {
        return isFinished;
    }

    @Override
    public boolean isFinalized() {
        return isFinalized;
    }

    private void StartFight(Player currentPlayer) {
        if (currentPlayer.getVALUE() >= VALUE) {
            state = State.DEFEATED;
        } else {
            state = State.WINNING;
        }
    }

    @Override
    public void Reward(Player player) {
        player.updateValue(VALUE);
    }

    public void render(Batch batch, GridPoint2 tilePos) {
    }

    public void render(Batch batch) {
        Vector2 visualPos = getVisualPixelPosition();

        batch.setColor(1, 1, 1, (255 - ofCount) / 255f);
        batch.draw(this.texture, visualPos.x, visualPos.y);

        batch.setColor(1, 1, 1, 1);

        String valueText = String.valueOf(VALUE);

        float textX = visualPos.x + texture.getWidth() / 2f;
        float textY = visualPos.y + texture.getHeight() + 15;

        glyphLayout.setText(valueFont, valueText);
        float textWidth = glyphLayout.width;

        valueFont.setColor(1, 1, 1, 1);
        valueFont.draw(batch, glyphLayout, textX - textWidth / 2f, textY);
    }

    enum State {
        UNACTIVE,
        STEPPED,
        DEFEATED,
        WINNING;
    }
}
