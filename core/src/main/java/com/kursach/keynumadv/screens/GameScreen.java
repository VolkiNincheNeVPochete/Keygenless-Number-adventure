package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.kursach.keynumadv.world.CameraController;
import com.kursach.keynumadv.world.Level;
import com.kursach.keynumadv.world.LocalRender;
import com.kursach.keynumadv.world.Player;


public class GameScreen extends BaseScreen {
    private Level level;
    private Player player;
    private CameraController cameraController;
    private OrthographicCamera camera;
    private BitmapFont playerFont;
    private SpriteBatch batch = new SpriteBatch();
    private SpriteBatch uiBatch = new SpriteBatch();

    public GameScreen(Game myGame, ScreenManager screMan) {
        super(myGame, screMan);
        init();
        LoadPlayer();
    }

    private void init() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        level = new Level("maps/level1.tmx");

        player = new Player(level.getSpawn());

        cameraController = new CameraController(
            camera,
            level.getWidth(),
            level.getHeight()
        );

        cameraController.follow(player.getPixelPosition());
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

        handleInput();
        player.update(delta);
        cameraController.follow(player.getPixelPosition());

        level.render(camera);

        uiBatch.setProjectionMatrix(camera.combined);
        uiBatch.begin();
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
        if (Gdx.input.isKeyJustPressed(Input.Keys.LEFT)) {
            player.tryMove(0, 1, level.gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.RIGHT)) {
            player.tryMove(0, -1, level.gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            player.tryMove(1, 0, level.gameMap);
        } else if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            player.tryMove(-1, 0, level.gameMap);
        }
    }
}
