package net.juude.transitiondemos;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

/**
 * Created by juude on 16/6/1.
 */
public class MainActivity extends AppCompatActivity{
    private MenuItem mMemuReplace;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setFragment(ListFragment.class);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        mMemuReplace = menu.add("transition");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item == mMemuReplace) {
            setFragment(TransitionFragment.class);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setFragment(Class<?> clazz) {
        getSupportFragmentManager().
                beginTransaction()
                .replace(R.id.fragment_container,
                        Fragment.instantiate(this, clazz.getName()))
                .commit();
    }
}
