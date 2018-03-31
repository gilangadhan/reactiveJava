package org.sandec.introrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.concurrent.TimeUnit;

import io.reactivex.Maybe;
import io.reactivex.MaybeObserver;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Single;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import io.reactivex.schedulers.Schedulers;

public class MainActivity extends AppCompatActivity {
    //TODO Kenalin
    Button submit;
    EditText input;
    TextView output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //TODO hubungin
        submit = findViewById(R.id.btnSubmit);
        input = findViewById(R.id.edtInput);
        output = findViewById(R.id.txtOutput);

        //TODO ngapain
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                observaleMapFilter();
            }
        });
    }

    public void observable() {
        //todo data
        int[] data = {1, 2, 3, 4};
        //todo execute
        Observable.fromArray(data).subscribeOn(Schedulers.newThread()).delay(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<int[]>() {
            @Override
            public void onSubscribe(Disposable d) {
                //TODO ketika di proses pertama kali
                output.setText("Proses on subscribe");
            }

            @Override
            public void onNext(int[] ints) {
                //TODO on next
                for (int datas : ints) {
                    output.setText(String.valueOf(datas));
                    Log.d("onNext", String.valueOf(datas));
                }
            }

            @Override
            public void onError(Throwable e) {
                output.setText(String.valueOf(e.getMessage()));
            }

            @Override
            public void onComplete() {
                output.setText("Complete");
            }
        });
    }

    public void single(String inputs) {
        Single.just(inputs).subscribeOn(Schedulers.newThread()).delay(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                output.setText("On Subcribe");
            }

            @Override
            public void onSuccess(String s) {
                output.setText(s);
            }

            @Override
            public void onError(Throwable e) {
                output.setText(String.valueOf(e.getMessage()));
            }
        });
    }

    public void singleMap(String input) {
        Single.just(input).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return Integer.parseInt(s);
            }
        }).subscribeOn(Schedulers.newThread()).delay(1, TimeUnit.SECONDS).observeOn(AndroidSchedulers.mainThread()).subscribe(new SingleObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {
                output.setText("On Subscribe");
            }

            @Override
            public void onSuccess(Integer integer) {
                output.setText("Mengubah nilai : " + integer);
            }

            @Override
            public void onError(Throwable e) {
                output.setText("Masukkan angka");
            }
        });
    }

    //TODO Maybe
    public void maybe(String data) {
        Maybe.just(data).subscribeOn(Schedulers.newThread()).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return Integer.parseInt(s);
            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer <= 10;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(new MaybeObserver<Integer>() {
            @Override
            public void onSubscribe(Disposable d) {output.setText("On Subcribe");}
            @Override
            public void onSuccess(Integer integer) {output.setText(String.valueOf(integer));}
            @Override
            public void onError(Throwable e) {output.setText(String.valueOf(e.getMessage()));}
            @Override
            public void onComplete() {output.setText("Success");}
        });
    }

    public void observaleMapFilter(){
        Observable.just("8","3","9","4","1","2","6").subscribeOn(Schedulers.newThread()).map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) throws Exception {
                return Integer.parseInt(s);
            }
        }).filter(new Predicate<Integer>() {
            @Override
            public boolean test(Integer integer) throws Exception {
                return integer%2 == 0;
            }
        }).observeOn(AndroidSchedulers.mainThread()).delay(1, TimeUnit.SECONDS).subscribe(new Observer<Integer>() {
            @Override
            public void onSubscribe(Disposable s) {output.setText("ON Subcribe");}
            @Override
            public void onNext(Integer integer) {Log.d("OnNext", String.valueOf(integer));}
            @Override
            public void onError(Throwable t) {output.setText(String.valueOf(t.getMessage()));}
            @Override
            public void onComplete() {output.setText("COMPLETE");}
        });
    }
}
