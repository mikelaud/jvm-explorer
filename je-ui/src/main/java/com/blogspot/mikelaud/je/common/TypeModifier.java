package com.blogspot.mikelaud.je.common;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import javafx.scene.image.Image;

public class TypeModifier {

	private static interface Const {
		Path IMAGES_PATH = Paths.get("type", "modifier", "combo");
		String IMAGE_EXT = ".png";
	}
	
	private static final Image[][] IMAGES = createImages();
	
	private static void createImage(Image[][] aImages, TypeInheritance aInheritance, TypeStatic aStatic) {
		String imageName = aInheritance.getLabel() + Const.IMAGE_EXT;
		Path imagePath = Paths.get(Const.IMAGES_PATH.toString());
		if (TypeStatic.Yes == aStatic) {
			imagePath = imagePath.resolve(aStatic.getLabel());
		}
		imagePath = imagePath.resolve(imageName);
		aImages[aInheritance.ordinal()][aStatic.ordinal()] = new Image(imagePath.toString());
	}
	
	private static void createImages(Image[][] aImages, TypeInheritance aInheritance) {
		Arrays.stream(TypeStatic.values()).forEach(s -> createImage(aImages, aInheritance, s));
	}
	
	private static Image[][] createImages() {
		final Image[][] images = new Image[TypeInheritance.values().length][TypeStatic.values().length];
		Arrays.stream(TypeInheritance.values()).forEach(i -> createImages(images, i));
		return images;
	}

	public static Image getImage(TypeInheritance aInheritance, TypeStatic aStatic, TypeType aType) {
		if (TypeInheritance.Abstract == aInheritance) {
			if (TypeType.Interface == aType || TypeType.Annotation == aType) {
				aInheritance = TypeInheritance.No;
			}
		}
		return IMAGES[aInheritance.ordinal()][aStatic.ordinal()];
	}
		
}
