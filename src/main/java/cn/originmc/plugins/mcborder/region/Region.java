package cn.originmc.plugins.mcborder.region;

import cn.originmc.plugins.mcborder.data.LangData;
import cn.originmc.plugins.mcborder.data.manager.LangDataManager;
import cn.originmc.plugins.mcborder.util.data.YamlManager;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Region {
    private String id;
    private String display;
    private String world;
    private int weight = 1;
    private final List<Node> nodes = new ArrayList<>(); // 用于存储区域节点的列表
    private final Map<String, String> flags = new LinkedHashMap<>();

    // 区域类的构造函数，初始化节点列表
    public Region() {
    }

    public Region(YamlManager yamlManager, String id) {
        if (id.equalsIgnoreCase("default")) {
            this.id = id;
            this.display = (String) yamlManager.get(id, "display");
            this.world = null;
            this.weight=-1;
            for (String s : yamlManager.getKeyList(id, "flags", true)) {
                flags.put(s, yamlManager.get(id, s) + "");
            }
            return;
        }
        this.id = id;
        this.display = (String) yamlManager.get(id, "display");
        this.world = (String) yamlManager.get(id, "world");
        this.weight= (int) yamlManager.get(id,"weight");
        for (String s : yamlManager.getKeyList(id, "nodes", false)) {
            String xKey = "nodes." + s + ".x";
            String zKey = "nodes." + s + ".z";
            double x = (double) yamlManager.get(id, xKey);
            double z = (double) yamlManager.get(id, zKey);
            Node node = new Node(x, z);
            this.nodes.add(node);
        }
        for (String s : yamlManager.getKeyList(id, "flags", true)) {
            flags.put(s, yamlManager.get(id, s) + "");
        }

    }

    public boolean hasFlag(String flagName) {
        return this.flags.containsKey(flagName);
    }

    public String getFlagValue(String flagName) {
        return this.flags.get(flagName);
    }

    // 添加节点到区域的方法
    public void addNode(Node node) {
        nodes.add(node);
    }
    public String denyMessage(String flagName){
        if (hasFlag(flagName)){
            return getFlagValue("deny_message").replace("~",this.display).replace("@",flagName);
        }else {
            return LangDataManager.getText(flagName,"Region ~ deny")
                    .replace("~",this.display);
        }
    }
    public boolean allowMove(Player player) {
        if (hasFlag("check_perm_move")) {
            return player.hasPermission(getFlagValue("check_perm_move"));
        } else {
            return true;
        }
    }
    public boolean allowJoin(Player player) {
        if (hasFlag("check_perm_join")) {
            return player.hasPermission(getFlagValue("check_perm_join"));
        } else {
            return true;
        }
    }

    // 判断给定位置是否在区域内的方法
    public boolean isInsideRegion(Location location) {
        if (this.id.equalsIgnoreCase("default")) {
            return true;
        }
        double x = location.getX();
        double z = location.getZ();

        int count = 0;
        int numNodes = nodes.size();

        // 遍历区域节点列表
        for (int i = 0; i < numNodes; i++) {

            double x1 = nodes.get(i).getX();
            double z1 = nodes.get(i).getZ();
            double x2 = nodes.get((i + 1) % numNodes).getX();
            double z2 = nodes.get((i + 1) % numNodes).getZ();

            // 使用射线法检查位置是否在当前节点和下一个节点之间
            if (((z1 <= z && z < z2) || (z2 <= z && z < z1)) &&
                    (x < (x2 - x1) * (z - z1) / (z2 - z1) + x1)) {
                count++;
            }
        }

        // 如果射线与区域边界相交的次数为奇数，则位置在区域内
        return count % 2 != 0;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDisplay() {
        return display;
    }

    public void setDisplay(String display) {
        this.display = display;
    }

    public Map<String, String> getFlags() {
        return flags;
    }

    public String getWorld() {
        return world;
    }

    public void setWorld(String world) {
        this.world = world;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }
}
