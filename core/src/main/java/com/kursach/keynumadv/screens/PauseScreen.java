package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseScreen extends BaseScreen {
    private final TextButton resumeButton = new TextButton("Resume", SKIN);
    private final TextButton menuButton = new TextButton("Main Menu", SKIN);

    public PauseScreen(Game myGame) {
        super(myGame);
    }

    @Override
    public void show() {
        stage = new Stage();

        resumeButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.popScreen();
            }
        });

        menuButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                ScreenManager.ShowMainMenu();
            }
        });


        table.center().pad(20);
        table.add(resumeButton).size(200, 50).pad(10).row();
        table.add(menuButton).size(200, 50).pad(10);

        stage.addActor(table);

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 0.5f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            ScreenManager.popScreen();
        }
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
    }
}
