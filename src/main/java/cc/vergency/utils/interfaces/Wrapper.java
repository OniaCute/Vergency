package cc.vergency.utils.interfaces;

import net.minecraft.client.MinecraftClient;

import java.util.Random;

public interface Wrapper {
    MinecraftClient mc = MinecraftClient.getInstance();
    Random RANDOM = new Random();
}
