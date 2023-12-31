package ltd.dreamcraft.dreamactivity;

import ltd.dreamcraft.dreamactivity.event.PlayerJoinQuitEvent;
import ltd.dreamcraft.dreamactivity.event.handleCustomPacketEvent;
import ltd.dreamcraft.dreamactivity.manager.Commands;
import ltd.dreamcraft.dreamactivity.manager.ConfigManager;
import ltd.dreamcraft.dreamactivity.manager.DataManager;
import ltd.dreamcraft.dreamactivity.tools.CheckPluginUpdate;
import ltd.dreamcraft.dreamactivity.tools.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import priv.seventeen.artist.dreampainter.api.DreamPainterScreenAPI;
import priv.seventeen.artist.dreampainter.api.screen.DreamPainterScreen;

import java.util.HashMap;

import static org.bukkit.Bukkit.getConsoleSender;

public final class DreamActivity extends JavaPlugin {
    public static DreamActivity in() {
        return getPlugin(DreamActivity.class);
    }

    public static DreamPainterScreen DreamActivity;
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new PlayerJoinQuitEvent(), this);
        getServer().getPluginManager().registerEvents(new handleCustomPacketEvent(), this);
        this.getCommand("DreamActivity").setExecutor(new Commands());
        getConsoleSender().sendMessage("§3 ____                              _        _   _       _ _ ");
        getConsoleSender().sendMessage("§3|  _ \\ _ __ ___  __ _ _ __ ___    / \\   ___| |_(_)_   _(_) |_ _   _");
        getConsoleSender().sendMessage("§3| | | | '__/ _ \\/ _` | '_ ` _ \\  / _ \\ / __| __| \\ \\ / / | __| | | |");
        getConsoleSender().sendMessage("§3| |_| | | |  __/ (_| | | | | | |/ ___ \\ (__| |_| |\\ V /| | |_| |_| |");
        getConsoleSender().sendMessage("§3|____/|_|  \\___|\\__,_|_| |_| |_/_/   \\_\\___|\\__|_| \\_/ |_|\\__|\\__, |");
        getConsoleSender().sendMessage("§3                                                              |___/ ");
        getConsoleSender().sendMessage("§b|> §e欢迎使用DreamActivity绘梦师附属活跃插件!");
        getConsoleSender().sendMessage("§b|> §e当前版本为：" + getDescription().getVersion());
        //检查插件是否需要更新 如果配置文件是true那么就启用更新检查，反之则关闭
        if (ConfigManager.isCheckUpdate()) {
            CheckPluginUpdate.checkPluginUpdate("https://pluginapi.dreamcraft.ltd/api/plugins/");
        }
        //初始化 配置文件
        DataManager dataManager = new DataManager();
        dataManager.init();
        //在插件中注册screen
        DreamActivity = new DreamPainterScreen(this, "screen/DreamActivity");
        DreamPainterScreenAPI.registerScreen(DreamActivity);
        DreamActivity.reload();
        getConsoleSender().sendMessage("§b|> §e成功载入1个screen");
        //统计
        Metrics metrics = new Metrics(this, 19943);
    }

    @Override
    public void onDisable() {
        // 获取在线玩家并保存数据
        Bukkit.getOnlinePlayers().forEach(PlayerJoinQuitEvent::save);
        DataManager.joinTimeMap = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            DataManager.joinTimeMap.put(player.getUniqueId(), Long.valueOf(System.currentTimeMillis()));
            PlayerJoinQuitEvent.updatePanelInfo(player);
        }
        DreamPainterScreenAPI.unRegisterScreen(DreamActivity);
        getLogger().info("插件被卸载,screen页面已被删除");
    }
}
