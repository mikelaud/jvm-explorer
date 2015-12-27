package com.blogspot.mikelaud.je.ui;

import javafx.beans.value.ChangeListener;
import javafx.geometry.Bounds;
import javafx.geometry.Rectangle2D;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

public class UiBackgroundImpl implements UiBackground {

	private final ImageView IMAGE_VIEW;
	private final Pane IMAGE_PANE;
	private final StackPane PANE;
	
	private ChangeListener<Bounds> createBoundsListener() {
		return (observable, oldValue, newValue) -> {
			Image image = IMAGE_VIEW.getImage();
			if (null != image) {
				double imageWidth = image.getWidth();
				double imageHeight = image.getHeight();
				//
				double windowWidth = newValue.getWidth();
				double windowHeight = newValue.getHeight();
				//
				if (imageHeight > 0 && windowHeight > 0) {
					//
					double imageRation = imageWidth / imageHeight;
					double windowRation = windowWidth / windowHeight;
					//
					double viewWidth;
					double viewHeigth;
					//
					double viewX;
					double viewY;
					//
					if (windowRation > imageRation) {
						viewWidth = imageWidth;
						viewHeigth = imageWidth / windowRation;
						//
						double extraHeight = imageHeight - viewHeigth;
						//
						viewX = 0;
						viewY = extraHeight / 2;
					}
					else {
						viewWidth = imageHeight * windowRation;
						viewHeigth = imageHeight;
						//
						double extraWidth = imageWidth - viewWidth;
						//
						viewX = extraWidth / 2;
						viewY = 0;
					}
					//
					Rectangle2D viewport = new Rectangle2D(viewX, viewY, viewWidth, viewHeigth);
					IMAGE_VIEW.setViewport(viewport);
				}
			}
		};
	}

	private void buildForm() {
		IMAGE_VIEW.setPreserveRatio(true);
		IMAGE_VIEW.setSmooth(true);
		//
		IMAGE_PANE.getChildren().addAll(IMAGE_VIEW);
		IMAGE_PANE.setMinSize(0, 0);
		IMAGE_PANE.layoutBoundsProperty().addListener(createBoundsListener());
		//
		PANE.getChildren().setAll(IMAGE_PANE);
		PANE.setMinSize(0, 0);
		//
		IMAGE_VIEW.fitWidthProperty().bind(IMAGE_PANE.widthProperty());
		IMAGE_VIEW.fitHeightProperty().bind(IMAGE_PANE.heightProperty());
	}

	public UiBackgroundImpl() {
		IMAGE_VIEW = new ImageView();
		IMAGE_PANE = new Pane();
		PANE = new StackPane();
		//
		buildForm();
	}

	@Override
	public final Pane getPane() {
		return PANE;
	}

	@Override
	public final Image getImage() {
		return IMAGE_VIEW.getImage();
	}
	
	@Override
	public final void setImage(Image aImage) {
		IMAGE_VIEW.setImage(aImage);
	}

}
