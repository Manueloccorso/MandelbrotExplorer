package manuelocc.view;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import manuelocc.controller.MainController;
import manuelocc.messages.Message;
import manuelocc.messages.mtv.MTVMessageJuliaImage;
import manuelocc.messages.mtv.MTVMessageImage;
import manuelocc.messages.vtc.*;
import manuelocc.model.ColourCretors.ColourCreator;
import manuelocc.model.ColourManager;
import manuelocc.utils.MessageDecoder;
import manuelocc.utils.Observer;
import manuelocc.view.components.CSaveLoad;

import java.awt.image.BufferedImage;
import java.net.URL;

public class GUI extends Application implements ViewInterface, MessageDecoder {

    //Controller
    private static MainController controller;

    //Root and Scene of the MainGUI
        private Parent root = null;
        private Stage stage;

        //EasyAccess to the FXML
        @FXML
        public ImageView canvas;
        @FXML
        public TextField tb_iter;
        @FXML
        public Label label_posR;
        @FXML
        public Label label_posI;
        @FXML
        public TextField tb_zoom;
        @FXML
        public CheckBox cb_julia;
        @FXML
        public ComboBox cb_colours;
        @FXML
        public HBox sb_container;


    //Message Log with info

    //Costants for the main layout:
        private final int CANVAS_WIDTH = 900;
        private final int CANVAS_HEIGHT = 900;

        /**
         * Init the GUI:
         *      Opens a registration window to connect to the Server.
         *      Creates the main stage.
         * @throws Exception Exception creating the window. Should never happen.
         */
        @Override
        public void start(Stage primaryStage) throws Exception{


            //Load the Main Window
            URL url  = getClass().getClassLoader().getResource( "main_gui.fxml" );
            if(url == null) throw new NullPointerException();
            root = FXMLLoader.load(url);

            //Create the main Window
            stage = primaryStage;
            stage.setTitle("Mandelbrot");
            stage.setScene(new Scene(root));
            stage.setMaximized(true);
            stage.setOnCloseRequest(event -> {
                System.exit(0);
            });
            stage.show();

            canvas = (ImageView )stage.getScene().lookup("#canvas");


            ObservableList<String> colourOptions = FXCollections.observableArrayList( "");
            for(ColourCreator  c : ColourManager.ColourCreators){
                colourOptions.addAll(c.toString());
            }
            cb_colours = (ComboBox) stage.getScene().lookup("#cb_colours");
            cb_colours.setItems(colourOptions);

            sb_container = (HBox) stage.getScene().lookup("#sb_container");
            CSaveLoad component_saver = new CSaveLoad(this, this.getStage());
            sb_container.getChildren().add(component_saver);


            //Init the GUI
            resetGUI();

            controller = new MainController(this);

            //Menù
            createMenu();
        }



        /**
         * Reset the GUI
         */
        void resetGUI(){
            canvas.setFitWidth(CANVAS_WIDTH);
            canvas.setFitHeight(CANVAS_HEIGHT);
        }


        //WINDOWS OPENING -----------------------------------------------------------------------------


        //CONTROLS-----------------------------------------------------------------------------------

        @Override
        public void refreshCanvas(BufferedImage image) {
            canvas.setImage(SwingFXUtils.toFXImage(image, null));
            canvas.setFitWidth(CANVAS_WIDTH);
            canvas.setFitHeight(CANVAS_HEIGHT);
            System.err.println("printed");
        }


        @Override
        public int getCanvasWidth() {
            return CANVAS_WIDTH;
        }

        @Override
        public int getCanvasHeight() {
            return CANVAS_HEIGHT;
        }


        //UPDATE --------------------------------------------------------------------------------

        @Override
        public void update(Message message) {
            message.acceptVisitor(this);
        }

        @Override
        public void visit(MTVMessageImage m) {
            refreshCanvas(m.getImage());
        }

        @Override
        public void visit(MTVMessageJuliaImage MTVMessageJuliaImage) {
            new WJulia(this, MTVMessageJuliaImage);
        }

    //------------------------------------ CLICKS & GUI EVENT ----------------------------------------------------------

        /**
         * The user clicked on the canvas, zoom and reset center
         * @param mouseEvent
         */
        public void onCanvasClick(MouseEvent mouseEvent) {
            System.out.println("x: " + mouseEvent.getX() + "; y: " + mouseEvent.getY() + ";");
            if (mouseEvent.getButton() == MouseButton.SECONDARY) {
                controller.update(new VTCMessageClick(mouseEvent.getX(), mouseEvent.getY(), VTCMessageClick.Mode.Right));
            }
            else {
                controller.update(new VTCMessageClick(mouseEvent.getX(), mouseEvent.getY(), VTCMessageClick.Mode.Left));
            }
        }

        public void onMouseOnCanvas(MouseEvent mouseEvent) {
            label_posR.setText(String.valueOf(controller.pixelsToReal(mouseEvent.getX())));
            label_posI.setText(String.valueOf(controller.pixelsToImag(mouseEvent.getY())));
        }


        public void onIterEnter(ActionEvent actionEvent) {
            int iter = Integer.parseInt(tb_iter.getText());
            controller.update(new VTCMessageIterations(iter));
        }


        public void onZoomEnter(ActionEvent actionEvent) {
            double zoom = Double.parseDouble(tb_zoom.getText());
            controller.update(new VTCMessageZoom(zoom));
        }

        public void juliaClicked(ActionEvent actionEvent) {
            controller.update(new VTCMessageJulia(cb_julia.isSelected()));
        }


        public void onColourSelect(ActionEvent actionEvent) {
            controller.update(new VTCMessageColourMode(cb_colours.getValue().toString()));
        }


        //----------------------------------------------- GUI UTILS --------------------------------------------------------

        /**
         * Creates the Menu bar
         */
        private void createMenu() {

        }








        // ----------------------------------- GETTERS AND SETTERS ------------------------------------------------

        public Stage getStage() {
            return stage;
        }


        public Observer<Message> getController() {
            return controller;
        }



        /**
        * Launch directly a GUI
        */
        public static void main(String [] args){
            launch();
        }



}
