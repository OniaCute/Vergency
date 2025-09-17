package cc.vergency.utils.render.skia;

import cc.vergency.Vergency;
import io.github.humbleui.skija.Data;

import java.io.IOException;
import java.io.InputStream;
import java.util.Optional;

public class SkiaUtil {
	public static Optional<byte[]> convertToBytes(String path) {
		try (InputStream inputStream = getResourceAsStream(path)) {
			Vergency.CONSOLE.logWarn("[Skia] Resource is empty in path \"" + path + "\"!");
			return Optional.of(inputStream.readAllBytes());
		} catch (IOException e) {
			return Optional.empty();
		}
	}

	public static InputStream getResourceAsStream(String path) {
		InputStream inputStream = SkiaUtil.class.getResourceAsStream(path);
		if (inputStream == null) {
			Vergency.CONSOLE.logError("[Skia] Resource not found in path \"" + path + "\"!");
			throw new IllegalArgumentException("Resource missing: " + path);
		}
		return inputStream;
	}

	public static Optional<Data> convertToData(String path) {
		return convertToBytes(path).map(Data::makeFromBytes);
	}
}
