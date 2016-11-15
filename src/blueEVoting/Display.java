package blueEVoting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.MaskFormatter;

/*Display class acts that the interface to the user. Initially this will be tested with print statements
	but as we begin coding we will implement Java Swing and use that to create our UI*/

public class Display {
	
	int height, width, timer;
	String instructions;
	Candidate selectedCandidate;
	Color backgroundColor = new Color(94, 192, 255);
	Color textColor = new Color(229, 229, 229);
	Color warnColor = new Color(255, 0, 0);
	Font font = new Font("Georgia", Font.PLAIN, 32) ;
	JFrame frame;
	JPanel panel;

	
	/*  Display verification of vote selections to voter */
	void displayVerification() {};
	
	/*exit the program*/
	void exit() {};
	
	/*Change display view to the next option*/
	void displayNextView() {};
	
	/*select candidate with checkbox/mouseClick interaction*/
	void selectCandidate() {};
	
	/*click checkmark box to select or deselect*/
	void setCheckmark() {};
	
	/*submission process dedicated on mouse input on button*/
	void submitSelection() {};
	
	/*If the user is absent for 60 seconds, the software will initiate a timeout, erasing short-term voter data*/
	void timeout() {};
	
	/*display admin panel and options following admin login*/
	void displayAdminPanel() {};
	
	/*admin only method to reveal results*/
	void showResults() {};
	
	
	void start() {
		//Create and set up the window.
        frame = new JFrame("Blue E-Voting: Trust Us! We don't!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BorderLayout());
        panel.setBackground( backgroundColor );
        //frame.setSize(500, 500);
 
        	}
	
	void displayVoterValidation(ActionListener actionListener) {
		//Add the label.
        JLabel label = new JLabel("Please enter your Voter ID in the field below:", SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200, 300));
        label.setForeground( textColor );
        label.setFont(font);
        //frame.getContentPane().add(label);
        
        JTextField idField = new JFormattedTextField(createFormatter("#####"));
        JButton button = new JButton("Vote Now, Fam");
        button.setPreferredSize(new Dimension(400, 200));
        button.addActionListener(actionListener);
        
        idField.setText("00000");
        idField.setPreferredSize(new Dimension(200, 200));
        
        panel.add(label, BorderLayout.PAGE_START);
        panel.add(idField, BorderLayout.CENTER);
        panel.add(button, BorderLayout.PAGE_END);
        //frame.getContentPane().add(idField);
        //frame.getContentPane().add(button);

        //Display the window.
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
        //frame.setUndecorated(true);
        frame.setVisible(true);

	}
	
	void displayCandidateView(Candidate[] candidates, ActionListener actionListener) {
		panel.removeAll();
		JList<Candidate> candidateList = new JList<Candidate>();
		candidateList.setListData(candidates);
		candidateList.setCellRenderer(new ChecklistCellRenderer<Candidate>());
		candidateList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// http://stackoverflow.com/questions/13800775/find-selected-item-of-a-jlist-and-display-it-in-real-time
				if (!e.getValueIsAdjusting()) {
	                  selectedCandidate = candidateList.getSelectedValue();
	                  System.out.println(selectedCandidate.getCandidateName());
	            }
			} 
		});
		panel.add(candidateList, BorderLayout.CENTER);
        frame.setVisible(true);
		
	}
	
	void warn(String warning) {
		JLabel warningLabel = new JLabel(warning, SwingConstants.LEFT);
		warningLabel.setPreferredSize(new Dimension(200, 300));
		warningLabel.setForeground(warnColor);
		panel.add(warningLabel, BorderLayout.PAGE_START);
		frame.setVisible(true);
	}
	
	private MaskFormatter createFormatter(String s) {
		MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(s);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}
	
	

}
