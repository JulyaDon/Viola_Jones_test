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

        int[][] firstTemplate = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

        int[][] secondTemplate = {{1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 0, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}};

        int[][] testTemplate = {{1, 1, 1, 1, 1, 1, 1, 1},
                                {1, 0, 0, 0, 0, 0, 0, 1},
                                {1, 1, 0, 0, 0, 0, 0, 1},
                                {1, 1, 0, 0, 0, 0, 0, 1},
                                {1, 1, 0, 0, 0, 0, 0, 1},
                                {1, 1, 1, 1, 1, 0, 0, 1},
                                {1, 1, 1, 1, 1, 0, 0, 1},
                                {1, 1, 1, 1, 1, 0, 0, 1},
                                {1, 1, 1, 1, 1, 1, 1, 1}};



        int[][] Image;
        BufferedImage testImage = null;
        try {
            testImage = ImageIO.read(new File("src/sample/Box11.jpg"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        Image = toRGBArray(testImage);
        int[][] binarized = Binarize(Image);

        newBuffered = backToBuffered(Image, 240, 180);

//        int[] coordinatesOfFirst = catchSquare(firstTemplate, binarized);
//        if(coordinatesOfFirst!=null) {
//            for (int i = 0; i < coordinatesOfFirst.length; i++) {
//                System.out.println("Coordinate #" + (i+1) + " of first object: " + coordinatesOfFirst[i]);
//            }
//        }
//        else System.out.println("No object was not found");

        int[] coordinatesOfSecond = catchSquare(testTemplate, binarized);
        if(coordinatesOfSecond!=null) {
            for (int i = 0; i < coordinatesOfSecond.length; i++) {
                System.out.println("Coordinate #" + (i+1) + " of second object: " + coordinatesOfSecond[i]);
            }
        }
        else {
            int[][] newTemplate = makeTemplate(testTemplate, 2, 2);
            int count = 0;
            System.out.println("Increased times: " + count++);
            showTemplate(newTemplate);
            coordinatesOfSecond = catchSquare(newTemplate, binarized);
            if(coordinatesOfSecond!=null) {
                for (int i = 0; i < coordinatesOfSecond.length; i++) {
                    System.out.println("Coordinate #" + (i + 1) + " of second object: " + coordinatesOfSecond[i]);
                }
            }
        }


    }

    /**
     * Looks for object at the image
     * @param template
     * @param image
     * @return first coordinates of object
     */
    private int[] catchSquare(int[][] template, int[][] image){
        double result = 0;
        int scaleX = 2;
        int scaleY = 2;
        int[] coordinates = new int[2];
        for(int x = 0; x < image.length-template.length; x++){
            for(int y = 0; y < image[0].length-template[0].length; y++){
                result = findingSquare(template, x, y, image);
                if(result >= 90){
                    System.out.println("They are similar!");
                    coordinates[0] = x;
                    coordinates[1] = y;
                    System.out.println("Percentage of similar: " + result);
                    return coordinates;
                }
            }
        }

//        scaleX++;
//        scaleY++;

        return null;
    }

    /**
     * Compares template to part of image which has size of this template
     * @param template
     * @param x - start x-coordinate of image part
     * @param y - start y-coordinate of image part
     * @param image - the whole image
     * @return percent of coincidence between template and part of image
     */
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
                else if ((template[i][j] == 1 && image[m][n] == 1)){
                    quantityOfSimilar++;
                }
            }
        }
        //System.out.println("Similar: " + quantityOfSimilar);
        percent = ((double) quantityOfSimilar/(double) generalQuantity) * 100;

        return percent;
    }

    /**
     * Creates new template N times as big as an old one
     * @param oldTemplate
     * @param scaleX times in which width will be bigger
     * @param scaleY times in which height will be bigger
     * @return created template
     */
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
        for(int i = 0; i < newTemplate.length; i++){
            for(int j = 0; j < newTemplate[0].length; j++){
                newTemplate[i][j] = (newTemplate[i][j]&0xFF);
            }
        }
        return newTemplate;
    }

    /**
     * Shows template array in console
     * @param template
     */
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

    /**
     * Binarizes image in pixel array to 0 and 1 for the purpose of mapping objects</br>
     * not the same as imageBinarize()<br>
     *     also refreshes binary representation of image that can be accessed by calling <b>getImageBin()</b>
     *
     * @param array to binarize. The result of imageBinarize() method;
     * @return NEW array with 0 and 1
     */
    private int[][] Binarize(int[][] array){
        double BinarizationThreshold = 0x20;
        int[][] BinarizedImage = new int[array.length][array[0].length];
        int[][] BinarizedImageRGB = new int[array.length][array[0].length];

        for (int i = 0; i < array.length; i++) {
            for (int j = 0; j < array[0].length; j++) {
                int pixel = array[i][j]&0xFF;
                if (pixel > BinarizationThreshold){
                    BinarizedImage[i][j]=1;
                    BinarizedImageRGB[i][j]=0xFFFFFF;
                } else {
                    BinarizedImage[i][j]=0;
                    BinarizedImageRGB[i][j]=0x000000;
                }
            }
        }
        return BinarizedImage;
    }

    /**
     * Turns Buffered Image into RGB-array
     * @param image - buffered image
     * @return RGB-array
     */
    public int[][] toRGBArray(BufferedImage image){

        int rgbArray[][] = new int[image.getWidth()][image.getHeight()];

        for (int i=0; i < image.getWidth(); i++)
            for (int j=0; j < image.getHeight(); j++)
            {
                rgbArray[i][j] = image.getRGB(i,j);
            }
        return rgbArray;
    }

    /**
     * Shows RGB-array of image in console
     * @param image
     */
    private void showRGBImage(int[][] image){
        for(int i = 0; i < image.length; i++){
            for(int j = 0; j < image[0].length; j++){
                System.out.print((image[i][j]&0xFF) + "  ");
            }
            System.out.println();
            System.out.println();
        }
    }

    /**
     * Turns RGB-array into Buffered Image
     * @param image - RGB-array of image
     * @param width of Buffered Image
     * @param height of Buffered Image
     * @return new Buffered Image
     */
    public BufferedImage backToBuffered(int[][] image, int width, int height){
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < image.length; i++){
            for(int j = 0; j < image[0].length; j++){
                bi.setRGB(i,j,image[i][j]);
            }
        }
        return bi;
    }

    /**
     * Turns Buffered Image into FXImage
     * @return FXImage
     */
    public Image getSecondImage(){
        return SwingFXUtils.toFXImage(newBuffered,null);
    }


}
