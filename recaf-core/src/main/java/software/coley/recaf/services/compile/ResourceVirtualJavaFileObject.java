package software.coley.recaf.services.compile;

import jakarta.annotation.Nonnull;

import javax.tools.SimpleJavaFileObject;
import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URI;

/**
 * Java file extension that exposes workspace resource for classpath.
 *
 * @author xDark
 */
public class ResourceVirtualJavaFileObject extends SimpleJavaFileObject {
	private final String resourceName;
	private final byte[] content;

	/**
	 * @param resourceName
	 * 		Name of the resource.
	 * @param content
	 * 		Class source content.
	 * @param resourceKind
	 * 		Kind of the resource.
	 */
	public ResourceVirtualJavaFileObject(String resourceName, byte[] content, Kind resourceKind) {
		super(URI.create("memory://" + resourceName + resourceKind.extension), resourceKind);
		this.resourceName = resourceName;
		this.content = content;
	}

	/**
	 * @return Resource name.
	 */
	@Nonnull
	public String getResourceName() {
		return resourceName;
	}

	@Override
	public InputStream openInputStream() {
		return new ByteArrayInputStream(content);
	}
}