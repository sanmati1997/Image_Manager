package Project_imagemanager.filter;

import java.awt.image.BufferedImage;

public class GrayscaleFilter implements ImageFilter {

    @Override
    public BufferedImage apply(BufferedImage image) {
        int width = image.getWidth();
        int height = image.getHeight();

        BufferedImage grayImage = new BufferedImage(width, height, BufferedImage.TYPE_BYTE_GRAY);

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int rgb = image.getRGB(x, y);
                grayImage.setRGB(x, y, rgb);
            }
        }

        return grayImage;
    }

    @Override
    public String getFilterName() {
        return "Grayscale";
    }

    @Override
    public String getDescription() {
        return "Converts image to black and white";
    }
}