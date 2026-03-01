package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kursach.keynumadv.world.BusyManager;
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
    private IsometricWorldRenderer entityRenderer;
    private BusyManager busyManager;
    private Stage stage;
    private Label valueLabel;

    public GameScreen(Game myGame) {
        super(myGame);
        init();
    }
    private void init() {
        batch = new SpriteBatch();

        stage = new Stage();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.BLACK;
        valueLabel = new Label("", style);
        valueLabel.setPosition(50, 50);
        valueLabel.setFontScale(5,5);
        stage.addActor(valueLabel);

        camera = new OrthographicCamera();
        camera.setToOrtho(Y_DOWN, CAM_WIDTH, CAM_HEIGHT);
        camera.update();

        level = new Level("maps/level2.tmx");

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
        entityRenderer = new IsometricWorldRenderer((int) level.getWidth(), (int) level.getHeight());
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
        entityRenderer.render(camera, level.gameMap.entities, player);

        stage.act(delta);
        stage.draw();
        valueLabel.setText("Power: " + player.getVALUE());

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
        if (stage != null) stage.dispose();
    }
    private void handleInput() {
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
