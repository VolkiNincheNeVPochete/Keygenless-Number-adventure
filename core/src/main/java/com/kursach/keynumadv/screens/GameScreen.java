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
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.kursach.keynumadv.world.BusyManager;
import com.kursach.keynumadv.world.Entities.CameraController;
import com.kursach.keynumadv.world.Entities.Entity;
import com.kursach.keynumadv.world.Entities.Player;
import com.kursach.keynumadv.world.GeneralEvents;
import com.kursach.keynumadv.world.map.GameMap;
import com.kursach.keynumadv.world.map.IsometricWorldRenderer;

import static com.kursach.keynumadv.world.LocalRender.*;


public class GameScreen extends BaseScreen {
    public GameMap gameMap;
    private String levelPath;
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
        this.levelPath = "maps/level1.tmx";
        init();
    }

    public GameScreen(Game myGame, String levelPath) {
        super(myGame);
        this.levelPath = levelPath;
        init();
    }

    private void init() {
        GeneralEvents.setLevelPath(levelPath);
        Entity.setPlayer(player);
        Entity.setBusyManager(busyManager);

        batch = new SpriteBatch();

        stage = new Stage();
        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.BLACK;
        valueLabel = new Label("", style);
        valueLabel.setPosition(50, 50);
        valueLabel.setFontScale(5, 5);
        stage.addActor(valueLabel);

        camera = new OrthographicCamera();
        camera.setToOrtho(Y_DOWN, CAM_WIDTH, CAM_HEIGHT);
        camera.update();

        gameMap = new GameMap(levelPath);

        busyManager = new BusyManager();
        player = new Player(gameMap.getSpawnTile(), gameMap.getSpawnValue(), busyManager);

        cameraController = new CameraController(
            camera,
            gameMap.getWidth(),
            gameMap.getHeight()
        );
        cameraController.follow(player.getVisualPixelPosition());
        renderer = new IsometricTiledMapRenderer(gameMap.map);
        entityRenderer = new IsometricWorldRenderer((int) gameMap.getWidth(), (int) gameMap.getHeight());
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        renderer.setView(camera);
        renderer.render();
        entityRenderer.render(camera, gameMap.entities, player);

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
    public void dispose() {
        if (gameMap != null) {
            gameMap.dispose();
            gameMap = null;
        }
        if (batch != null) {
            batch.dispose();
            batch = null;
        }
        if (stage != null) {
            stage.dispose();
            stage = null;
        }
        if (entityRenderer != null) {
            entityRenderer.dispose();
            entityRenderer = null;
        }
        if (player != null) {
            player.dispose();
            player = null;
        }
    }

    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            ScreenManager.pushScreen(new PauseScreen(myGame));
            return;
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP) |
            Gdx.input.isKeyJustPressed(Input.Keys.W)) {
            player.tryMove(0, 1, gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN) |
            Gdx.input.isKeyJustPressed(Input.Keys.S)) {
            player.tryMove(0, -1, gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT) |
            Gdx.input.isKeyJustPressed(Input.Keys.D)) {
            player.tryMove(1, 0, gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT) |
            Gdx.input.isKeyJustPressed(Input.Keys.A)) {
            player.tryMove(-1, 0, gameMap);
        }
    }
}
