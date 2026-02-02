package Project_imagemanager.converter;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

// BMP Converter
public class BMPConverter extends AbstractImageConverter {

    public BMPConverter() {
        super("BMP Converter", new String[]{"BMP"});
    }

    @Override
    public File convert(File sourceFile, String targetFormat) throws IOException {
        if (!supportsFormat(targetFormat)) {
            throw new IllegalArgumentException("Unsupported format: " + targetFormat);
        }

        try {
            // Read source image
            BufferedImage image = readImage(sourceFile);

            // BMP requires TYPE_INT_RGB for compatibility
            BufferedImage bmpImage = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );

            Graphics2D g2d = bmpImage.createGraphics();
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();

            // Creates output file name
            String outputPath = sourceFile.getParent() + File.separator +
                    getFileNameWithoutExtension(sourceFile) + "_converted.bmp";
            File outputFile = new File(outputPath);

            // Writes in BMP format
            writeImage(bmpImage, outputFile, "bmp");

            return outputFile;

        } catch (IOException e) {
            throw new IOException("BMP conversion failed: " + e.getMessage(), e);
        }
    }
}