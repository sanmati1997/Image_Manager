package Project_imagemanager.converter;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

 // PNG Converter
public class PNGConverter extends AbstractImageConverter {

    public PNGConverter() {
        super("PNG Converter", new String[]{"PNG"});
    }

    @Override
    public File convert(File sourceFile, String targetFormat) throws IOException {
        if (!supportsFormat(targetFormat)) {
            throw new IllegalArgumentException("Unsupported format: " + targetFormat);
        }

        try {
            // Read source image
            BufferedImage image = readImage(sourceFile);

            // Create output file name
            String outputPath = sourceFile.getParent() + File.separator +
                    getFileNameWithoutExtension(sourceFile) + "_converted.png";
            File outputFile = new File(outputPath);

            // Write in PNG format
            writeImage(image, outputFile, "png");

            return outputFile;

        } catch (IOException e) {
            throw new IOException("PNG conversion failed: " + e.getMessage(), e);
        }
    }
}