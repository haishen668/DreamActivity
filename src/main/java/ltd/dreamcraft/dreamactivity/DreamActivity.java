package ltd.dreamcraft.dreamactivity;

import ltd.dreamcraft.dreamactivity.event.PlayerJoinQuitEvent;
import ltd.dreamcraft.dreamactivity.manager.Commands;
import ltd.dreamcraft.dreamactivity.manager.DataManager;
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
        this.getCommand("DreamActivity").setExecutor(new Commands());
        getConsoleSender().sendMessage("��3 ____                              _        _   _       _ _ ");
        getConsoleSender().sendMessage("��3|  _ \\ _ __ ___  __ _ _ __ ___    / \\   ___| |_(_)_   _(_) |_ _   _");
        getConsoleSender().sendMessage("��3| | | | '__/ _ \\/ _` | '_ ` _ \\  / _ \\ / __| __| \\ \\ / / | __| | | |");
        getConsoleSender().sendMessage("��3| |_| | | |  __/ (_| | | | | | |/ ___ \\ (__| |_| |\\ V /| | |_| |_| |");
        getConsoleSender().sendMessage("��3|____/|_|  \\___|\\__,_|_| |_| |_/_/   \\_\\___|\\__|_| \\_/ |_|\\__|\\__, |");
        getConsoleSender().sendMessage("��3                                                              |___/ ");
        getConsoleSender().sendMessage("��b|> ��e��ӭʹ��DreamActivity����ʦ������Ծ���!");
        getConsoleSender().sendMessage("��b|> ��e��ǰ�汾Ϊ��" + getDescription().getVersion());
        //��ʼ�� �����ļ�
        DataManager dataManager = new DataManager();
        dataManager.init();
        //�ڲ����ע��screen
        DreamActivity = new DreamPainterScreen(this, "screen/DreamActivity");
        DreamPainterScreenAPI.registerScreen(DreamActivity);
        DreamActivity.reload();
        getConsoleSender().sendMessage("��b|> ��e�ɹ�����3��screen");
    }

    @Override
    public void onDisable() {
        // ��ȡ������Ҳ���������
        Bukkit.getOnlinePlayers().forEach(PlayerJoinQuitEvent::save);
        DataManager.joinTimeMap = new HashMap<>();
        for (Player player : Bukkit.getOnlinePlayers()) {
            DataManager.joinTimeMap.put(player.getUniqueId(), Long.valueOf(System.currentTimeMillis()));
            PlayerJoinQuitEvent.updatePanelInfo(player);
        }
        DreamPainterScreenAPI.unRegisterScreen(DreamActivity);
        getLogger().info("�����ж��,screenҳ���ѱ�ɾ��");
    }
}
