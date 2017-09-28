使用方法:

// https://github.com/futuresimple/android-floating-action-button

  protected void initFloatingActionMenu() {
        FloatingActionsMenu menuMultipleActions = (FloatingActionsMenu) findViewById(R.id.multiple_actions);
        final com.getbase.floatingactionbutton.FloatingActionButton actionA = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.action_a);
        actionA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                menuMultipleActions.collapse();
            }
        });

        final com.getbase.floatingactionbutton.FloatingActionButton action_b = (com.getbase.floatingactionbutton.FloatingActionButton) findViewById(R.id.action_b);
        action_b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              
            }
        });
    }

  <com.getbase.floatingactionbutton.FloatingActionsMenu
        android:id="@+id/multiple_actions"
        android:layout_width="wrap_content"
        android:layout_height="wrap_content"
        android:layout_alignParentBottom="true"
        android:layout_alignParentRight="true"
        android:layout_gravity="bottom|right"
        android:layout_alignParentEnd="true"
        fab:fab_addButtonColorNormal="@color/toolbarBackground"
        fab:fab_addButtonColorPressed="@color/half_black"
        fab:fab_labelStyle="@style/menu_labels_style"

        android:layout_marginBottom="16dp"
        android:layout_marginRight="16dp"
        android:layout_marginEnd="16dp">

        <com.getbase.floatingactionbutton.FloatingActionButton
            android:id="@+id/action_a"
            android:layout_width="256dp"
            android:layout_height="256dp"
            fab:fab_colorNormal="@color/toolbarBackground"
            fab:fab_title="打印测试"
            fab:fab_size="mini"
            fab:fab_icon="@drawable/svg_bluetooth_test"
            fab:fab_colorPressed="@color/half_black"/>

        <com.getbase.floatingactionbutton.FloatingActionButton
            android:id="@+id/action_b"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:padding="1dp"
            fab:fab_colorNormal="@color/toolbarBackground"
            fab:fab_title="系统蓝牙"
            fab:fab_size="mini"

            fab:fab_colorPressed="@color/half_black"/>

    </com.getbase.floatingactionbutton.FloatingActionsMenu>









