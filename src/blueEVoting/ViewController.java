package blueEVoting;

import javax.swing.*;

/*View control acts as the median between the User->display->ballot. takes the interaction and gives results to transfer*/

public class ViewController {
	
	int currentView;
	Candidate[] candidates;
	Candidate selectedCandidate;
	Voter localVoter;
	Display display;
	
	void start() {
		//Create and set up the window.
        JFrame frame = new JFrame("HelloWorldSwing");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
 
        //Add the ubiquitous "Hello World" label.
        JLabel label = new JLabel("Hello World");
        frame.getContentPane().add(label);
 
        //Display the window.
        frame.pack();
        frame.setVisible(true);
	}
	
	/*verifies admin*/
	boolean validateAdmin() {
		return false;
	};
	
	/*verifies voter*/
	boolean validateVoter() {
		return false;
	};
	
	/*process that shows user next selection after finishing a previous selection*/
	void moveToNextView() {};
	
	/*get candidate information for ballot*/
	void getCandidates() {};
	
	/*selection of candidate with mouseclick interaction*/
	void selectCandidate() {};
	
	/*confirmation of user selection*/
	void confirmSelections() {};

}
