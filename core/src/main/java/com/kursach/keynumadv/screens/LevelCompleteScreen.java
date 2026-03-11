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

public class LevelCompleteScreen extends BaseScreen {
    private final float playerValue;
    private final TextButton nextLevelButton = new TextButton("Next Level", SKIN);
    private final TextButton restartButton = new TextButton("Restart", SKIN);
    private final TextButton menuButton = new TextButton("Main Menu", SKIN);
    private String levelPath;
    private Label scoreLabel;
    private Label valueLabel;

    public LevelCompleteScreen(Game myGame, float playerValue, String levelPath) {
        super(myGame);
        this.levelPath = levelPath;
        this.playerValue = playerValue;
    }

    @Override
    public void show() {
        stage = new Stage();

        Label.LabelStyle titleStyle = new Label.LabelStyle();
        titleStyle.font = new BitmapFont();
        titleStyle.fontColor = Color.GREEN;
        scoreLabel = new Label("Level Complete!", titleStyle);
        scoreLabel.setFontScale(3, 3);

        Label.LabelStyle valueStyle = new Label.LabelStyle();
        valueStyle.font = new BitmapFont();
        valueStyle.fontColor = Color.BLACK;
        valueLabel = new Label("Your Score: " + (int) playerValue, valueStyle);
        valueLabel.setFontScale(2, 2);

        table.setFillParent(true);
        table.center().pad(20);

        table.add(scoreLabel).pad(20).row();
        table.add(valueLabel).pad(20).row();
        table.add(nextLevelButton).size(200, 50).pad(10).row();
        table.add(restartButton).size(200, 50).pad(10).row();
        table.add(menuButton).size(200, 50).pad(10);

        stage.addActor(table);

        nextLevelButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.ShowLevelSelectScreen();
            }
        });

        restartButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.ShowGame(levelPath);
            }
        });

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.ShowMainMenu();
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0.5f, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
    }
}
