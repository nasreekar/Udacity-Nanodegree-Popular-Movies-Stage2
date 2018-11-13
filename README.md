# Udacity-Nanodegree-Popular-Movies-Stage2
 This is an extension to the Popular Movies app which is built in stage 1

 Stage 1 app will:

 - Upon launch, present the user with an grid arrangement of movie posters.
 - Allow the user to change sort order via a setting: The sort order can be by most popular, or by top rated
 - Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
   - original title
   - movie poster image - background poster
   - A plot synopsis (called overview in the api)
   - user rating (called vote_average in the api)
   - release date
   - original language
   
 Stage 2 app will:
 
 - Modify the existing sorting criteria for the main view to include an additional pivot to show their favorites collection.
 - In Details screen of the selected movie:
    - Allow users to read reviews of a selected movie
    - Allow users to view and play trailers (either in the youtube app or a web browser).
    - Allow users to see the cast of a selected movie
    - Allow users to mark a movie as a favorite in the details view by tapping a button (star).
    - Allow users to share the movie trailer link to social media or any other sharing platform on long press
   
## What Will I Learn After Stage 2?
 - Make use of Android Architecture Components (Room, LiveData, ViewModel and Lifecycle) to create a robust an efficient application.
 - Create a database using Room to store the names and ids of the user's favorite movies (and optionally, the rest of the information needed to display their favorites collection while offline).

## API Key
The movie information uses [The Movie Database (TMDb)](https://www.themoviedb.org/documentation/api) API.
To make your app work, you have to enter your own API key into `build.gradle` file.

```build.gradle (app)
API_KEY="Api Key"
```
## App Media

| ![Home Screen - Popular Movies](https://s8.postimg.cc/e9f8u4evp/Screen_Shot_2018-08-28_at_12.12.09_AM.png) | ![Sort Criteria](https://i.postimg.cc/nzyxDn3D/Screen-Shot-2018-11-13-at-10-56-44-AM.png)| ![Details Screen](https://i.postimg.cc/DyCskD0X/Screen-Shot-2018-11-13-at-10-53-01-AM.png) | ![Details Screen Extended](https://i.postimg.cc/9MtRxgsz/Screen-Shot-2018-11-13-at-10-53-24-AM.png) |
|:---:|:---:|:---:|:---:|
| ![Reviews](https://i.postimg.cc/FKSJSHZy/Screen-Shot-2018-11-13-at-10-54-06-AM.png)| ![Share](https://i.postimg.cc/fLhSfQ26/Screen-Shot-2018-11-13-at-10-53-41-AM.png) | ![Favorites](https://i.postimg.cc/T3FDhDr2/Screen-Shot-2018-11-13-at-10-54-34-AM.png) | 

