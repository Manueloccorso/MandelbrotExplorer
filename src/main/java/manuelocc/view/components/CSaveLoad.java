package manuelocc.view.components;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manuelocc.messages.vtc.VTCMessageLoad;
import manuelocc.messages.vtc.VTCMessageSave;
import manuelocc.view.GUI;

import java.io.File;
import java.io.IOException;

/**
 * Component to save / load an image
 */
public class CSaveLoad extends AnchorPane {

    public Button sb_saveBtn;
    public CheckBox sb_zip;
    public CheckBox sb_locinfo;
    public CheckBox sb_multic;
    public TextField sb_path_tb;
    public Button sb_search_dir;
    public TextField sb_filetoload_path;

    private GUI gui;
    private Stage primaryStage;
    //ROOT of FXML
    private Node root;

    public CSaveLoad(GUI gui, Stage primaryStage){
        this.gui = gui;
        this.primaryStage = primaryStage;

        //Load FXML
        FXMLLoader fxmlLoader =
                new FXMLLoader(getClass().getClassLoader()
                        .getResource("components/c_save.fxml"));
        fxmlLoader.setControllerFactory(param -> this);
        try {
            root = (Node) fxmlLoader.load();
        } catch (IOException ex) {
            //Should Never Happen : FXML loaded statically
        }
        getChildren().add(root);

    }


    public void saveAction(ActionEvent actionEvent) {
        gui.getController().update(new VTCMessageSave(
                            sb_path_tb.getText(),
                            sb_multic.isSelected(),
                            sb_locinfo.isSelected(),
                            sb_zip.isSelected()
                ));
    }

    public void searchAction(ActionEvent actionEvent) {

        DirectoryChooser chooser = new DirectoryChooser();
        chooser.setTitle("Choose Path for saving picture");
        File defaultDirectory = new File("c:/");
        chooser.setInitialDirectory(defaultDirectory);
        File selectedDirectory = chooser.showDialog(primaryStage);
        if (selectedDirectory != null) {
            sb_path_tb.setText(selectedDirectory.getPath().toString());
        }
    }

    public void OnSearchFileToLoad(ActionEvent actionEvent) {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Load Settings from disk");
        String path = fileChooser.showOpenDialog(primaryStage).getPath();
        sb_filetoload_path.setText(path);
    }

    public void onLoadFromDisk(ActionEvent actionEvent) {
        gui.getController().update(new VTCMessageLoad(sb_filetoload_path.getText()));
    }
}
