package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.kursach.keynumadv.world.*;
import com.kursach.keynumadv.world.Entities.CameraController;
import com.kursach.keynumadv.world.Entities.Entity;
import com.kursach.keynumadv.world.Entities.Player;
import com.kursach.keynumadv.world.map.IsometricWorldRenderer;
import com.kursach.keynumadv.world.map.Level;

import static com.kursach.keynumadv.world.LocalRender.*;


public class GameScreen extends BaseScreen {
    private Level level;
    private Player player;
    private CameraController cameraController;
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private IsometricTiledMapRenderer renderer;
    private IsometricWorldRenderer entityrenderer;
    private BusyManager busyManager;

    public GameScreen(Game myGame) {
        super(myGame);
        init();
    }

    private void init() {
        batch = new SpriteBatch();

        camera = new OrthographicCamera();
        camera.setToOrtho(Y_DOWN, CAM_WIDTH, CAM_HEIGHT);
        camera.update();

        level = new Level("maps/level1.tmx");

        busyManager = new BusyManager();
        player = new Player(level.getSpawn(), busyManager);

        Entity.setPlayer(player);
        Entity.setBusyManager(busyManager);

        cameraController = new CameraController(
            camera,
            level.getWidth(),
            level.getHeight()
        );

        cameraController.follow(player.getVisualPixelPosition());
        renderer = new IsometricTiledMapRenderer(level.gameMap.map);
        entityrenderer = new IsometricWorldRenderer((int) level.getWidth(), (int) level.getHeight());
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();
        entityrenderer.render(camera, level.gameMap.entities);
        player.render();

        handleInput();
        player.update(delta);
        cameraController.follow(player.getVisualPixelPosition());
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(Y_DOWN, CAM_WIDTH, CAM_HEIGHT);
        camera.update();
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
        if (level != null) level.dispose();
        if (batch != null) batch.dispose();
    }


    private void handleInput() {
        if (Gdx.input.isTouched()) {
            int screenX = Gdx.input.getX();
            int screenY = Gdx.input.getY();

            Vector3 worldPos = new Vector3(screenX, screenY, 0);
            camera.unproject(worldPos);

            System.out.println("World pixel position: (" + worldPos.x + ", " + worldPos.y + ")");
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.tryMove(0, 1, level.gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.tryMove(0, -1, level.gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.tryMove(1, 0, level.gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.tryMove(-1, 0, level.gameMap);
        }
    }
}
