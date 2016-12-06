THIS IS THE B-E-VOTING SOFTWARE 

IMPORTANT:

The only thing to do in order to make this software relative to your machine is to go into the DatabaseController.java file and change the string userName and string password to your sql username and password. These are located on lines 24 and 25. 

After this all you need to do is run the project and enjoy regular voting activities.

------------------------------------
------------------------------------

Sample Test Case Process:

-Login as # 32345		Output: Incorrect Registration Number
-Login as # 24			Output: Incorrect Registration Number
-Login as # 10032		Output: *taken to ballot screen*
-Vote for candidates and submit ballot
-Login as admin 12347 		Output: taken to admin screen
-Click the ‘Count’ button and view the results of your ballot
-Exit Program