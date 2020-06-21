package com.gmail.denislatushko92.animation;

import android.animation.AnimatorInflater;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.BounceInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.startAnimationButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animationXmlFade();
                animationXmlTranslate();
                animationXmlScale();
                animationXMLTranslateFade();
                animationTranslateFadeChain();
                animateImageViewWithFade();
                valueAnimator();
                objectAnimator();
                objectAnimatorSet();
                animatorXML();
            }
        });
    }

    private void animationXmlFade() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.fade);
        ImageView imageView = findViewById(R.id.imageFade);
        imageView.startAnimation(animation);
    }

    private void animationXmlTranslate() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate);
        ImageView imageView = findViewById(R.id.imageTranslate);
        imageView.startAnimation(animation);
    }

    private void animationXMLTranslateFade() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.translate_fade_set);
        ImageView imageView = findViewById(R.id.imageTranslateFadeSet);
        imageView.startAnimation(animation);
    }

    private void animationXmlScale() {
        Animation animation = AnimationUtils.loadAnimation(this, R.anim.scale);
        ImageView imageView = findViewById(R.id.imageScale);
        imageView.startAnimation(animation);
    }

    private void animationTranslateFadeChain() {
        final ImageView imageView = findViewById(R.id.imageTranslateFadeChain);
        final Animation fadeAnimation = AnimationUtils.loadAnimation(this, R.anim.blink);
        fadeAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {

            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d("AnimationExample", "AnimationListener FADE - onAnimationRepeat");
            }
        });
        final Animation translateAnimation = AnimationUtils.loadAnimation(this, R.anim.translate);
        translateAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                Log.d("AnimationExample", "AnimationListener - onAnimationStart");
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                Log.d("AnimationExample", "AnimationListener - onAnimationEnd");
                imageView.startAnimation(fadeAnimation);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                Log.d("AnimationExample", "AnimationListener - onAnimationRepeat");
            }

        });
        imageView.startAnimation(translateAnimation);
    }

    private void valueAnimator() {
        final TextView textView = findViewById(R.id.textImageValueAnimator);
        textView.setScaleX(.0f);

        ValueAnimator animator = ValueAnimator.ofFloat(.0f, 1.0f);
        animator.setDuration(2000);
        animator.setInterpolator(new BounceInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                textView.setScaleX(animatedValue);
            }
        });
        animator.start();
    }

    private void objectAnimator() {
        final TextView textView = findViewById(R.id.textImageObjectAnimator);
        ObjectAnimator animator = ObjectAnimator.ofFloat(textView, "translationX", 500.0f, .0f);
        animator.setDuration(2000);
        animator.setInterpolator(new BounceInterpolator());
        animator.start();
    }

    private void objectAnimatorSet() {
        final TextView textView = findViewById(R.id.textImageAnimatorSet);

        ValueAnimator valueAnimator = ValueAnimator.ofFloat(.0f, 1.0f);
        valueAnimator.setDuration(2000);
        valueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        valueAnimator.setRepeatMode(ValueAnimator.RESTART);
        valueAnimator.setInterpolator(new BounceInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float animatedValue = (float) animation.getAnimatedValue();
                textView.setAlpha(animatedValue);
            }
        });

        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(textView, "translationX", .0f, 500.0f);
        objectAnimator.setDuration(2000);
        objectAnimator.setInterpolator(new BounceInterpolator());

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playSequentially(valueAnimator, objectAnimator);
        animatorSet.start();
    }

    private void animatorXML() {
        TextView textView = findViewById(R.id.textImageXMLObjectAnimator);
        ObjectAnimator animator = (ObjectAnimator) AnimatorInflater.loadAnimator(this, R.animator.object_animator);
        animator.setTarget(textView);
        animator.start();
    }

    private void animateImageViewWithFade() {
        TextView textView = findViewById(R.id.textImageFade);
        textView.setAlpha(.0f);
        textView.setScaleX(.0f);
        textView.animate()
                .setInterpolator(new OvershootInterpolator())
                .alpha(1.0f)
                .scaleX(1.0f)
                .setDuration(1000)
                .start();
    }
}
