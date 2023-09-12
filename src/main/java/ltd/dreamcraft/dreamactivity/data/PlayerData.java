package ltd.dreamcraft.dreamactivity.data;

import ltd.dreamcraft.dreamactivity.DreamActivity;
import ltd.dreamcraft.dreamactivity.event.PlayerJoinQuitEvent;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.List;

public class PlayerData {
    private int totalCount;

    private long refreshDate;

    private List<String> rewardList;

    private int todayOnlineSecond;

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public void setRefreshDate(long refreshDate) {
        this.refreshDate = refreshDate;
    }

    public void setRewardList(List<String> rewardList) {
        this.rewardList = rewardList;
    }

    public void setTodayOnlineSecond(int todayOnlineSecond) {
        this.todayOnlineSecond = todayOnlineSecond;
    }

    public int getTotalCount() {
        return this.totalCount;
    }

    public long getRefreshDate() {
        return this.refreshDate;
    }

    public List<String> getRewardList() {
        return this.rewardList;
    }

    public int getTodayOnlineSecond() {
        return this.todayOnlineSecond;
    }


    public PlayerData(YamlConfiguration yaml) {
        this.todayOnlineSecond = yaml.getInt("onlineTime");
        this.totalCount = yaml.getInt("count");
        this.refreshDate = yaml.getLong("date");
        this.rewardList = yaml.getStringList("reward");
    }

    public boolean update() {
        if (PlayerJoinQuitEvent.isSameData(this.refreshDate, System.currentTimeMillis())) {
            this.refreshDate = System.currentTimeMillis();
            this.todayOnlineSecond = 0;
            this.rewardList.removeIf(l -> l.startsWith("time"));
            return true;
        }
        return false;
    }

    public YamlConfiguration save() {
        YamlConfiguration yaml = new YamlConfiguration();
        yaml.set("onlineTime", Integer.valueOf(this.todayOnlineSecond));
        yaml.set("count", Integer.valueOf(this.totalCount));
        yaml.set("date", Long.valueOf(this.refreshDate));
        yaml.set("reward", this.rewardList);
        return yaml;
    }
}