package horseracer.screens.utils;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class ImageLoader {

  /**
   * Create an image
   */
  public static ImageView create(String image_name) {
    // Load image (from file or resource)
    Image image = new Image(ImageLoader.class.getResource(image_name).toExternalForm());

    // Create ImageView
    ImageView imageView = new ImageView(image);
    imageView.setPreserveRatio(true);

    return imageView;

  }
}
