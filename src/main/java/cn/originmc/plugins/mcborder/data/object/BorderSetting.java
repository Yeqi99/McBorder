package cn.originmc.plugins.mcborder.data.object;

import cn.originmc.plugins.mcborder.util.data.YamlManager;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldBorder;

import java.util.concurrent.TimeUnit;

public class BorderSetting {
    private double x;
    private double z;
    private double size;
    private String world;
    private double warningDistance;
    private double warningTime;
    private double bufferDistance;
    private double damageAmount;
    private long changeTime;

    public BorderSetting(YamlManager yamlManager, String id) {
        this.x = (double) yamlManager.get(id, "x");
        this.z = (double) yamlManager.get(id, "z");
        this.size = (double) yamlManager.get(id, "size");
        this.world = id;
        this.warningDistance = (double) yamlManager.get(id, "warningDistance");
        this.warningTime = (double) yamlManager.get(id, "warningTime");
        this.bufferDistance = (double) yamlManager.get(id, "bufferDistance");
        this.damageAmount = (double) yamlManager.get(id, "damageAmount");
        this.changeTime = (long) yamlManager.get(id, "changeTime");
    }

    public boolean upWorld() {
        World w = Bukkit.getWorld(world);
        if (w == null) {
            return false;
        }
        WorldBorder worldBorder = w.getWorldBorder();
        worldBorder.setSize(size, changeTime);
        worldBorder.setCenter(x, z);
        worldBorder.setDamageAmount(damageAmount);
        worldBorder.setDamageBuffer(bufferDistance);
        worldBorder.setWarningDistance((int) warningDistance);
        worldBorder.setWarningTime((int) warningTime);
        return true;
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

    public double getSize() {
        return size;
    }

    public void setSize(double size) {
        this.size = size;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public double getWarningDistance() {
        return warningDistance;
    }

    public void setWarningDistance(double warningDistance) {
        this.warningDistance = warningDistance;
    }

    public double getWarningTime() {
        return warningTime;
    }

    public void setWarningTime(double warningTime) {
        this.warningTime = warningTime;
    }

    public double getBufferDistance() {
        return bufferDistance;
    }

    public void setBufferDistance(double bufferDistance) {
        this.bufferDistance = bufferDistance;
    }

    public double getDamageAmount() {
        return damageAmount;
    }

    public void setDamageAmount(double damageAmount) {
        this.damageAmount = damageAmount;
    }

    public double getChangeTime() {
        return changeTime;
    }

}
