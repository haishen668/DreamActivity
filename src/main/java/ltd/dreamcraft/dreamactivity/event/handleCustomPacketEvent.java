package ltd.dreamcraft.dreamactivity.event;


import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import priv.seventeen.artist.dreampainter.event.ClientCustomPacketEvent;


public class handleCustomPacketEvent implements Listener {
    @EventHandler
    public void CustomPacketEvent(ClientCustomPacketEvent event) {
//        ˵��: ��һ���б��͵������ ��һ��������ʶ��ID �ڶ������б����
//                - English -> Packet.send('MyPacket',$list)
//                - ���� -> ͨѶ.����('MyPacket',$list)
//                - ����(Return) -> void
        if (!event.getId().equals("DreamActivity")) {
            return;
        }
        Player player = event.getPlayer();
        String action = event.getData(0).getString();
        if ("rewards".equalsIgnoreCase(action)) {
            String rewards = event.getData(1).getString();
            //ִ������ DreamActivity + time1
            player.performCommand("DreamActivity " + rewards);
        }
    }
}
