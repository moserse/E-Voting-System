package blueEVoting;

import java.awt.Component;

import javax.swing.JList;
import javax.swing.JRadioButton;
import javax.swing.ListCellRenderer;

// http://stackoverflow.com/questions/19766/how-do-i-make-a-list-with-checkboxes-in-java-swing by Rene Link

@SuppressWarnings("serial")
public class ChecklistCellRenderer<E> extends JRadioButton implements ListCellRenderer<E> {

    public Component getListCellRendererComponent(JList<? extends E> list, E value, int index, 
            boolean isSelected, boolean cellHasFocus) {

    	// Sets the component (In this case RadioButton) to all the stuff that the List has set.
        setComponentOrientation(list.getComponentOrientation());
        setFont(list.getFont());
        setBackground(list.getBackground());
        setForeground(list.getForeground());
        setSelected(isSelected);
        setEnabled(list.isEnabled());
        
        if ( value instanceof Candidate) {
        	setText(((Candidate) value).getCandidateName() );
        } else setText(value == null ? "" : value.toString());  

        return this;
    }
}