package Tools;

import javax.swing.*;
import java.awt.*;

/**
 * Created by sargis on 4/28/17.
 */
public class ImageTools extends JPanel {
    private JButton zoomIn ;
    private JButton zoomOut;
    private JButton frameDrawer;
    private JButton filter;
    private JButton filter_byframe;
    private JButton saveFrame;
    public ImageTools()
    {

        zoomIn=new JButton("ZoomIn");
        zoomOut = new JButton("ZoomOut");
        frameDrawer=new JButton("Frame");
        filter = new JButton("Filter");
        filter_byframe=new JButton("Filter_Frame");
        saveFrame=new JButton("Save_Frame");
            //zoomOut.setPreferredSize(new Dimension(zoomButtonWidth, 25));
            //zoomIn.setPreferredSize(new Dimension(zoomButtonWidth, 25));
           //int wid= (int) (zoomButtonWidth*1.2);
            //setPreferredSize(new Dimension(wid, 180));
        setLayout(new GridLayout(8, 1));
        add(zoomIn);
        add(zoomOut);
        add(frameDrawer);
        add(filter);
     }
 }

