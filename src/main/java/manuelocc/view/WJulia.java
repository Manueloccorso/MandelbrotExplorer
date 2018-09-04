package manuelocc.view;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import manuelocc.messages.mtv.MTVMessageJuliaImage;

import java.io.IOException;

public class WJulia {

    GUI mainGUI;
    Stage stage;

    @FXML
    public ImageView imageBoxJulia;

    public WJulia(GUI gui, MTVMessageJuliaImage message) {
        this.mainGUI = gui;
        //Load FXML
        Node registrationRoot;
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getClassLoader().getResource("windows/w_julia.fxml"));
        stage = new Stage();
        fxmlLoader.setControllerFactory(param -> this);
        try {
            registrationRoot = fxmlLoader.load();
        } catch (IOException ex) {
            //Should Never Happen : FXML loaded statically
            return;
        }
        stage.setTitle("Julia's Set for pixel");
        stage.setScene(new Scene((Parent)registrationRoot));
        stage.setMaximized(true);
        stage.show();

        //Controller setting
        imageBoxJulia.setFitWidth(message.juliaImage.getWidth());
        imageBoxJulia.setFitHeight(message.juliaImage.getHeight());
        imageBoxJulia.setImage(SwingFXUtils.toFXImage(message.juliaImage, null));
    }
}
