# Proguard rules for Aura AI
# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles settings in build.gradle.kts.

# Keep Hilt / Dagger generated classes
-keep class * implements dagger.hilt.internal.GeneratedComponent { *; }
-keep class * extends dagger.hilt.internal.GeneratedComponent { *; }

# Keep Room DB generated schemas and entities
-keep class * extends androidx.room.RoomDatabase { *; }

# Keep Firebase classes
-keep class com.google.firebase.** { *; }
