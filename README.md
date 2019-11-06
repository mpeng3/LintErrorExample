# LintErrorExample

This sample project demonstrates a bug in Android Studio's "ApiDetector" lint check that results in a crash.

The line of code that causes the issue is line 24 of [MainActivity.java](app/src/main/java/com/mpeng3/linterrorexample/MainActivity.java).  Commenting it out causes the lint check to operate normally.
Similarly, renaming the `foo()` methods so they're all unique also avoids the crash.

It appears the issue is related to the ApiDetector not being able to distinguish between the versions of `foo()` which take a `Runnable` vs. an `RxGetter<T>` when you pass a method reference as an argument.

To see the error, simply run `./gradlew lintDebug` from the command line.
