**APIs used: GPS(Android) and Google Map**


# General Comments:


## 1.	Design:
* Be more consistent with how each fragment looks. 
..* Your buttons aren’t always aligned the same way. 
..*Add margins to your list views!
..* Don’t vertically center your listviews. Lists should start at the top of the fragment.
* Tapping on “Manage Courses” on the first fragment takes the user to another screen with “Manage Courses” (again) and “Create New Course”. The names are confusing. Consider naming the second “Manage Courses” something like “Delete Courses” because that is all I can do. 
* Once you start the walk, the points are all displayed well on the map
* The users also can’t delete points while creating a course or even see the points they added.


## 2.	Code:
* Make sure to comment on the top what each class does
* Delete unused methods or write code for them (`CompeteMapsActivity` has empty `onConnectionFailed` and `onConnectionSuspended` and `changeMap`)
* Don’t hardcode strings (use strings.xml)
* There are portions of code that could use more comments and were difficult to understand at first look
* You have Todos in the code that you never completed. But it is good documentation for the future steps of the project. You could also organize these incomplete todos in one place so that people don’t have to go searching.


## 3.	Bugs:
* Rotating the screen causes your homescreen to overlay itself on top of other fragments
* Pressing back button several times takes the users to unexpected fragments. In your app, back button takes you to the last visited fragment. If I go back and forth a bunch of times by selecting a course to compete in and going back by tapping “I’m Out”, there is a stack of fragments. Pressing the back button while I am in the list, it goes through the list of courses and the map multiple times before I get to the home screen.
* We couldn’t actually complete the course
* If you rotate the screen when looking at the map, the timer resets


## 4. Git:
* I see you tried to use issues for task management. Make sure to follow through! Assign people to issues, and mark them as completed when done
* Everyone should comment on PRs :-) You can use PRs to review other members’ code. They’re useful since you can only accept merging a pull request if you understand all the code that’s written, so everyone becomes up to date. In the future, make sure multiple people check off on PRs.
* Also, don’t merge your own pull requests, even if you’re at the end of your project


# Completion:


Here are the features listed in the proposal:


[~] App allows for a user to compete on a scavenger hunt or set courses for other people to compete. 
* Could not actually complete the course by walking around the AC


[~] For setting the course, the user sets pins on a map and then saves the locations. 
* You can add locations to the course, but there is no visual indication that there is a “pin” at a certain location


[x] For competitors, users have to go to the locations and a timer keeps track of how long it takes for the user to reach all destinations. 


[x] The scores are then saved so a user can look at them later.


# Your grade:


## Lab Rubric(210/250 points total):


#### Proposal (15 points)
* 15 - You did the proposal and a teaching team member checked it off


#### Wireframes (30/35 points)
* 30 - You created wireframes for all fragments/screens of your app. You created a rough design of what your app will look like. You clearly outlined the workflow of your app (fragment transitions, how users navigate the app, where the user enters/receives information) (-5 points for hard to read and unclear wireframes)


### Final Deliverable (165/200 points)
#### Functionality: 60/85 points
##### Completion: 50 points
* 70 - You implemented all of the required features in the assignment. The assignment is complete
* 50 - All major features were implemented. You didn't get to one or two small features.
* 30 - Some of the required features were implemented.
* 15 - An attempt was made to implement some of the of the desired features.
* 0 - Very few or none of the desired features were implemented.


##### Bug free: 10/15 points
* 10 - Your app has one or two bugs, occasionally causing unexpected behavior


#### Design/Usability: 15/20 points
* 15 - A small part of the app was unintuitive to interact with. 


#### Quality: 90 points


##### Git practices: 20/25 pts
* 20 - Not all code was reviewed before being merged.


##### Good coding practices: 25 pts
* 25 - Objects are intelligently designed and used where appropriate, inheritance used when appropriate, code is concise, naming conventions followed, proper access modifiers are used, public getters/setters are used instead of public variables, etc.
* 10 - Some of the above practices were broken, but you mostly followed good practices.
* 0 - Good coding practices were consistently broken.


##### Readability: 25 pts
* 25 - Functions and variables are named well. Code is well commented where appropriate. Confusing lines are commented. Lines are not too long.
* 10 - Code is occasionally confusing. Could use more comments. Some variables are poorly named.
* 0 - Code is difficult to read. Other developers would not be able to use your code.


##### Even work distribution: 20/20 pts
* 20 - You divided work fairly. Everyone had a significant contribution to your app


