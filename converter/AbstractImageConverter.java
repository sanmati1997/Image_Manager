package Project_imagemanager.converter;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


 // Abstract base class for image converters
public abstract class AbstractImageConverter implements ImageConverter {

    protected String[] supportedFormats;
    protected String converterName;

    public AbstractImageConverter(String converterName, String[] supportedFormats) {
        this.converterName = converterName;
        this.supportedFormats = supportedFormats;
    }

    @Override
    public String[] getSupportedFormats() {
        return supportedFormats;
    }

    @Override
    public boolean supportsFormat(String format) {
        for (String supported : supportedFormats) {
            if (supported.equalsIgnoreCase(format)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String getConverterName() {
        return converterName;
    }


     // Conversion logic - reads source image and throws IO Exception if read fails

    protected BufferedImage readImage(File sourceFile) throws IOException {
        BufferedImage image = ImageIO.read(sourceFile);
        if (image == null) {
            throw new IOException("Failed to read image: " + sourceFile.getName());
        }
        return image;
    }

    // Conversion logic - writes output image and throws IO Exception if write fails

    protected void writeImage(BufferedImage image, File outputFile, String format) throws IOException {
        boolean success = ImageIO.write(image, format.toLowerCase(), outputFile);
        if (!success) {
            throw new IOException("Failed to write image in format: " + format);
        }
    }

    //Helper method to get filename without extension
    protected String getFileNameWithoutExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return (lastDot > 0) ? name.substring(0, lastDot) : name;
    }
}