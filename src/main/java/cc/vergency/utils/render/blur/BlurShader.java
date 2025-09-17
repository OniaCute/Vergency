package cc.vergency.utils.render.blur;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Identifier;
import org.apache.commons.io.IOUtils;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;

import java.io.IOException;
import java.nio.FloatBuffer;
import java.nio.charset.StandardCharsets;

import static org.lwjgl.opengl.GL11C.GL_FALSE;
import static org.lwjgl.opengl.GL11C.GL_TRUE;
import static org.lwjgl.opengl.GL20C.GL_FRAGMENT_SHADER;
import static org.lwjgl.opengl.GL20C.GL_VERTEX_SHADER;

public class BlurShader {
    private static final FloatBuffer MAT = BufferUtils.createFloatBuffer(4 * 4);

    public static BlurShader BOUND;

    private final int id;
    private final Object2IntMap<String> uniformLocations = new Object2IntOpenHashMap<>();

    public BlurShader(String vertPath, String fragPath) {
        int vert = GlStateManager.glCreateShader(GL_VERTEX_SHADER);
        GlStateManager.glShaderSource(vert, read(vertPath));

        String vertError = ShaderHelper.compileShader(vert);
        if (vertError != null) {
            throw new RuntimeException("Failed to compile vertex shader: " + vertError);
        }

        int frag = GlStateManager.glCreateShader(GL_FRAGMENT_SHADER);
        GlStateManager.glShaderSource(frag, read(fragPath));

        String fragError = ShaderHelper.compileShader(frag);
        if (fragError != null) {
            throw new RuntimeException("Failed to compile fragment shader: " + fragError);
        }

        id = GlStateManager.glCreateProgram();

        String programError = ShaderHelper.linkProgram(id, vert, frag);
        if (programError != null) {
            throw new RuntimeException("Failed to link program: " + programError);
        }

        GlStateManager.glDeleteShader(vert);
        GlStateManager.glDeleteShader(frag);
    }

    private String read(String path) {
        try {
            return IOUtils.toString(
                    MinecraftClient.getInstance().getResourceManager().getResource(Identifier.of("vergence", "shaders/core/" + path)).get().getInputStream(),
                    StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new IllegalStateException("Could not read shader '" + path + "'", e);
        }
    }

    public void bind() {
        ShaderHelper.useProgram(id);
        BOUND = this;
    }

    private int getLocation(String name) {
        if (uniformLocations.containsKey(name))
            return uniformLocations.getInt(name);

        int location = ShaderHelper.getUniformLocation(id, name);
        uniformLocations.put(name, location);
        return location;
    }

    public void set(String name, boolean v) {
        ShaderHelper.uniformInt(getLocation(name), v ? GL_TRUE : GL_FALSE);
    }

    public void set(String name, int v) {
        ShaderHelper.uniformInt(getLocation(name), v);
    }

    public void set(String name, double v) {
        ShaderHelper.uniformFloat(getLocation(name), (float) v);
    }

    public void set(String name, double v1, double v2) {
        ShaderHelper.uniformFloat2(getLocation(name), (float) v1, (float) v2);
    }

    public void set(String name, Matrix4f mat) {
        mat.get(MAT);
        GlStateManager._glUniformMatrix4(getLocation(name), false, MAT);
    }

    public void setDefaults() {
        set("u_Proj", RenderSystem.getProjectionMatrix());
        set("u_ModelView", RenderSystem.getModelViewStack());
    }
}
