# MapLibre usa JNI e reflexão
-keep class org.maplibre.** { *; }
-dontwarn org.maplibre.**
# Binder crus da TopWay (spec 006)
-keep class app.mylauncher.data.vehicle.topway.** { *; }
# Auxiliar de navegação embutida: carregado por nome via app_process (spec 008), não por código.
-keep class app.mylauncher.nav.embedded.NavHelper { *; }
-keep class app.mylauncher.nav.embedded.NavHelper$* { *; }
