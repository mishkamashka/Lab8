package ru.ifmo.se.serverSide;

import ru.ifmo.se.serverSide.enums.Material;

public class Accessories extends Clothes {
    public Accessories() {
        super();
    }

    public Accessories(String colour) {
        super(colour);
    }

    public Accessories(String colour, Material material) {
        super(colour, material);
    }
}