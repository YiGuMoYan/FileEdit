package top.yigumoyan;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Main extends Application {

    private String fileUrl = "";

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER);
        vbox.setSpacing(10);
        vbox.setStyle("-fx-padding: 10;");

        HBox hBoxTop = new HBox();
        hBoxTop.setSpacing(10);
        Button loadButton = new Button(Constant.LOAD);
        loadButton.setPrefWidth(Constant.BUTTON_WIDTH);
        loadButton.setPrefHeight(Constant.BUTTON_HEIGHT);
        Button saveButton = new Button(Constant.SAVE);
        saveButton.setPrefWidth(Constant.BUTTON_WIDTH);
        saveButton.setPrefHeight(Constant.BUTTON_HEIGHT);
        hBoxTop.getChildren().addAll(loadButton, saveButton);


        TextArea textArea = new TextArea();
        textArea.setPrefWidth(Constant.TEXT_AREA_WIDTH);
        textArea.setPrefHeight(Constant.TEXT_AREA_HEIGHT);

        Label label = new Label(String.format("%s%d", Constant.LEN_LABEL, textArea.getText().length()));
        HBox hBoxBottom = new HBox();
        hBoxBottom.getChildren().addAll(label);

        textArea.textProperty().addListener(event -> {
            label.setText(String.format("%s%d", Constant.LEN_LABEL, textArea.getText().length()));
        });

        saveButton.setOnAction(event -> saveToFile(textArea.getText()));
        loadButton.setOnAction(event -> textArea.setText(loadFile()));

        vbox.getChildren().addAll(hBoxTop, textArea, hBoxBottom);
        VBox.setVgrow(textArea, Priority.ALWAYS);

        Scene scene = new Scene(vbox, Constant.STAGE_WIDTH, Constant.STAGE_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void saveToFile(String content) {
        if (!"".equals(this.fileUrl)) {
            try (FileWriter writer = new FileWriter(this.fileUrl)) {
                writer.write(content);
            } catch (IOException e) {
                System.out.println(Constant.SAVE_FAIL);
            }
            return;
        }

        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(Constant.TXT_EXTENSION_FILTER_front, Constant.TXT_EXTENSION_FILTER_end));
        File file = fileChooser.showSaveDialog(null);

        if (file != null) {
            this.fileUrl = file.getAbsolutePath();
            try (FileWriter writer = new FileWriter(file)) {
                writer.write(content);
            } catch (IOException e) {
                System.out.println(Constant.SAVE_FAIL);
            }
        }
    }

    private String loadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter(Constant.TXT_EXTENSION_FILTER_front, Constant.TXT_EXTENSION_FILTER_end));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            this.fileUrl = file.getAbsolutePath();

            try {
                Scanner scanner = new Scanner(new File(this.fileUrl));
                StringBuilder stringBuilder = new StringBuilder();
                while (scanner.hasNext()) {
                    stringBuilder.append(scanner.nextLine());
                }
                return stringBuilder.toString();
            } catch (Exception e) {
                System.out.println(Constant.SAVE_FAIL);
            }

        }

        return "";
    }
}