package Tools;

import javax.swing.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

/**
 * Created by sargis on 4/28/17.
 */
public class FilterTool extends JPanel {
    private JToolBar t1;
    private JCheckBox ch1 = new JCheckBox(" - Filter 1");
    private JCheckBox ch2 = new JCheckBox(" - Filter 2");
    private JCheckBox ch3 = new JCheckBox(" - Filter 3");
    private JCheckBox ch4 = new JCheckBox(" - Filter 4");
    private List<HashMap<int[],Boolean>> filters;
    private JButton resetFilter;
    public HashMap<String,Filter> getSelectedFilters() {
        return selectedFilters;
    }
    public void setSelectedFilters(HashMap<String,Filter> selectedFilters) {
        this.selectedFilters = selectedFilters;
    }
    private Filter[] filterList;
    private HashMap<String,Filter> selectedFilters;
    private boolean isCheckedCh0 = false, isCheckedCh1 = false, isCheckedCh2 = false, isCheckedCh3 = false, isCheckedCh4 = false;
    public final static String first= "first", second = "second", third = "third", forth = "forth", five = "five";
    private String current=null;
    private BufferedImage bufferedImage;
    public FilterTool(BufferedImage bufferedImage)
    {
        createAndShowGUI();
        event e = new event();

        this.bufferedImage=bufferedImage;
        filterList=getFilters();
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
                selectedFilters=new HashMap<>();
            }
            if (ch1.isSelected()) {
                isCheckedCh1 = true;
                selectedFilters.put(second,filterList[0]);

            } else {
                isCheckedCh1 = false;
                if(selectedFilters.containsKey(second) )
                {
                    selectedFilters.remove(second);
                }
            }
            if (ch2.isSelected()) {
                isCheckedCh2 = true;
                selectedFilters.put(third,filterList[1]);
            } else {
                isCheckedCh2 = false;
                if(selectedFilters.containsKey(third))
                {
                    selectedFilters.remove(third);
                }
            }
            if (ch3.isSelected()) {
                selectedFilters.put(forth,filterList[2]);
                isCheckedCh3 = true;

            } else {
                isCheckedCh3 = false;
                if(selectedFilters.containsKey(forth))
                {
                    selectedFilters.remove(forth);
                }
            }
            if (ch4.isSelected()) {
                isCheckedCh4 = true;
                selectedFilters.put(five,filterList[3]);;
            } else {
                isCheckedCh4 = false;
                if(selectedFilters.containsKey(five))
                {
                    selectedFilters.remove(five);
                }
            }
        }
    }

     public  boolean isFaceLayer(int R,int B,int G)
     {
         boolean isFace=false;
         if(selectedFilters==null)
         {
             selectedFilters=new HashMap<>();
             selectedFilters.put(second,filterList[0]);
         }
         /*for(int i=0;i<selectedFilters.size();i++)
         {

             if(isFaceLayerByFilter(R,B,G,selectedFilters.get(i))) {
                      return true;
                  }
         }*/
         Set<String> selectedBoxes=selectedFilters.keySet();
         Iterator<String> iterator=selectedBoxes.iterator();
         while (iterator.hasNext())
         {
             if(selectedFilters.get(iterator.next()).isFaceLayerByFilter(R,B,G))
             {
                 return true;
             }
         }
         return isFace;
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
