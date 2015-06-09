package net.juude.droidviews.dagger;

import net.juude.droidviews.DroidViewsApplication;

import javax.inject.Inject;

/**
 * Created by juude on 15-6-5.
 */
public class SomeThing {

    @Inject
    protected String mName;

    public SomeThing() {
        DroidViewsApplication.getInstance().getGraph().inject(this);
    }

    public String getName(){
        return mName;
    }
}
