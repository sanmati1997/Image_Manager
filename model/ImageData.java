package Project_imagemanager.model;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;

// ImageData class - Only basic information
public class ImageData {

    // Basic fields
    private File file;
    private String fileName;
    private String filePath;
    private long fileSize;   // KB
    private int width;
    private int height;
    private String format;
    private BufferedImage bufferedImage;

    // Constructor
    public ImageData(File file) throws IOException {
        this.file = file;
        this.fileName = file.getName();
        this.filePath = file.getAbsolutePath();
        this.fileSize = file.length() / 1024;

        loadImageProperties();
    }

    // Loads basic image properties
    private void loadImageProperties() throws IOException {
        try {
            bufferedImage = ImageIO.read(file);

            if (bufferedImage != null) {
                this.width = bufferedImage.getWidth();
                this.height = bufferedImage.getHeight();
            }

            ImageInputStream iis = ImageIO.createImageInputStream(file);
            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);

            if (readers.hasNext()) {
                ImageReader reader = readers.next();
                this.format = reader.getFormatName().toUpperCase();
                reader.dispose();
            } else {
                this.format = "UNKNOWN";
            }

            iis.close();

        } catch (IOException e) {
            throw new IOException("Failed to load image properties: " + e.getMessage(), e);
        }
    }

    // Getters
    public File getFile() { return file; }
    public String getFileName() { return fileName; }
    public String getFilePath() { return filePath; }
    public long getFileSize() { return fileSize; }
    public int getWidth() { return width; }
    public int getHeight() { return height; }
    public String getFormat() { return format; }
    public BufferedImage getBufferedImage() { return bufferedImage; }

    // Helper methods
    public String getFormattedFileSize() {
        if (fileSize < 1024) return fileSize + " KB";
        return String.format("%.2f MB", fileSize / 1024.0);
    }

    public String getDimensionsString() {
        return width + " x " + height + " pixels";
    }

    public boolean isValid() {
        return bufferedImage != null && width > 0 && height > 0;
    }

    @Override
    public String toString() {
        return "ImageData{" +
                "fileName='" + fileName + '\'' +
                ", dimensions=" + width + "x" + height +
                ", format='" + format + '\'' +
                ", size=" + fileSize + "KB" +
                '}';
    }
}
