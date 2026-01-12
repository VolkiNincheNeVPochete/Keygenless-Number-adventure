package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class PauseScreen extends BaseScreen {
    private final TextButton resumeButton = new TextButton("Resume", skin);
    private final TextButton settingButton = new TextButton("Settings", skin);
    private final TextButton menuButton = new TextButton("Main Menu", skin);

    public PauseScreen() {
        super();
    }

    public PauseScreen(Game myGame, ScreenManager screMan) {
        super(myGame, screMan);
    }

    @Override
    public void show() {
        if (stage == null) {
            stage = new Stage();

            resumeButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    screMan.popScreen();
                }
            });

            settingButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    screMan.pushScreen(new SettingsScreen());
                }
            });

            menuButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    screMan.ShowMainMenu();
                }
            });


            table.center().pad(20);
            table.add(resumeButton).size(200, 50).pad(10).row();
            table.add(settingButton).size(200, 50).pad(10).row();
            table.add(menuButton).size(200, 50).pad(10);

            stage.addActor(table);
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1, 1, 1, 0.7f);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
    }
}
