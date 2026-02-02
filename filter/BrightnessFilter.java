package Project_imagemanager.filter;

import java.awt.image.BufferedImage;

// Brightness Filter - Increases image brightness
public class BrightnessFilter implements ImageFilter {

    private int brightnessLevel;

     // Constructor with default brightness increase of 50
    public BrightnessFilter() {
        this.brightnessLevel = 50;
    }

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage brightImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                // Extract RGB components
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Increase brightness
                r = Math.min(255, Math.max(0, r + brightnessLevel));
                g = Math.min(255, Math.max(0, g + brightnessLevel));
                b = Math.min(255, Math.max(0, b + brightnessLevel));

                // Combine back to RGB
                int newRgb = (r << 16) | (g << 8) | b;
                brightImage.setRGB(x, y, newRgb);
            }
        }

        return brightImage;
    }

    @Override
    public String getFilterName() {
        return "Brightness";
    }

    @Override
    public String getDescription() {
        return "Increases image brightness by " + brightnessLevel;
    }
}