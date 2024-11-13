package de.poohscord.poohlobby.lobby.geometry;

import org.bukkit.util.Vector;

public class Rectangle {

    private final Vector pos1, pos2;

    public Rectangle(Vector pos1, Vector pos2) {
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public boolean isInside(Vector vector) {
        return vector.getX() >= Math.min(pos1.getX(), pos2.getX()) && vector.getX() <= Math.max(pos1.getX(), pos2.getX()) &&
                vector.getY() >= Math.min(pos1.getY(), pos2.getY()) && vector.getY() <= Math.max(pos1.getY(), pos2.getY()) &&
                vector.getZ() >= Math.min(pos1.getZ(), pos2.getZ()) && vector.getZ() <= Math.max(pos1.getZ(), pos2.getZ());
    }

    public Vector getPos1() {
        return pos1;
    }

    public Vector getPos2() {
        return pos2;
    }

}
