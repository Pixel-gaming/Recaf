package software.coley.recaf.ui.control.richtext.suggest;

import jakarta.annotation.Nonnull;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.List;

import static javafx.scene.input.KeyCode.*;

/**
 * An event handler to target {@link KeyEvent#KEY_RELEASED} which updates
 * the items and visibility state of a {@link CompletionPopup}.
 *
 * @author Matt Coley
 */
public class CompletionPopupUpdater<T> implements EventHandler<KeyEvent> {
	private final TabCompleter<T> tabCompleter;
	private final CompletionPopup<T> completionPopup;

	public CompletionPopupUpdater(@Nonnull TabCompleter<T> tabCompleter,
								  @Nonnull CompletionPopup<T> completionPopup) {
		this.tabCompleter = tabCompleter;
		this.completionPopup = completionPopup;
	}

	@Override
	public void handle(KeyEvent event) {
		KeyCode code = event.getCode();

		// Remove popup if escape is pressed.
		if (code == ESCAPE) {
			completionPopup.hide();
			event.consume();
			return;
		}

		// Skip handling for navigation keys.
		// These move around the completion menu.
		if (code.isArrowKey() || code.isNavigationKey() || code == TAB || code == ENTER) {
			return;
		}

		// Any non-word character should remove the popup.
		// Modifiers are also excluded so capitalizing letters won't hide the popup.
		if (!code.isLetterKey() && !code.isDigitKey() && !code.isModifierKey()) {
			completionPopup.hide();
			return;
		}

		// Update the bounds, or hide the popup if the caret bounds cannot be found.
		if (!completionPopup.updateCaretBounds()) {
			completionPopup.hide();
			return;
		}

		// Fill completion candidates
		List<T> items = tabCompleter.computeCurrentCompletions();

		// Update the popup
		completionPopup.updateItems(items);
		if (!items.isEmpty())
			completionPopup.show();
		else
			completionPopup.hide();
	}
}
