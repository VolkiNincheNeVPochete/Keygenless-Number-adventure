package com.kursach.keynumadv.Interfaces;

public interface StepReactive {
    boolean isStepable();
    void onStep();
    void onFinish();
    boolean isFinished();
}
