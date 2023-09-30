package ltd.dreamcraft.dreamactivity.event;

import ltd.dreamcraft.dreamactivity.DreamActivity;
import ltd.dreamcraft.dreamactivity.data.AccumulateReward;
import ltd.dreamcraft.dreamactivity.data.OnlineReward;
import ltd.dreamcraft.dreamactivity.data.PlayerData;
import ltd.dreamcraft.dreamactivity.manager.DataManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

public class PlayerJoinQuitEvent implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        DataManager.joinTimeMap.put(player.getUniqueId(), Long.valueOf(System.currentTimeMillis()));
        System.out.println(DataManager.joinTimeMap);
        Bukkit.getScheduler().runTaskLater(DreamActivity.in(), () -> updatePanelInfo(player), 40L);

    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        save(e.getPlayer());
    }

    public static void save(Player player) {
        if (DataManager.joinTimeMap.containsKey(player.getUniqueId())) {
            System.out.println(player.getName() + "quit server");
            PlayerData playerData = new PlayerData(DataManager.getData(player.getName()));
            System.out.println(playerData.update());
            updateJoinTimeMap(player);
            playerData.setTodayOnlineSecond(playerData.getTodayOnlineSecond() + getPlayerOnlineSecond(player));
            System.out.println(playerData.getTodayOnlineSecond());
            DataManager.setData(player.getName(), playerData.save());
            DataManager.joinTimeMap.remove(player.getUniqueId());
        }
    }

    public static int getPlayerOnlineSecond(Player player) {
        if (DataManager.joinTimeMap.containsKey(player.getUniqueId()))
            return (int) ((System.currentTimeMillis() - DataManager.joinTimeMap.get(player.getUniqueId())) / 1000L);
        return 0;
    }

    public static void updatePanelInfo(Player player) {
        System.out.println(player.getName() + 1111);
        PlayerData playerData = new PlayerData(DataManager.getData(player.getName()));
        System.out.println(playerData);
        playerData.update();
        int totalOnlineSecond = playerData.getTodayOnlineSecond() + getPlayerOnlineSecond(player);
        int totalOnlineTime = totalOnlineSecond / 60;
        int time = 0, time_max = 0, step = 1, count = 0, next_count = 0;
        int i;
        for (i = DataManager.onlineReward.size() - 1; i >= 0; i--) {
            OnlineReward reward = DataManager.onlineReward.get(i);
            if (totalOnlineTime >= reward.getValue()) {
                step = Math.min(i + 2, DataManager.onlineReward.size());
                if (DataManager.onlineReward.size() <= i + 1) {
                    time_max = 1;
                    time = 1;
                    break;
                }
                time_max = ((DataManager.onlineReward.get(i + 1)).getValue() - reward.getValue()) * 60;
                time = totalOnlineSecond - reward.getValue() * 60;
                break;
            }
            if (i == 0) {
                time = totalOnlineSecond;
                time_max = (DataManager.onlineReward.get(0)).getValue() * 60;
            }
        }
        count = playerData.getTotalCount();
        for (i = DataManager.accumulateReward.size() - 1; i >= 0; i--) {
            AccumulateReward reward = DataManager.accumulateReward.get(i);
            if (count >= reward.getValue()) {
                if (DataManager.accumulateReward.size() <= i + 1) {
                    next_count = reward.getValue();
                    break;
                }
                next_count = (DataManager.accumulateReward.get(i + 1)).getValue();
                break;
            }
            if (i == 0)
                next_count = reward.getValue();
        }
//        Map<String, String> placeholder = new LinkedHashMap<>();
//        placeholder.put("activity_time", String.valueOf(time_max - time));
//        placeholder.put("activity_time_max", String.valueOf(time_max));
//        placeholder.put("activity_step", String.valueOf(step));
//        placeholder.put("activity_count", String.valueOf(count));
//        placeholder.put("activity_count_next", String.valueOf(next_count));
        //PacketSender.sendSyncPlaceholder(player, placeholder);
        DreamActivity.DreamActivity.open(player);
        String activity_time = String.valueOf(time_max - time);
        String activity_time_max = String.valueOf(time_max);
        String activity_step = String.valueOf(step);
        String activity_count = String.valueOf(count);
        String activity_count_next = String.valueOf(next_count);
        DreamActivity.DreamActivity.runShimmerCode(player, "init(" + activity_time + "," + activity_time_max + "," + activity_step + "," + activity_count + "," + activity_count_next + ");");
    }

    public static void updateJoinTimeMap(Player player) {
        if (DataManager.joinTimeMap.containsKey(player.getUniqueId())) {
            long aLong = DataManager.joinTimeMap.get(player.getUniqueId()).longValue();
            if (!isSameData(aLong, System.currentTimeMillis()))
                DataManager.joinTimeMap.put(player.getUniqueId(), getTodayZeroPointTimestamps());
        }
    }

    public static boolean isSameData(long time1, long time2) {
        LocalDate date1 = Instant.ofEpochMilli(time1).atZone(ZoneId.of("Asia/Shanghai")).toLocalDate();
        LocalDate date2 = Instant.ofEpochMilli(time2).atZone(ZoneId.of("Asia/Shanghai")).toLocalDate();
        return date1.isEqual(date2);
    }

    public static Long getTodayZeroPointTimestamps() {
        long currentTimestamps = System.currentTimeMillis();
        long oneDayTimestamps = 86400000L;
        return Long.valueOf(currentTimestamps - (currentTimestamps + 28800000L) % oneDayTimestamps);
    }


}
