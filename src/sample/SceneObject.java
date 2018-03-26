package sample;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

/**
 * Created by Andrew on 24.08.2016.
 * This os used to represent any object that appears on the scene.
 */
public class SceneObject{
    //TODO: Finish this and make some comments
    private Rectangle Bounds;
    private int Area;
    private Long ID;

    private ArrayList<String> passedLines;

    private long BornTime = 0;

    public SceneObject( Rectangle bounds, int area){
        Area = area;
        Bounds = bounds;
        BornTime = new Date().getTime();

        Random r = new Random();
        ID = r.nextLong();
        passedLines = new ArrayList<>();
    }

    public Rectangle getBounds() {
        return Bounds;
    }

    public Long getID(){
        return ID;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        if (obj == this) return true;
        if (!(obj instanceof SceneObject))return false;

        //if ID of objects are equal - objects are equal

        SceneObject testObj = (SceneObject) obj;
        if (testObj.getID() == this.getID()) return true;

        return false;
    }

}

