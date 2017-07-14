package sample;

import com.sun.org.apache.xpath.internal.SourceTree;
import javafx.embed.swing.SwingFXUtils;

import javax.imageio.ImageIO;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
/**
 * Created by July on 10.07.2017.
 */
public class ImageProcessor {

    private BufferedImage newBuffered = new BufferedImage(640,480, BufferedImage.TYPE_INT_RGB);

    public ImageProcessor(){
        int[][] template = {{1, 1, 1, 1},
                            {1, 0, 0, 1},
                            {1, 0, 0, 1},
                            {1, 0, 0, 1},
                            {1, 1, 1, 1}};

        int[][] newImage = {{255, 255, 255, 255, 255, 255, 255, 255},
                            {255, 255, 255, 255, 255, 255, 255, 255},
                            {255, 255, 255, 255, 255, 255, 255, 255},
                            {255, 255, 255, 0,   0,   255, 255, 255},
                            {255, 255, 255, 0,   0,   255, 255, 255},
                            {255, 255, 255, 0,   0,   255, 255, 255},
                            {255, 255, 255, 255, 255, 255, 255, 255},
                            {255, 255, 255, 255, 255, 255, 255, 255},
                            {255, 255, 255, 255, 255, 255, 255, 255}};
        int[][] Image;
        BufferedImage testImage = null;
        try {
            testImage = ImageIO.read(new File("src/sample/Box13.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image = toRGBArray(testImage);
        newBuffered = backToBuffered(Image, 640, 480);

        //backToBuffered(newImage);
        showTemplate(template);

        int[][] temp = makeTemplate(template, 3, 3);
//        for(int i = 0; i < temp.length; i++){
//            for(int j = 0; j < temp[0].length; j++){
//                System.out.print((temp[i][j]&0xFF) + "   ");
//            }
//            System.out.println();
//            System.out.println();
//        }

        int[] coordinates = catchSquare(template, Image);
        if(coordinates!=null) {
            for (int i = 0; i < coordinates.length; i++) {
                System.out.println(coordinates[i]);
            }
        }
        else System.out.println("No object was found");
    }

    private int[] catchSquare(int[][] template, int[][] image){
        double result = 0;
        int scaleX = 3;
        int scaleY = 3;
        int[] coordinates = new int[2];
        for(int x = 0; x < image.length-template.length; x+=10){
            for(int y = 0; y < image[0].length-template[0].length; y+=10){
                result = findingSquare(template, x, y, image);
                if(result >= 20){
                    System.out.println("They are similar!");
                    coordinates[0] = x;
                    coordinates[1] = y;
                    System.out.println("Percentage of similar: " + result);
                    return coordinates;
                }
            }
        }
        int[][] newTemplate = makeTemplate(template, scaleX, scaleY);
        int count = 0;
        System.out.println("Increased times: " + count++);
        if(newTemplate.length>=image.length || newTemplate[0].length >= image[0].length){
            System.out.println("Object not found!");
            return null;
        }
        catchSquare(newTemplate, image);
        scaleX++;
        scaleY++;

        return null;
    }

    private double findingSquare(int[][] template, int x, int y, int[][] image){
        double percent;
        int generalQuantity = template.length * template[0].length;
        int quantityOfSimilar = 0;
        int i, j, m, n;
        for(i = 0, m = x; i < template.length && m < template.length+x; i++, m++){
            for(j = 0, n = y; j < template[0].length && n < template[0].length+y; j++, n++){
                if((template[i][j] == 0 && image[m][n] == 0)){
                    quantityOfSimilar++;
                }
                else if ((template[i][j] == 1 && image[m][n] == 255)){
                    quantityOfSimilar++;
                }
            }
        }
        System.out.println("Similar: " + quantityOfSimilar);
        percent = ((double) quantityOfSimilar/(double) generalQuantity) * 100;

        return percent;
    }

    private int[][] makeTemplate(int[][] oldTemplate, int scaleX, int scaleY){
        BufferedImage before = backToBuffered(oldTemplate, oldTemplate.length, oldTemplate[0].length);
        int[][] newTemplate;
        int w = before.getWidth();
        int h = before.getHeight();
        BufferedImage after = new BufferedImage(w*scaleX, h*scaleY, BufferedImage.TYPE_INT_ARGB);
        AffineTransform at = new AffineTransform();
        at.scale(scaleX, scaleY);
        AffineTransformOp scaleOp =
                new AffineTransformOp(at, AffineTransformOp.TYPE_BILINEAR);
        after = scaleOp.filter(before, after);
        newTemplate = toRGBArray(after);
        return newTemplate;
    }
    
    private void showTemplate(int[][] template){
        for(int i = 0; i < template.length; i++){
            for(int j = 0; j < template[0].length; j++){
                System.out.print(template[i][j] + "   ");
            }
            System.out.println();
            System.out.println();
        }
    }
    ////////////////////////////////////////////////////////////////////////////////////////////////////////
    // Image processing
    public int[][] toRGBArray(BufferedImage image){

        int rgbArray[][] = new int[image.getWidth()][image.getHeight()];

        for (int i=0; i < image.getWidth(); i++)
            for (int j=0; j < image.getHeight(); j++)
            {
                rgbArray[i][j] = image.getRGB(i,j);
            }
        return rgbArray;
    }

    private void showRGBImage(int[][] image){
        for(int i = 0; i < image.length; i++){
            for(int j = 0; j < image[0].length; j++){
                System.out.print(image[i][j]&0xFF);
            }
            System.out.println();

        }
    }

    public BufferedImage backToBuffered(int[][] image, int width, int height){
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < image.length; i++){
            for(int j = 0; j < image[0].length; j++){
                bi.setRGB(i,j,image[i][j]);
            }
        }
        return bi;
    }

    public Image getSecondImage(){
        return SwingFXUtils.toFXImage(newBuffered,null);
    }


}
