# Add project specific ProGuard rules here.
# By default, the flags in this file are appended to flags specified
# in /home/shang/Android/Sdk/tools/proguard/proguard-android.txt
# You can edit the include path and order by changing the proguardFiles
# directive in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# Add any project specific keep options here:

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}
-dontnote
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontskipnonpubliclibraryclasses
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
#-ignorewarnings
-dontnote
-ignorewarnings
#-dontoptimize

-renamesourcefileattribute SourceFile

-keepattributes InnerClasses,EnclosingMethod,SourceFile,LineNumberTable,Signature,*Annotation*


-keepclasseswithmembernames class * {
    native <methods>;
}

# keep setters in Views so that animations can still work.
-keepclassmembers public class * extends android.view.View {
   void set*(***);
   *** get*();
}

-keepclassmembers class **.R$* {
    public static <fields>;
}

-keep class **.R$* {*;}

-keep class **.R {*;}


-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class * extends android.inputmethodservice.InputMethodService
-keep public class * extends android.support.v4.app.Fragment
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.webkit.WebView





-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet);
}
-keepclasseswithmembers class * {
    public <init>(android.content.Context, android.util.AttributeSet, int);
}
-keepclassmembers class * extends android.app.Activity {
    public void *(android.view.View);
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keep class * implements android.os.Parcelable {
    public static final android.os.Parcelable$Creator *;
}

-keep class * implements android.os.IInterface {*;}