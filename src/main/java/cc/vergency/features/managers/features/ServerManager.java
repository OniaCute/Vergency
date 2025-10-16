package cc.vergency.features.managers.features;

import cc.vergency.features.event.events.game.ServerEvent;
import cc.vergency.utils.interfaces.Wrapper;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.ServerInfo;

public class ServerManager implements Wrapper {
    public ServerInfo serverInfo;

    public void onServerEvent(ServerEvent.Connect event) {
        this.serverInfo = event.getInfo();
    }

    public int getPing() {
        if (mc.player == null || mc.world == null) {
            return -1;
        }
        if (mc.getNetworkHandler() != null) {
            PlayerListEntry playerEntry = mc.getNetworkHandler().getPlayerListEntry(mc.player.getGameProfile().getId());
            if (playerEntry != null) {
                return playerEntry.getLatency();
            }
        }
        return -1;
    }
}
