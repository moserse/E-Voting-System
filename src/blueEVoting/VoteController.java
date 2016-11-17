package blueEVoting;

/*Vote controller for ballot so that ballot can interact with database*/

public class VoteController {
	
	Ballot localBallot;
	Ballot[] ballots;
	
	/*submission of ballot just prior to database storage*/
	void submitBallot() {
		
	}
	
	/*user option to modify their ballot in case of fickleness or accidentaly wrong choice*/
	void modifyBallot() {
		
	}
	
	/*a submission of initial selection from user, not entirely final*/
	void submitSelection(Candidate candidate) {
		if (localBallot == null) localBallot = new Ballot(candidate);
		else localBallot.setCandidate(candidate);
	}	
	
	void submitSelection(Candidate candidate, int position) {
		if (ballots == null ) {
			setNumberOfBallots(10);
		}
		if ( position > ballots.length - 1 ) {	
			// Ha ha no. Extend here, though a use case of >10 positions is rather improbable.
		}
		ballots[position] = new Ballot(candidate);
	}
	
	void setNumberOfBallots(int ballots) {
		this.ballots = new Ballot[ballots];
	}
	/*final commit of user vote activity selection ready to be transfered to database*/
	void commitSelections() {
		
	}

	public String getSelections() {
		// Theoretically gets multiple ballots and what not if needed
		return localBallot.getSelections();
	}

}
