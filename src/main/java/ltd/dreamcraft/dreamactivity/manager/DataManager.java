package ltd.dreamcraft.dreamactivity.manager;

import ltd.dreamcraft.dreamactivity.DreamActivity;
import ltd.dreamcraft.dreamactivity.data.AccumulateReward;
import ltd.dreamcraft.dreamactivity.data.OnlineReward;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.*;

import static org.bukkit.Bukkit.getConsoleSender;

/**
 * 数据管理器对象
 */
public class DataManager {
    /**
     * 初始化配置文件
     */

    private YamlConfiguration yaml;
    public static Map<UUID, Long> joinTimeMap;
    public static List<OnlineReward> onlineReward;
    public static List<AccumulateReward> accumulateReward;

    public DataManager() {
        joinTimeMap = new HashMap<>();
    }

    public void init() {
        //初始化config.yml配置文件
        initConfig();
    }

    public List<OnlineReward> getOnlineReward() {
        return onlineReward;
    }

    public List<AccumulateReward> getAccumulateReward() {
        return accumulateReward;
    }

    public YamlConfiguration getYaml() {
        return yaml;
    }

    public Map<UUID, Long> getJoinTimeMap() {
        return joinTimeMap;
    }

    private void initConfig() {
        File config = new File(DreamActivity.in().getDataFolder(), "config.yml");
        //如果config.yml不存在就从资源包copy一份默认的配置文件
        //并且发送信息告诉控制台 “生成默认的配置文件config.yml!”
        if (!config.exists()) {
            DreamActivity.in().saveDefaultConfig();
            getConsoleSender().sendMessage("§b|> §e生成默认的配置文件config.yml!");
        }
        // 初始化 joinTimeMap
        new DataManager(); // 调用构造函数来初始化
        loadRewardData(config);

    }

    /**
     * 读取配置文件中的在线时间奖励 和 累积次数奖励
     *
     * @param config 配置文件
     */
    private void loadRewardData(File config) {
        yaml = YamlConfiguration.loadConfiguration(config);
        onlineReward = new ArrayList<>();
        accumulateReward = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            ConfigurationSection section = this.yaml.getConfigurationSection("reward.time" + i);
            if (section == null)
                break;
            this.onlineReward.add(new OnlineReward(section));
        }
        for (int i = 1; i <= 10; i++) {
            ConfigurationSection section = this.yaml.getConfigurationSection("reward.count" + i);
            if (section == null)
                break;
            this.accumulateReward.add(new AccumulateReward(section));
        }
    }

    public static YamlConfiguration getData(String player) {
        return YamlConfiguration.loadConfiguration(new File(DreamActivity.in().getDataFolder(), "PlayerData" + File.separator + player + ".yml"));
    }

    public static void setData(String player, YamlConfiguration config) {
        try {
            File file = new File(DreamActivity.in().getDataFolder(), "PlayerData" + File.separator + player + ".yml");
            file.getParentFile().mkdirs();
            config.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
