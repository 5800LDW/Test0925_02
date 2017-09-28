package com.ldw.xyz.activity;

/**
 * Created by LDW10000000 on 01/08/2017.
 *
 * 作者：怪盗kidou
 * 链接：http://www.jianshu.com/p/fff1ef649fc0
 *
 * LDW注解:
 *
 * 这个是处理好了fragemnt的返回事件的activity, 同样处理好了viewPager里面的fragment的返回事件;
 * 使用方法(必须配合下面三项):
 *      1.配合 HandleFragmentBackPressActivity 使用;
 *      2.配合 HandleBackPressFragment 使用;
 *      3.配合 FragmentUtil使用(其实就是transaction.addToBackStack(null)加入到了任务栈);
 *
 *      4.Activity覆盖onBackPressed方法（必须）
 *          .....
 *          @Override
 *          public void onBackPressed() {
 *               //是不是已经一层一层退出fragment, 如果不需要层层退出, 就直接 super.onBackPressed();就好了;
 *              if (!BackHandlerHelper.handleBackPress(this)) {
 *                  super.onBackPressed();
 *              }
 *          }
 *
 *        5. 当你不需要fragment的层层返回的时候:重写BackHandledFragment 的interceptBackPressed方法;
 *           @Override
 *          public boolean interceptBackPressed() {
 *                  if (backHandled) {
 *                          //你要处理的业务逻辑
 *                      return true;//拦截,不需要处理层层返回
 *                  }
 *                  else{
 *                       return false;
 *                  }
 *          }
 *
 *
 *
 *
 *
 */
public abstract class HandleFragmentBackPressActivity extends  BaseActivity{

}
