# MapLibre usa JNI e reflexão
-keep class org.maplibre.** { *; }
-dontwarn org.maplibre.**
# Binder crus da TopWay (spec 006)
-keep class app.mylauncher.data.vehicle.topway.** { *; }
