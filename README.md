## DruTask
A simple app that displays a categorized list of trending movies.

## Challenge description
- Code sample to display a list of trending movies and their details. 
- The app consists of 2 screens 1st is for categorized movies list and 2nd is for movie details.
- App load and cache the movies and their details in local data storage.

## What I have done
I started working on it by migrating gradle files to Kotlin DSL then modelling the project to clean architecture ( core, app, data, domain, presentation) modules,
applied clean architecture with MVI design pattern used Coroutines with Flow to handle heavy operations on the background thread,
Jetpack Compose for building UI,
Dagger 2 And Hilt for dependency injection, 
enabled pro-guard for obfuscating and securing the code base,

## Specifications
- Portrait and landscape, Clean architecture, MVI, Coroutines, Flow, Use-Cases, View State, Reactive code, Kotlin DSL, Enabling ProGuard, Paging 3, Dagger Hilt, Jetpack Compose and Modularization.

## Languages, libraries and tools used
 * [Kotlin](https://kotlinlang.org/)
 * [Jetpack Compose](https://developer.android.com/jetpack/compose?gclid=CjwKCAjwx_eiBhBGEiwA15gLNzq_E_R3QU3oiCLF2BXBQTH9heyjMdTZTedoSLFwALpclmoBzCEfOhoCzuQQAvD_BwE&gclsrc=aw.ds)
 * [Dagger Hilt](https://dagger.dev/hilt/)
 * [Paging 3](https://developer.android.com/topic/libraries/architecture/paging/v3-overview)
 
 ## Download links
 1- [APK](https://drive.google.com/file/d/1kFFNE7zQB37imxA0_59sbXQBgYLvNpPe/view?usp=sharing) || 2- [APK](https://github.com/iamahmedalaa/Dru-Task/blob/master/DruTask.apk)

## Screenshot

- Light Mode.

<img src="https://github.com/iamahmedalaa/Dru-Task/blob/master/MoviesList-light.png" width="300">|
<img src="https://github.com/iamahmedalaa/Dru-Task/blob/master/MovieDetails-light.png" width="300">|

- Dark Mode.

<img src="https://github.com/iamahmedalaa/Dru-Task/blob/master/MoviesList-Dark.png" width="300">|
<img src="https://github.com/iamahmedalaa/Dru-Task/blob/master/MovieDetails-Dark.png" width="300">|

 
## Requirements
- min SDK 21

## Installation
DruTask requires Android Studio version 3.6 or higher.

## Implementation
* In this project I'm using [Clean architecture with MVI Pattern](https://developer.android.com/jetpack/docs/guide)
as an application architecture adopted with the usage of UseCases with these design patterns in mind:-
- Repository Pattern
- Singleton
- Adapters

* Using Dagger2 or Hilt for dependency injection will make testing easier and make our code cleaner more readable and more handy when creating dependencies.
* Separation of concerns: The most important principle used in this project to avoid many lifecycle-related problems.
<img src="https://developer.android.com/topic/libraries/architecture/images/final-architecture.png"></a>
* Each component depends only on the component one level below it. For example, activities and Views depend only on a view model. The repository is the only class that depends on multiple other classes; in this example, the repository depends on a persistent data model and a remote backend data source.
This design creates a consistent and pleasant user experience. Regardless of whether the user comes back to the app several minutes after they've last closed it or several days later, they instantly see a user's information that the app persists locally. If this data is stale, the app's repository module starts updating the data in the background.
* Using to best of managing ViewState with less complex tools, using Sealed Classes and LiveData we created a solid source that we can expose to view to show what the app can do to the user without worrying about the side effects
```
class MoviesContract {

    data class State(
        val movies: Flow<PagingData<MovieUiModel>> = flow { PagingData.empty<MovieUiModel>() },
        ): ViewState

    sealed class Event: ViewEvent {
        data class OnItemClick(val movieUiModel: MovieUiModel) : Event()
    }

    object Navigation {
        object Routes {
            const val MY_HOME = "myHome"
        }
    }

    sealed class Effect: ViewSideEffect {
        sealed class Navigation: Effect() {
            data class ToMovieDetails(val movieId: Long) : Effect()
        }
    }

}
```
A View will a) emit its event to a ViewModel, and b) subscribe to this ViewModel in order to receive states needed to render its own UI.


Then ViewModel starts to delegate the event to it's suitable UseCase with thread handling in mind using RxJava (Logic Holders for each case)

```
 suspend fun loadMovies() {
        if (isInitialized) return

        val flow = getMoviesUseCase.loadMovies()
            .flowOn(dispatchers.io)
            .map { it.map { it.mapToUi() } }
            .cachedIn(viewModelScope)

        setState { copy(movies = flow) }

        // Update isInitialized flag
        isInitialized = true
    }
```
As the UseCase process and gets the required models from the repository it returns the result back to the ViewModel to start Exposing it as Flow to our Lifecycle Owner (Activity)   

```
class GetMoviesUseCase @Inject constructor(
    private val moviesRepository: MoviesRepository
) {

    suspend fun loadMovies() = moviesRepository.loadMovies()
}

```
          

then back to our View it will listen for any change in our state() ( ViewState Holder) and React to it.

```
 // Render movies content
    MoviesContentContainer(
        state = viewModel.viewState.value,
        onEvent = viewModel::setEvent
    )
```

```
   val moviesState = state.movies.collectAsLazyPagingItems()

   when(moviesState.itemCount){
            0-> {
            
            // Handle Loading error state 
                Column {
                    PagingState(
                        loadState = moviesState.loadState.mediator,
                        itemCount = moviesState.itemCount,
                        onRefresh = { moviesState.refresh() }
                    )
                }
            }else->{
            
            // Movies list
            MoviesList(
                movies = moviesState,
                onItemClick = { _, item ->
                    onEvent(Event.OnItemClick(item))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .weight(1f)
                )
            }
        }
```

## Immutability
Data immutability is embraced to help keep the logic simple. Immutability means that we do not need to manage data being mutated in other methods, in other threads, etc; because we are sure the data cannot change. Data immutability is implemented with State class in BaseViewModel.

## ViewModel LifeCycle
The ViewModel should outlive the View on configuration changes. For instance, on rotation, the Activity gets destroyed and recreated but your ViewModel should not be affected by this. If the ViewModel was to be recreated as well, all the ongoing tasks and cached latest ViewState would be lost.
We use the Architecture Components library to instantiate our ViewModel in order to easily have its lifecycle correctly managed.
