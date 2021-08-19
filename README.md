# MemesSharing
An Online Meme Sharing app with swipeable vidoes, user can like, share different videos, each viewpager item has one video to show.


<b>1. Flow of App</b>

https://user-images.githubusercontent.com/20916492/129900460-ff3fb60f-4c57-4b8a-97f0-d9d7577660a7.mp4

<a href="https://github.com/vikasmain/MemesSharing/blob/master/meme.apk"><b>APK</b></a>

1. User can see videos in viewpager by swiping up.
2. User can like, share the video after clicking on like, share icon.
3. User can play/pause the video after clicking on videoview.
4. User can see filtered listview of videos in gridview.

<b>2. Share Video to any other app</b>


https://user-images.githubusercontent.com/20916492/129901403-2a94001a-5e1f-47f7-9f77-7168eac88e73.mp4

Video will be available to share after downloading. During downloading video videoview will be paused and user will see a chooser for sharing video.


<h2>Screenshots of app:-</h2>
1. User can play the video after clicking on it(if its paused). icon will show with bounce animation.
<img src="https://user-images.githubusercontent.com/20916492/129902320-2cf698e5-c761-46b4-9480-0166b15b1039.jpg" width="200" height="400">

2. User can pause the video after clicking on it. icon will show with bounce animation.
<img src="https://user-images.githubusercontent.com/20916492/129902459-0293bff9-6166-40a9-acf3-6ba5c9109b23.jpg" width="200" height="400">

3. User can like or share the video after clicking on like and share imageview. 
<img src="https://user-images.githubusercontent.com/20916492/129902616-87d6e6ef-d625-4078-99d1-cf843e204cd3.jpg" width="200" height="400">

4. User will see listview of different videos types in gridview.
<img src="https://user-images.githubusercontent.com/20916492/129902941-ae8ca514-9d59-449e-bf27-d6ce2391ccf4.jpg" width="200" height="400">


<h2>Api end points:-</h2>

Used Heroku for deploying api's
1. https://memestik.herokuapp.com/items - api to add videos to list, those videos will available in viewpager
You can add data to this api using post request using postman like below.
<img width="500" height="400" alt="Screenshot 2021-08-18 at 6 26 21 PM" src="https://user-images.githubusercontent.com/20916492/129901900-e8b13f89-97f0-4980-b003-fc82ea7f6271.png">

2. https://memestik.herokuapp.com/types - api to add items in list video, after clicking on these list view items you will see filtered list view.
You can add data to this api using post request using postman like below.
<img width="500" height="400" alt="Screenshot 2021-08-18 at 5 12 28 PM" src="https://user-images.githubusercontent.com/20916492/129902150-78f69b38-b38d-44f5-b6c4-2fa2b3de5575.png">

<h2>CodeFlow of App</h2> 

<h2>There are two branches of app:-</h2>

<b>a) Master branch contain this code</b>

1. Used MVP architecture here:- https://github.com/vikasmain/MemesSharing/blob/master/app/src/main/java/com/example/memessharing/MemesContract.kt

2. Used coroutines for api calls here:- https://github.com/vikasmain/MemesSharing/blob/master/app/src/main/java/com/example/memessharing/presenter/MemesPresenter.kt#L68
 
3. Used Dagger hilt for dependency injection here:- https://github.com/vikasmain/MemesSharing/tree/master/app/src/main/java/com/example/memessharing/deps
 
4. Used StateFlows for different managing states:- https://github.com/vikasmain/MemesSharing/blob/master/app/src/main/java/com/example/memessharing/StateFlows.kt
 
5. Added tests for coroutines:- https://github.com/vikasmain/MemesSharing/tree/master/app/src/test/java/com/example/memessharing

b) <b><a href="https://github.com/vikasmain/MemesSharing/tree/feature/view-impl"><b>feature/view-impl</b></a> branch contain this code</b>

1. Removed activity reference from dagger hilt module:- https://github.com/vikasmain/MemesSharing/blob/feature/view-impl/app/src/main/java/com/example/memessharing/deps/MainModule.kt

2. Created a separate view implementation for view interface:- https://github.com/vikasmain/MemesSharing/blob/feature/view-impl/app/src/main/java/com/example/memessharing/view/MemeViewImpl.kt

3. Used MVP architecture here:- https://github.com/vikasmain/MemesSharing/blob/master/app/src/main/java/com/example/memessharing/MemesContract.kt

4. Used coroutines for api calls here:- https://github.com/vikasmain/MemesSharing/blob/master/app/src/main/java/com/example/memessharing/presenter/MemesPresenter.kt#L68
 
5. Used Dagger hilt for dependency injection here:- https://github.com/vikasmain/MemesSharing/tree/master/app/src/main/java/com/example/memessharing/deps
 
6. Used StateFlows for different managing states:- https://github.com/vikasmain/MemesSharing/blob/master/app/src/main/java/com/example/memessharing/StateFlows.kt
 
7. Added tests for coroutines:- https://github.com/vikasmain/MemesSharing/tree/master/app/src/test/java/com/example/memessharing
