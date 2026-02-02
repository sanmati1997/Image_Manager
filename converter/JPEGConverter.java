package Project_imagemanager.converter;

import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

 // JPEG Converter
public class JPEGConverter extends AbstractImageConverter {

    private float quality = 0.95f; // 95% quality (high quality)

    public JPEGConverter() {
        super("JPEG Converter", new String[]{"JPEG", "JPG"});
    }

    @Override
    public File convert(File sourceFile, String targetFormat) throws IOException {
        if (!supportsFormat(targetFormat)) {
            throw new IllegalArgumentException("Unsupported format: " + targetFormat);
        }

        try {
            BufferedImage image = readImage(sourceFile);

            // JPEG doesn't support transparency, convert to RGB
            BufferedImage rgbImage = new BufferedImage(
                    image.getWidth(),
                    image.getHeight(),
                    BufferedImage.TYPE_INT_RGB
            );

            java.awt.Graphics2D g2d = rgbImage.createGraphics();

            // Quality rendering hints
            g2d.setRenderingHint(
                    java.awt.RenderingHints.KEY_INTERPOLATION,
                    java.awt.RenderingHints.VALUE_INTERPOLATION_BICUBIC
            );
            g2d.setRenderingHint(
                    java.awt.RenderingHints.KEY_RENDERING,
                    java.awt.RenderingHints.VALUE_RENDER_QUALITY
            );
            g2d.setRenderingHint(
                    java.awt.RenderingHints.KEY_ANTIALIASING,
                    java.awt.RenderingHints.VALUE_ANTIALIAS_ON
            );

            g2d.setColor(java.awt.Color.WHITE);
            g2d.fillRect(0, 0, image.getWidth(), image.getHeight());
            g2d.drawImage(image, 0, 0, null);
            g2d.dispose();

            String outputPath = sourceFile.getParent() + File.separator +
                    getFileNameWithoutExtension(sourceFile) + "_converted.jpg";
            File outputFile = new File(outputPath);

            // Write
            writeHighQualityJPEG(rgbImage, outputFile);

            return outputFile;

        } catch (IOException e) {
            throw new IOException("JPEG conversion failed: " + e.getMessage(), e);
        }
    }

    private void writeHighQualityJPEG(BufferedImage image, File outputFile) throws IOException {
        // Get JPEG writer
        Iterator<ImageWriter> writers = ImageIO.getImageWritersByFormatName("jpg");
        if (!writers.hasNext()) {
            throw new IOException("No JPEG writer found");
        }

        ImageWriter writer = writers.next();

        try (ImageOutputStream ios = ImageIO.createImageOutputStream(outputFile)) {
            writer.setOutput(ios);

            // Set high quality compression
            ImageWriteParam param = writer.getDefaultWriteParam();
            if (param.canWriteCompressed()) {
                param.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
                param.setCompressionQuality(quality); // 0.95 = 95% quality
            }

            // Write image with high quality
            writer.write(null, new IIOImage(image, null, null), param);

        } finally {
            writer.dispose();
        }
    }

     // Sets the JPEG quality level
    public void setQuality(float quality) {
        this.quality = Math.max(0.0f, Math.min(1.0f, quality));
    }
}