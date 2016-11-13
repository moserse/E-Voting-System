package blueEVoting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

/*View control acts as the median between the User->display->ballot. takes the interaction and gives results to transfer*/

public class ViewController {
	
	int currentView;
	int currentState;
	Candidate[] candidates;
	Candidate selectedCandidate;
	Voter localVoter;
	Display display;
	
	public static void main(String args[]) {
		ViewController view = new ViewController();
		view.startView();
	}
	
	public void startView() {
		Display display = new Display();
		currentView = 0;
		display.start();
		display.displayVoterValidation(new ActionListener() {
			public void actionPerformed( ActionEvent event ) {
				if ( validateVoter() == true ) moveToNextView();
				else display.warn("Incorrect Registration Number.");
			} 
		});
	}
	
	/*verifies admin*/
	private boolean validateAdmin() {
		return false;
	};
	
	/*verifies voter*/
	private boolean validateVoter() {
		return false;
	};
	
	/*process that shows user next selection after finishing a previous selection*/
	private void moveToNextView() {
		switch (currentView) {
		case 0: 
		default: 
		}
	};
	
	/*get candidate information for ballot*/
	void getCandidates() {};
	
	/*selection of candidate with mouseclick interaction*/
	void selectCandidate() {};
	
	/*confirmation of user selection*/
	void confirmSelections() {};
	

}
