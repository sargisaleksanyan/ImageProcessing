package Tools;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created by sargis on 4/28/17.
 */
public class ImageTool extends JPanel {
    private JButton save;
    private JButton zoomIn ;
    private JButton zoomOut;
    private JButton frameDrawer;
    private JButton saveFrame;
    private JButton axis;
    public  JButton getSave() {
        return save;
    }
    public  JButton getAxis(){return axis;}
    public  JButton getZoomIn() {
        return zoomIn;
    }
    public  JButton getZoomOut() {
        return zoomOut;
    }
    public  JButton getFrameDrawer() {
        return frameDrawer;
    }

    public  JButton getSaveFrame() {
        return saveFrame;
    }
    public ImageTool(int panel_width)
    {
       initButtons();
    }
    public void initButtons()
    {
       save=new JButton("Save");
       zoomIn=new JButton("ZoomIn");
       zoomOut = new JButton("ZoomOut");
       frameDrawer=new JButton("Frame");

       saveFrame=new JButton("Save_Frame");
       axis=new JButton("Axis");
       setLayout(new GridLayout(9, 1));
       setVisible(true);
       add(save);
       add(zoomIn);
       add(zoomOut);
       add(frameDrawer);

       add(saveFrame);
       add(axis);
       setVisible(true);
   }
   public void setActionListener(ActionListener actionListener)
   {
       save.addActionListener(actionListener);
       zoomIn.addActionListener(actionListener);
       zoomOut.addActionListener(actionListener);
       save.addActionListener(actionListener);
       frameDrawer.addActionListener(actionListener);
       saveFrame.addActionListener(actionListener);
       axis.addActionListener(actionListener);
   }
  public void reInitButtons()
  {
      initButtons();
  }
  public void removeComponents()
  {
      removeAll();
  }

}

