package Project_imagemanager.filter;

import java.awt.image.BufferedImage;

// Sepia Filter - Applies vintage sepia tone effect
public class SepiaFilter implements ImageFilter {

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage sepiaImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);

                // Extract RGB components
                int r = (rgb >> 16) & 0xFF;
                int g = (rgb >> 8) & 0xFF;
                int b = rgb & 0xFF;

                // Apply sepia formula
                int newR = (int) (0.393 * r + 0.769 * g + 0.189 * b);
                int newG = (int) (0.349 * r + 0.686 * g + 0.168 * b);
                int newB = (int) (0.272 * r + 0.534 * g + 0.131 * b);

                // Clamp values to 0-255
                newR = Math.min(255, newR);
                newG = Math.min(255, newG);
                newB = Math.min(255, newB);

                // Combine back to RGB
                int newRgb = (newR << 16) | (newG << 8) | newB;
                sepiaImage.setRGB(x, y, newRgb);
            }
        }

        return sepiaImage;
    }

    @Override
    public String getFilterName() {
        return "Sepia Tone";
    }

    @Override
    public String getDescription() {
        return "Applies vintage sepia tone effect";
    }
}