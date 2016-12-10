This was a 2 person project made throughout a semester for a software engineering course. For the first half of the semester we designed classes, architecture, and several UML diagrams before coding all the 2nd half of the semester. 

------------------------------------
------------------------------------

IMPORTANT INFO FOR RUNNING:

STEP 1: launch mysql database on your machine

STEP 2: Go to lines 29 and 30 of the DatabaseController.java file and change the userName and password strings to match your unique SQL information. 

After this, all you need to do is run the project and enjoy regular voting activities.

------------------------------------
------------------------------------

Sample Test Case Process:

-Login as # 32345		Output: Incorrect Registration Number
-Login as # 24			Output: Incorrect Registration Number
-Login as # 10032		Output: *taken to ballot screen*
-Vote for candidates and submit or reset ballot
-Login as # 10032 again		Output: Incorrect Registration Number

-Login as admin 12347 		Output: taken to admin screen
-Click the ‘Count’ button and view the results of your ballot
-Exit Program


Continue as necessary, you can also view the contents of each table by logging into mysql, use bevoting, and select * from (table name) (These table names include Voters, Candidates, and Ballots)




-(McCarthy & Moser)