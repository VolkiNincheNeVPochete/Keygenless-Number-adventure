package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class LevelCompleteScreen extends BaseScreen {
    private final TextButton toMainMenuButton = new TextButton("To main menu", skin);
    public LevelCompleteScreen() {
        super();
    }
    public LevelCompleteScreen(Game myGame) {
        super(myGame);
    }

    @Override
    public void show() {
        if (stage == null) {
            stage = new Stage();

            table.setFillParent(true);
            table.center().pad(20);

            table.add(toMainMenuButton).size(200, 50).pad(10).row();

            toMainMenuButton.setPosition(100f, 700f);

            stage.addActor(toMainMenuButton);

            toMainMenuButton.addListener(new ClickListener() {
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

        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.draw();
        stage.act(delta);
    }

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

    @Override
    public void dispose() {
        stage.dispose();
    }
}
