package Project_imagemanager.converter;

/**
 * ImageConverterFactory - Demonstrates FACTORY PATTERN
 * Creates appropriate ImageConverter based on target format and centralizes object creation logic
 * Encapsulates object creation, allows easy addition of new converters
 */
public class ImageConverterFactory {

    // Gets appropriate converter for the specified format and throws exception if format is not supported

    public ImageConverter getConverter(String format) throws IllegalArgumentException {

        if (format == null || format.isEmpty()) {
            throw new IllegalArgumentException("Format cannot be null or empty");
        }

        // Factory logic - decides which concrete class to instantiate
        switch (format.toUpperCase()) {
            case "PNG":
                return new PNGConverter();

            case "JPEG":
            case "JPG":
                return new JPEGConverter();

            case "GIF":
                return new GIFConverter();

            case "BMP":
                return new BMPConverter();

            default:
                throw new IllegalArgumentException(
                        "Unsupported format: " + format +
                                ". Supported formats: PNG, JPEG, GIF, BMP"
                );
        }
    }

    public ImageConverter[] getAllConverters() {
        return new ImageConverter[] {
                new PNGConverter(),
                new JPEGConverter(),
                new GIFConverter(),
                new BMPConverter()
        };
    }

     // Checks if a format is supported
    public boolean isFormatSupported(String format) {
        try {
            getConverter(format);
            return true;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

     // Gets list of all supported formats

    public String[] getSupportedFormats() {
        return new String[] {"PNG", "JPEG", "JPG", "GIF", "BMP"};
    }
}