package com.mpeng3.linterrorexample;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import rx.Single;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // This is fine, calls runnable version
        foo(this::getRunnable).subscribe();

        // This is fine, calls Single version
        foo(this.getIntSingle()).subscribe();

        // This is NOT fine - calls RxGetter version, causes ApiChecker to crash
        // Comment out this line to get lint to pass, uncomment for failure
        foo(this::getIntSingle).subscribe();
    }

    private Single<Integer> getIntSingle() {
        return Single.just(5);
    }

    private void getRunnable() {
        // Doesn't have to do anything
    }

    protected interface RxGetter<T> {
        rx.Single<T> call();
    }

    protected static <T> Single<T> foo(@NonNull Runnable runnable) {
        runnable.run();
        return Single.error(new Throwable("Some error from foo() as a placeholder"));
    }

    protected static <T> Single<T> foo(@NonNull Single<T> single) {
        return single;
    }

    protected static <T> Single<T> foo(@NonNull RxGetter<T> getter) {
        return getter.call();
    }
}
