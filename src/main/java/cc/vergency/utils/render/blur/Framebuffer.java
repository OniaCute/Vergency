package cc.vergency.utils.render.blur;


import com.mojang.blaze3d.platform.GlStateManager;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import org.lwjgl.opengl.GL30C;

import static org.lwjgl.opengl.GL11C.*;
import static org.lwjgl.opengl.GL12C.GL_CLAMP_TO_EDGE;
import static org.lwjgl.opengl.GL30C.GL_COLOR_ATTACHMENT0;
import static org.lwjgl.opengl.GL30C.GL_FRAMEBUFFER;

public class Framebuffer {
    private int id;
    public int texture;
    public double sizeMulti = 1;
    public int width, height;

    public Framebuffer(double sizeMulti) {
        this.sizeMulti = sizeMulti;
        init();
    }

    public Framebuffer() {
        init();
    }

    private void init() {

        Window window = MinecraftClient.getInstance().getWindow();

        id = GlStateManager.glGenFramebuffers();
        bind();

        texture = GlStateManager._genTexture();
        ShaderHelper.bindTexture(texture);
        ShaderHelper.defaultPixelStore();

        ShaderHelper.textureParam(GL_TEXTURE_2D, GL_TEXTURE_WRAP_S, GL_CLAMP_TO_EDGE);
        ShaderHelper.textureParam(GL_TEXTURE_2D, GL_TEXTURE_WRAP_T, GL_CLAMP_TO_EDGE);
        ShaderHelper.textureParam(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_LINEAR);
        ShaderHelper.textureParam(GL_TEXTURE_2D, GL_TEXTURE_MAG_FILTER, GL_LINEAR);

        width = (int) (window.getFramebufferWidth() * sizeMulti);
        height = (int) (window.getFramebufferHeight() * sizeMulti);

        ShaderHelper.textureImage2D(GL_TEXTURE_2D, 0, GL_RGB, width, height, 0, GL_RGB, GL_UNSIGNED_BYTE, null);
        ShaderHelper.framebufferTexture2D(GL_FRAMEBUFFER, GL_COLOR_ATTACHMENT0, GL_TEXTURE_2D, texture, 0);

        unbind();
    }

    public void bind() {
        GlStateManager._glBindFramebuffer(GL30C.GL_FRAMEBUFFER, id);
    }

    public void setViewport() {
        ShaderHelper.viewport(0, 0, width, height);
    }

    public void unbind() {
        MinecraftClient.getInstance().getFramebuffer().beginWrite(false);
    }

    public void resize() {
        GlStateManager._glDeleteFramebuffers(id);
        GlStateManager._deleteTexture(texture);

        init();
    }
}
