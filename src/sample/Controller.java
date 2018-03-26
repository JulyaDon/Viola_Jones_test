package sample;

import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.net.URL;
import java.util.*;
import java.util.List;

public class Controller  implements Initializable{
    @FXML
    private ImageView finalImage;

    private ImageProcessor imageProc = new ImageProcessor();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        BufferedImage BufImg = imageProc.getNewBuffered();
        List<SceneObject> objects;
        objects = imageProc.getObjectList();
        Graphics g = BufImg.createGraphics();
        g.setColor(Color.RED);
        for (SceneObject object : objects) {
            Rectangle bounds = object.getBounds();
            g.drawRect(bounds.x,bounds.y,bounds.width,bounds.height);
        }

        g.dispose();

        finalImage.setImage(SwingFXUtils.toFXImage(BufImg,null));
        //comment for commit
    }
}
