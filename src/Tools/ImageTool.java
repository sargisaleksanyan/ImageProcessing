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
    private JButton filter;
    private JButton filter_byframe;
    private JButton saveFrame;
    public JButton getSave() {
        return save;
    }

    public JButton getZoomIn() {
        return zoomIn;
    }

    public JButton getZoomOut() {
        return zoomOut;
    }

    public JButton getFrameDrawer() {
        return frameDrawer;
    }

    public JButton getFilter() {
        return filter;
    }

    public JButton getFilter_byframe() {
        return filter_byframe;
    }

    public JButton getSaveFrame() {
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
       filter = new JButton("Filter");
       filter_byframe=new JButton("Filter_Frame");
       saveFrame=new JButton("Save_Frame");
       //  setPreferredSize(new Dimension(panel_width, getComponents().length*25));
       setLayout(new GridLayout(8, 1));
       setVisible(true);
       add(save);
       add(zoomIn);
       add(zoomOut);
       add(frameDrawer);
       add(filter);
       add(filter_byframe);
       add(saveFrame);
       setVisible(true);
   }
   public void setActionListener(ActionListener actionListener)
   {
        save.addActionListener(actionListener);
        zoomIn.addActionListener(actionListener);
        zoomOut.addActionListener(actionListener);
        save.addActionListener(actionListener);
       frameDrawer.addActionListener(actionListener);
       filter.addActionListener(actionListener);
       filter_byframe.addActionListener(actionListener);
       saveFrame.addActionListener(actionListener);
   /*    private JButton  ;
       private JButton zoomOut;
       private JButton frameDrawer;
       private JButton filter;
       private JButton filter_byframe;
       private JButton saveFrame;*/
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

