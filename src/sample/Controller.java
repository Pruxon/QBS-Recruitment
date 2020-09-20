package sample;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;

import java.io.IOException;


public class Controller {

    private ArgumentsController argumentsController;
    private FilesFinder filesFinder;
    private InFileFinder inFileFinder;
    @FXML private TextField path;
    @FXML private TextField extension;
    @FXML private TextField bytes;
    @FXML private TextField replacingBytes;

    @FXML public void initialize() {
        filesFinder = new FilesFinder();
        inFileFinder = new InFileFinder();
    }

    @FXML protected void createParams() throws IOException {
        String[] params = new String[4];
        if (!path.getText().equals("")) params[0] = path.getText();
        if (!extension.getText().equals("")) params[1] = extension.getText();
        if (!bytes.getText().equals("")) params[2] = bytes.getText();
        if (!replacingBytes.getText().equals("")) params[3] = replacingBytes.getText();

        if(params[0] != null && params[1] != null && params[2] != null && params[3] != null) {
            argumentsController = new ArgumentsController(params[0], params[1], params[2].getBytes(), params[3].getBytes());

            filesFinder.resetFoundFiles();
            findFiles(argumentsController.getPath(), argumentsController.getFileExtension());
            for (Object file : filesFinder.getFoundFiles()
            ) {
                inFileFinder.changeFile(file.toString(), argumentsController.getBytes(), argumentsController.getReplacingBytes());
            }
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            createInformationDialog(alert);
        } else {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            createErrorDialog(alert, params);

        }
    }

    @FXML protected void listChangedFiles() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Changed Files Information");
        alert.setHeaderText("Files previously changed:");

        String alertContent = createChangedFilesString();
        alert.setContentText(alertContent);
        alert.showAndWait();
    }

    @FXML protected void countChangedFiles() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Changed Files Information");
        alert.setHeaderText("Number of changed files:");

        int filesChanged = filesFinder.getFoundFiles().size();
        alert.setContentText(String.valueOf(filesChanged));

        alert.showAndWait();
    }

    private void createErrorDialog(Alert alert, String[] params) {
        alert.setTitle("Error Dialog");
        alert.setHeaderText("Missing aruments:");
        String alertMessage = "";
        if (params[0] == null) {
            alertMessage += "Path to folder\n";
        }
        if (params[1] == null) {
            alertMessage += "File extension\n";
        }
        if (params[2] == null) {
            alertMessage += "Bytes to be replaced\n";
        }
        if (params[3] == null) {
            alertMessage += "Bytes to replace\n";
        }
        alert.setContentText(alertMessage);
        alert.showAndWait();
    }

    private String createChangedFilesString(){
        String toReturn = "";

        if (filesFinder.getFoundFiles() != null) {
            for (Object file: filesFinder.getFoundFiles()
                 ) {
                toReturn += file.toString();
                toReturn += "\n";
            }
        }

        return toReturn;
    }

    private void createInformationDialog(Alert alert){
        alert.setTitle("Information Dialog");
        alert.setHeaderText(null);
        alert.setContentText("Files in given folder have been changed!");
        alert.showAndWait();
    }

    private void findFiles(String path, String extension) {
        filesFinder.findFiles(path, extension);
    }
}
