package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainMenuScreen extends BaseScreen {
    private TextButton playButton;
    private TextButton settingButton;
    private TextButton exitButton;
    public MainMenuScreen(Game myGame) {
        super(myGame);
    }

    @Override
    public void show() {
        stage = new Stage();

        playButton = new TextButton("Start", skin);
        settingButton = new TextButton("Settings", skin);
        exitButton = new TextButton("Exit", skin);

        Table table = new Table();
        table.setFillParent(true);
        table.center().pad(20);

        table.add(playButton).size(200, 50).pad(10).row();
        table.add(settingButton).size(200, 50).pad(10).row();
        table.add(exitButton).size(200, 50).pad(10);

        playButton.setPosition(100f, 100f);
        playButton.setSize(200f, 50f);
        settingButton.setPosition(100f, 400f);
        settingButton.setSize(200f, 50f);
        exitButton.setPosition(100f, 700f);
        exitButton.setSize(200f, 50f);

        stage.addActor(playButton);
        stage.addActor(settingButton);
        stage.addActor(exitButton);

        Gdx.input.setInputProcessor(stage);


        playButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screMan.showGame();
            }
        });
        settingButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screMan.showSettings();
                System.out.printf("кнопка нажата");
            }
        });
        exitButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screMan.exit();
            }
        });
    }

//    @Override
//    public void render(float delta) {
//
//    }

    @Override
    public void resize(int width, int height) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

//    @Override
//    public void dispose() {
//
//    }
}
