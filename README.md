# Image Manager

A JavaFX desktop application for managing, processing, and converting images with an intuitive graphical user interface.

![Java](https://img.shields.io/badge/Java-17%2B-blue)
![JavaFX](https://img.shields.io/badge/JavaFX-17%2B-green)
![License](https://img.shields.io/badge/License-MIT-yellow)

## ✨ Features

### Image Processing
- **Upload & Manage** — Import multiple images and view them as thumbnails
- **Image Properties** — View detailed metadata including dimensions, format, and file size

### Filters
Apply various filters to your images:
| Filter | Description |
|--------|-------------|
| **Grayscale** | Convert images to black and white |
| **Sepia** | Apply a warm, vintage tone |
| **Blur** | Add a soft blur effect |
| **Brightness** | Adjust image brightness levels |
| **Invert** | Invert all colors in the image |

### Format Conversion
Convert images between multiple formats:
- **PNG** — Lossless compression with transparency support
- **JPEG** — Lossy compression for smaller file sizes
- **BMP** — Uncompressed bitmap format
- **GIF** — Animated/static images with limited colors

## 📁 Project Structure

```
Project_imagemanager 2/
├── ImageManagerApp.java          # Main application entry point (JavaFX UI)
├── config/
│   └── ConfigurationManager.java # Singleton for app settings
├── model/
│   └── ImageData.java            # Image data model with metadata
├── filter/
│   ├── ImageFilter.java          # Filter interface
│   ├── GrayscaleFilter.java      # Grayscale conversion
│   ├── SepiaFilter.java          # Sepia tone effect
│   ├── BlurFilter.java           # Blur effect
│   ├── BrightnessFilter.java     # Brightness adjustment
│   └── InvertFilter.java         # Color inversion
└── converter/
    ├── ImageConverter.java       # Converter interface
    ├── AbstractImageConverter.java # Base converter implementation
    ├── ImageConverterFactory.java  # Factory for creating converters
    ├── PNGConverter.java         # PNG format converter
    ├── JPEGConverter.java        # JPEG format converter
    ├── BMPConverter.java         # BMP format converter
    └── GIFConverter.java         # GIF format converter
```

## 🚀 Getting Started

### Prerequisites
- **Java 17** or higher
- **JavaFX SDK 17** or higher

### Running the Application

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd Project_imagemanager\ Test
   ```

2. **Compile the project**
   ```bash
   javac --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.swing -d out Project_imagemanager\ 2/*.java Project_imagemanager\ 2/**/*.java
   ```

3. **Run the application**
   ```bash
   java --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.swing -cp out Project_imagemanager.ImageManagerApp
   ```

### Using IntelliJ IDEA
1. Open the project in IntelliJ IDEA
2. Configure JavaFX library in Project Structure
3. Add VM options in Run Configuration:
   ```
   --module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.swing
   ```
4. Run `ImageManagerApp.java`

## 📖 Usage

1. **Upload Images** — Click "Upload Images" to select one or more image files
2. **Select Image** — Click on a thumbnail to view its properties
3. **Process Image** — Click "Process Image" to open the processing dialog
4. **Apply Filters** — Select a filter and preview the result
5. **Convert Format** — Choose an output format and save the processed image
6. **Clear All** — Remove all loaded images from the workspace

## 🏗️ Architecture

### Design Patterns Used
- **Singleton** — `ConfigurationManager` for application-wide settings
- **Factory** — `ImageConverterFactory` for creating format converters
- **Strategy** — `ImageFilter` interface for interchangeable filter algorithms

### Key Components
- **ImageManagerApp** — Main JavaFX application with UI logic
- **ImageData** — Model class encapsulating image metadata and pixel data
- **ImageFilter** — Interface defining the contract for all filters
- **ImageConverter** — Interface for format conversion operations

## 🔧 Configuration

The application stores configuration in `~/.imagemanager.properties`:

| Property | Default | Description |
|----------|---------|-------------|
| `thumbnail.size` | 100 | Thumbnail display size in pixels |
| `default.output.format` | PNG | Default format for saved images |

## 📝 License

This project is licensed under the MIT License.

## 🤝 Contributing

Contributions are welcome! Please feel free to submit a Pull Request.

---

**Made with ❤️ using JavaFX**
