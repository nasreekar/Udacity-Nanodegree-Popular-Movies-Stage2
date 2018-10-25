# Udacity-Nanodegree-Popular-Movies-Stage1
 An app to help users discover popular and recent movies.

 This app will:

 - Upon launch, present the user with an grid arrangement of movie posters.
 - Allow the user to change sort order via a setting: The sort order can be by most popular, or by top rated
 - Allow the user to tap on a movie poster and transition to a details screen with additional information such as:
   - original title
   - movie poster image - background poster
   - A plot synopsis (called overview in the api)
   - user rating (called vote_average in the api)
   - release date
   - original language
   
## What Will I Learn After Stage 1?
You will fetch data from the Internet with theMovieDB API.
You will use adapters and custom list layouts to populate list views.
You will incorporate libraries to simplify the amount of code you need to write

## API Key
The movie information uses [The Movie Database (TMDb)](https://www.themoviedb.org/documentation/api) API.
To make your app work, you have to enter your own API key into `build.gradle` file.

```build.gradle (app)
API_KEY="Api Key"
```
## App Media

| ![Home Screen - Popular Movies](https://s8.postimg.cc/e9f8u4evp/Screen_Shot_2018-08-28_at_12.12.09_AM.png) | ![Sort Criteria](https://s8.postimg.cc/v9y52tf2d/Screen_Shot_2018-08-28_at_12.12.58_AM.png)| ![Details Screen](https://s8.postimg.cc/jku5eu8o5/Screen_Shot_2018-08-28_at_12.13.21_AM.png) | ![Top Rated Movies](https://s8.postimg.cc/63x6vz62d/Screen_Shot_2018-08-28_at_12.13.41_AM.png) |
|:---:|:---:|:---:|:---:|
