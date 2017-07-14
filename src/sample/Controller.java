package sample;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.io.File;
import java.net.URL;
import java.util.ResourceBundle;

public class Controller  implements Initializable{

    @FXML
    private ImageView imageView;
    @FXML
    private ImageView finalImage;

    private ImageProcessor imageProc = new ImageProcessor();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        File file = new File("src/sample/Box13.jpg");
        Image image = new Image(file.toURI().toString());
        imageView.setImage(image);

        finalImage.setImage(imageProc.getSecondImage());
    }
}
