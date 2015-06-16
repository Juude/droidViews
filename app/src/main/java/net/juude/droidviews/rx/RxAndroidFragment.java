package net.juude.droidviews.rx;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

import net.juude.droidviews.R;

import rx.Subscriber;
import rx.Subscription;
import rx.android.app.AppObservable;
import rx.observables.ConnectableObservable;
import rx.subscriptions.Subscriptions;

/**
 * Created by juude on 15/5/25.
 */
public class RxAndroidFragment extends Fragment {
    private ConnectableObservable<String> strings;
    private Subscription subscription = Subscriptions.empty();

    public RxAndroidFragment() {
        super();
        setRetainInstance(true);
        net.juude.library.CircleView
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
        v.setOnClickListener(v1 -> Toast.makeText(getActivity(), "clicked", Toast.LENGTH_LONG).show());
        return v;
    }

    @Override
    public void onDestroyView() {
        subscription.unsubscribe();
        super.onDestroyView();
    }
}
