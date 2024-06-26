package software.coley.recaf.services.cell.icon;

import dev.xdark.blw.code.Instruction;
import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.objectweb.asm.tree.AbstractInsnNode;
import software.coley.recaf.info.*;
import software.coley.recaf.info.annotation.Annotated;
import software.coley.recaf.info.annotation.AnnotationInfo;
import software.coley.recaf.info.member.ClassMember;
import software.coley.recaf.info.member.FieldMember;
import software.coley.recaf.info.member.LocalVariable;
import software.coley.recaf.info.member.MethodMember;
import software.coley.recaf.services.Service;
import software.coley.recaf.services.cell.context.FieldContextMenuProviderFactory;
import software.coley.recaf.services.cell.context.MethodContextMenuProviderFactory;
import software.coley.recaf.ui.control.tree.WorkspaceTreeCell;
import software.coley.recaf.util.BlwUtil;
import software.coley.recaf.workspace.model.Workspace;
import software.coley.recaf.workspace.model.bundle.*;
import software.coley.recaf.workspace.model.resource.WorkspaceResource;

/**
 * Provides support for providing icons for a variety of item types.
 * For instance, the graphics of {@link WorkspaceTreeCell} instances.
 * <br>
 * The icons displayed in the UI can be swapped out by supplying your own
 * {@link IconProviderFactory} instances to the overrides:
 * <ul>
 *     <li>{@link #setClassIconProviderOverride(ClassIconProviderFactory)}</li>
 *     <li>{@link #setFileIconProviderOverride(FileIconProviderFactory)}</li>
 *     <li>{@link #setInnerClassIconProviderOverride(InnerClassIconProviderFactory)}</li>
 *     <li>{@link #setFieldIconProviderOverride(FieldIconProviderFactory)}</li>
 *     <li>{@link #setMethodIconProviderOverride(MethodIconProviderFactory)}</li>
 *     <li>{@link #setPackageIconProviderOverride(PackageIconProviderFactory)}</li>
 *     <li>{@link #setDirectoryIconProviderOverride(DirectoryIconProviderFactory)}</li>
 *     <li>{@link #setBundleIconProviderOverride(BundleIconProviderFactory)}</li>
 *     <li>{@link #setResourceIconProviderOverride(ResourceIconProviderFactory)}</li>
 * </ul>
 *
 * @author Matt Coley
 */
@ApplicationScoped
public class IconProviderService implements Service {
	public static final String SERVICE_ID = "cell-icons";
	private final IconProviderServiceConfig config;
	// Defaults
	private final ClassIconProviderFactory classIconDefault;
	private final FileIconProviderFactory fileIconDefault;
	private final InnerClassIconProviderFactory innerClassIconDefault;
	private final FieldIconProviderFactory fieldIconDefault;
	private final MethodIconProviderFactory methodIconDefault;
	private final InstructionIconProviderFactory instructionIconDefault;
	private final ThrowsIconProviderFactory throwsIconDefault;
	private final CatchIconProviderFactory catchIconDefault;
	private final VariableIconProviderFactory variableIconDefault;
	private final AnnotationIconProviderFactory annotationIconDefault;
	private final PackageIconProviderFactory packageIconDefault;
	private final DirectoryIconProviderFactory directoryIconDefault;
	private final BundleIconProviderFactory bundleIconDefault;
	private final ResourceIconProviderFactory resourceIconDefault;
	// Overrides
	private ClassIconProviderFactory classIconOverride;
	private FileIconProviderFactory fileIconOverride;
	private InnerClassIconProviderFactory innerClassIconOverride;
	private FieldIconProviderFactory fieldIconOverride;
	private MethodIconProviderFactory methodIconOverride;
	private InstructionIconProviderFactory instructionIconOverride;
	private ThrowsIconProviderFactory throwsIconOverride;
	private CatchIconProviderFactory catchIconOverride;
	private VariableIconProviderFactory variableIconOverride;
	private AnnotationIconProviderFactory annotationIconOverride;
	private PackageIconProviderFactory packageIconOverride;
	private DirectoryIconProviderFactory directoryIconOverride;
	private BundleIconProviderFactory bundleIconOverride;
	private ResourceIconProviderFactory resourceIconOverride;

	@Inject
	public IconProviderService(@Nonnull IconProviderServiceConfig config,
							   @Nonnull ClassIconProviderFactory classIconDefault,
							   @Nonnull FileIconProviderFactory fileIconDefault,
							   @Nonnull InnerClassIconProviderFactory innerClassIconDefault,
							   @Nonnull FieldIconProviderFactory fieldIconDefault,
							   @Nonnull MethodIconProviderFactory methodIconDefault,
							   @Nonnull InstructionIconProviderFactory instructionIconDefault,
							   @Nonnull ThrowsIconProviderFactory throwsIconDefault,
							   @Nonnull CatchIconProviderFactory catchIconDefault,
							   @Nonnull VariableIconProviderFactory variableIconDefault,
							   @Nonnull AnnotationIconProviderFactory annotationIconDefault,
							   @Nonnull PackageIconProviderFactory packageIconDefault,
							   @Nonnull DirectoryIconProviderFactory directoryIconDefault,
							   @Nonnull BundleIconProviderFactory bundleIconDefault,
							   @Nonnull ResourceIconProviderFactory resourceIconDefault) {
		this.config = config;

		// Default factories
		this.classIconDefault = classIconDefault;
		this.fileIconDefault = fileIconDefault;
		this.innerClassIconDefault = innerClassIconDefault;
		this.fieldIconDefault = fieldIconDefault;
		this.methodIconDefault = methodIconDefault;
		this.instructionIconDefault = instructionIconDefault;
		this.throwsIconDefault = throwsIconDefault;
		this.catchIconDefault = catchIconDefault;
		this.variableIconDefault = variableIconDefault;
		this.annotationIconDefault = annotationIconDefault;
		this.packageIconDefault = packageIconDefault;
		this.directoryIconDefault = directoryIconDefault;
		this.bundleIconDefault = bundleIconDefault;
		this.resourceIconDefault = resourceIconDefault;
	}

	/**
	 * Delegates to {@link ClassIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param info
	 * 		The class to create an icon for.
	 *
	 * @return Icon provider for the class.
	 */
	@Nonnull
	public IconProvider getJvmClassInfoIconProvider(@Nonnull Workspace workspace,
													@Nonnull WorkspaceResource resource,
													@Nonnull JvmClassBundle bundle,
													@Nonnull JvmClassInfo info) {
		ClassIconProviderFactory factory = classIconOverride != null ? classIconOverride : classIconDefault;
		return factory.getJvmClassInfoIconProvider(workspace, resource, bundle, info);
	}

	/**
	 * Delegates to {@link ClassIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param info
	 * 		The class to create an icon for.
	 *
	 * @return Icon provider for the class.
	 */
	@Nonnull
	public IconProvider getAndroidClassInfoIconProvider(@Nonnull Workspace workspace,
														@Nonnull WorkspaceResource resource,
														@Nonnull AndroidClassBundle bundle,
														@Nonnull AndroidClassInfo info) {
		ClassIconProviderFactory factory = classIconOverride != null ? classIconOverride : classIconDefault;
		return factory.getAndroidClassInfoIconProvider(workspace, resource, bundle, info);
	}

	/**
	 * Delegates to {@link FileIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param info
	 * 		The file to create an icon for.
	 *
	 * @return Icon provider for the file.
	 */
	@Nonnull
	public IconProvider getFileInfoIconProvider(@Nonnull Workspace workspace,
												@Nonnull WorkspaceResource resource,
												@Nonnull FileBundle bundle,
												@Nonnull FileInfo info) {
		FileIconProviderFactory factory = fileIconOverride != null ? fileIconOverride : fileIconDefault;
		return factory.getFileInfoIconProvider(workspace, resource, bundle, info);
	}

	/**
	 * Delegates to {@link InnerClassIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param outerClass
	 * 		Outer class.
	 * @param inner
	 * 		The inner class to create an icon for.
	 *
	 * @return Icon provider for the inner class.
	 */
	@Nonnull
	public IconProvider getInnerClassInfoIconProvider(@Nonnull Workspace workspace,
													  @Nonnull WorkspaceResource resource,
													  @Nonnull ClassBundle<? extends ClassInfo> bundle,
													  @Nonnull ClassInfo outerClass,
													  @Nonnull InnerClassInfo inner) {
		InnerClassIconProviderFactory factory = innerClassIconOverride != null ? innerClassIconOverride : innerClassIconDefault;
		return factory.getInnerClassInfoIconProvider(workspace, resource, bundle, outerClass, inner);
	}

	/**
	 * Delegates to {@link FieldContextMenuProviderFactory} and {@link MethodContextMenuProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param declaringClass
	 * 		Containing class.
	 * @param member
	 * 		The member to create an icon for.
	 *
	 * @return Icon provider for the class member.
	 */
	@Nonnull
	public IconProvider getClassMemberIconProvider(@Nonnull Workspace workspace,
												   @Nonnull WorkspaceResource resource,
												   @Nonnull ClassBundle<? extends ClassInfo> bundle,
												   @Nonnull ClassInfo declaringClass,
												   @Nonnull ClassMember member) {
		if (member.isField()) {
			FieldIconProviderFactory factory = fieldIconOverride != null ? fieldIconOverride : fieldIconDefault;
			return factory.getFieldMemberIconProvider(workspace, resource, bundle, declaringClass, (FieldMember) member);
		} else if (member.isMethod()) {
			MethodIconProviderFactory factory = methodIconOverride != null ? methodIconOverride : methodIconDefault;
			return factory.getMethodMemberIconProvider(workspace, resource, bundle, declaringClass, (MethodMember) member);
		} else {
			throw new IllegalStateException("Unsupported member: " + member.getClass().getName());
		}
	}

	/**
	 * Delegates to {@link InstructionIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param declaringClass
	 * 		Containing class.
	 * @param declaringMethod
	 * 		Containing method.
	 * @param instruction
	 * 		The instruction to create an icon for.
	 *
	 * @return Icon provider for the class member.
	 */
	@Nonnull
	public IconProvider getInstructionIconProvider(@Nonnull Workspace workspace,
												   @Nonnull WorkspaceResource resource,
												   @Nonnull ClassBundle<?> bundle,
												   @Nonnull ClassInfo declaringClass,
												   @Nonnull MethodMember declaringMethod,
												   @Nonnull AbstractInsnNode instruction) {
		return getInstructionIconProvider(workspace, resource, bundle, declaringClass, declaringMethod, BlwUtil.convert(instruction));
	}

	/**
	 * Delegates to {@link InstructionIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param declaringClass
	 * 		Containing class.
	 * @param declaringMethod
	 * 		Containing method.
	 * @param instruction
	 * 		The instruction to create an icon for.
	 *
	 * @return Icon provider for the instruction.
	 */
	@Nonnull
	public IconProvider getInstructionIconProvider(@Nonnull Workspace workspace,
												   @Nonnull WorkspaceResource resource,
												   @Nonnull ClassBundle<?> bundle,
												   @Nonnull ClassInfo declaringClass,
												   @Nonnull MethodMember declaringMethod,
												   @Nonnull Instruction instruction) {
		InstructionIconProviderFactory factory = instructionIconOverride != null ? instructionIconOverride : instructionIconDefault;
		return factory.getInstructionIconProvider(workspace, resource, bundle, declaringClass, declaringMethod, instruction);
	}

	/**
	 * Delegates to {@link VariableIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param declaringClass
	 * 		Containing class.
	 * @param declaringMethod
	 * 		Containing method.
	 * @param variable
	 * 		The local variable to create an icon for.
	 *
	 * @return Icon provider for the local variable.
	 */
	@Nonnull
	public IconProvider getVariableIconProvider(@Nonnull Workspace workspace,
												@Nonnull WorkspaceResource resource,
												@Nonnull ClassBundle<?> bundle,
												@Nonnull ClassInfo declaringClass,
												@Nonnull MethodMember declaringMethod,
												@Nonnull LocalVariable variable) {
		VariableIconProviderFactory factory = variableIconOverride != null ? variableIconOverride : variableIconDefault;
		return factory.getVariableIconProvider(workspace, resource, bundle, declaringClass, declaringMethod, variable);
	}

	/**
	 * Delegates to {@link InstructionIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param declaringClass
	 * 		Containing class.
	 * @param declaringMethod
	 * 		Containing method.
	 * @param thrown
	 * 		The thrown type to create an icon for.
	 *
	 * @return Icon provider for the thrown type.
	 */
	@Nonnull
	public IconProvider getThrowsIconProvider(@Nonnull Workspace workspace,
											  @Nonnull WorkspaceResource resource,
											  @Nonnull ClassBundle<?> bundle,
											  @Nonnull ClassInfo declaringClass,
											  @Nonnull MethodMember declaringMethod,
											  @Nonnull String thrown) {
		ThrowsIconProviderFactory factory = throwsIconOverride != null ? throwsIconOverride : throwsIconDefault;
		return factory.getThrowsIconProvider(workspace, resource, bundle, declaringClass, declaringMethod, thrown);
	}

	/**
	 * Delegates to {@link InstructionIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param declaringClass
	 * 		Containing class.
	 * @param declaringMethod
	 * 		Containing method.
	 * @param caught
	 * 		The caught type to create an icon for.
	 *
	 * @return Icon provider for the caught type.
	 */
	@Nonnull
	public IconProvider getCatchIconProvider(@Nonnull Workspace workspace,
											 @Nonnull WorkspaceResource resource,
											 @Nonnull ClassBundle<?> bundle,
											 @Nonnull ClassInfo declaringClass,
											 @Nonnull MethodMember declaringMethod,
											 @Nonnull String caught) {
		CatchIconProviderFactory factory = catchIconOverride != null ? catchIconOverride : catchIconDefault;
		return factory.getCatchIconProvider(workspace, resource, bundle, declaringClass, declaringMethod, caught);
	}

	/**
	 * Delegates to {@link AnnotationIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param annotated
	 * 		The annotated item.
	 * @param annotation
	 * 		The annotation to create an icon for.
	 *
	 * @return Text provider for the annotation.
	 */
	@Nonnull
	public IconProvider getAnnotationIconProvider(@Nonnull Workspace workspace,
												  @Nonnull WorkspaceResource resource,
												  @Nonnull ClassBundle<? extends ClassInfo> bundle,
												  @Nonnull Annotated annotated,
												  @Nonnull AnnotationInfo annotation) {
		AnnotationIconProviderFactory factory = annotationIconOverride != null ? annotationIconOverride : annotationIconDefault;
		return factory.getAnnotationIconProvider(workspace, resource, bundle, annotated, annotation);
	}

	/**
	 * Delegates to {@link PackageIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param packageName
	 * 		The full package name, separated by {@code /}.
	 *
	 * @return Icon provider for the package.
	 */
	@Nonnull
	public IconProvider getPackageIconProvider(@Nonnull Workspace workspace,
											   @Nonnull WorkspaceResource resource,
											   @Nonnull ClassBundle<? extends ClassInfo> bundle,
											   @Nonnull String packageName) {
		PackageIconProviderFactory factory = packageIconOverride != null ? packageIconOverride : packageIconDefault;
		return factory.getPackageIconProvider(workspace, resource, bundle, packageName);
	}

	/**
	 * Delegates to {@link DirectoryIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		Containing bundle.
	 * @param directoryName
	 * 		The full path of the directory.
	 *
	 * @return Icon provider for the directory.
	 */
	@Nonnull
	public IconProvider getDirectoryIconProvider(@Nonnull Workspace workspace,
												 @Nonnull WorkspaceResource resource,
												 @Nonnull FileBundle bundle,
												 @Nonnull String directoryName) {
		DirectoryIconProviderFactory factory = directoryIconOverride != null ? directoryIconOverride : directoryIconDefault;
		return factory.getDirectoryIconProvider(workspace, resource, bundle, directoryName);
	}

	/**
	 * Delegates to {@link BundleIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		Containing resource.
	 * @param bundle
	 * 		The bundle to create an icon for.
	 *
	 * @return Icon provider for the bundle.
	 */
	@Nonnull
	public IconProvider getBundleIconProvider(@Nonnull Workspace workspace,
											  @Nonnull WorkspaceResource resource,
											  @Nonnull Bundle<? extends Info> bundle) {
		BundleIconProviderFactory factory = bundleIconOverride != null ? bundleIconOverride : bundleIconDefault;
		return factory.getBundleIconProvider(workspace, resource, bundle);
	}

	/**
	 * Delegates to {@link ResourceIconProviderFactory}.
	 *
	 * @param workspace
	 * 		Containing workspace.
	 * @param resource
	 * 		The resource to create an icon for.
	 *
	 * @return Icon provider for the resource.
	 */
	@Nonnull
	public IconProvider getResourceIconProvider(@Nonnull Workspace workspace,
												@Nonnull WorkspaceResource resource) {
		ResourceIconProviderFactory factory = resourceIconOverride != null ? resourceIconOverride : resourceIconDefault;
		return factory.getResourceIconProvider(workspace, resource);
	}

	/**
	 * @return Default icon provider for classes.
	 */
	@Nonnull
	public ClassIconProviderFactory getClassIconDefault() {
		return classIconDefault;
	}

	/**
	 * @return Default icon provider for methods.
	 */
	@Nonnull
	public MethodIconProviderFactory getMethodIconDefault() {
		return methodIconDefault;
	}

	/**
	 * @return Default icon provider for fields.
	 */
	@Nonnull
	public FieldIconProviderFactory getFieldIconDefault() {
		return fieldIconDefault;
	}

	/**
	 * @return Default icon provider for annotations.
	 */
	@Nonnull
	public AnnotationIconProviderFactory getAnnotationIconDefault() {
		return annotationIconDefault;
	}

	/**
	 * @return Default icon provider for inner classes.
	 */
	@Nonnull
	public InnerClassIconProviderFactory getInnerClassIconDefault() {
		return innerClassIconDefault;
	}

	/**
	 * @return Default icon provider for instructions.
	 */
	@Nonnull
	public InstructionIconProviderFactory getInstructionIconDefault() {
		return instructionIconDefault;
	}

	/**
	 * @return Default icon provider for thrown types.
	 */
	@Nonnull
	public ThrowsIconProviderFactory getThrowsIconDefault() {
		return throwsIconDefault;
	}

	/**
	 * @return Default icon provider for catch blocks.
	 */
	@Nonnull
	public CatchIconProviderFactory getCatchIconDefault() {
		return catchIconDefault;
	}

	/**
	 * @return Default icon provider for variables.
	 */
	@Nonnull
	public VariableIconProviderFactory getVariableIconDefault() {
		return variableIconDefault;
	}

	/**
	 * @return Default icon provider for files.
	 */
	@Nonnull
	public FileIconProviderFactory getFileIconDefault() {
		return fileIconDefault;
	}

	/**
	 * @return Default icon provider for packages.
	 */
	@Nonnull
	public PackageIconProviderFactory getPackageIconDefault() {
		return packageIconDefault;
	}

	/**
	 * @return Default icon provider for directories.
	 */
	@Nonnull
	public DirectoryIconProviderFactory getDirectoryIconDefault() {
		return directoryIconDefault;
	}

	/**
	 * @return Default icon provider for bundles.
	 */
	@Nonnull
	public BundleIconProviderFactory getBundleIconDefault() {
		return bundleIconDefault;
	}

	/**
	 * @return Default icon provider for resources.
	 */
	@Nonnull
	public ResourceIconProviderFactory getResourceIconDefault() {
		return resourceIconDefault;
	}

	/**
	 * @return Override factory for supplying class icon providers.
	 */
	@Nullable
	public ClassIconProviderFactory getClassIconProviderOverride() {
		return classIconOverride;
	}

	/**
	 * @param classIconOverride
	 * 		Override factory for supplying class icon providers.
	 */
	public void setClassIconProviderOverride(@Nullable ClassIconProviderFactory classIconOverride) {
		this.classIconOverride = classIconOverride;
	}

	/**
	 * @return Override factory for supplying file icon providers.
	 */
	@Nullable
	public FileIconProviderFactory getFileIconProviderOverride() {
		return fileIconOverride;
	}

	/**
	 * @param fileIconOverride
	 * 		Override factory for supplying file icon providers.
	 */
	public void setFileIconProviderOverride(@Nullable FileIconProviderFactory fileIconOverride) {
		this.fileIconOverride = fileIconOverride;
	}

	/**
	 * @return Override factory for supplying inner class icon providers.
	 */
	@Nullable
	public InnerClassIconProviderFactory getInnerClassIconProviderOverride() {
		return innerClassIconOverride;
	}

	/**
	 * @param innerClassIconOverride
	 * 		Override factory for supplying inner class icon providers.
	 */
	public void setInnerClassIconProviderOverride(@Nullable InnerClassIconProviderFactory innerClassIconOverride) {
		this.innerClassIconOverride = innerClassIconOverride;
	}

	/**
	 * @return Override factory for supplying field icon providers.
	 */
	@Nullable
	public FieldIconProviderFactory getFieldIconProviderOverride() {
		return fieldIconOverride;
	}

	/**
	 * @param fieldIconOverride
	 * 		Override factory for supplying field icon providers.
	 */
	public void setFieldIconProviderOverride(@Nullable FieldIconProviderFactory fieldIconOverride) {
		this.fieldIconOverride = fieldIconOverride;
	}

	/**
	 * @return Override factory for supplying method icon providers.
	 */
	@Nullable
	public MethodIconProviderFactory getMethodIconProviderOverride() {
		return methodIconOverride;
	}

	/**
	 * @param methodIconOverride
	 * 		Override factory for supplying method icon providers.
	 */
	public void setMethodIconProviderOverride(@Nullable MethodIconProviderFactory methodIconOverride) {
		this.methodIconOverride = methodIconOverride;
	}

	/**
	 * @return Override factory for supplying instruction icon providers.
	 */
	@Nullable
	public InstructionIconProviderFactory getInstructionIconOverride() {
		return instructionIconOverride;
	}

	/**
	 * @param instructionIconOverride
	 * 		Override factory for supplying instruction icon providers.
	 */
	public void setInstructionIconOverride(@Nullable InstructionIconProviderFactory instructionIconOverride) {
		this.instructionIconOverride = instructionIconOverride;
	}

	/**
	 * @return Override factory for supplying {@code throws} icon providers.
	 */
	@Nullable
	public ThrowsIconProviderFactory getThrowsIconOverride() {
		return throwsIconOverride;
	}

	/**
	 * @param throwsIconOverride
	 * 		Override factory for supplying {@code throws} icon providers.
	 */
	public void setThrowsIconOverride(@Nullable ThrowsIconProviderFactory throwsIconOverride) {
		this.throwsIconOverride = throwsIconOverride;
	}

	/**
	 * @return Override factory for supplying {@code catch} icon providers.
	 */
	@Nullable
	public CatchIconProviderFactory getCatchIconOverride() {
		return catchIconOverride;
	}

	/**
	 * @param catchIconOverride
	 * 		Override factory for supplying {@code catch} icon providers.
	 */
	public void setCatchIconOverride(@Nullable CatchIconProviderFactory catchIconOverride) {
		this.catchIconOverride = catchIconOverride;
	}

	/**
	 * @return Override factory for supplying {@code var} icon providers.
	 */
	@Nullable
	public VariableIconProviderFactory getVariableIconOverride() {
		return variableIconOverride;
	}

	/**
	 * @param variableIconOverride
	 * 		Override factory for supplying {@code var} icon providers.
	 */
	public void setVariableIconOverride(@Nullable VariableIconProviderFactory variableIconOverride) {
		this.variableIconOverride = variableIconOverride;
	}

	/**
	 * @return Override factory for supplying annotation icon providers.
	 */
	@Nullable
	public AnnotationIconProviderFactory getAnnotationIconProviderOverride() {
		return annotationIconOverride;
	}

	/**
	 * @param annotationIconOverride
	 * 		Override factory for supplying annotation icon providers.
	 */
	public void setAnnotationIconProviderOverride(@Nullable AnnotationIconProviderFactory annotationIconOverride) {
		this.annotationIconOverride = annotationIconOverride;
	}

	/**
	 * @return Override factory for supplying package icon providers.
	 */
	@Nullable
	public PackageIconProviderFactory getPackageIconProviderOverride() {
		return packageIconOverride;
	}

	/**
	 * @param packageIconOverride
	 * 		Override factory for supplying package icon providers.
	 */
	public void setPackageIconProviderOverride(@Nullable PackageIconProviderFactory packageIconOverride) {
		this.packageIconOverride = packageIconOverride;
	}

	/**
	 * @return Override factory for supplying directory icon providers.
	 */
	@Nullable
	public DirectoryIconProviderFactory getDirectoryIconProviderOverride() {
		return directoryIconOverride;
	}

	/**
	 * @param directoryIconOverride
	 * 		Override factory for supplying directory icon providers.
	 */
	public void setDirectoryIconProviderOverride(@Nullable DirectoryIconProviderFactory directoryIconOverride) {
		this.directoryIconOverride = directoryIconOverride;
	}

	/**
	 * @return Override factory for supplying bundle icon providers.
	 */
	@Nullable
	public BundleIconProviderFactory getBundleIconProviderOverride() {
		return bundleIconOverride;
	}

	/**
	 * @param bundleIconOverride
	 * 		Override factory for supplying bundle icon providers.
	 */
	public void setBundleIconProviderOverride(@Nullable BundleIconProviderFactory bundleIconOverride) {
		this.bundleIconOverride = bundleIconOverride;
	}

	/**
	 * @return Override factory for supplying resource icon providers.
	 */
	@Nullable
	public ResourceIconProviderFactory getResourceIconProviderOverride() {
		return resourceIconOverride;
	}

	/**
	 * @param resourceIconOverride
	 * 		Override factory for supplying resource icon providers.
	 */
	public void setResourceIconProviderOverride(@Nullable ResourceIconProviderFactory resourceIconOverride) {
		this.resourceIconOverride = resourceIconOverride;
	}

	@Nonnull
	@Override
	public String getServiceId() {
		return SERVICE_ID;
	}

	@Nonnull
	@Override
	public IconProviderServiceConfig getServiceConfig() {
		return config;
	}
}
