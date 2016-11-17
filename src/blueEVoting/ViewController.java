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
	VoteController vc;
	
	public static void main(String args[]) {
		
		
		
		DatabaseController db = new DatabaseController();
		
		ViewController view = new ViewController();
		view.startView();
		
		//db.dropTable();
		
		//submitting a test ballot
		//Ballot testBallot = new Ballot();
		//testBallot.voterID = 10000;
		//db.submitBallot(testBallot);
		
		//db.countResults();
		//db.print();
		//db.dropTable();
		
		//making sure this voter cannot vote again
		//db.validateVoter(10000);
		//db.dropTable();
	}
	
	public void startView() {
		display = new Display();
		db = new DatabaseController();
		vc = new VoteController();
		currentView = 0;
		display.start();
		display.displayVoterValidation(new ActionListener() {
			public void actionPerformed( ActionEvent event ) {
				if ( validateVoter( Integer.parseInt( display.getTextFieldText() ) ) == true ) moveToNextView();
				else display.warn("Incorrect Registration Number.");
			} 
		});
	}
	
	public void restartView() {
		db = new DatabaseController();
		vc = new VoteController();
		currentView = 0;
		display.restart();
		display.displayVoterValidation(new ActionListener() {
			public void actionPerformed( ActionEvent event ) {
				if ( validateVoter( Integer.parseInt( display.getTextFieldText() ) ) == true ) moveToNextView();
				else display.warn("Incorrect Registration Number.");
			} 
		});
	}
	
	/*verifies admin*/
	private boolean validateAdmin() {
		return db.validateAdmin();
	};
	
	/*verifies voter*/
	private boolean validateVoter(int id) {
		return db.validateVoter(id);
	};
	
	/*process that shows user next selection after finishing a previous selection*/
	private void moveToNextView() {
		// Positions can be displayed by just moving to each next view.
		// Check if out of positions, if so, move to final commit view.
		if ( db.getNumberOfPositions() > currentView) {
			displayCandidateView(currentView);
			currentView++;
		} else if (db.getNumberOfPositions() == currentView) {
			// Display final verification
			displayVerification();
			currentView++;
		} else {
			// reset
			restartView();
		}
	};
	
	private void displayVerification() {
		display.displayVerification(vc.localBallot, new ActionListener() {
			public void actionPerformed( ActionEvent event) {
				vc.submitBallot();
				System.out.println("Ballot submitted");
				//db.showBallots();
				//db.showVoters();
				//db.countResults();
				moveToNextView();
			}
		});
	}
	private void displayCandidateView(int position) {
		display.displayCandidateView(db.getCandidates(), new ActionListener() {
			public void actionPerformed( ActionEvent event ) {
				// Submit candidate selection
				if (display.getSelectedCandidate() == null) display.warn("Please select a Candidate first.");
				else {
					vc.submitSelection(display.getSelectedCandidate());
					moveToNextView();
				}
			} 
		});
	}
	
	/*get candidate information for ballot*/
	void getCandidates() {};
	
	/*selection of candidate with mouseclick interaction*/
	void selectCandidate() {};
	
	/*confirmation of user selection*/
	void confirmSelections() {};
	

}
