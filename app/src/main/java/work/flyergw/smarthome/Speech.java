package work.flyergw.smarthome;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class Speech extends MainActivity{
    private Button btn_detect;
    private TextView showTxt;



    /**
     * InitViews:初始化
     * */
    private void InitViews(){
        btn_detect = findViewById(R.id.btn_detect);
        showTxt = findViewById(R.id.showTxt);
    }

    /**
     * InitEvents:初始化点击事件
     * */
    private void InitEvents(){
        btn_detect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(Speech.this,"开始录音",Toast.LENGTH_SHORT).show();

            }
        });

    }
}
