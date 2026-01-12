package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.GridPoint2;
import com.kursach.keynumadv.world.CameraController;
import com.kursach.keynumadv.world.Level;
import com.kursach.keynumadv.world.Player;

public class GameScreen implements Screen {
    private Level level;
    private Player player;
    private CameraController cameraController;
    private OrthographicCamera camera;

    public GameScreen() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 600);

        level = new Level("maps/level1.tmx");

        GridPoint2 spawn = level.getSpawn();
        player = new Player(spawn);

        cameraController = new CameraController(
            camera,
            level.getWidth(),
            level.getHeight()
        );
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        handleInput();

        player.update(delta);
        cameraController.follow(player.getPixelPosition());

        Gdx.gl.glClearColor(0.2f, 0.3f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        level.render(camera);
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

    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            System.out.println(Input.Keys.UP);
            player.tryMove(0, -1, level.gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.tryMove(0, 1, level.gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.tryMove(-1, 0, level.gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.tryMove(1, 0, level.gameMap);
        }
    }
}
