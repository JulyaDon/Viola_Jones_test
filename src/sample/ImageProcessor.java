package sample;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.List;


/**
 * Created by July on 10.07.2017.
 */
public class ImageProcessor {

    private BufferedImage newBuffered = new BufferedImage(1280,960, BufferedImage.TYPE_INT_RGB);
    private List<SceneObject> objectList = new ArrayList<>();
    public ImageProcessor() {
        //Image
        int[][] Image;

        File imagePath = new File("src/sample/images/image1.jpg");
        BufferedImage BufImage = null;
        try {
            BufImage = ImageIO.read(imagePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Image = toRGBArray(BufImage);
        int[][] binarizedImage = Binarize(Image);

        //Template
        int[][] Template;

        File templatePath = new File("src/sample/images/template.jpg");
        BufferedImage BufTemp = null;
        try {
            BufTemp = ImageIO.read(templatePath);
        } catch (IOException e) {
            e.printStackTrace();
        }
        Template = toRGBArray(BufTemp);
        int[][] binarizedTemplate = Binarize(Template);

        catchObject(binarizedTemplate, binarizedImage);

        newBuffered = backToBuffered(Image, Image[0].length, Image.length);

//        System.out.println("Width: " + imageL[0].length);
//        System.out.println("Height: " + imageL.length);
//        int[][] newT = resizePixels(imageL, 2, 3);
//        showTemplate(newT);
    }

    /**
     * Looks for object at the image
     * @param template
     * @param image
     * @return first coordinates of object
     */
    private List<SceneObject> catchObject(int[][] template, int[][] image){
        int frameWidth = template[0].length;
        int frameHeight = template.length;

        List<SceneObject> objectList = new ArrayList<>();
        double result;

        for(int x = 0; x < image.length-template.length; x+=frameHeight){
            for(int y = 0; y < image[0].length-template[0].length; y+=frameWidth){
                result = getPercent(template, x, y, image);
                if(result >= 10){

                    objectList.add(new SceneObject(new Rectangle(y,x,frameWidth,frameHeight), 0));
                    System.out.println("Percentage of similar: " + result);
                }
            }
        }
        return objectList;
    }

    /**
     * Compares template to part of image which has size of this template
     * @param template
     * @param x - start x-coordinate of image part
     * @param y - start y-coordinate of image part
     * @param image - the whole image
     * @return percent of coincidence between template and part of image
     */
    private double getPercent(int[][] template, int x, int y, int[][] image){
        double percent;
        int generalQuantity = template.length * template[0].length;
        int quantityOfSimilar = 0;

        for(int i = 0, m = x; i < template.length && m < template.length+x; i++, m++){
            for(int j = 0, n = y; j < template[0].length && n < template[0].length+y; j++, n++){
                if ((template[i][j] == 0 && image[m][n] == 0)) {
                    quantityOfSimilar++;
                } else if ((template[i][j] == 1 && image[m][n] == 1)) {
                    quantityOfSimilar++;
                }
            }
        }

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
        double BinarizationThreshold = 127;
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
    private int[][] toRGBArray(BufferedImage image){

        int rgbArray[][] = new int[image.getHeight()][image.getWidth()];

        for (int i=0; i < rgbArray.length; i++)
            for (int j=0; j < rgbArray[0].length; j++)
            {
                rgbArray[i][j] = image.getRGB(j,i);
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
    private BufferedImage backToBuffered(int[][] image, int width, int height){
        BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for(int i = 0; i < image.length; i++){
            for(int j = 0; j < image[0].length; j++){
                bi.setRGB(j,i,image[i][j]);
            }
        }
        return bi;
    }

    public BufferedImage getNewBuffered(){

        return newBuffered;
    }

    public List<SceneObject> getObjectList(){
        return objectList;
    }

    public int[][] resizePixels(int[][] pixels, int w2,int h2) {
        int[][] temp = new int[h2][w2] ;
        int w1 = pixels[0].length;
        int h1 = pixels.length;
        double x_ratio = w1/(double)w2 ;
        double y_ratio = h1/(double)h2 ;
        double px, py ;
        for (int i=0;i<h2;i++) {
            for (int j=0;j<w2;j++) {
                px = Math.floor(j*x_ratio) ;
                py = Math.floor(i*y_ratio) ;
                temp[i][j] = pixels[(int)((py*w1)+px)/w1][(int)((py*w1)+px)%w1] ;
            }
        }
        return temp ;
    }
}