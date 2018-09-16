package kld.com.rfid.ldw;

import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.widget.ImageView;


/**
 * Created by Marshon.Chen on 2016/6/1.
 * DESC:
 */
public class WelcomeActivity extends AppCompatActivity {

    private ImageView imageView;
//    private TextView textView;
    private Animation entrance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_2main_test3);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0及以上
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {//4.4到5.0
            WindowManager.LayoutParams localLayoutParams = getWindow().getAttributes();
            localLayoutParams.flags = (WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS | localLayoutParams.flags);
        }
        init();



//        LoadingPathAnimView pathAnimView1 = (LoadingPathAnimView) findViewById(R.id.fillView1);
//        SvgPathParser svgPathParser = new SvgPathParser();
//        try {
//            Path path = svgPathParser.parsePath(getString(R.string.toys));
//            pathAnimView1.setSourcePath(path);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        pathAnimView1.startAnim();
//
//        Path sPath = new Path();
//        sPath.moveTo(0, 0);
//        sPath.addCircle(40, 40, 30, Path.Direction.CW);
//        pathAnimView1.setSourcePath(sPath);




//        final StoreHouseAnimView pathAnimView1 = (StoreHouseAnimView) findViewById(R.id.pathAnimView1);
//        Path sPath = new Path();
//        sPath.moveTo(0, 0);
//        sPath.addCircle(40, 40, 30, Path.Direction.CW);
//        pathAnimView1.setSourcePath(PathParserUtils.getPathFromArrayFloatList(StoreHousePath.getPath("McXtZhang")));
//        pathAnimView1.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pathAnimView1.startAnim();
//            }
//        });


        //SVG转-》path
        //还在完善中，我从github上找了如下工具类，发现简单的SVG可以转path，复杂点的 就乱了
//        SvgPathParser svgPathParser = new SvgPathParser();
//        final PathAnimView  storeView3 = (PathAnimView) findViewById(R.id.storeView3);
//        String qianbihua = getString(R.string.myTest);
//        try {
//            Path path = svgPathParser.parsePath(qianbihua);
//            storeView3.setSourcePath(path);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//
//        storeView3.getPathAnimHelper().setAnimTime(5000);
//        storeView3.startAnim();


//        final PathView pathView = (PathView) findViewById(R.id.pathView);
//        final Path path = makeConvexArrow(50, 100);
//        pathView.setPath(path);
        //       pathView.setFillAfter(true);
        //  pathView.useNaturalColors();

//                pathView.getPathAnimator().
//                        //pathView.getSequentialPathAnimator().
//                        delay(100).
//                        duration(1500).
//                        interpolator(new AccelerateDecelerateInterpolator()).
//                        start();



//
//        pathView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pathView.getPathAnimator().
//                        //pathView.getSequentialPathAnimator().
//                                delay(10).
//                        duration(300).
//                        interpolator(new AccelerateDecelerateInterpolator()).
//                        start();
//                pathView.setPathColor(Main2ActivityTest3.this.getResources().getColor(R.color.suofeiya_color));
////                pathView.useNaturalColors();
//                pathView.setFillAfter(true);
//            }
//        });
//        pathView.performClick();
//
//

//        ImageView imageView = (ImageView)findViewById(R.id.image);
//        // 加载应用资源
//        int resource = R.drawable.suofeiya_black;
//        Glide.with(this).load(resource).into(imageView);


        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                next();
            }
        },150);

    }

    public void init() {
//
//        ViewFinder finder = new ViewFinder(this);
//        imageView = finder.find(R.id.image);
////        textView = finder.find(R.id.title);
//        entrance = AnimationUtils.loadAnimation(this,R.anim.welcome_entrance);
//
//        entrance.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//                next();
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//            }
//        });
//        imageView.startAnimation(entrance);


    }




    public void next() {
        Intent intent = new Intent(this, SuoFeiYaMainActivity.class);
        startActivity(intent);
        finish();
    }
}
