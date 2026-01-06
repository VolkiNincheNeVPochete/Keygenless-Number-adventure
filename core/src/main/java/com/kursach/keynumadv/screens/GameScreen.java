package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.kursach.keynumadv.world.CameraController;
import com.kursach.keynumadv.world.Entities.Player;
import com.kursach.keynumadv.world.Level;


public class GameScreen extends BaseScreen {
    private OrthographicCamera camera;
    private Level level;
    private Player player;
    private CameraController cameraController;
    private static SpriteBatch batch;
    private final TextButton pauseButton = new TextButton("Pause", skin);

    public GameScreen() {
        super();
        this.batch = new SpriteBatch();
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 1280, 720);
        level = new Level("maps/level1.tmx");
        player = new Player();
        cameraController = new CameraController(camera, level.getWidth(), level.getHeight());
    }
    public GameScreen(Game myGame, ScreenManager screMan) {
        super(myGame, screMan);
    }

    @Override
    public void show() {
        if (stage == null) {
            stage = new Stage();

            table.setFillParent(true);
            table.center().pad(20);

            table.add(pauseButton).size(200, 50).pad(10).row();
            pauseButton.setPosition(1300f, 100f);

            stage.addActor(pauseButton);

            pauseButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    screMan.pushScreen(new PauseScreen());
                }
            });
        }

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        player.update(delta);
        cameraController.follow(player.getPosition());

        Gdx.gl.glClearColor(0.2f, 0.3f, 0.4f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        level.render(camera);

        batch.setProjectionMatrix(camera.combined);
        batch.begin();
        player.render(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();

        level.dispose();
        batch.dispose();
        player.dispose();
    }
}
