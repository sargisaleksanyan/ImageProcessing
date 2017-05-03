package ImageGeometry;

import java.io.Serializable;

/**
 * Created by sargis on 5/2/17.
 */
public class ImageData implements Serializable {
    public String imageName;
    public ImageData(String imageName)
    {
        this.imageName=imageName;
    }

    public int MeanX;
    public int MeanY;
    public int deviationX;
    public int deviationY;
}
