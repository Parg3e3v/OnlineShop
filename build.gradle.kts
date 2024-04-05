buildscript {
    dependencies {
        classpath("org.jetbrains.kotlin:kotlin-serialization:1.9.10")
    }
}// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    alias(libs.plugins.androidApplication) apply false
    alias(libs.plugins.jetbrainsKotlinAndroid) apply false
    alias(libs.plugins.androidLibrary) apply false
    alias(libs.plugins.daggerHiltAndroid) apply false
    alias(libs.plugins.devtoolsKsp) apply false
}