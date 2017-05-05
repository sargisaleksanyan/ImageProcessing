package Windows;

import ImageGeometry.ImageData;
import ImageGeometry.ImageGeometry;
import Tools.FilterTool;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.*;

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
    private JButton filter_byframe;
    private JButton saveFrame;
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
    private JButton reset;
    private Graphics myg = null;
    private Point imagePoint;
    private ImageData image_Data;
    public Window()
    {

      initializeWindow();
    }
    public FilterTool getFilterTool()
    {
        return filterFrame;
    }

    public void drawFrame() {

        ImageGeometry geo = new ImageGeometry(myImage,this);
        ImageData imageData=geo.getImageDate(imageSourceName);
        image_Data=imageData;
        Graphics2D graph=currentImage.createGraphics();
        graph.setColor(Color.RED);
        graph.drawRect(imageData.MeanX - imageData.deviationX ,
                       imageData.MeanY - imageData.deviationY ,
                       imageData.deviationX * 2, imageData.deviationY * 2);
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
            zoomIn=new JButton("ZoomIn");
            zoomOut = new JButton("ZoomOut");
            frameDrawer=new JButton("Frame");
            filter = new JButton("Filter");
            filter_byframe=new JButton("Filter_Frame");
            saveFrame=new JButton("Save_Frame");
            zoomOut.setPreferredSize(new Dimension(zoomButtonWidth, 25));
            zoomIn.setPreferredSize(new Dimension(zoomButtonWidth, 25));
            zoomIn.addActionListener(this);
            zoomOut.addActionListener(this);
            frameDrawer.addActionListener(this);
            filter_byframe.addActionListener(this);
            saveFrame.addActionListener(this);
            filter.addActionListener(this);
            int wid= (int) (zoomButtonWidth*1.2);
            buttonPanel.setPreferredSize(new Dimension((int)(wid), 200));
            buttonPanel.setLayout(new GridLayout(8, 1));
            buttonPanel.add(zoomIn);
            buttonPanel.add(zoomOut);
            buttonPanel.add(frameDrawer);
            buttonPanel.add(filter);
            buttonPanel.add(filter_byframe);
            buttonPanel.add(saveFrame);
    }
    public void setFilterTool()
    {
        if(filterFrame!=null)
        {
            remove(filterFrame);
        }
        reset= new JButton("Reset");
        reset.setPreferredSize(new Dimension(200,20));
        filterFrame=new FilterTool(currentImage);
        add(filterFrame);
        filterFrame.getResetFilter().addActionListener(this);
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
    public  void resetImage()
    {
        imageLabel.setIcon(null);
        imageLabel.setIcon(new ImageIcon(myImage));
        currentImage=myImage;
        filterFrame.setSelectedFilters(null);
        invalidate();
    }
    public void saveImageFrame()
    {
        if(image_Data==null)
        {
            ImageGeometry geo = new ImageGeometry(myImage,this);
            ImageData imageData=geo.getImageDate(imageSourceName);
            image_Data=geo.getImageDate(imageSourceName);
        }
        FileOutputStream fos=null;
        ObjectOutputStream objectOutputStream=null;
        try {
            File file=new File("Files/"+imageSourceName.substring(29,imageSourceName.length()-3)+"txt");
            fos=new FileOutputStream(file);
            objectOutputStream=new ObjectOutputStream(fos);
            objectOutputStream.writeObject( image_Data);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if(objectOutputStream!=null)
            {
                try {
                    fos.close();
                    objectOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }
    public void filterFrameImage(ImageData iD)  {
        int w = myImage.getWidth();
        int h = myImage.getHeight();

      //  BufferedImage buf=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
        Graphics2D graph=myImage.createGraphics();
        for (int i = iD.MeanY-iD.deviationY; i < iD.MeanY+iD.deviationY; i++) {
            for (int j = iD.MeanX-iD.deviationX; j <iD.MeanX+iD.deviationX; j++) {
                Color c = new Color(myImage.getRGB(j, i));
                int R = c.getRed();
                int G = c.getGreen();
                int B = c.getBlue();
                if (filterFrame.isFaceLayer(R, B, G)) {
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
        imageLabel.setIcon(new ImageIcon(myImage));
        currentImage=myImage;
        image=new ImageIcon(myImage);
        invalidate();
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
               if (filterFrame.isFaceLayer(R, B, G)) {
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
        int newHeight= (int) (image.getIconHeight()*1.25);
        int remaining= (int) (width*0.6-(iniButtonWidth*2));

        if(newWidth<(remaining)&&newHeight<height*0.9) {
            Image im = image.getImage();
            Image newimg = im.getScaledInstance((int) (image.getIconWidth() * 1.25), (int) (image.getIconHeight() * 1.25),
                    java.awt.Image.SCALE_SMOOTH);
            image = new ImageIcon(newimg);
            imageLabel.setIcon(image);
            imagePoint=imageLabel.getLocation();
            invalidate();
        }
    }
   public ImageData getImage_Data()
   {
       String fileName="Files/"+imageSourceName.substring(29,imageSourceName.length()-3)+"txt";
       FileInputStream file= null;
       ImageData imageData=null;
       ObjectInputStream objectInputStream=null;
       try {
           file = new FileInputStream(fileName);
           objectInputStream=new ObjectInputStream(file);
           imageData= (ImageData) objectInputStream.readObject();
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }

        catch (IOException e) {
           e.printStackTrace();
       } catch (ClassNotFoundException e) {
           e.printStackTrace();
       }
       return imageData;
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
        else if(actionEvent.getSource()==filterFrame.getResetFilter())
        {
            filterFrame.unSelectCheckBox();
            resetImage();
        }
        else if(actionEvent.getSource()==saveFrame)
        {
            saveImageFrame();
        }
        else if(actionEvent.getSource()==filter_byframe)
        {
            ImageData id= getImage_Data();
            filterFrameImage(id);
            int a=5;
        }

    }
}
