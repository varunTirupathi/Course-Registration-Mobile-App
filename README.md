# Course-Registration-Mobile-App
This Android mobile application is developed as part of my Master course curriculum.

Components Used in application: 1) Recycler view
                                2) Tab layout
                                3) Fragments
                                4) Sqlite database
                                5) Http client
                                6) picasso

How Application works:

There are two Tab views in the application. 1) Available courses and 2) Enrolled courses
In Tab 1 there are two layouts where one layout shows the list of available courses in recycler view and the other layout shows the course details. When the user clicks on any course from available courses the corresponding course details will be automatically popped up in the edit text details so that user can view the course details and then register for the course.

Validations in Tab 1:

1)	User cannot Register for the course without selecting the course if do so, it will show a message as “Cannot register for the course without course details”
2)	Cannot register for the same course again, If the course is already registered. 
There is a button: Register course, when user clicks on that the course that the user has chosen will be added to the Registered courses which we can see in the Tab 2 which is names as Registered courses.
In Tab 2 there are two courses(CS255 and CS263) those are already preregistered when we open the registered courses tab those two courses will be already exists.
For dropping the course Onlongclicklistner feature is used.
To drop any course by long clicking on the course the course will get dropped.

