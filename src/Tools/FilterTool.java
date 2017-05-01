package Tools;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * Created by sargis on 4/28/17.
 */
public class FilterTool extends JPanel implements ActionListener{
    JToolBar t1;
    JCheckBox ch0 = new JCheckBox(" - NOT");
    JCheckBox ch1 = new JCheckBox(" - Filter 1");
    JCheckBox ch2 = new JCheckBox(" - Filter 2");
    JCheckBox ch3 = new JCheckBox(" - Filter 3");
    JCheckBox ch4 = new JCheckBox(" - Filter 4");
    JButton resetFilter;
    boolean isCheckedCh0 = false, isCheckedCh1 = false, isCheckedCh2 = false, isCheckedCh3 = false, isCheckedCh4 = false;

    public FilterTool()
    {
        createAndShowGUI();
        event e = new event();
        ch0.addItemListener(e);
        ch1.addItemListener(e);
        ch2.addItemListener(e);
        ch3.addItemListener(e);
        ch4.addItemListener(e);

        resetFilter.addActionListener(this);
    }
    public void unSelectCheckBox()
    {
        ch0.setSelected(false);
        ch1.setSelected(false);
        ch2.setSelected(false);
        ch3.setSelected(false);
        ch4.setSelected(false);

    }
    @Override
    public void actionPerformed(ActionEvent actionEvent) {
       unSelectCheckBox();
    }

    public class event implements ItemListener {
        public void itemStateChanged(ItemEvent e){
            if (ch0.isSelected()) {
                isCheckedCh0 = true;
            } else {
                isCheckedCh0 = false;
            }
            if (ch1.isSelected()) {
                isCheckedCh1 = true;
            } else {
                isCheckedCh1 = false;
            }
            if (ch2.isSelected()) {
                isCheckedCh2 = true;
            } else {
                isCheckedCh2 = false;
            }
            if (ch3.isSelected()) {
                isCheckedCh3 = true;
            } else {
                isCheckedCh3 = false;
            }
            if (ch4.isSelected()) {
                isCheckedCh4 = true;
            } else {
                isCheckedCh4 = false;
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

    private void createAndShowGUI()
    {

        t1=new JToolBar(JToolBar.VERTICAL);
        resetFilter = new JButton("Reset Filter");
        t1.add(ch0);
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
