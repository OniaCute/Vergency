package cc.vergency.features.event.events.game;

import cc.vergency.features.event.Event;
import net.minecraft.client.network.ServerAddress;
import net.minecraft.client.network.ServerInfo;

public class ServerEvent extends Event {
    private final ServerInfo info;
    private final ServerAddress address;
    private final String reason;

    public ServerEvent(ServerInfo info, ServerAddress address, String reason) {
        this.info = info;
        this.address = address;
        this.reason = reason;
    }

    public ServerInfo getInfo() {
        return info;
    }

    public ServerAddress getAddress() {
        return address;
    }

    public String getReason() {
        return reason;
    }

    public static class Connect extends ServerEvent {
        public Connect(ServerInfo info, ServerAddress address) {
            super(info, address, "NoReason");
        }
    }

    public static class Disconnect extends ServerEvent {
        public Disconnect(String reason) {
            super(null, null, reason);
        }
    }
}
