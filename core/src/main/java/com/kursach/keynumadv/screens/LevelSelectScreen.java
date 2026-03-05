package com.kursach.keynumadv.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class LevelSelectScreen extends BaseScreen {
    private final List<TextButton> levelButtons = new ArrayList<>();
    private final TextButton backButton = new TextButton("Back", skin);
    private static final String LEVELS_FOLDER = "assets/maps/";
    private static final String LEVEL_PREFIX = "level";
    private static final String LEVEL_EXTENSION = ".tmx";

    public LevelSelectScreen() {
        super();
    }

    public LevelSelectScreen(Game myGame) {
        super(myGame);
    }

    @Override
    public void show() {
        if (stage == null) {
            stage = new Stage();
            table.setFillParent(true);
            table.center().pad(20);

            table.add("Select Level").pad(20).row();

            List<String> levels = findAvailableLevels();

            for (String levelPath : levels) {
                int levelNumber = extractLevelNumber(levelPath);
                TextButton levelButton = new TextButton("Level " + levelNumber, skin);
                levelButton.setSize(200, 50);
                levelButton.pad(10);

                final String finalLevelPath = levelPath;
                levelButton.addListener(new ClickListener() {
                    @Override
                    public void clicked(InputEvent event, float x, float y) {
                        ScreenManager.ShowGame(finalLevelPath);
                    }
                });

                table.add(levelButton).pad(5).row();
                levelButtons.add(levelButton);
            }

            table.add(backButton).size(200, 50).pad(20);
            backButton.addListener(new ClickListener() {
                @Override
                public void clicked(InputEvent event, float x, float y) {
                    ScreenManager.ShowMainMenu();
                }
            });

            stage.addActor(table);
        }
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(1f, 1f, 1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void dispose() {
        if (stage != null) stage.dispose();
    }

    private List<String> findAvailableLevels() {
        List<String> levels = new ArrayList<>();

        try {
            FileHandle folder = Gdx.files.internal(LEVELS_FOLDER);
            if (folder.exists() && folder.isDirectory()) {
                for (FileHandle file : folder.list()) {
                    if (file.name().startsWith(LEVEL_PREFIX) && file.name().endsWith(LEVEL_EXTENSION)) {

                        levels.add(LEVELS_FOLDER + file.name());
                    }
                }
            }
        } catch (Exception e) {
            Gdx.app.error("LevelSelect", "Error finding levels: " + e.getMessage());
        }

        Collections.sort(levels, new Comparator<String>() {
            @Override
            public int compare(String s1, String s2) {
                int n1 = extractLevelNumber(s1);
                int n2 = extractLevelNumber(s2);
                return Integer.compare(n1, n2);
            }
        });

        return levels;
    }

    private int extractLevelNumber(String levelPath) {
        try {
            String fileName = levelPath.substring(
                levelPath.lastIndexOf('/') + 1,
                levelPath.lastIndexOf('.')
            );
            String number = fileName.replaceAll("[^0-9]", "");
            return Integer.parseInt(number);
        } catch (Exception e) {
            return 0;
        }
    }
}
