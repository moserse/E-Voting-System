package blueEVoting;

/*Display class acts that the interface to the user. Initially this will be tested with print statements
	but as we begin coding we will implement Java Swing and use that to create our UI*/

public class Display {
	
	int height, width, timer;
	String instructions;
	Candidate selectedCandidate;
	
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

}
