package Project_imagemanager.filter;

import java.awt.image.BufferedImage;

// Invert Filter - Inverts image colors (negative effect)
public class InvertFilter implements ImageFilter {

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage invertedImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                // Extract RGB components
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Invert colors
                r = 255 - r;
                g = 255 - g;
                b = 255 - b;

                // Combine back to RGB
                int newRgb = (r << 16) | (g << 8) | b;
                invertedImage.setRGB(x, y, newRgb);
            }
        }

        return invertedImage;
    }

    @Override
    public String getFilterName() {
        return "Invert Colors";
    }

    @Override
    public String getDescription() {
        return "Inverts all colors (negative effect)";
    }
}