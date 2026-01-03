package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class SettingsScreen extends BaseScreen {
    private Button backButton;
    public SettingsScreen(Game myGame) {
        super(myGame);
    }

    @Override
    public void show() {
        stage = new Stage();

        backButton = new TextButton("Go back", skin);

        backButton.setPosition(100f, 100f);
        backButton.setScale(200f, 50f);

        stage.addActor(backButton);

        Gdx.input.setInputProcessor(stage);


        backButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                screMan.showMainMenu();
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
