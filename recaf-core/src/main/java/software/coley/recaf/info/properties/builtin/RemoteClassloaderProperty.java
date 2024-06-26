package software.coley.recaf.info.properties.builtin;

import it.unimi.dsi.fastutil.ints.Int2ObjectArrayMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import software.coley.recaf.info.JvmClassInfo;
import software.coley.recaf.info.properties.BasicProperty;
import software.coley.recaf.workspace.model.resource.WorkspaceRemoteVmResource;

/**
 * Built in property to the classloader ID a {@link JvmClassInfo} is associated with
 * in a {@link WorkspaceRemoteVmResource}.
 *
 * @author Matt Coley
 */
public class RemoteClassloaderProperty extends BasicProperty<Integer> {
	private static final Int2ObjectMap<RemoteClassloaderProperty> cache = new Int2ObjectArrayMap<>();
	public static final String KEY = "remote-classloader-id";

	/**
	 * @param value
	 * 		Loader ID
	 */
	private RemoteClassloaderProperty(int value) {
		super(KEY, value);
	}

	/**
	 * @param classInfo
	 * 		Class info to link with a classloader via its ID.
	 *
	 * @return Classloader ID associated with the class,
	 * used as key for {@link WorkspaceRemoteVmResource#getJvmClassloaderBundles()}.
	 * May be {@code null} if no loader association exists.
	 */
	@Nullable
	public static Integer get(@Nonnull JvmClassInfo classInfo) {
		return classInfo.getPropertyValueOrNull(KEY);
	}

	/**
	 * @param classInfo
	 * 		Class info to link with a version.
	 * @param loaderId
	 * 		Loader ID associated with the class,
	 * 		used as key for {@link WorkspaceRemoteVmResource#getJvmClassloaderBundles()}.
	 */
	public static synchronized void set(@Nonnull JvmClassInfo classInfo, int loaderId) {
		synchronized (cache) {
			classInfo.setProperty(cache.computeIfAbsent(loaderId, RemoteClassloaderProperty::new));
		}
	}

	/**
	 * @param info
	 * 		Info instance.
	 */
	public static void remove(@Nonnull JvmClassInfo info) {
		info.removeProperty(KEY);
	}
}
