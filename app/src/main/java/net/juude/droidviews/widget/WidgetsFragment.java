package net.juude.droidviews.widget;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.Toast;

import net.juude.droidviews.R;

import java.util.Calendar;

/**
 * Created by juude on 15-5-14.
 */
public class WidgetsFragment extends Fragment{
    private static final String TAG = "WidgetsFragment";
    private DateDialogFragment mDateDialogFragment;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_widgets, container, false);
        v.findViewById(R.id.date).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                mDateDialogFragment  = new DateDialogFragment();
                getActivity().getSupportFragmentManager()
                        .beginTransaction().
                        add(mDateDialogFragment, "hehe")
                        .commit()
                ;
            }
        });
        return v;
    }

    @Override
    public void onPause() {
        super.onPause();
        if(mDateDialogFragment != null) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .remove(mDateDialogFragment)
                    .commit();
            Log.d(TAG, "fragments : " + getActivity().getSupportFragmentManager().getFragments());
        }
    }

    public static class DateDialogFragment extends DialogFragment implements DatePickerDialog.OnDateSetListener{

        public DateDialogFragment() {
        }

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            Calendar cal=Calendar.getInstance();
            int year=cal.get(Calendar.YEAR);
            int month=cal.get(Calendar.MONTH);
            int day=cal.get(Calendar.DAY_OF_MONTH);
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
            showSetDate(year,monthOfYear,dayOfMonth);
        }

        public void showSetDate(int year,int month,int day) {
            Toast.makeText(getActivity(), year + "/" + month + "/" + day, Toast.LENGTH_SHORT).show();
        }

    }


}
