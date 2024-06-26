package software.coley.recaf.ui.menubar;

import jakarta.annotation.Nonnull;
import jakarta.enterprise.context.Dependent;
import jakarta.inject.Inject;
import javafx.geometry.Insets;
import javafx.scene.control.MenuBar;

/**
 * Main menu component, bundling sub-menu components.
 *
 * @author Matt Coley
 */
@Dependent
public class MainMenu extends MenuBar {
	@Inject
	public MainMenu(@Nonnull FileMenu fileMenu,
					@Nonnull ConfigMenu configMenu,
					@Nonnull SearchMenu searchMenu,
					@Nonnull MappingMenu mappingMenu,
					@Nonnull AnalysisMenu analysisMenu,
					@Nonnull ScriptMenu scriptMenu,
					@Nonnull HelpMenu helpMenu) {
		getMenus().addAll(fileMenu, configMenu, searchMenu, mappingMenu, analysisMenu, scriptMenu, helpMenu);
		setPadding(new Insets(0, 0, 2, 0));
	}
}
