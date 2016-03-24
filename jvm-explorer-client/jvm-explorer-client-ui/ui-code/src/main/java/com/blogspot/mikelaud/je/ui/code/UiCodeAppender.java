package com.blogspot.mikelaud.je.ui.code;

import java.util.concurrent.CompletableFuture;

import com.blogspot.mikelaud.je.ui.background.UiBackground;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.AppenderBase;
import javafx.application.Platform;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.text.Font;

public class UiCodeAppender extends AppenderBase<ILoggingEvent> {

	private static UiBackground UI_BACKGROUND;

	@Override
	protected void append(ILoggingEvent aEvent) {
		if (null != UI_BACKGROUND) {
			CompletableFuture.runAsync(() -> Platform.runLater(() -> UI_BACKGROUND.getLogger().appendText(aEvent.getFormattedMessage() + "\n")));
		}
	}

	public static void setUiBackground(UiBackground aUiBackground) {
		UI_BACKGROUND = aUiBackground;
		UI_BACKGROUND.getLogger().setVisible(true);
		UI_BACKGROUND.getLogger().setFont(Font.font ("Consolas", 10));
		UI_BACKGROUND.getImageView().setEffect(new ColorAdjust(0, 0, -0.7, 0));
	}

}
