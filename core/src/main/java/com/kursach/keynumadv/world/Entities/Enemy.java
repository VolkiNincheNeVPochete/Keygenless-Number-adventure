package com.kursach.keynumadv.world.Entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.math.GridPoint2;
import com.badlogic.gdx.math.Vector2;

import java.util.Timer;
import java.util.TimerTask;

public class Enemy extends Entity {
    private float VALUE;
    private float count;
    static Texture texture = new Texture(Gdx.files.internal("sprites/enemies/Centipede/Centipede_enemy.png"));
    public Enemy(GridPoint2 tilePos, float thisValue) {
        super(tilePos);
        this.VALUE = thisValue;
    }
    public Enemy() {
        super();
    }
    @Override
    protected void init() {
        this.isFinished = false;
        timer = new Timer();
        count = 255f;
        this.timerTask = new TimerTask() {
            @Override
            public void run() {
                count--;
                if (count <= 0) {
                    isFinished = true;
                    timer.cancel();
                }
            }
        };

        shaderProgram = new ShaderProgram(
            Gdx.files.internal("shaders/vertex.glsl"),
            Gdx.files.internal("shaders/fragment.glsl")
        );
    }
    @Override
    public boolean isStepable() {
        return true;
    }
    @Override
    public void onStep() {
        timer.schedule(timerTask, 0, 1);
    }
    @Override
    public void onFinish() {
        System.out.println("афавыф");
        return;
    }
    @Override
    public boolean isFinished() {
        return isFinished;
    }
    private void StartFight() {}
    private float GetReward() {
        return VALUE;
    }
    public void render(Batch batch, GridPoint2 tilePos) {
    }
    public void render(Batch batch) {
        batch.setShader(shaderProgram);

        Vector2 visualPos = getVisualPixelPosition();
        batch.draw(this.texture, visualPos.x, visualPos.y);

        batch.setShader(null);
    }
}
