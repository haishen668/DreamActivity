package ltd.dreamcraft.dreamactivity.manager;

import ltd.dreamcraft.dreamactivity.DreamActivity;
import ltd.dreamcraft.dreamactivity.data.AccumulateReward;
import ltd.dreamcraft.dreamactivity.data.OnlineReward;
import ltd.dreamcraft.dreamactivity.data.PlayerData;
import ltd.dreamcraft.dreamactivity.event.PlayerJoinQuitEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Commands implements CommandExecutor {
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1)
            return true;
        if (args.length == 1 && "reload".equalsIgnoreCase(args[0]) && sender.isOp()) {
            Bukkit.getOnlinePlayers().forEach(PlayerJoinQuitEvent::save);
            DataManager d = new DataManager();
            d.init();
            for (Player player : Bukkit.getOnlinePlayers()) {
                DataManager.joinTimeMap.put(player.getUniqueId(), Long.valueOf(System.currentTimeMillis()));
                PlayerJoinQuitEvent.updateJoinTimeMap(player);
            }
            sender.sendMessage("重载完成");
            return true;
        }
        OnlineReward reward1 = null;
        AccumulateReward reward2 = null;
        boolean isTimeReward = false;
        if (args[0].startsWith("time")) {
            isTimeReward = true;
            reward1 = DataManager.onlineReward.stream().filter(t -> t.getSectionName().equals(args[0])).findFirst().orElse(null);
            System.out.println(reward1);
        } else if (args[0].startsWith("count")) {
            reward2 = DataManager.accumulateReward.stream().filter(t -> t.getSectionName().equals(args[0])).findFirst().orElse(null);
        } else {
            sender.sendMessage("指令错误");
        }
        if (reward1 == null && reward2 == null)
            return true;
        Player player = (Player) sender;
        PlayerJoinQuitEvent.updateJoinTimeMap(player);
        PlayerData playerData = new PlayerData(DataManager.getData(player.getName()));
        boolean update = playerData.update();
        if (playerData.getRewardList().contains(args[0])) {
            sender.sendMessage(DreamActivity.in().getConfig().getString("reward." + args[0] + ".Already"));
            return true;
        }
        int val = isTimeReward ? ((playerData.getTodayOnlineSecond() + PlayerJoinQuitEvent.getPlayerOnlineSecond(player)) / 60) : playerData.getTotalCount();
        if (reward1 != null && val < reward1.getValue()) {
            sender.sendMessage(reward1.getFailedMessage().replace("%value%", String.valueOf(val)));
            if (update)
                PlayerJoinQuitEvent.updatePanelInfo(player);
            return true;
        } else if (reward2 != null && val < reward2.getValue()) {
            sender.sendMessage(reward2.getFailedMessage().replace("%value%", String.valueOf(val)));
            if (update)
                PlayerJoinQuitEvent.updatePanelInfo(player);
            return true;
        }
        playerData.getRewardList().add(args[0]);
        if (isTimeReward)
            playerData.setTotalCount(playerData.getTotalCount() + 1);
        DataManager.setData(player.getName(), playerData.save());
        if (reward1 != null) {
            for (String s : reward1.getCommands())
                executeCommand(player, s);
            sender.sendMessage(reward1.getSuccessMessage());
        } else if (reward2 != null) {
            for (String s : reward2.getCommands())
                executeCommand(player, s);
            sender.sendMessage(reward2.getSuccessMessage());
        }
        PlayerJoinQuitEvent.updatePanelInfo(player);
        return true;
    }


    public static void executeCommand(Player player, String cmd) {
        cmd = cmd.replace("%p%", player.getName());
        cmd = cmd.replace("%player%", player.getName());
        cmd = cmd.replace("<player>", player.getName());
//        cmd = PlaceholderAPI.setPlaceholders(player, cmd);
        String type = cmd.toLowerCase();
        if (type.startsWith("[op]")) {
            boolean isOp = player.isOp();

            try {
                player.setOp(true);
                player.chat("/" + cmd.substring(4).trim());
            } catch (Throwable var8) {
                DreamActivity.in().getLogger().info("执行OP命令出现了异常: " + cmd);
                var8.printStackTrace();
            } finally {
                player.setOp(isOp);
            }
        } else if (type.startsWith("[console]")) {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), cmd.substring(9).trim());
        } else {
            player.chat("/" + cmd.trim());
        }

    }
}
