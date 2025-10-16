package cc.vergency.utils.render.skia;

import cc.vergency.utils.interfaces.Wrapper;
import com.mojang.blaze3d.platform.GlConst;
import com.mojang.blaze3d.systems.RenderSystem;
import io.github.humbleui.skija.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.BufferRenderer;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL13;
import org.lwjgl.opengl.GL33;

import java.util.function.Consumer;

/*
 * Source Code From "Soar Client v8"
 */

public class SkiaContext implements Wrapper {
	private static DirectContext context = null;
	private static Surface surface;
	private static BackendRenderTarget renderTarget;

	public static Canvas getCanvas() {
		return surface.getCanvas();
	}

	public static void createSurface(int width, int height) {
		if (context == null) {
			context = DirectContext.makeGL();
		}

		if (surface != null) {
			surface.close();
			surface = null;
		}

		if (renderTarget != null) {
			renderTarget.close();
			renderTarget = null;
		}

		renderTarget = BackendRenderTarget.makeGL(width, height, 0, 8, MinecraftClient.getInstance().getFramebuffer().fbo, GL11.GL_RGBA8);
		surface = Surface.wrapBackendRenderTarget(context, renderTarget, SurfaceOrigin.BOTTOM_LEFT, SurfaceColorFormat.RGBA_8888, ColorSpace.getSRGB());
	}

	public static void draw(Consumer<Canvas> drawingLogic) {
		RenderSystem.pixelStore(GlConst.GL_UNPACK_ROW_LENGTH, 0);
		RenderSystem.pixelStore(GlConst.GL_UNPACK_SKIP_PIXELS, 0);
		RenderSystem.pixelStore(GlConst.GL_UNPACK_SKIP_ROWS, 0);
		RenderSystem.pixelStore(GlConst.GL_UNPACK_ALIGNMENT, 4);
		RenderSystem.clearColor(0f, 0f, 0f, 0f);
		context.resetGLAll();

		Canvas canvas = getCanvas();
		drawingLogic.accept(canvas);

		context.flush();

		BufferRenderer.reset();
		GL33.glBindSampler(0, 0);
		RenderSystem.disableBlend();
		GL11.glDisable(GL11.GL_BLEND);
		RenderSystem.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
		RenderSystem.blendEquation(GL33.GL_FUNC_ADD);
		GL33.glBlendEquation(GL33.GL_FUNC_ADD);
		RenderSystem.colorMask(true, true, true, true);
		GL11.glColorMask(true, true, true, true);
		RenderSystem.depthMask(true);
		GL11.glDepthMask(true);
		RenderSystem.disableScissor();
		GL11.glDisable(GL11.GL_SCISSOR_TEST);
		GL11.glDisable(GL11.GL_STENCIL_TEST);
		RenderSystem.disableDepthTest();
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL13.glActiveTexture(GL13.GL_TEXTURE0);
		RenderSystem.activeTexture(GL13.GL_TEXTURE0);
		RenderSystem.disableCull();
	}

	public static boolean isAvailable() {
		return context != null;
	}

	public static DirectContext getContext() {
		return context;
	}
}
