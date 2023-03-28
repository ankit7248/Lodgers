# Lodgers
Lodgers ➤  Flat, Pg Finder App 

In Project First I use Firebase for Login, Registration, Uploading images, Forget Password , Save Profile Database, Google Sign In, Facebook Sign In.


Option 1: Add Firebase using the Firebase console
Adding Firebase to your app involves tasks both in the Firebase console and in your open Android project (for example, you download Firebase config files from the console, then move them into your Android project).

Step 1: Create a Firebase project
Before you can add Firebase to your Android app, you need to create a Firebase project to connect to your Android app. Visit Understand Firebase Projects to learn more about Firebase projects.

Create a Firebase project

Step 2: Register your app with Firebase
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

Step 3: Add a Firebase configuration file
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
Step 4: Add Firebase SDKs to your app
In your module (app-level) Gradle file (usually <project>/<app-module>/build.gradle), add the dependencies for the Firebase products that you want to use in your app. We recommend using the Firebase Android BoM to control library versioning.

Analytics enabled
Analytics not enabled
Kotlin+KTX
Java

dependencies {
  // ...

  // Import the Firebase BoM
  implementation platform('com.google.firebase:firebase-bom:31.3.0')

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



Option 2: Add Firebase using the Firebase Assistant
The Firebase Assistant registers your app with a Firebase project and adds the necessary Firebase files, plugins, and dependencies to your Android project — all from within Android Studio!

Open your Android project in Android Studio, then make sure that you're using the latest versions of Android Studio and the Firebase Assistant:

Windows / Linux: Help > Check for updates
macOS: Android Studio > Check for updates
Open the Firebase Assistant: Tools > Firebase.

In the Assistant pane, choose a Firebase product to add to your app. Expand its section, then click the tutorial link (for example, Analytics > Log an Analytics event).

Click Connect to Firebase to connect your Android project with Firebase.

What does this workflow do?

Click the button to add a desired Firebase product (for example, Add Analytics to your app).

Sync your app to ensure that all dependencies have the necessary versions.

In the Assistant pane, follow the remaining setup instructions for your selected Firebase product.

Add as many other Firebase products as you'd like via the Firebase Assistant!

Are you using Kotlin?
You can use the alternative Kotlin extensions (KTX) libraries which enable you to write beautiful and idiomatic Kotlin code.

Do you want an easier way to manage library versions?
You can use the Firebase Android BoM to manage your Firebase library versions and ensure that your app is always using compatible library versions.

That's it! Make sure to check out the recommended next steps.

If you're having trouble getting set up, though, visit the Android troubleshooting & FAQ.



Available libraries
This section lists the Firebase products supported for Android and their Gradle dependencies. Learn more about these Firebase Android libraries:

Reference documentation (Kotlin+KTX | Java)

Firebase Android SDK GitHub repo

Note that when using the Firebase Android BoM, you don't specify individual library versions when you declare Firebase library dependencies in build.gradle.

Kotlin+KTX
Java
Service or Product	Gradle dependency	Latest
version	Add Analytics?
Firebase Android BoM
(Bill of Materials)	com.google.firebase:firebase-bom
The latest Firebase BoM version contains the latest versions of each Firebase Android library. To learn which library versions are mapped to a specific BoM version, review the release notes for that BoM version.

31.3.0	
AdMob	com.google.android.gms:play-services-ads	21.5.0	
Analytics	com.google.firebase:firebase-analytics-ktx	21.2.1	
App Check custom provider	com.google.firebase:firebase-appcheck-ktx	16.1.2	
App Check debug provider	com.google.firebase:firebase-appcheck-debug	16.1.2	
App Check Play Integrity provider	com.google.firebase:firebase-appcheck-playintegrity	16.1.2	
App Distribution	com.google.firebase:firebase-appdistribution	16.0.0-beta07	
App Distribution API	com.google.firebase:firebase-appdistribution-api-ktx	16.0.0-beta07	
App Distribution plugin	com.google.firebase:firebase-appdistribution-gradle	4.0.0	
Authentication	com.google.firebase:firebase-auth-ktx	21.1.0	
Cloud Firestore	com.google.firebase:firebase-firestore-ktx	24.4.5	
Cloud Functions for Firebase Client SDK	com.google.firebase:firebase-functions-ktx	20.2.2	
Cloud Messaging	com.google.firebase:firebase-messaging-ktx	23.1.2	
Cloud Storage	com.google.firebase:firebase-storage-ktx	20.1.0	
Crashlytics	com.google.firebase:firebase-crashlytics-ktx	18.3.6	
Crashlytics NDK	com.google.firebase:firebase-crashlytics-ndk	18.3.6	
Crashlytics plugin	com.google.firebase:firebase-crashlytics-gradle	2.9.4	
Dynamic feature module support	com.google.firebase:firebase-dynamic-module-support	16.0.0-beta03	
Dynamic Links	com.google.firebase:firebase-dynamic-links-ktx	21.1.0	
In-App Messaging	com.google.firebase:firebase-inappmessaging-ktx	20.3.1	
(required)
In-App Messaging Display	com.google.firebase:firebase-inappmessaging-display-ktx	20.3.1	
(required)
Firebase installations	com.google.firebase:firebase-installations-ktx	17.1.3	
Firebase ML Model Downloader API	com.google.firebase:firebase-ml-modeldownloader-ktx	24.1.2	
Performance Monitoring	com.google.firebase:firebase-perf-ktx	20.3.1	
Performance Monitoring plugin	com.google.firebase:perf-plugin	1.4.2	
Realtime Database	com.google.firebase:firebase-database-ktx	20.1.0	
Remote Config	com.google.firebase:firebase-config-ktx	21.3.0	
Google Play services plugin	com.google.gms:google-services	4.3.15	
Deprecated libraries
App Check SafetyNet provider	com.google.firebase:firebase-appcheck-safetynet	16.1.2	
App Indexing	com.google.firebase:firebase-appindexing	20.0.0	
Firebase ML Kit libraries



Next steps
Add Firebase services to your app:

Gain insights on user behavior with Analytics.

Set up a user authentication flow with Authentication.

Store data, like user information, with Cloud Firestore or Realtime Database.

Store files, like photos and videos, with Cloud Storage.

Trigger backend code that runs in a secure environment with Cloud Functions.

Send notifications with Cloud Messaging.

Find out when and why your app is crashing with Crashlytics.

Learn about Firebase:

Visit Understand Firebase Projects to learn more about Firebase projects and best practices for projects.

Visit Learn more about Android and Firebase if you have questions about concepts that are unfamiliar or specific to Firebase and Android development.

Explore sample Firebase apps.

Get hands-on experience with the Firebase Android Codelab.

Learn more with the Firebase in a Weekend course.

Prepare to launch your app:

Set up budget alerts for your project in the Google Cloud Console.
Monitor the Usage and billing dashboard in the Firebase console to get an overall picture of your project's usage across multiple Firebase services.
Review the Firebase launch checklist.
Having trouble with Firebase and your Android project? Visit the Android troubleshooting & FAQ.
