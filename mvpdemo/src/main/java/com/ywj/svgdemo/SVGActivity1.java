package com.ywj.svgdemo;

import android.graphics.drawable.Animatable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.antonioleiva.mvpexample.app.R;

public class SVGActivity1 extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_svg1);
        final ImageView imageView = (ImageView) findViewById(R.id.image);
        final ImageView imageView2 = (ImageView) findViewById(R.id.image2);
        final ImageView imageView3 = (ImageView) findViewById(R.id.image3);


        setOnClick(imageView);
        imageView2.setImageDrawable(getResources().getDrawable(R.drawable.anim_vector2));
        setOnClick(imageView2);
        setOnClick(imageView3);
    }

    private void setOnClick(final ImageView imageView) {
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final Drawable drawable = imageView.getDrawable();
                if (drawable instanceof Animatable) {
                    ((Animatable) drawable).start();
                }
            }
        });
    }
}
