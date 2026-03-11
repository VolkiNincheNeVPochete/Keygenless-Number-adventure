package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.Vector2;

public class CameraController {
    private OrthographicCamera camera;
    private float levelWidth, levelHeight;
    private float viewportWidth, viewportHeight;

    public CameraController(OrthographicCamera camera, float levelWidth, float levelHeight) {
        this.camera = camera;
        this.levelWidth = levelWidth;
        this.levelHeight = levelHeight;
        this.viewportWidth = camera.viewportWidth;
        this.viewportHeight = camera.viewportHeight;
    }

    public void follow(Vector2 target) {
        camera.position.x = target.x;
        camera.position.y = target.y;
        camera.update();
    }
}
