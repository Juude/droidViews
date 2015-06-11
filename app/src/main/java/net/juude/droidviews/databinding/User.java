package net.juude.droidviews.databinding;

import android.databinding.Bindable;

/**
 * Created by juude on 15-6-9.
 */
public class User {
    private String name;
    private String sex;

    @Bindable
    public String getSex() {
        return sex;
    }

    @Bindable
    public void setSex(String mSex) {
        this.sex = sex;
    }

    @Bindable
    public String getName() {
        return name;
    }

    @Bindable
    public void setName(String mName) {
        this.name = name;
    }
}
