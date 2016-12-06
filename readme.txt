THIS IS THE B-E-VOTING SOFTWARE 

IMPORTANT:

The only thing to do in order to make this software relative to your machine is to go into the DatabaseController.java file and change the string userName and string password to your sql username and password. These are located on lines 24 and 25. After you've replaced these variables, ensure that mySQL database is running on your local computer. 

After this all you need to do is run the project and enjoy regular voting activities.

------------------------------------
------------------------------------

A few test cases:
-Input into Login: 43210  Output: Incorrect registration number
-Input into Login: 33     Output: Incorrect registration number
-Input into Login: 10034  Output: *taken to voting panel*
-Vote for series of candidates and submit ballot
-Attempt to Login: 10034  Output: Incorrect registration number
-Login as 12347		  Output: Admin Screen
-Click Count		  Output: results of your previous vote as ID 10034