# Lodgers
Lodgers ➤  Flat, Pg Finder App 

In Project I use Firebase for Login, Registration, Uploading images, Forget Password , Save Profile Database, Google Sign In, Facebook Sign In.

<h1 align="left">Option1: Add Firebase using the Firebase console</h1>

Adding Firebase to your app involves tasks both in the Firebase console and in your open Android project (for example, you download Firebase config files from the console, then move them into your Android project).

<h2 align="left">Step 1: Create a Firebase project</h2>

Before you can add Firebase to your Android app, you need to create a Firebase project to connect to your Android app. Visit Understand Firebase Projects to learn more about Firebase projects.

Create a Firebase project

<h2 align="left">Step 2: Register your app with Firebase</h2>
To use Firebase in your Android app, you need to register your app with your Firebase project. Registering your app is often called "adding" your app to your project.

Note: Check out our best practices for adding apps to a Firebase project, including how to handle multiple variants.
Go to the Firebase console.

In the center of the project overview page, click the Android icon (plat_android) or Add app to launch the setup workflow.

Enter your app's package name in the Android package name field.

What's a package name, and where do you find it?

Make sure to enter the package name that your app is actually using. The package name value is case-sensitive, and it cannot be changed for this Firebase Android app after it's registered with your Firebase project.
(Optional) Enter other app information: App nickname and Debug signing certificate SHA-1.

How are the App nickname and the Debug signing certificate SHA-1 used within Firebase?

Click Register app.

<h2 align="left">Step 3: Add a Firebase configuration file</h2>
Download and then add the Firebase Android configuration file (google-services.json) to your app:

Click Download google-services.json to obtain your Firebase Android config file.

Move your config file into the module (app-level) root directory of your app.

What do you need to know about this config file?

To make the values in your google-services.json config file accessible to Firebase SDKs, you need the Google services Gradle plugin (google-services).

In your root-level (project-level) Gradle file (<project>/build.gradle), add the Google services plugin as a buildscript dependency:


buildscript {

    repositories {
      // Make sure that you have the following two repositories
      google()  // Google's Maven repository
      mavenCentral()  // Maven Central repository
    }

    dependencies {
      ...

      // Add the dependency for the Google services Gradle plugin
      classpath 'com.google.gms:google-services:4.3.15'
    }
}

allprojects {
  ...

  repositories {
    // Make sure that you have the following two repositories
    google()  // Google's Maven repository
    mavenCentral()  // Maven Central repository
  }
}
In your module (app-level) Gradle file (usually <project>/<app-module>/build.gradle), add the Google services plugin:


plugins {
    id 'com.android.application'

    // Add the Google services Gradle plugin
    id 'com.google.gms.google-services'
    ...
}

<h2 align="left">Step 4: Add Firebase SDKs to your app</h2>

In your module (app-level) Gradle file (usually <project>/<app-module>/build.gradle), add the dependencies for the Firebase products that you want to use in your app. We recommend using the Firebase Android BoM to control library versioning.
Analytics enabled 
<p> </p>
Kotlin+KTX


dependencies {
  // ...

  // Import the Firebase BoM
  implementation platform('com.google.firebase:firebase-bom:31.4.0')

  // When using the BoM, you don't specify versions in Firebase library dependencies

  // Add the dependency for the Firebase SDK for Google Analytics
  implementation 'com.google.firebase:firebase-analytics-ktx'

  // TODO: Add the dependencies for any other Firebase products you want to use
  // See https://firebase.google.com/docs/android/setup#available-libraries
  // For example, add the dependencies for Firebase Authentication and Cloud Firestore
  implementation 'com.google.firebase:firebase-auth-ktx'
  implementation 'com.google.firebase:firebase-firestore-ktx'
}
By using the Firebase Android BoM, your app will always use compatible versions of Firebase Android libraries.



After adding the dependencies for the products you want to use, sync your Android project with Gradle files.

 Are you getting a build failure about invoke-custom support and enabling desugaring? Here's how to fix it.

That's it! You can skip ahead to check out the recommended next steps.

If you're having trouble getting set up, though, visit the Android troubleshooting & FAQ.


<h3> Available libraries </h3>
This section lists the Firebase products supported for Android and their Gradle dependencies. Learn more about these Firebase Android libraries:

Reference documentation (<a href="https://firebase.google.com/docs/reference/kotlin/packages">Kotlin+KTX</a> | Java)


Firebase Android SDK <a href = "https://github.com/firebase/firebase-android-sdk">GitHub repo</a>


<h2> Next steps </h2>
<h3> Add Firebase services to your app:</h3>

Gain insights on user behavior with Analytics.

Set up a user authentication flow with Authentication.

Store data, like user information, with Cloud Firestore or Realtime Database.

Store files, like photos and videos, with Cloud Storage.

Trigger backend code that runs in a secure environment with Cloud Functions.

Send notifications with Cloud Messaging.

Find out when and why your app is crashing with Crashlytics.

<hr>

<h2> Using LottieAnimationView :- To get better Ui through Animation</h2>
<hr> 
<h2> Home Page (dependencies) </h2> Used of : - 

<h2>1. NavGraph </h3>
<h2>2. Fragments </h3>
<h2>3. Menu Bar Navigation </h3>

<h2> Home Page(XML) -> </h2>
<p> 1. Put in Search View ->  to find the location of flat where user want </p>
<p> 2. Recommend -> Recommend of flat or Pg , by the given location </p> 
<p> 3. Near You -> Near you give the flat or Pg's , which location user want to take nearby flat or pg </p>
<p> 4. Browse by Locality -> it gives you a Flat | Pg's in a particular places </p>





