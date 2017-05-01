package Windows;

import ImageGeometry.ImageGeometry;
import Tools.FilterTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

/**
 * Created by sargis on 4/28/17.
 */
public class Window extends JFrame implements ActionListener {

    private Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    private double width = screenSize.getWidth();
    private double height = screenSize.getHeight();
    private JButton imageUpload;
    private JButton save;
    private JButton zoomIn ;
    private JButton zoomOut;
    private JButton frameDrawer;
    private JButton filter;
    private JLabel  imageLabel;
    private JPanel  buttonPanel;
    private ImageIcon image;
    private BufferedImage currentImage;
    private BufferedImage myImage;
    private String imageSourceName;
    private final int iniButtonWidth=105;
    private final int zoomButtonWidth=105;
    private final double widthScale=0.7;
    private final double heightScale=0.8;
    private FilterTool filterFrame;
    private boolean isClicked = false;
    private Graphics myg = null;
    private Point imagePoint;
    public Window()
    {
      initializeWindow();
    }
    public FilterTool getFilterTool()
    {
        return filterFrame;
    }

    public void drawFrame() {
       // ImageGeometry geo = new ImageGeometry(currentImage,this);
        ImageGeometry geo = new ImageGeometry(myImage,this);
        int w = (int) geo.getMeanX();
        int h = (int) geo.getMeanY();
        int dX = (int) geo.deviationX();
        int dY = (int) geo.deviationY();
        Graphics2D graph=currentImage.createGraphics();
        graph.setColor(Color.RED);
        graph.drawRect(w - dX , h - dY , dX * 2, dY * 2);
        imageLabel.setIcon(null);
        imageLabel.setIcon(new ImageIcon(currentImage));
        graph.dispose();
        invalidate();

    }
    public void initializeWindow() {
        setVisible(true);
        setLayout(new FlowLayout());
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        imageLabel=new JLabel();
        add(imageLabel);
        save= new JButton("Save");
        imageUpload=new JButton("Upload");
        save.setPreferredSize(new Dimension(iniButtonWidth,25));
        imageUpload.setPreferredSize(new Dimension(iniButtonWidth,25));
        int w= (int) (width*widthScale);
        int h= (int) (height*heightScale);
        save.addActionListener(this);
        imageUpload.addActionListener(this);
        buttonPanel=new JPanel();
        buttonPanel.setPreferredSize(new Dimension(iniButtonWidth,50));
        buttonPanel.setLayout(new GridLayout(2,1));
        buttonPanel.add(imageUpload);
        buttonPanel.add(save);
        add(buttonPanel);
        setSize(w,h);
        setVisible(true);
    }

    public void initButtonsForImage()
    {
        if(zoomOut!=null&&zoomIn !=null) {
            buttonPanel.removeAll();
        }
            zoomIn = new JButton("ZoomIn");
            zoomOut = new JButton("ZoomOut");
            frameDrawer=new JButton("Frame");
            filter = new JButton("Filter");
            zoomOut.setPreferredSize(new Dimension(zoomButtonWidth, 25));
            zoomIn.setPreferredSize(new Dimension(zoomButtonWidth, 25));
            zoomIn.addActionListener(this);
            zoomOut.addActionListener(this);
            frameDrawer.addActionListener(this);
            filter.addActionListener(this);
            int wid= (int) (zoomButtonWidth*1.2);
            buttonPanel.setPreferredSize(new Dimension(wid, 180));
            buttonPanel.setLayout(new GridLayout(6, 1));
            buttonPanel.add(zoomIn);
            buttonPanel.add(frameDrawer);
            buttonPanel.add(zoomOut);
            buttonPanel.add(filter);

    }
    public void setFilterTool()
    {
        if(filterFrame!=null)
        {
            remove(filterFrame);
        }
        filterFrame=new FilterTool();
        add(filterFrame);
        invalidate();
    }
    private void uploadImage() {
        JFileChooser chooser = new JFileChooser();
        chooser.setCurrentDirectory(new java.io.File("."));
        chooser.setDialogTitle("Browse the folder to process");
        chooser.setFileSelectionMode(JFileChooser.FILES_AND_DIRECTORIES);
        chooser.setAcceptAllFileFilterUsed(false);
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
            image = new ImageIcon(chooser.getSelectedFile().toString());
            imageSourceName = chooser.getSelectedFile().toString();
            imageLabel.setIcon(image);
            initButtonsForImage();
            setFilterTool();
            imagePoint=imageLabel.getLocation();
            myImage = readImage(imageSourceName);
            invalidate();
        } else {
            System.out.println("No Selection ");
        }
    }

    private BufferedImage readImage(String fileName) {
        currentImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
        File f = new File(fileName);
        try {
            currentImage = ImageIO.read(f);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        return currentImage;
    }
   public void filterImage()  {
       int w = myImage.getWidth();
       int h = myImage.getHeight();
       BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
       Graphics2D graph=buf.createGraphics();
       for (int i = 0; i < h; i++) {
           for (int j = 0; j < w; j++) {
               Color c = new Color(myImage.getRGB(j, i));
               int R = c.getRed();
               int G = c.getGreen();
               int B = c.getBlue();
               if (filterFrame.calcFilter(R, B, G)) {
                   graph.setColor(Color.WHITE);
                   graph.fill(new Rectangle2D.Float(j, i, 1, 1));
               }
               else {
                   graph.setColor(Color.BLACK);
                   graph.fill(new Rectangle2D.Float(j, i, 1, 1));
               }
           }
       }
       graph.dispose();
       imageLabel.setIcon(null);
       imageLabel.setIcon(new ImageIcon(buf));
       currentImage=buf;
       image=new ImageIcon(buf);
       invalidate();
   }

    public void zoomIn() {
        int newWidth= (int) (image.getIconWidth()*1.25);
        int remaining= (int) (width*0.6-(iniButtonWidth*2));
        if(newWidth<(remaining)) {
            Image im = image.getImage();
            Image newimg = im.getScaledInstance((int) (image.getIconWidth() * 1.25), (int) (image.getIconHeight() * 1.25),
                    java.awt.Image.SCALE_SMOOTH);
            image = new ImageIcon(newimg);
            
            imageLabel.setIcon(image);
            imagePoint=imageLabel.getLocation();
            invalidate();
        }
    }
    public void zoomOut() {
        Image im = image.getImage(); // transform it
        Image newimg = im.getScaledInstance((int) (image.getIconWidth()*0.8), (int) (image.getIconHeight()*0.8),
                java.awt.Image.SCALE_SMOOTH);
        image = new ImageIcon(newimg);
        imageLabel.setIcon(image);
        imagePoint=imageLabel.getLocation();
        invalidate();
    }
    public static void main(String[] args) {
        new Window();
    }
    public void save(String name) {
        File f = new File(name);
        String suffix = name.substring(name.lastIndexOf('.') + 1);
        suffix = suffix.toLowerCase();
        try {
            if (currentImage != null) {
                ImageIO.write(currentImage, suffix, f);
            } else {
                ImageIO.write(readImage(name), suffix, f);
            }
            //setImage(readImage(name));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if(actionEvent.getSource()==imageUpload) {
            uploadImage();
        }
       else  if (actionEvent.getSource() == filter) {
            filterImage();
        }
        else if(actionEvent.getSource()==zoomIn) {
            zoomIn();
        }
        else if(actionEvent.getSource()==zoomOut) {
            zoomOut();
        }
        else if(actionEvent.getSource()==save)
        {
            String pictureName = JOptionPane.showInputDialog("Name of New picture and format png/gif/bmp");
            save(pictureName);
        }
        else if(actionEvent.getSource()==frameDrawer)
        {
            drawFrame();
        }
    }
}
