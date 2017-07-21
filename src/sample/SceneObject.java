package sample;

import javax.lang.model.element.Name;
import java.awt.*;

/**
 * Created by July on 20.07.2017.
 */
public class SceneObject {
    Rectangle rect = new Rectangle();
    String name = "";

    SceneObject(Rectangle rectangle, String Name){
        this.rect = rectangle;
        this.name = Name;
    }

    public String  getName(){
        return name;
    }

    public void setName(String Name){
        this.name = Name;
    }

    public Rectangle getRect(){
        return rect;
    }
}
