package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.GlyphLayout;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.renderers.IsometricTiledMapRenderer;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kursach.keynumadv.world.*;

import static com.kursach.keynumadv.world.LocalRender.CAM_HEIGHT;
import static com.kursach.keynumadv.world.LocalRender.CAM_WIDTH;


public class GameScreen extends BaseScreen {
    private Level level;
    private Player player;
    private CameraController cameraController;
    private OrthographicCamera camera;
    private BitmapFont playerFont;
    private SpriteBatch batch = new SpriteBatch();
    private SpriteBatch uiBatch = new SpriteBatch();
    private IsometricTiledMapRenderer renderer;
    private IsometricWorldRenderer entityrenderer;
    private BattleSystem battleSystem;

    public GameScreen(Game myGame, ScreenManager screMan) {
        super(myGame, screMan);
        init();
        LoadPlayer();
    }

    private void init() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, CAM_WIDTH, CAM_HEIGHT);

        level = new Level("maps/level3.tmx");

        battleSystem = new BattleSystem();

        player = new Player(level.getSpawn(), battleSystem);

        cameraController = new CameraController(
            camera,
            level.getWidth(),
            level.getHeight()
        );

        cameraController.follow(player.getPixelPosition());
        renderer = new IsometricTiledMapRenderer(level.gameMap.map);
        entityrenderer = new IsometricWorldRenderer((int) level.getWidth(), (int) level.getHeight());
    }

    private void LoadPlayer() {
        playerFont = new BitmapFont(Gdx.files.internal("commodore64/raw/commodore-64.fnt"));
        playerFont.setColor(Color.BLACK);
        System.out.println("Font loaded: " + (playerFont != null));
        Vector2 pos = player.getPixelPosition();
        System.out.println("Player render pos: " + pos);
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
        entityrenderer.render(camera, level.gameMap.entities, player);

        handleInput();
        player.update(delta);
        cameraController.follow(player.getPixelPosition());

        uiBatch.begin();
        uiBatch.setProjectionMatrix(camera.combined);

        playerFont.draw(uiBatch, "F(x)h", player.getPixelPosition().x - 32, player.getPixelPosition().y + 8);

        uiBatch.end();
    }

    @Override
    public void resize(int width, int height) {
        camera.setToOrtho(false, width, height);
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
        if (playerFont != null) playerFont.dispose();
        if (uiBatch != null) uiBatch.dispose();
    }


    private void handleInput() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.tryMove(1, 0, level.gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.tryMove(-1, 0, level.gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.tryMove(0, -1, level.gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.tryMove(0, 1, level.gameMap);
        }
    }
}
