package com.me.git.edittextwithdel;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {
        final EdittextWithDel edittextWithDel = (EdittextWithDel) findViewById(R.id.et_del);
        Button btn_login = (Button) findViewById(R.id.btn_login);
        btn_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (edittextWithDel.getText().length() == 0){
                    edittextWithDel.setShakeAnimation();
                    Toast.makeText(MainActivity.this,"帐号不能为空",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}
