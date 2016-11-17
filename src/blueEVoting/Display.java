package blueEVoting;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.sql.SQLException;

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
	JTextField textfield;
	JComboBox combo;

	
	
	
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
	
	/**
	 * Starts the frame and panel
	 */
	void start() {
		//Create and set up the window.
        frame = new JFrame("Blue E-Voting: Trust Us! We don't!");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        panel = new JPanel();
        frame.add(panel);
        panel.setLayout(new BorderLayout());
        panel.setBackground( backgroundColor );
        frame.pack();
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH); 
	}
	
	void restart() {
		panel.removeAll();
	}
	
	/**
	 * Displays the Voter Validation components
	 */
	void displayVoterValidation(ActionListener actionListener) {
		//Add the label.
        JLabel label = new JLabel("Please enter your Voter ID in the field below:", SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200, 300));
        label.setForeground( textColor );
        label.setFont(font);
        //frame.getContentPane().add(label);
        
        textfield = new JFormattedTextField(createFormatter("#####"));
        JButton button = new JButton("Vote Now, Fam");
        button.setPreferredSize(new Dimension(400, 200));
        button.addActionListener(actionListener);
        
        textfield.setText("00000");
        textfield.setPreferredSize(new Dimension(200, 200));
        
        panel.add(label, BorderLayout.PAGE_START);
        panel.add(textfield, BorderLayout.CENTER);
        panel.add(button, BorderLayout.PAGE_END);
        //frame.getContentPane().add(idField);
        //frame.getContentPane().add(button);

        //Display the window.
        frame.setVisible(true);

	}
	
	/**
	 * Displays the Candidate Components
	 */
	void displayCandidateView(Candidate[] candidates, ActionListener actionListener) {
		panel.removeAll();
		selectedCandidate = null;
		
		JLabel label = new JLabel("Please select a Candidate from below:", SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200, 300));
        label.setForeground( textColor );
        label.setFont(font);
		JList<Candidate> candidateList = new JList<Candidate>();
		
		JButton button = new JButton("I like this person I selected");
        button.setPreferredSize(new Dimension(400, 200));
        button.addActionListener(actionListener);
        
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
		
        panel.add(label, BorderLayout.PAGE_START);
		panel.add(candidateList, BorderLayout.CENTER);
        panel.add(button, BorderLayout.PAGE_END);
        frame.setVisible(true);
		
	}
	
	/**
	 * Displays the Candidate Components
	 */
	void displayCountView(String results) {
		panel.removeAll();
		selectedCandidate = null;
		
		JLabel label = new JLabel("Here are the counts for dem here candidotes", SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200, 300));
        label.setForeground( textColor );
        label.setFont(font);
        
        JLabel candidatesLabel = new JLabel(results, SwingConstants.CENTER);
        candidatesLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        candidatesLabel.setPreferredSize(new Dimension(200, 300));
        candidatesLabel.setForeground( textColor );
        candidatesLabel.setFont(font);
		//JList<Candidate> candidateList = new JList<Candidate>();
		
		//JButton button = new JButton("I like this person I selected");
        //button.setPreferredSize(new Dimension(400, 200));
        //button.addActionListener(actionListener);
        
		//candidateList.setListData(candidates);
		//candidateList.setCellRenderer(new ChecklistCellRenderer<Candidate>());
		/**candidateList.addListSelectionListener(new ListSelectionListener() {
			@Override
			public void valueChanged(ListSelectionEvent e) {
				// http://stackoverflow.com/questions/13800775/find-selected-item-of-a-jlist-and-display-it-in-real-time
				if (!e.getValueIsAdjusting()) {
	                  selectedCandidate = candidateList.getSelectedValue();
	                  System.out.println(selectedCandidate.getCandidateName());
	            }
			} 
		});
		*/
		
        panel.add(label, BorderLayout.PAGE_START);
		panel.add(candidatesLabel, BorderLayout.CENTER);
        //panel.add(button, BorderLayout.PAGE_END);
        frame.setVisible(true);
		
	}


	/**
	 * Displays the Verification Components
	 */
	void displayVerification(String ballotSelections, ActionListener actionListener, ActionListener goBack) {
		panel.removeAll();
		//selectedCandidate = null;
		
		JLabel label = new JLabel("Please confirm your selections.", SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200, 300));
        label.setForeground( textColor );
        label.setFont(font);
        
        JLabel ballotLabel = new JLabel(ballotSelections, SwingConstants.CENTER);
        ballotLabel.setHorizontalTextPosition(SwingConstants.CENTER);
        ballotLabel.setPreferredSize(new Dimension(200, 300));
        ballotLabel.setForeground( textColor );
        ballotLabel.setFont(font);
		//JList<Candidate> candidateList = new JList<Candidate>();
		
        JPanel buttonPanel = new JPanel();
        
		JButton button = new JButton("This is fine.");
        button.setPreferredSize(new Dimension(400, 200));
        button.addActionListener(actionListener);
        JButton buttonNegative = new JButton("This ain't fine.");
        buttonNegative.setPreferredSize(new Dimension(400, 200));
        buttonNegative.addActionListener(goBack);
        
        buttonPanel.add(button, BorderLayout.EAST);
        buttonPanel.add(buttonNegative, BorderLayout.EAST);

		
        panel.add(label, BorderLayout.PAGE_START);
        panel.add(ballotLabel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.PAGE_END);
        frame.setVisible(true);
		
	}
	
	/**
	 * Displays the Admin Panel
	 */
	void displayAdminPanel(ActionListener actionListener, ActionListener restart, ActionListener next) {
		panel.removeAll();
		//selectedCandidate = null;
		
		JLabel label = new JLabel("Please add your stuff sir/miss admin sir/miss!", SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200, 300));
        label.setForeground( textColor );
        label.setFont(font);
        
        textfield = new JFormattedTextField();
        textfield.setText("Joe McShoe");
        textfield.setPreferredSize(new Dimension(200, 200));
        String[] positions = { "President", "Vice President", "Senator", "Representative"};
        combo = new JComboBox<String>(positions);
        
        JButton button = new JButton("Add");
        button.setPreferredSize(new Dimension(400, 200));
        button.addActionListener(actionListener);
        
        JButton buttonNegative = new JButton("Done");
        buttonNegative.setPreferredSize(new Dimension(400, 200));
        buttonNegative.addActionListener(restart);
        
        JButton buttonNext = new JButton("Count");
        buttonNext.setPreferredSize(new Dimension(400, 200));
        buttonNext.addActionListener(next);
		
        
        JPanel entryPanel = new JPanel();
        entryPanel.add(textfield, BorderLayout.EAST);
        entryPanel.add(combo, BorderLayout.WEST);
        
        JPanel buttonPanel = new JPanel();
        buttonPanel.add(button);
        buttonPanel.add(buttonNegative);
        buttonPanel.add(buttonNext);
        

		
        panel.add(label, BorderLayout.PAGE_START);
        panel.add(entryPanel, BorderLayout.CENTER);
        panel.add(buttonPanel, BorderLayout.PAGE_END);
        frame.setVisible(true);
		
	}
	
	/**
	 * Returns the selected Candidate
	 * 
	 * @return selectedCandidate	The selected candidate, set from the Renderer.
	 */
	public Candidate getSelectedCandidate() {
		return selectedCandidate;
	}
	
	/**
	 * Returns the text field info
	 * 
	 * @return text	The text in the text field. Generalized to be more usable.
	 */
	public String getTextFieldText() {
		if ( textfield != null && textfield.getParent() == panel) return textfield.getText();
		else return null;
	}
	
	/**
	 * Returns the Jcombo selection
	 * 
	 * @return object	The text in the text field. Generalized to be more usable.
	 */
	public Object getComboSelection() {
		if ( combo != null && combo.getParent() == panel) return combo.getSelectedItem();
		else return null;
	}
	
	/**
	 * Sets up a warning label and added it to the panel, to be used by ViewController in proper cases.
	 */
	void warn(String warning) {
		JLabel warningLabel = new JLabel(warning, SwingConstants.LEFT);
		warningLabel.setPreferredSize(new Dimension(200, 300));
		warningLabel.setForeground(warnColor);
		panel.add(warningLabel, BorderLayout.PAGE_START);
		frame.setVisible(true);
	}
	
	/**
	 * Constructs a MaskFormatter object to mask input correctly
	 * Thanks to person here http://stackoverflow.com/questions/20581159/jformattedtextfield-specific-format
	 * 
	 * @return formatter	A mask formatter that is needed to limit input.
	 */
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
