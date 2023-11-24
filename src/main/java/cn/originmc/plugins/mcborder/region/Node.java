package cn.originmc.plugins.mcborder.region;

import org.bukkit.Location;

public class Node {
    private double x;
    private double z;
    public Node(double x,double z){
        this.x=x;
        this.z=z;
    }
    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
    }

    public double getZ() {
        return z;
    }

    public void setZ(double z) {
        this.z = z;
    }
}
