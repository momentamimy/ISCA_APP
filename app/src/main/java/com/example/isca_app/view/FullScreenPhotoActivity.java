package com.example.isca_app.view;

import android.graphics.Bitmap;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;

import com.example.isca_app.R;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Transformation;
import com.victor.loading.rotate.RotateLoading;

public class FullScreenPhotoActivity extends AppCompatActivity {

    ImageView fullscreenImage;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_photo);

        fullscreenImage=findViewById(R.id.fullscreenImage);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Fade fade = new Fade();
            View decor = getWindow().getDecorView();
            fade.excludeTarget(decor.findViewById(R.id.action_bar_container), true);
            fade.excludeTarget(android.R.id.statusBarBackground, true);

            fade.excludeTarget(android.R.id.navigationBarBackground, true);


            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                getWindow().setEnterTransition(fade);
                getWindow().setExitTransition(fade);
            }
        }

        final RotateLoading rotateLoading=findViewById(R.id.rotateloading);
        rotateLoading.start();

        Picasso.with(this).load(geturlData())
                .fit().centerInside()
                .into(fullscreenImage, new com.squareup.picasso.Callback() {
                    @Override
                    public void onSuccess() {
                        rotateLoading.stop();
                    }
                    @Override
                    public void onError() {
                        rotateLoading.stop();
                        fullscreenImage.setImageResource(R.drawable.ic_action_navigation_arrow_back);
                    }
                });
    }

    public String geturlData()
    {
        return getIntent().getStringExtra("URL");
    }
}
