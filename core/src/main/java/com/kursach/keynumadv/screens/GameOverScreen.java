package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class GameOverScreen extends BaseScreen {
    private final float playerValue;
    private final TextButton restartButton = new TextButton("Restart", skin);
    private final TextButton menuButton = new TextButton("Main Menu", skin);
    private Label scoreLabel;

    public GameOverScreen(float playerValue) {
        super();
        this.playerValue = playerValue;
    }

    public GameOverScreen(Game myGame, float playerValue) {
        super(myGame);
        this.playerValue = playerValue;
    }

    @Override
    public void show() {
        if (stage == null) {
            stage = new Stage();

            Label.LabelStyle scoreStyle = new Label.LabelStyle();
            scoreStyle.font = new BitmapFont();
            scoreStyle.fontColor = Color.RED;
            scoreLabel = new Label("Game Over!", scoreStyle);
            scoreLabel.setFontScale(3, 3);

            Label.LabelStyle valueStyle = new Label.LabelStyle();
            valueStyle.font = new BitmapFont();
            valueStyle.fontColor = Color.BLACK;
            Label valueLabel = new Label("Your Score: " + (int) playerValue, valueStyle);
            valueLabel.setFontScale(2, 2);

            table.setFillParent(true);
            table.center().pad(20);

            table.add(scoreLabel).pad(20).row();
            table.add(valueLabel).pad(20).row();
            table.add(restartButton).size(200, 50).pad(10).row();
            table.add(menuButton).size(200, 50).pad(10);

            stage.addActor(table);

            restartButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ScreenManager.ShowGame();
                }
            });

            menuButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ScreenManager.ShowMainMenu();
                }
            });
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.3f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
    }
}
