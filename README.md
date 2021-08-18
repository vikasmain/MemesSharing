# MemesSharing
An Online Meme Sharing app with swipeable vidoes, user can like, share different videos, each viewpager item has one video to show.


<b>Flow of App</b>

https://user-images.githubusercontent.com/20916492/129900460-ff3fb60f-4c57-4b8a-97f0-d9d7577660a7.mp4

1. User can see videos in viewpager by swiping.
2. User can like, share the video after clicking on like, share icon.
3. User can play/pause the video after clicking on videoview.
4. User can see filtered listview of videos in gridview.


<h2>CodeFlow of App</h2> 

1. Used MVP architecture here:- https://github.com/vikasmain/MemesSharing/blob/master/app/src/main/java/com/example/memessharing/MemesContract.kt

2. Used coroutines for api calls here:- https://github.com/vikasmain/MemesSharing/blob/master/app/src/main/java/com/example/memessharing/presenter/MemesPresenter.kt
 
3. Used Dagger hilt for dependency injection here:- https://github.com/vikasmain/MemesSharing/tree/master/app/src/main/java/com/example/memessharing/deps
 
4. Used StateFlows for different managing states:- https://github.com/vikasmain/MemesSharing/blob/master/app/src/main/java/com/example/memessharing/StateFlows.kt
 
5. Added tests for coroutines:- https://github.com/vikasmain/MemesSharing/tree/master/app/src/test/java/com/example/memessharing
