package Tools;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Created by sargis on 5/4/17.
 */
public class Filter {
    private String name;
    private  boolean[][] imagePixel;
    private BufferedImage bufferedImage;
    public Filter(BufferedImage bufferedImage,String name)
    {

        this.name=name;
        this.bufferedImage=bufferedImage;
        imagePixel=getImagePoints();
    }
   public boolean isFaceLayerByFilter(int R,int B,int G)
    {
        double filters[]=bounds(G);
        double S_1=filters[0];
        double S_2=filters[1];
        if(R>G &&R>B &&(R+G+B)/3<180 &&S_1<=(R+B)/2 && (R+B)/2 <=S_2) {
            return true;
        }
        return false;
    }

    private boolean[][] getImagePoints()
    {
        int w = bufferedImage.getWidth();
        int h = bufferedImage.getHeight();
        boolean imagePixeles[][]=new boolean[h][w];
        int a[][]=new int[h][w];
        for (int i = 0; i < h; i++) {
            for (int j = 0; j < w; j++) {
                Color c = new Color(bufferedImage.getRGB(j, i));
                int R = c.getRed();
                int G = c.getGreen();
                int B = c.getBlue();
                if (isFaceLayerByFilter(R, B, G)) {
                    imagePixeles[i][j]=true;
                }
                else {
                    imagePixeles[i][j]=false;
                }
            }
        }
        return imagePixeles;
    }
    public BufferedImage getBufferedImage() {
        return bufferedImage;
    }

    public void setBufferedImage(BufferedImage bufferedImage) {
        this.bufferedImage = bufferedImage;
    }

    private double[] bounds(int G) {
        double value1=0;
        double value2=0;
        switch (name)
        {
            case FilterTool.second:
                value1 =(0.9848 * G -6.7474);
                value2=-0.0009*G*G+1.1917*G+4.0146;
                break;
            case FilterTool.third:
                value1=-0.0009*G*G+1.1917*G+4.0146;
                value2=-0.0011*G*G+1.2262*G+4.0264;
                break;
            case FilterTool.forth:
                value1=-0.0011*G*G+1.2262*G+4.0264;
                value2=-0.0013*G*G+1.2608*G+12.067;
                break;
            case FilterTool.five:
                value1 =(0.9848 * G -6.7474);
                value2=-0.0009*G*G+1.1917*G+4.0146;
                break;

        }
        return new double[]{value1,value2};

   }
}
