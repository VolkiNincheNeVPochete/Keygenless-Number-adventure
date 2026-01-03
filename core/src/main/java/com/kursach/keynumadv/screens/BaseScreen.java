package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;


public class BaseScreen implements Screen {
    protected Game myGame;
    protected Stage stage;
    protected final Skin skin = new Skin(Gdx.files.internal("commodore64/skin/uiskin.json"));
    protected ScreenManager screMan;
    protected int screenWidth = Gdx.graphics.getWidth();
    protected int screenHeight = Gdx.graphics.getHeight();

    public BaseScreen(Game myGame) {
        this.myGame = myGame;
        this.screMan = new ScreenManager(myGame);
    }

    @Override
    public void show() {
        stage = new Stage();
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
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
        if (stage != null) stage.dispose();
        if (skin != null) skin.dispose();
    }
}
