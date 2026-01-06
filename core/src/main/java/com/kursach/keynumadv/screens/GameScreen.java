package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.nio.file.Path;

public class GameScreen extends BaseScreen {
    public Path levelPath;
    private TextButton pauseButton = new TextButton("Pause", skin);

    public GameScreen() {
        super();
    }
    public GameScreen(Game myGame, ScreenManager screMan) {
        super(myGame, screMan);
    }
    public GameScreen(Game myGame, ScreenManager screMan, Path levelPath) {
        super(myGame, screMan);
        this.levelPath = levelPath;
    }

    @Override
    public void show() {
        if (stage == null) {
            stage = new Stage();

            table.setFillParent(true);
            table.center().pad(20);

            table.add(pauseButton).size(200, 50).pad(10).row();
            pauseButton.setPosition(1300f, 100f);

            stage.addActor(pauseButton);

            pauseButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    screMan.pushScreen(new PauseScreen());
                }
            });
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
    }
}
