plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("org.jetbrains.kotlin.kapt")
  //  id("com.google.devtools.ksp")
}

android {
    namespace = "com.example.lifebalance"
    compileSdk = 34

    defaultConfig {
        applicationId = "com.example.lifebalance"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8


    }
    kotlinOptions {
        jvmTarget = "1.8"


    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = "1.5.1"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {
    implementation(platform("androidx.compose:compose-bom:2024.04.00"))
    implementation("androidx.activity:activity-compose:1.8.2")
    implementation("androidx.compose.material3:material3")
    implementation("androidx.compose.ui:ui")
    implementation("androidx.compose.ui:ui-tooling")
    implementation("androidx.compose.ui:ui-tooling-preview")
    implementation("androidx.core:core-ktx:1.12.0")
    implementation("androidx.lifecycle:lifecycle-runtime-ktx:2.7.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-compose:2.7.0")
    implementation("androidx.navigation:navigation-compose:2.7.7")


    val room_version = "2.6.1"
    //Room
    implementation("androidx.room:room-runtime:$room_version")
    annotationProcessor("androidx.room:room-compiler:$room_version")
    implementation("androidx.room:room-ktx:$room_version")
     kapt("androidx.room:room-compiler:$room_version")

  //  implementation("androidx.room:room-runtime:$room_version")
  //  annotationProcessor("androidx.room:room-compiler:$room_version")
  //  ksp("androidx.room:room-compiler:$room_version")


    // Testing
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")

    implementation ("com.google.accompanist:accompanist-pager:0.28.0")

    implementation ("io.insert-koin:koin-android:3.4.0")
    implementation ("io.insert-koin:koin-androidx-compose:3.4.0")

    implementation ("androidx.compose.material:material:1.6.5")
    implementation ("androidx.navigation:navigation-compose:2.7.7")

    implementation ("io.github.vanpra.compose-material-dialogs:core:0.9.0")
    coreLibraryDesugaring("com.android.tools:desugar_jdk_libs:2.0.4")

    implementation ("androidx.datastore:datastore-preferences:1.1.1")
    implementation("androidx.datastore:datastore-preferences-core:1.1.1")
}
