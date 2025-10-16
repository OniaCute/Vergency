package cc.vergency.utils.interfaces;

import net.minecraft.network.packet.Packet;

public interface INetworkHandler {
    void sendSilentPacket(final Packet<?> packet);
}
