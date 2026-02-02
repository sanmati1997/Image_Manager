package Project_imagemanager.converter;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// GIF Converter

public class GIFConverter extends AbstractImageConverter {

    public GIFConverter() {
        super("GIF Converter", new String[]{"GIF"});
    }

    @Override
    public File convert(File sourceFile, String targetFormat) throws IOException {
        if (!supportsFormat(targetFormat)) {
            throw new IllegalArgumentException("Unsupported format: " + targetFormat);
        }

        try {
            // Read source image
            BufferedImage image = readImage(sourceFile);

            // Convert to GIF-compatible format
            BufferedImage gifImage = convertToGIFFormat(image);

            // Create output file name
            String outputPath = sourceFile.getParent() + File.separator +
                    getFileNameWithoutExtension(sourceFile) + "_converted.gif";
            File outputFile = new File(outputPath);

            // Write in GIF format
            writeImage(gifImage, outputFile, "gif");

            return outputFile;

        } catch (IOException e) {
            throw new IOException("GIF conversion failed: " + e.getMessage(), e);
        }
    }

    // Converts image to GIF-compatible format (GIF supports max 256 colors, so need to reduce color depth)

    private BufferedImage convertToGIFFormat(BufferedImage source) {
        // Create a new image with TYPE_INT_RGB for better GIF compatibility
        int width = source.getWidth();
        int height = source.getHeight();

        BufferedImage gifImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);

        // Draw the source image onto the new image
        Graphics2D g2d = gifImage.createGraphics();

        // Set rendering hints for better quality
        g2d.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BILINEAR);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Handle transparency - GIF supports limited transparency
        // Fill with white background if source has transparency
        if (hasTransparency(source)) {
            g2d.setColor(Color.WHITE);
            g2d.fillRect(0, 0, width, height);
        }

        g2d.drawImage(source, 0, 0, null);
        g2d.dispose();

        return gifImage;
    }

    private boolean hasTransparency(BufferedImage image) {
        return image.getColorModel().hasAlpha();
    }
}