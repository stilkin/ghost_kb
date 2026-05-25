plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.ktlint)
    alias(libs.plugins.detekt)
}

android {
    namespace = "be.pocito.pboard"
    compileSdk {
        version = release(36)
    }

    defaultConfig {
        applicationId = "be.pocito.ghostkb"
        minSdk = 30
        targetSdk = 36
        versionCode = 25
        versionName = "3.1"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    signingConfigs {
        create("release") {
            storeFile = file(System.getProperty("user.home") + "/ghostkb-release.keystore")
            storePassword = System.getenv("GHOSTKB_KEYSTORE_PASSWORD")
            keyAlias = "ghostkb"
            keyPassword = System.getenv("GHOSTKB_KEY_PASSWORD")
        }
    }

    buildTypes {
        release {
            signingConfig = signingConfigs.getByName("release")
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro",
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
}

detekt {
    config.setFrom(rootProject.files("detekt.yml"))
    buildUponDefaultConfig = true
}

dependencies {
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.appcompat)
    implementation(libs.material)
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}
