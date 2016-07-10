package net.juude.rxdemos.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import net.juude.rxdemos.R;
import net.juude.rxdemos.data.QuoteItem;
import net.juude.rxdemos.data.QuotesRepository;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.observables.ConnectableObservable;
import rx.schedulers.Schedulers;
import rx.subscriptions.Subscriptions;

/**
 * Created by juude on 15/5/25.
 */
public class RxAndroidFragment extends Fragment {
    private static final String TAG = "RxAndroidFragment";
    private ConnectableObservable<String> strings;
    private Subscription subscription = Subscriptions.empty();

    public RxAndroidFragment() {
        super();
        setRetainInstance(true);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        strings = SampleObservables.numberStrings(1, 50, 250).publish();
        strings.connect();
        View v = inflater.inflate(R.layout.fragment_rxandroid, null);
        final EditText edit_text = (EditText) v.findViewById(R.id.edit_text);
        subscription = AppObservable.bindFragment(this, strings).subscribe(new Subscriber<String>() {
            @Override
            public void onCompleted() {
                Toast.makeText(getActivity(), "Done!", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Throwable throwable) {

            }

            @Override
            public void onNext(String s) {
                edit_text.setText(s);
            }
        });
        QuotesRepository.getQuotesObservable()
                .filter(new Func1<List<QuoteItem>, Boolean>() {
                    @Override
                    public Boolean call(List<QuoteItem> quoteItems) {
                        return Boolean.TRUE;
                    }
                })
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<QuoteItem>>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onNext(List<QuoteItem> quoteItems) {
                        for (QuoteItem quoteItem : quoteItems) {
                            Log.d(TAG, "quoteItem" + quoteItem);
                        }
                    }
                });
        return v;
    }

    @Override
    public void onDestroyView() {
        subscription.unsubscribe();
        super.onDestroyView();
    }
}
