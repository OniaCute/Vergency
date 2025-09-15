package cc.vergency.utils.interfaces;

import net.minecraft.client.MinecraftClient;

import java.util.Random;

public interface Wrapper {
    MinecraftClient mc = MinecraftClient.getInstance();
    Random RANDOM = new Random();

    default double randomDouble(double maxNum, double minNum) {
        if (minNum > maxNum) {
            double temp = minNum;
            minNum = maxNum;
            maxNum = temp;
        }
        return minNum + RANDOM.nextDouble() * (maxNum - minNum);
    }

    default int randomInt(int maxNum, int minNum) {
        if (minNum > maxNum) {
            int temp = minNum;
            minNum = maxNum;
            maxNum = temp;
        }
        return minNum + RANDOM.nextInt() * (maxNum - minNum);
    }
}
