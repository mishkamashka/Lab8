package ru.ifmo.se.clientSide.enums;

import java.awt.*;

public enum State {
    BORED(new Color(0x6a5acd)),
    INTERESTED(new Color(0xffa500)),
    NEUTRAL(new Color(0x40e0d0)),
    ANGRY(new Color(0xff0000));

    private Color color;

    State(Color color){
        this.color = color;
    }

    public Color getColor(){
        return this.color;
    }
}