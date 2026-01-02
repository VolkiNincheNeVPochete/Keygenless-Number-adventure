package com.kursach.keynumadv.world.Entities;

import com.kursach.keynumadv.Interfaces.StepReactive;
import com.kursach.keynumadv.world.Player;

public abstract class Entity implements StepReactive {
    protected int x,y;

    public void OnStep(Player stepper)
    {

    }
}
