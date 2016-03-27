package com.blogspot.mikelaud.je.ui.background;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import javafx.application.Platform;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.ImageView;

public class UiBackgroundAppender extends AppenderBase<ILoggingEvent> {

	private static UiBackground UI;

	@Override
	protected void append(ILoggingEvent aEvent) {
		if (null == UI) return;
		Platform.runLater(() -> UI.getLogger().appendText(aEvent.getFormattedMessage() + "\n"));
	}

	public static UiBackground getUi() {
		return UI;
	}

	public static void setUi(UiBackground aUi) {
		UI = aUi;
	}

	public static void clearUi() {
		if (null == UI) return;
		Platform.runLater(() -> UI.getLogger().clear());
	}

	public static void setVisible(boolean aVisible) {
		if (null == UI) return;
		clearUi();
		Platform.runLater(() -> {
			ImageView imageView = UI.getImageView();
			if (aVisible) {
				imageView.setEffect(new ColorAdjust(0, 0, -0.7, 0));
			}
			else {
				imageView.setEffect(null);
			}
			UI.getLogger().setVisible(aVisible);
		});
	}

}
