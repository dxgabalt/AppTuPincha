import org.jetbrains.compose.desktop.application.dsl.TargetFormat
import org.jetbrains.kotlin.gradle.ExperimentalKotlinGradlePluginApi
import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.targets.js.webpack.KotlinWebpackConfig

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.composeCompiler)
}

kotlin {
    androidTarget {
        @OptIn(ExperimentalKotlinGradlePluginApi::class)
        compilerOptions {
            jvmTarget.set(JvmTarget.JVM_11)
        }
    }

    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        moduleName = "composeApp"
        browser {
            val rootDirPath = project.rootDir.path
            val projectDirPath = project.projectDir.path
            commonWebpackConfig {
                outputFileName = "composeApp.js"
                devServer = (devServer ?: KotlinWebpackConfig.DevServer()).apply {
                    static = (static ?: mutableListOf()).apply {
                        // Serve sources to debug inside browser
                        add(rootDirPath)
                        add(projectDirPath)
                    }
                }
            }
        }
        binaries.executable()
    }

    sourceSets {

        androidMain.dependencies {
            implementation(compose.preview)
            implementation(libs.androidx.activity.compose)
            implementation(libs.ktor.client.okhttp)
        }
        commonMain.dependencies {
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.viewmodel)
            implementation(libs.androidx.lifecycle.runtime.compose)
            implementation(libs.voyager.navigator)
            implementation(libs.voyager.transitions)
            implementation(libs.voyager.tabNavigator)
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.negotiation)
            implementation(libs.kotlin.serialization)
            implementation(libs.settings)
            implementation("org.jetbrains.kotlinx:kotlinx-datetime:0.4.0")
            implementation("media.kamel:kamel-image:1.0.3")
            // Cliente HTTP de Ktor (necesario para Kamel)
            implementation("io.ktor:ktor-client-core:2.3.4")
            implementation("io.ktor:ktor-client-cio:2.3.4")  // Para multiplataforma
            // Soporte adicional para deserialización (opcional si se requiere)
            implementation("io.ktor:ktor-client-json:2.3.4")
            implementation("io.ktor:ktor-serialization-kotlinx-json:2.3.4")
            // Corrutinas multiplataforma (por si aún no están agregadas)
            implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.7.3")
            implementation(platform("io.github.jan-tennert.supabase:bom:3.0.3"))
            implementation("io.github.jan-tennert.supabase:storage-kt")
            implementation("io.github.jan-tennert.supabase:postgrest-kt")
            implementation("io.github.jan-tennert.supabase:auth-kt")
            implementation("io.github.jan-tennert.supabase:realtime-kt")
        }
    }
}

android {
    namespace = "com.dxgabalt.tupincha"
    compileSdk = libs.versions.android.compileSdk.get().toInt()

    defaultConfig {
        applicationId = "com.dxgabalt.tupincha"
        minSdk = libs.versions.android.minSdk.get().toInt()
        targetSdk = libs.versions.android.targetSdk.get().toInt()
        versionCode = 1
        versionName = "1.0"
    }
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
    buildTypes {
        getByName("release") {
            isMinifyEnabled = false
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

dependencies {
    debugImplementation(compose.uiTooling)
}

