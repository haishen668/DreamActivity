package ltd.dreamcraft.dreamactivity.event;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import priv.seventeen.artist.dreampainter.event.ClientCustomPacketEvent;


public class handleCustomPacketEvent implements Listener {
    @EventHandler
    public void CustomPacketEvent(ClientCustomPacketEvent event) {
//        说明: 将一个列表发送到服务端 第一个参数是识别ID 第二个是列表对象
//                - English -> Packet.send('MyPacket',$list)
//                - 中文 -> 通讯.发送('MyPacket',$list)
//                - 返回(Return) -> void
        if (!event.getId().equals("DreamActivity")) {
            return;
        }
        Player player = event.getPlayer();
        String action = event.getData(0).getString();
        if ("rewards".equalsIgnoreCase(action)) {
            String rewards = event.getData(1).getString();
            //执行命令 DreamActivity + time1
            player.performCommand("DreamActivity " + rewards);
        }
    }
}
