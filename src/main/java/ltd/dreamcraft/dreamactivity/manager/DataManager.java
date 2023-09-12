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
 * ���ݹ���������
 */
public class DataManager {
    /**
     * ��ʼ�������ļ�
     */

    private YamlConfiguration yaml;
    public static Map<UUID, Long> joinTimeMap;
    public static List<OnlineReward> onlineReward;
    public static List<AccumulateReward> accumulateReward;

    public DataManager() {
        joinTimeMap = new HashMap<>();
    }

    public void init() {
        //��ʼ��config.yml�����ļ�
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
        //���config.yml�����ھʹ���Դ��copyһ��Ĭ�ϵ������ļ�
        //���ҷ�����Ϣ���߿���̨ ������Ĭ�ϵ������ļ�config.yml!��
        if (!config.exists()) {
            DreamActivity.in().saveDefaultConfig();
            getConsoleSender().sendMessage("��b|> ��e����Ĭ�ϵ������ļ�config.yml!");
        }
        // ��ʼ�� joinTimeMap
        new DataManager(); // ���ù��캯������ʼ��
        loadRewardData(config);

    }

    /**
     * ��ȡ�����ļ��е�����ʱ�佱�� �� �ۻ���������
     *
     * @param config �����ļ�
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
