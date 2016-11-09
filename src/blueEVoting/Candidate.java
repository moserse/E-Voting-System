package blueEVoting;

/*Candidate class stores all candiate information 
	Methods in this class are admin only */

public class Candidate {
	
	String candidateName;
	String candidateParty;
	int candidateID;
	String candidatePosition;
	
	/*Change the name of the candidate, non set candidates are set to "joe"*/
	void changeName( String name ) {};
	
	/*change the candidates position for voting activity context*/
	void changePosition() {};
	
	void print(){
		System.out.printf("Candidate name = %s\n Candidate party = %s\n Candidate ID = %d\n Candidate position = %s\n", 
				candidateName, candidateParty, candidateID, candidatePosition );
		
	}

}
