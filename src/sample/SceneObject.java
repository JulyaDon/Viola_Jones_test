package sample;

import java.awt.*;

/**
 * Created by July on 20.07.2017.
 */
public class SceneObject {
    private Rectangle rect = new Rectangle();
    private String name = "";

    SceneObject(Rectangle rectangle, String Name){
        this.rect = rectangle;
        this.name = Name;
    }

    public String  getName(){
        return name;
    }

    public Rectangle getRect(){
        return rect;
    }
}
