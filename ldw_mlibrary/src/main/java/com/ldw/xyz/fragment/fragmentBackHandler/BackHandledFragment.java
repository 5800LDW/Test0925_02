
/*
 * Copyright 2016 ikidou
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.ldw.xyz.fragment.fragmentBackHandler;

import android.support.v4.view.ViewPager;

import com.ldw.xyz.fragment.base.LazyLoadFragment;

public abstract class BackHandledFragment extends LazyLoadFragment implements FragmentBackHandler {
    public BackHandledFragment() {
    }

    /**
     * LDW
     *    1.这里定义为final , 就是强制要一层一层fragment返回;
     *    2.如果你不需要一层一层fragment返回,就要重写interceptBackPressed方法,例如:
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
     * @return
     */
    @Override
    public final boolean onBackPressed() {
        return interceptBackPressed() ||
                ((getBackHandleViewPager() == null ? BackHandlerHelper.handleBackPress(this) : BackHandlerHelper.handleBackPress(getBackHandleViewPager()))) ;
    }

    /**
     * 是否拦截返回键, 默认false不拦截
     * @return
     */
    public boolean interceptBackPressed() {
        return false;
    }

    /**
     * 2.1 版本已经不在需要单独对ViewPager处理
     * @deprecated
     */
    @Deprecated
    public ViewPager getBackHandleViewPager() {
        return null;
    }
}
