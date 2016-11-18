package blueEVoting;

/*Candidate class stores all candidate information 
	Methods in this class are admin only */

public class Candidate {
	
	String candidateName;
	String candidateParty;
	int candidateID;
	String candidatePosition;
	
	
	/*change the candidates position for voting activity context*/
	void setCandidatePosition(String string) {
		candidatePosition = string;
	};
	
	void print(){
		System.out.printf("Candidate name = %s\n Candidate party = %s\n Candidate ID = %d\n Candidate position = %s\n", 
				candidateName, candidateParty, candidateID, candidatePosition );
		
	}
	
	/*Change the name of the candidate, non set candidates are typically set to "joe"*/
	public void setCandidateName(String string) {
		candidateName = string;
	}
	
	public String getCandidateName() {
		return candidateName;
	}
	
	public String getCandidatePosition(){
		return candidatePosition;
	}

}
