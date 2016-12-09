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
	
	private int height, width, timer;
	private String instructions;
	private Candidate selectedCandidate;
	private final Color backgroundColor = new Color(94, 192, 255);
	private final Color textColor = new Color(229, 229, 229);
	private final Color warnColor = new Color(255, 0, 0);
	private final Font font = new Font("Georgia", Font.PLAIN, 32) ;
	private JFrame frame;
	private JPanel panel;
	private JTextField textfield;
	private JComboBox combo;

	

	/*If the user is absent for 60 seconds, the software will initiate a timeout, erasing short-term voter data*/
	public void timeout() {};

	
	
	/**
	 * Starts the frame and panel
	 */
	public void start() {
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
	
	public void restart() {
		panel.removeAll();
	}
	
	/**
	 * Displays the Voter Validation components
	 */
	public void displayVoterValidation(ActionListener actionListener) {
		//Add the label.
        JLabel label = new JLabel("Please enter your Voter ID in the field below:", SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200, 300));
        label.setForeground( textColor );
        label.setFont(font);
        //frame.getContentPane().add(label);
        
        textfield = new JFormattedTextField(createFormatter("#####"));
        JButton button = new JButton("Vote Now");
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
	public void displayCandidateView(Candidate[] candidates, ActionListener actionListener) {
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
	public void displayCountView(String[] results, boolean valid, ActionListener actionListener) {
		panel.removeAll();
		selectedCandidate = null;
		
		JLabel label = new JLabel("Here are the candidate results", SwingConstants.CENTER);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setPreferredSize(new Dimension(200, 300));
        label.setForeground( textColor );
        label.setFont(font);
        
        //JLabel candidatesLabel = new JLabel(results, SwingConstants.CENTER);
        
		JList<String> candidatesList = new JList<String>(results);
		//candidatesList.setHorizontalTextPosition(SwingConstants.CENTER);
        candidatesList.setPreferredSize(new Dimension(200, 300));
        candidatesList.setForeground( textColor );
        candidatesList.setFont(font);
		JButton button = new JButton("Back");
        button.setPreferredSize(new Dimension(300, 100));
        button.addActionListener(actionListener);
        
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
		panel.add(candidatesList, BorderLayout.CENTER);
        panel.add(button, BorderLayout.PAGE_END);
        frame.setVisible(true);
		
	}


	/**
	 * Displays the Verification Components
	 */
	public void displayVerification(String ballotSelections, ActionListener actionListener, ActionListener goBack) {
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
        JButton buttonNegative = new JButton("This is not fine.");
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
	public void displayAdminPanel(ActionListener actionListener, ActionListener restart, ActionListener next) {
		panel.removeAll();
		//selectedCandidate = null;
		
		JLabel label = new JLabel("Please add candidates (admin)", SwingConstants.CENTER);
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
        

		
        panel.add(label, BorderLayout.NORTH);
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
		if ( textfield != null) return textfield.getText();
		else return null;
	}
	
	/**
	 * Returns the Jcombo selection
	 * 
	 * @return object	The text in the text field. Generalized to be more usable.
	 */
	public Object getComboSelection() {
		if ( combo != null) return combo.getSelectedItem();
		else return null;
	}

	
	/**
	 * Sets up a warning label and added it to the panel, to be used by ViewController in proper cases.
	 */
	public void warn(String warning) {
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
	private MaskFormatter createFormatter(String mask) {
		MaskFormatter formatter = null;
	    try {
	        formatter = new MaskFormatter(mask);
	    } catch (java.text.ParseException exc) {
	        System.err.println("formatter is bad: " + exc.getMessage());
	        System.exit(-1);
	    }
	    return formatter;
	}
	
	

}
