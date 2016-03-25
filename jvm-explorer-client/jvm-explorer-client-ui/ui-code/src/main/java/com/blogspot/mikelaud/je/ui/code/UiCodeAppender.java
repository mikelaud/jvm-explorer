package com.blogspot.mikelaud.je.ui.code;

import com.blogspot.mikelaud.je.ui.background.UiBackground;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import javafx.application.Platform;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.text.Font;

public class UiCodeAppender extends AppenderBase<ILoggingEvent> {

	private static UiBackground UI;

	@Override
	protected void append(ILoggingEvent aEvent) {
		if (null != UI) {
			Platform.runLater(() -> UI.getLogger().appendText(aEvent.getFormattedMessage() + "\n"));
		}
	}

	public static UiBackground getUi() {
		return UI;
	}
	
	public static void setUi(UiBackground aUi) {
		UI = aUi;
		UI.getLogger().setVisible(true);
		UI.getLogger().setFont(Font.font ("Consolas", 10));
		UI.getImageView().setEffect(new ColorAdjust(0, 0, -0.7, 0));
	}

	public static void clearUi() {
		Platform.runLater(() -> UI.getLogger().clear());
	}
	
}
