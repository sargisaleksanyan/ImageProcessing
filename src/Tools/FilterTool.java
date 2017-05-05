package Tools;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * Created by sargis on 4/28/17.
 */
public class FilterTool extends JPanel {
    private JToolBar t1;
 //   private JCheckBox ch0 = new JCheckBox(" - NOT");
    private JCheckBox ch1 = new JCheckBox(" - Filter 1");
    private JCheckBox ch2 = new JCheckBox(" - Filter 2");
    private JCheckBox ch3 = new JCheckBox(" - Filter 3");
    private JCheckBox ch4 = new JCheckBox(" - Filter 4");
    private List<HashMap<int[],Boolean>> filters;


    private JButton resetFilter;

    public List<String> getSelectedFilters() {
        return selectedFilters;
    }

    public void setSelectedFilters(List<String> selectedFilters) {
        this.selectedFilters = selectedFilters;
    }
    private List<Filter> filterList;
    private List<String> selectedFilters;
    private boolean isCheckedCh0 = false, isCheckedCh1 = false, isCheckedCh2 = false, isCheckedCh3 = false, isCheckedCh4 = false;
    public final static String first= "first", second = "second", third = "third", forth = "forth", five = "five";
    private String current=null;
    private BufferedImage bufferedImage;
    public FilterTool(BufferedImage bufferedImage)
    {
        createAndShowGUI();
        event e = new event();
        this.bufferedImage=bufferedImage;
      //  ch0.addItemListener(e);
        ch1.addItemListener(e);
        ch2.addItemListener(e);
        ch3.addItemListener(e);
        ch4.addItemListener(e);

    }
    public Filter[] getFilters()
    {
        Filter filter[]=new Filter[4];
        filter[0]=new Filter(bufferedImage,first);
        filter[1]=new Filter(bufferedImage,second);
        filter[2]=new Filter(bufferedImage,third);
        filter[3]=new Filter(bufferedImage,forth);
        return filter;
    }
    public JButton getResetFilter() {
        return resetFilter;
    }

    public void setResetFilter(JButton resetFilter) {
        this.resetFilter = resetFilter;
    }
    public void unSelectCheckBox()
    {
        ch1.setSelected(false);
        ch2.setSelected(false);
        ch3.setSelected(false);
        ch4.setSelected(false);
    }


    public class event implements ItemListener {
        public void itemStateChanged(ItemEvent e){
            if(selectedFilters==null)
            {
                selectedFilters=new ArrayList<>();
            }
            if (ch1.isSelected()) {
                isCheckedCh1 = true;
                selectedFilters.add(second);

            } else {
                isCheckedCh1 = false;
                if(selectedFilters.contains(second))
                {
                    selectedFilters.remove(second);
                }
            }
            if (ch2.isSelected()) {
                isCheckedCh2 = true;
                selectedFilters.add(third);
            } else {
                isCheckedCh2 = false;
                if(selectedFilters.contains(third))
                {
                    selectedFilters.remove(third);
                }
            }
            if (ch3.isSelected()) {
                selectedFilters.add(forth);
                isCheckedCh3 = true;

            } else {
                isCheckedCh3 = false;
                if(selectedFilters.contains(forth))
                {
                    selectedFilters.remove(forth);
                }
            }
            if (ch4.isSelected()) {
                isCheckedCh4 = true;
                selectedFilters.add(five);;
            } else {
                isCheckedCh4 = false;
                if(selectedFilters.contains(five))
                {
                    selectedFilters.remove(five);
                }
            }
        }
    }
    public boolean calcFilter(int R, int B, int G) {
        int S0 = 0, S4 = 0;
        if (isCheckedCh1 && isCheckedCh2) {
            S0 = (int) Math.ceil(-0.0009 * G * G + 1.1917 * G - 4.0146);
            S4 = (int) Math.ceil(-0.0011 * G * G + 1.2262 * G + 4.0264);
        }
        if (isCheckedCh1 && isCheckedCh3) {
            S0 = (int) Math.ceil(-0.0009 * G * G + 1.1917 * G - 4.0146);
            S4 = (int) Math.ceil(-0.0013 * G * G + 1.2608 * G + 12.067);
        }
        if (isCheckedCh1 && isCheckedCh4) {
            S0 = (int) Math.ceil(-0.0009 * G * G + 1.1917 * G - 4.0146);
            S4 = (int) Math.ceil(-0.0026 * G * G + 1.5713 * G + 14.8);
        }
        if (isCheckedCh2 && isCheckedCh3) {
            S0 = (int) Math.ceil(-0.0011 * G * G + 1.2262 * G + 4.0264);
            S4 = (int) Math.ceil(-0.0013 * G * G + 1.2608 * G + 12.067);
        }
        if (isCheckedCh2 && isCheckedCh4) {
            S0 = (int) Math.ceil(-0.0011 * G * G + 1.2262 * G + 4.0264);
            S4 = (int) Math.ceil(-0.0026 * G * G + 1.5713 * G + 14.8);

        }
        int mid = (R + B) / 2;
        if (!isCheckedCh0) {
            if (S0 <= mid && mid <= S4) {
                return true;
            } else
                return false;
        } else {
            if (S0 <= mid && mid <= S4) {
                return false;
            } else
                return true;
        }

    }
     public  boolean isFaceLayer(int R,int B,int G)
     {
         boolean isFace=false;
         if(selectedFilters==null)
         {
             selectedFilters=new ArrayList<>();
             selectedFilters.add(second);
         }
         for(int i=0;i<selectedFilters.size();i++)
         {
             if(isFaceLayerByFilter(R,B,G,selectedFilters.get(i))) {
                      return true;
                  }
         }
         return isFace;
     }
     private boolean isFaceLayerByFilter(int R,int B,int G,String filter)
     {
            double filters[]=calcPoint(G,filter);
            double S_1=filters[0];
            double S_2=filters[1];
           if(R>G &&R>B &&(R+G+B)/3<180 &&S_1<=(R+B)/2 && (R+B)/2 <=S_2) {
             return true;
         }
         return false;
     }
    private double[] calcPoint(int G,String filter) {

        double value1=0;
        double value2=0;
        switch (filter)
        {
            case second:
                value1 =(0.9848 * G -6.7474);
                value2=-0.0009*G*G+1.1917*G+4.0146;
                break;
            case third:
                value1=-0.0009*G*G+1.1917*G+4.0146;
                value2=-0.0011*G*G+1.2262*G+4.0264;
                break;
            case forth:
                value1=-0.0011*G*G+1.2262*G+4.0264;
                value2=-0.0013*G*G+1.2608*G+12.067;
                break;
            case five:
                value1 =(0.9848 * G -6.7474);
                value2=-0.0009*G*G+1.1917*G+4.0146;
                break;
            default:
                value1 =(0.9848 * G -6.7474);
                value2=-0.0009*G*G+1.2262*G+4.0264;
                break;
        }
        return new double[]{value1,value2};
    }

    private void createAndShowGUI()
    {

        t1=new JToolBar(JToolBar.VERTICAL);
        resetFilter = new JButton("Reset Filter");
       // t1.add(ch0);
        t1.add(ch1);
        t1.add(ch2);
        t1.add(ch3);
        t1.add(ch4);
        t1.add(resetFilter);
        t1.setRollover(false);
        t1.setFloatable(false);
        add(t1);
        setSize(200,400);
        setLocation(100, 400);
        setVisible(true);
    }

}
