plugins {
    id("com.android.application")
}

android {
    namespace = "com.example.java_project"
    compileSdk = 33

    defaultConfig {
        applicationId = "com.example.java_project"
        minSdk = 33
        targetSdk = 33
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
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
}

dependencies {

    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.9.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("com.android.volley:volley:1.2.0")
    testImplementation("junit:junit:4.13.2")
    implementation ("mysql:mysql-connector-java:8.0.33")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
    implementation("org.tensorflow:tensorflow-lite:2.8.0") // 텐서플로우 라이브러리 버전에 따라 수정할 수 있습니다.
}