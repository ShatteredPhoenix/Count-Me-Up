<h2>Project name: Count Me Up</h2>
<b>Description:</b> <br>
This is a technical assignment that had to be done for the BBC Hiring Event

<h3>Information: Initial Branch:</h3>

This branch contains my initial netbeans implementation which is based on object oriented programming principles, the users and candidates are represented as objects and it simulates a system where the users vote randomly for one of 5 candidates. limiting each user to 3 votes. It then displays the results as total number of votes per candidate and the over percentage of the votes they recieved. 

<h3>Information Master Branch:</h3> 
The master branch contains an upscaled version of the project which is a java web application that simulates the way I interpreted the assignment. The application uses a MySQL database to store registered users with their votes left, in addition to candidates with the amount of votes each one has recieved so far. 

It allows uses to login or sign up (as only registered users can vote) to the system and vote for their candidate of choice, each user is limited to 3 votes and the login/signup page regularly updates the current results live every second on the screen. 

<h3>Installation: Initial Branch</h3>

<h4>Requires:</h4>
- Netbeans

<ol>
<li> Download/Clone from the branch. </li>
<li> Open in Netbbeans </li>
<li> Run CountMeUp.Java </li> 
</ol>

<h3>Installation: Master Branch</h3>

<h4>Requires:</h4>
- Netbeans
- MySQL
- Glassfish server (testing was done on this and comes with Netbeans)

<ol>
<li> Download/Clone latest version </li>
<li> Open up in Netbeans </li> 
<li> Open and Edit VoteCounter.Java and Validation.Java as described below: </li> <br> 
Change <b>URL, USERNAME and PSW</b> Strings in Validation.Java and VoteCounter.Java to match your MySQL server setup - in addition to this make sure your database contains the tables 'users' and 'candidates' with the following table setup's:<br><br>

users:<br>
ID | Name | Password | Vote Count | <br><br>


candidates: <br>
ID | Name | Votes | <br><br>

I have supplised my database structure and data within the files incase you wish to use it instead. They are located within the 'Database SQL Files' folder. <br><br>

<li> Run the Application - If set up is correct it should work </li>
</ol>

Usage: 
- LoginIn/Signup page:
![login_signup image](https://cloud.githubusercontent.com/assets/13851941/23819282/02f38814-05fc-11e7-8b61-a6e25cc15471.png)

<br> 


<b> Credits: Humza Ahmed </b>

