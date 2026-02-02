package Project_imagemanager.converter;

import java.io.File;
import java.io.IOException;

public interface ImageConverter {

    File convert(File sourceFile, String targetFormat) throws IOException;

    String[] getSupportedFormats();

    boolean supportsFormat(String format);

    String getConverterName();
}