package software.coley.recaf.info.builder;

import jakarta.annotation.Nonnull;
import software.coley.recaf.info.BasicImageFileInfo;
import software.coley.recaf.info.ImageFileInfo;

/**
 * Builder for {@link ImageFileInfo}.
 *
 * @author Matt Coley
 */
public class ImageFileInfoBuilder extends FileInfoBuilder<ImageFileInfoBuilder> {
	public ImageFileInfoBuilder() {
		// empty
	}

	public ImageFileInfoBuilder(ImageFileInfo imageFileInfo) {
		super(imageFileInfo);
	}

	public ImageFileInfoBuilder(FileInfoBuilder<?> other) {
		super(other);
	}

	@Nonnull
	@Override
	public BasicImageFileInfo build() {
		return new BasicImageFileInfo(this);
	}
}
