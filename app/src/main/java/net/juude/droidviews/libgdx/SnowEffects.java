package net.juude.droidviews.libgdx;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.ParticleEffectPool;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.CheckBox;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Array;

/**
 * Created by juude on 15-9-14.
 */
public class SnowEffects extends ApplicationAdapter {
    private SpriteBatch spriteBatch;
    com.badlogic.gdx.graphics.g2d.ParticleEffect effect;
    ParticleEffectPool effectPool;
    Array<ParticleEffectPool.PooledEffect> effects = new Array();
    ParticleEffectPool.PooledEffect latestEffect;
    float fpsCounter;
    CheckBox skipCleanup;
    Button clearEmitters;
    Label logLabel;

    @Override
    public void create () {
        spriteBatch = new SpriteBatch();

        effect = new com.badlogic.gdx.graphics.g2d.ParticleEffect();
        effect.load(Gdx.files.internal("particle/snowParticle.p"), Gdx.files.internal("particle"));
        effect.setPosition(Gdx.graphics.getWidth() / 2, Gdx.graphics.getHeight() / 2);
        effectPool = new ParticleEffectPool(effect, 20, 20);

        InputProcessor inputProcessor = new InputAdapter() {

            public boolean touchDragged (int x, int y, int pointer) {
                if (latestEffect != null) latestEffect.setPosition(x, Gdx.graphics.getHeight() - y);
                return false;
            }

            public boolean touchDown (int x, int y, int pointer, int newParam) {
                latestEffect = effectPool.obtain();
                latestEffect.setEmittersCleanUpBlendFunction(true);
                latestEffect.setPosition(x, Gdx.graphics.getHeight() - y);
                effects.add(latestEffect);

                return false;
            }

        };

        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(inputProcessor);

        Gdx.input.setInputProcessor(multiplexer);
    }

    @Override
    public void dispose () {
        spriteBatch.dispose();
        effect.dispose();
    }

    @Override
    public void resize (int width, int height) {
    }

    public void render () {
        spriteBatch.getProjectionMatrix().setToOrtho2D(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        float delta = Gdx.graphics.getDeltaTime();
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        spriteBatch.begin();
        for (com.badlogic.gdx.graphics.g2d.ParticleEffect e : effects)
            e.draw(spriteBatch, delta);
        spriteBatch.end();
    }

    public boolean needsGL20 () {
        return false;
    }

}
