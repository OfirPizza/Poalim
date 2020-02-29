

Overview:
 API - 26
 3rd library -navigation,rxJava,coroutines,ViewModel,Glide,Retrofit,Koin,ROOM


what architecture have you used and why?
 I used MVVM - it's very modeler architecture also it is ver easy for data binding of views

what was the most difficult part of the assignment?
 The most difficult was writing Uni tests

describe the main components, layers, architecture
 In the app you have single Activity with 3 fragments all of them Implement the MVVM architecture,
 And we have 1 service for cleaning the cache.
 I have added a connectivity class to handle cases when there is no internet.
 For the navigation part i used the navGraph and bottom navigation for viewing the liked list and the list of the movies

if there is any known bugs?
  1. in case of getting an empty list from the server(TMDB) there is no handling for this case i am assuming that TMDB server will work fine and it will not change there API
  2. in case of cleaning the cache in can be happen more then one day due the doze mode but it will happen when the device will "wake up" (according to google docs)


what would you change in the project?
I would change all my Rxjava calls to coroutines calls