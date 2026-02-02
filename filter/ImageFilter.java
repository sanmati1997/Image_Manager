package Project_imagemanager.filter;

import java.awt.image.BufferedImage;

// ImageFilter Interface
public interface ImageFilter {

    // Applies the filter to an image
    BufferedImage apply(BufferedImage image);

    // Gets the filter name
    String getFilterName();

    // Gets the filter description
    String getDescription();
}