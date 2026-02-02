package Project_imagemanager.filter;

import java.awt.image.BufferedImage;
import java.awt.image.ConvolveOp;
import java.awt.image.Kernel;

// Blur Filter using Gaussian blur
public class BlurFilter implements ImageFilter {

    @Override
    public BufferedImage apply(BufferedImage image) {
        // Gaussian blur kernel (5x5)
        float[] matrix = {
                1f/256f,  4f/256f,  6f/256f,  4f/256f, 1f/256f,
                4f/256f, 16f/256f, 24f/256f, 16f/256f, 4f/256f,
                6f/256f, 24f/256f, 36f/256f, 24f/256f, 6f/256f,
                4f/256f, 16f/256f, 24f/256f, 16f/256f, 4f/256f,
                1f/256f,  4f/256f,  6f/256f,  4f/256f, 1f/256f
        };

        Kernel kernel = new Kernel(5, 5, matrix);
        ConvolveOp op = new ConvolveOp(
                kernel,
                ConvolveOp.EDGE_NO_OP,
                null
        );

        return op.filter(image, null);
    }

    @Override
    public String getFilterName() {
        return "Blur";
    }

    @Override
    public String getDescription() {
        return "Applies Gaussian blur";
    }
}