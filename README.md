## Github Search

A simple app that can be used to search repositories on Github!


### How it is structured

Completely written in Kotlin. The architecture is MVVM which is implemented using Android architecture components (ViewModel, LiveData, and LifeCycle). Repository pattern is used for remote data access.

Here is a list of third-party libraries that are used:
- OkHttp and Retrofit for networking
- Dagger2 for DI
- Glide for image loading
- Coroutines for easy asynchronous programming
- Google Android components like Material, RecyclerView and...
- Gson for dealing with JSON
- JUnit and Mockito for unit testing

### What on earth is `TlsProviderInstaller`?
As you may know, Github API enforces TLS1.2. On old Android versions (<API21) TLS1.2 it is included but not enabled by default.
As we are supporting API19+ for this app, We use `TlsProviderInstaller` which uses google play services to install the latest cipher suites and enables TLS1.2. We also use `TlsSocketFactory` which is an extension for `OkHttpBuilder` that installs a `SocketFactory` so that the use of TLS1.2 is added to the network requests.