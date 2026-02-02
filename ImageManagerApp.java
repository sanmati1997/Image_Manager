package Project_imagemanager;

import Project_imagemanager.config.ConfigurationManager;
import Project_imagemanager.model.ImageData;
import Project_imagemanager.filter.*;
import Project_imagemanager.converter.ImageConverter;
import Project_imagemanager.converter.ImageConverterFactory;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class ImageManagerApp extends Application {

    private Stage primaryStage;
    private FlowPane thumbnailPane;
    private VBox propertiesPane;
    private List<ImageData> imageDataList;
    private ImageData selectedImage;
    private ConfigurationManager configManager;

    @Override
    public void start(Stage primaryStage) {
        this.primaryStage = primaryStage;
        this.imageDataList = new ArrayList<>();
        this.configManager = ConfigurationManager.getInstance();

        primaryStage.setTitle("Image Management Tool");

        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        ScrollPane scrollPane = new ScrollPane();
        thumbnailPane = new FlowPane();
        thumbnailPane.setHgap(10);
        thumbnailPane.setVgap(10);
        thumbnailPane.setPadding(new Insets(10));
        scrollPane.setContent(thumbnailPane);
        scrollPane.setFitToWidth(true);

        root.setCenter(scrollPane);

        ScrollPane propertiesScroll = new ScrollPane();
        propertiesPane = new VBox(10);
        propertiesPane.setPadding(new Insets(10));

        Label propertiesLabel = new Label("Image Properties");
        propertiesLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        propertiesPane.getChildren().add(propertiesLabel);

        propertiesScroll.setContent(propertiesPane);
        propertiesScroll.setFitToWidth(true);
        propertiesScroll.setPrefWidth(300);
        propertiesScroll.setStyle("-fx-border-color: gray; -fx-border-width: 1;");

        root.setRight(propertiesScroll);

        HBox buttonBox = createButtonBox();
        root.setBottom(buttonBox);

        Scene scene = new Scene(root, 1200, 700);
        primaryStage.setScene(scene);
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(650);
        primaryStage.show();
    }

    private HBox createButtonBox() {
        HBox buttonBox = new HBox(15);
        buttonBox.setPadding(new Insets(15));
        buttonBox.setAlignment(Pos.CENTER);
        buttonBox.setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #cccccc; -fx-border-width: 1 0 0 0;");

        Button uploadBtn = new Button("Upload Images");
        uploadBtn.setPrefWidth(180);
        uploadBtn.setPrefHeight(40);
        uploadBtn.setStyle("-fx-font-size: 14; -fx-background-color: #2196F3; -fx-text-fill: white;");
        uploadBtn.setOnAction(e -> handleUploadImages());

        Button processBtn = new Button("Process Image");
        processBtn.setPrefWidth(180);
        processBtn.setPrefHeight(40);
        processBtn.setStyle("-fx-font-size: 14; -fx-background-color: #4CAF50; -fx-text-fill: white;");
        processBtn.setOnAction(e -> handleProcessImage());

        Button clearBtn = new Button("Clear All");
        clearBtn.setPrefWidth(180);
        clearBtn.setPrefHeight(40);
        clearBtn.setStyle("-fx-font-size: 14; -fx-background-color: #F44336; -fx-text-fill: white;");
        clearBtn.setOnAction(e -> handleClearAll());

        buttonBox.getChildren().addAll(uploadBtn, processBtn, clearBtn);
        return buttonBox;
    }

    private void handleUploadImages() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Select Image Files");

        FileChooser.ExtensionFilter imageFilter = new FileChooser.ExtensionFilter(
                "Image Files", "*.jpg", "*.jpeg", "*.png", "*.gif", "*.bmp"
        );
        fileChooser.getExtensionFilters().add(imageFilter);

        try {
            List<File> selectedFiles = fileChooser.showOpenMultipleDialog(primaryStage);

            if (selectedFiles != null && !selectedFiles.isEmpty()) {
                for (File file : selectedFiles) {
                    loadImage(file);
                }
                showAlert(Alert.AlertType.INFORMATION, "Upload Successful",
                        selectedFiles.size() + " image(s) uploaded successfully!");
            }
        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Upload Error",
                    "Failed to upload images:\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    private void loadImage(File file) {
        try {
            ImageData imageData = new ImageData(file);

            if (!imageData.isValid()) {
                showAlert(Alert.AlertType.WARNING, "Invalid Image",
                        "Cannot load: " + file.getName());
                return;
            }

            imageDataList.add(imageData);

            int thumbSize = configManager.getThumbnailSize();
            Image thumbnail = new Image(file.toURI().toString(), thumbSize, thumbSize, true, true);
            ImageView thumbnailView = new ImageView(thumbnail);
            thumbnailView.setFitWidth(thumbSize);
            thumbnailView.setFitHeight(thumbSize);

            VBox thumbnailContainer = new VBox(5);
            thumbnailContainer.setAlignment(Pos.CENTER);
            thumbnailContainer.setStyle("-fx-border-color: lightgray; -fx-border-width: 2; -fx-padding: 5;");

            Label nameLabel = new Label(file.getName());
            nameLabel.setStyle("-fx-font-size: 10;");
            nameLabel.setMaxWidth(100);
            nameLabel.setWrapText(true);

            thumbnailContainer.getChildren().addAll(thumbnailView, nameLabel);

            thumbnailContainer.setOnMouseClicked(e -> {
                selectedImage = imageData;
                displayImageProperties(imageData);

                thumbnailPane.getChildren().forEach(node ->
                        node.setStyle("-fx-border-color: lightgray; -fx-border-width: 2; -fx-padding: 5;")
                );

                thumbnailContainer.setStyle("-fx-border-color: #2196F3; -fx-border-width: 3; -fx-padding: 5;");
            });

            thumbnailPane.getChildren().add(thumbnailContainer);

        } catch (Exception e) {
            showAlert(Alert.AlertType.ERROR, "Load Error",
                    "Failed to load image: " + file.getName() + "\n" + e.getMessage());
            e.printStackTrace();
        }
    }

    // FINAL BASIC-ONLY PROPERTIES PANEL
    private void displayImageProperties(ImageData imageData) {
        propertiesPane.getChildren().clear();

        Label titleLabel = new Label("Image Properties");
        titleLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 16; -fx-text-fill: #1565C0;");

        Label basicLabel = new Label("━━━ Basic Information ━━━");
        basicLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: #1976D2; -fx-padding: 10 0 5 0;");

        Label nameLabel = new Label("Name: " + imageData.getFileName());
        Label formatLabel = new Label("Format: " + imageData.getFormat());
        Label sizeLabel = new Label("File Size: " + imageData.getFormattedFileSize());
        Label dimensionsLabel = new Label("Dimensions: " + imageData.getDimensionsString());

        Label pathLabel = new Label("Path: " + imageData.getFilePath());
        pathLabel.setWrapText(true);
        pathLabel.setMaxWidth(280);

        propertiesPane.getChildren().addAll(
                titleLabel,
                new Separator(),
                basicLabel,
                nameLabel,
                formatLabel,
                sizeLabel,
                dimensionsLabel,
                pathLabel
        );
    }

    private void handleProcessImage() {
        if (selectedImage == null) {
            showAlert(Alert.AlertType.WARNING, "No Image Selected",
                    "Please select an image first.");
            return;
        }
        showProcessingDialog();
    }

    private void showProcessingDialog() {
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Process Image");
        dialog.setHeaderText("Process: " + selectedImage.getFileName());

        VBox content = new VBox(20);
        content.setPadding(new Insets(20));
        content.setPrefWidth(400);

        Label filterLabel = new Label("Select Filter (Optional):");
        filterLabel.setStyle("-fx-font-weight: bold;");

        ToggleGroup filterGroup = new ToggleGroup();

        RadioButton noneRadio = new RadioButton("None (Keep Original)");
        noneRadio.setToggleGroup(filterGroup);
        noneRadio.setSelected(true);

        RadioButton grayscaleRadio = new RadioButton("Grayscale");
        grayscaleRadio.setToggleGroup(filterGroup);

        RadioButton sepiaRadio = new RadioButton("Sepia Tone");
        sepiaRadio.setToggleGroup(filterGroup);

        RadioButton brightnessRadio = new RadioButton("Brightness");
        brightnessRadio.setToggleGroup(filterGroup);

        RadioButton invertRadio = new RadioButton("Invert Colors");
        invertRadio.setToggleGroup(filterGroup);

        RadioButton blurRadio = new RadioButton("Blur");
        blurRadio.setToggleGroup(filterGroup);

        VBox filterOptions = new VBox(8, noneRadio, grayscaleRadio, sepiaRadio,
                brightnessRadio, invertRadio, blurRadio);

        Label formatLabel = new Label("Output Format:");
        formatLabel.setStyle("-fx-font-weight: bold;");

        ComboBox<String> formatCombo = new ComboBox<>();
        formatCombo.getItems().add("Keep Original");

        ImageConverterFactory factory = new ImageConverterFactory();
        for (String f : factory.getSupportedFormats()) {
            if (!f.equalsIgnoreCase("JPG")) {
                formatCombo.getItems().add(f);
            }
        }

        formatCombo.setValue(configManager.getDefaultOutputFormat());

        content.getChildren().addAll(
                filterLabel, filterOptions,
                new Separator(),
                formatLabel, formatCombo
        );

        dialog.getDialogPane().setContent(content);

        ButtonType processButton = new ButtonType("Process & Save", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(processButton, ButtonType.CANCEL);

        dialog.showAndWait().ifPresent(response -> {
            if (response == processButton) {
                try {
                    RadioButton selectedRadio = (RadioButton) filterGroup.getSelectedToggle();
                    String filterName = selectedRadio.getText();

                    BufferedImage processedImage = selectedImage.getBufferedImage();

                    if (!filterName.equals("None (Keep Original)")) {
                        ImageFilter filter = getFilterByName(filterName);
                        if (filter != null) {
                            processedImage = filter.apply(processedImage);
                        }
                    }

                    String format = formatCombo.getValue();
                    if (format.equals("Keep Original")) {
                        format = selectedImage.getFormat();
                    }

                    saveProcessedImage(processedImage, format, filterName);

                } catch (Exception ex) {
                    showAlert(Alert.AlertType.ERROR, "Processing Error",
                            ex.getMessage());
                }
            }
        });
    }

    private ImageFilter getFilterByName(String filterName) {
        switch (filterName) {
            case "Grayscale": return new GrayscaleFilter();
            case "Sepia Tone": return new SepiaFilter();
            case "Brightness": return new BrightnessFilter();
            case "Invert Colors": return new InvertFilter();
            case "Blur": return new BlurFilter();
        }
        return null;
    }

    private void saveProcessedImage(BufferedImage image, String format, String filterApplied) throws IOException {
        File tempFile = null;

        try {
            String baseName = getFileNameWithoutExtension(selectedImage.getFile());

            tempFile = new File(selectedImage.getFile().getParent(), baseName + "_temp.png");
            ImageIO.write(image, "png", tempFile);

            ImageConverterFactory factory = new ImageConverterFactory();
            ImageConverter converter = factory.getConverter(format);

            File convertedFile = converter.convert(tempFile, format);

            File finalFile;
            if (!filterApplied.equals("None (Keep Original)")) {
                String suffix = "_" + filterApplied.toLowerCase().replace(" ", "_");

                String extension = format.toLowerCase();
                if (extension.equals("jpeg")) extension = "jpg";

                finalFile = new File(convertedFile.getParent(),
                        baseName + suffix + "." + extension);

                convertedFile.renameTo(finalFile);
            } else {
                finalFile = convertedFile;
            }

            showAlert(Alert.AlertType.INFORMATION, "Processing Complete",
                    "Saved as: " + finalFile.getName() +
                            "\nLocation: " + finalFile.getParent());

        } finally {
            if (tempFile != null && tempFile.exists()) tempFile.delete();
        }
    }

    private String getFileNameWithoutExtension(File file) {
        String name = file.getName();
        int lastDot = name.lastIndexOf('.');
        return (lastDot > 0) ? name.substring(0, lastDot) : name;
    }

    private void handleClearAll() {
        thumbnailPane.getChildren().clear();
        propertiesPane.getChildren().clear();
        imageDataList.clear();
        selectedImage = null;

        Label propertiesLabel = new Label("Image Properties");
        propertiesLabel.setStyle("-fx-font-weight: bold; -fx-font-size: 14;");
        propertiesPane.getChildren().add(propertiesLabel);

        showAlert(Alert.AlertType.INFORMATION, "Cleared",
                "All images removed from workspace.");
    }

    private void showAlert(Alert.AlertType type, String title, String msg) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(msg);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
