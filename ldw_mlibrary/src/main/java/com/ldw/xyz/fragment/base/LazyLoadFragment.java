package com.ldw.xyz.fragment.base;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

/**
 * Created by LDW10000000 on 10/07/2017.
 *
 * 我建议在lazyLoad里面使用findviewByid ,因为这时候是view对象不为空了;
 *
 *
 * Fragment预加载问题的解决方案：
 *
 * 1.可以懒加载的Fragment
 *
 * 2.切换到其他页面时停止加载数据（可选）
 * Created by yuandl on 2016-11-17.
 * blog ：http://blog.csdn.net/linglongxin24/article/details/53205878
 *
 * 3.使用方法:
 *    public class Fragment1 extends LazyLoadFragment {
 *         @Override
 *         public int setContentView() {
 *              return R.layout.fm_layout1;
 *         }
 *
 *        @Override
 *        protected void lazyLoad() {
 *              String message = "Fragment1" + (isInit ? "已经初始并已经显示给用户可以加载数据" : "没有初始化不能加载数据")+">>>>>>>>>>>>>>>>>>>";
 *              showToast(message);
 *              Log.d(TAG, message);
 *        }
 *
 *       @Override
 *       protected void stopLoad() {
 *              Log.d(TAG, "Fragment1" + "已经对用户不可见，可以停止加载数据");
 *       }
 * }
 *
 * 4.     @Override
 *        public void onViewCreated(View view, Bundle savedInstanceState) {
 *              super.onViewCreated(view, savedInstanceState);
 *               ((TextView) view.findViewById(R.id.tv_title)).setText(toastText);
 *               view.findViewById(R.id.btn_add_child).setOnClickListener(this);
 *                view.findViewById(R.id.btn_replace_child).setOnClickListener(this);
 *               view.findViewById(R.id.btn_pop).setOnClickListener(this);
 *        }
 *
 *
 */

public abstract class LazyLoadFragment extends Fragment {
    /**
     * 视图是否已经初初始化
     */
    protected boolean isInit = false;
    protected boolean isLoad = false;
    protected final String TAG = "LazyLoadFragment";
    private View view;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(setContentView(), container, false);
        isInit = true;
        /**初始化的时候去加载数据**/
        isCanLoadData();
        return view;
    }

    /**
     * 判断 Fragment 的 UI 是否对用户可见
     * 判断参数 isVisibleToUser 是否为 True 即可知道该 Fragment 的 UI 是否对用户可见
     *
     *   对于单个 Fragment，setUserVisibleHint 是不会被调用的，
     *   只有该 Fragment 在 ViewPager 里才会被调用。
     *   所以，我写了一个 ViewPager + Fragment 的 Demo，
     *   打印了一下 Log可以看到 setUserVisibleHint 的执行顺序如下：
     *   setUserVisibleHint: isVisibleToUser = false
     *   onAttach
     *   onCreate
     *   setUserVisibleHint: isVisibleToUser = true
     *   onCreateView
     *   onActivityCreated
     *   onStart
     *   onResume
     *   onPause
     *   onStop
     *   onDestroyView
     *   onDestroy
     *   onDetach
     *
     * @param isVisibleToUser
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        isCanLoadData();
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            lazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                stopLoad();
            }
        }
    }

    /**
     * 视图销毁的时候讲Fragment是否初始化的状态变为false
     */
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        isInit = false;
        isLoad = false;

    }

    protected void showToast(String message) {
        if (!TextUtils.isEmpty(message)) {
            Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
        }

    }

    /**
     * 设置Fragment要显示的布局
     *
     * @return 布局的layoutId
     */
    protected abstract int setContentView();

    /**
     * 获取设置的布局
     *
     * @return
     */
    protected View getContentView() {
        return view;
    }

    /**
     * 找出对应的控件
     *
     * @param id
     * @param <T>
     * @return
     */
    protected <T extends View> T findViewById(int id) {

        return (T) getContentView().findViewById(id);
    }

    /**
     * 当视图初始化并且对用户可见的时候去真正的加载数据
     */
    protected abstract void lazyLoad();

    /**
     * 当视图已经对用户不可见并且加载过数据，如果需要在切换到其他页面时停止加载数据，可以覆写此方法
     */
    protected abstract void stopLoad();
}

