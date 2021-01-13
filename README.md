# README #

### What is this repository for? ###
* Sample Pokemon Search App
* Given a Pokemon name the app searches for its existence and provides its Pokeman's sprite and it's shakespearean translated text on a UI component
* Repository has two parts to it; (1) SDK (2) Application

**(1) SDK - Shakemon**
* Acts as a common service provider for 3 services
  1. Service that provides "Shakspearean text for a given Pokemon name"
  2. Service that provides "Sprite of a given Pokemon name"
  3. Service that displays "Shakspearean text" and "Sprite of a given Pokemon" on a dialog UI
        
**(2) Application**
Single screen app that has below features
*Search screen with 
  1. Search bar to search a Pokemon name and display contents(Sprite & it's shakespearean text) on UI
  2. Option to save as favourite from the search history
*My Favourite screen 
  1. Displays user's favourite Pokemon list
  2. Additionally, user can see the Pokemon details(Sprite & it's shakespearean text) on UI

### How do I get set up? ###
**New Application with SDK please follow below steps**
1. Download install Android studio (https://developer.android.com/studio?authuser=1)
2. Clone or download the repository from the Github repos
3. Open the project in Android Studio
4. Initiate Build
5. Run 'app'(Cmd + R) to build and install the binary on to Any Android Device or 
    Android emulator that is connected via cable to the computer from Android Studio.
6. Launch the App to see the result

**Integrating SDK only into existing application**
1. Download the repo and open the project in Android Studio
2. Build the project
3. SDK "shakemon.aar" should have generated in the *build* folder 
(shakemon_release.aar - for release; shakemon_debug.arr for debug version)
4. Open the existing project in Android studio
5. Click File > New > New Module.
6. Click Import .JAR/.AAR Package then click Next.
7. Enter the location of the "shakemon.aar" file then click Finish.
8. Android studio generates module directory and creates below line in app's build.gradle file
*artifacts.add("default", file('libraryName'))*

### Architecture used ###
* App is developed using Kotlin language
* Application is designed and implemented with MVVM Architecture style + Repository+ HILT DI
* Unit tests are done with Mockito

### Libraries/Components used ###
1. Retrofit for networking
2. androidx.*
3. Picasso for image download
4. Room database for local storage (app only)
5. HILT dependency framework (app only)
6. Mockito for automate testing