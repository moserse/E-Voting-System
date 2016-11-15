package blueEVoting;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

//import javax.swing.*;

/*View control acts as the median between the User->display->ballot. takes the interaction and gives results to transfer*/

public class ViewController {
	
	int currentView;
	int currentState;
	Candidate[] candidates;
	Candidate selectedCandidate;
	Voter localVoter;
	Display display;
	DatabaseController db;
	
	public static void main(String args[]) {
		ViewController view = new ViewController();
		view.startView();
	}
	
	public void startView() {
		display = new Display();
		db = new DatabaseController();
		currentView = 0;
		display.start();
		display.displayVoterValidation(new ActionListener() {
			public void actionPerformed( ActionEvent event ) {
				if ( validateVoter() != true ) moveToNextView();
				else display.warn("Incorrect Registration Number.");
			} 
		});
	}
	
	/*verifies admin*/
	private boolean validateAdmin() {
		return db.validateAdmin();
	};
	
	/*verifies voter*/
	private boolean validateVoter() {
		return db.validateVoter();
	};
	
	/*process that shows user next selection after finishing a previous selection*/
	private void moveToNextView() {
		switch (currentView) {
		case 0: display.displayCandidateView(db.getCandidates(), new ActionListener() {
			public void actionPerformed( ActionEvent event ) {
				;
			} 
		});
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
