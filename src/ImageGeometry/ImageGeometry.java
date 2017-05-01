package ImageGeometry;

import Windows.Window;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sargis on 4/28/17.
 */
public class ImageGeometry {
    private List<Integer> valX;
    private List<Integer> valY;
    private  BufferedImage image;
    private  Window window;
    public ImageGeometry(BufferedImage b,Window window) {
        image = b;
        this.window=window;
    }

    public double deviationX() {
        List<Double> sdX = new ArrayList<Double>();
        double mid = getMeanX();
        for (int i = 0; i < valX.size(); i++) {
            double n = Math.pow(valX.get(i) - mid, 2);
            sdX.add(n);
        }
        double sum = 0;
        for (int i = 0; i < valX.size(); i++) {
            sum += sdX.get(i);
        }
        return Math.sqrt(sum / sdX.size());

    }

    public double deviationY() {
        List<Double> sdY = new ArrayList<Double>();
        double mid = getMeanY();
        for (int i = 0; i < valY.size(); i++) {
            double n = Math.pow(valY.get(i) - mid, 2);
            sdY.add(n);
        }
        double sum = 0;
        for (int i = 0; i < valY.size(); i++) {
            sum += sdY.get(i);
        }
        return Math.sqrt(sum / sdY.size());

    }

    public double getMeanX() {
        int sum = 0;
        if (valX == null) {
            calculateMeanValue();

        }
        for (int i = 0; i < valX.size(); i++) {
            sum += valX.get(i);
        }




        return sum / valX.size();
    }

    public double getMeanY() {
        int sum = 0;
        if (valY == null) {
            calculateMeanValue();

        }
        for (int i = 0; i < valY.size(); i++) {
            sum += valY.get(i);
        }

        return sum / valY.size();
    }


    public void calculateMeanValue() {
        int w = image.getWidth();
        int h = image.getHeight();
        valX = new ArrayList<Integer>();
        valY = new ArrayList<Integer>();

        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Color c = new Color(image.getRGB(j, i));
                int R = c.getRed();
                int G = c.getGreen();
                int B = c.getBlue();
                if (window.getFilterTool().calcFilter(R, B, G)) {
                    valX.add(j);
                    valY.add(i);
                } else {

                }
            }

        }
    }


}