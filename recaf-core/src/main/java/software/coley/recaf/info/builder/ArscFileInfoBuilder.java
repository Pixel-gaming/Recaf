package software.coley.recaf.info.builder;

import jakarta.annotation.Nonnull;
import software.coley.recaf.info.ArscFileInfo;
import software.coley.recaf.info.BasicArscFileInfo;
import software.coley.recaf.info.BasicBinaryXmlFileInfo;

/**
 * Builder for {@link BasicBinaryXmlFileInfo}.
 *
 * @author Matt Coley
 */
public class ArscFileInfoBuilder extends ChunkFileInfoBuilder<ArscFileInfoBuilder> {
	public ArscFileInfoBuilder() {
		// empty
	}

	public ArscFileInfoBuilder(ArscFileInfo arscInfo) {
		super(arscInfo);
	}

	@Nonnull
	@Override
	public BasicArscFileInfo build() {
		return new BasicArscFileInfo(this);
	}
}
