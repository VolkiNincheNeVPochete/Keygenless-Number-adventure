package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen extends BaseScreen {
    private final TextButton playButton = new TextButton("Start", skin);
    private final TextButton settingButton = new TextButton("Settings", skin);
    private final TextButton exitButton = new TextButton("Exit", skin);

    public MainMenuScreen() {
        super();
    }

    public MainMenuScreen(Game myGame, ScreenManager screMan) {
        super(myGame, screMan);
    }

    @Override
    public void show() {
        if (stage == null) {
            stage = new Stage();

            table.setFillParent(true);
            table.center().pad(20);

            table.add(playButton).size(200, 50).pad(10).row();
            table.add(settingButton).size(200, 50).pad(10).row();
            table.add(exitButton).size(200, 50).pad(10);

            playButton.setPosition(100f, 100f);
            settingButton.setPosition(100f, 400f);
            exitButton.setPosition(100f, 700f);

            stage.addActor(playButton);
            stage.addActor(settingButton);
            stage.addActor(exitButton);

            playButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    screMan.pushScreen(new GameScreen(myGame, screMan));
                }
            });

            settingButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    screMan.pushScreen(new SettingsScreen());
                }
            });

            exitButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    screMan.exit();
                }
            });
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {

        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);
    }
}

