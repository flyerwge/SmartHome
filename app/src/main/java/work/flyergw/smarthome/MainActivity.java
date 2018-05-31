package work.flyergw.smarthome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

import java.util.ArrayList;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "===>";
    private Button btnDetect;
    private TextView showTxt;
    private SpeechRecognizer recognizer;    //语音识别对象
    private RecognizerListener recognizerListener;
    private String resultTxt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5b03f4b7");//SDK初始化
        initViews();
        initEvents();
        initRecognizer();

    }

    /**
     * InitViews:初始化
     */
    private void initViews() {
        //界面初始化
        btnDetect = findViewById(R.id.btn_detect);
        showTxt = findViewById(R.id.showTxt);


        //初始化语音识别参数
        recognizer = SpeechRecognizer.createRecognizer(MainActivity.this, null);
        recognizer.setParameter(SpeechConstant.DOMAIN, "iat");
        recognizer.setParameter(SpeechConstant.LANGUAGE, "zh_cn");  //中文
        recognizer.setParameter(SpeechConstant.ACCENT, "mandarin ");    //普通话

    }

    /**
     * initRecognizer:初始化讯飞语音识别
     */
    private void initRecognizer() {
        recognizerListener = new RecognizerListener() {
            @Override
            public void onVolumeChanged(int i, byte[] bytes) {

            }

            @Override
            public void onBeginOfSpeech() {
                btnDetect.setClickable(false);
                btnDetect.setText("正在录音...");
            }

            @Override
            public void onEndOfSpeech() {
                Toast.makeText(MainActivity.this, "录音结束", Toast.LENGTH_SHORT).show();
                btnDetect.setText("点击录音");
                btnDetect.setClickable(true);
            }

            @Override
            public void onResult(RecognizerResult recognizerResult, boolean b) {
                String result = recognizerResult.getResultString();
                resultTxt = resultJson(result);
                updateTextView(resultTxt);
            }

            @Override
            public void onError(SpeechError speechError) {
                btnDetect.setText("点击录音");
                btnDetect.setClickable(true);
                updateTextView(speechError.getErrorDescription());
                Toast.makeText(MainActivity.this, speechError.getErrorDescription(), Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onEvent(int i, int i1, int i2, Bundle bundle) {

            }
        };
    }

    /**
     * InitEvents:初始化点击事件
     */
    private void initEvents() {
        btnDetect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(MainActivity.this, "开始录音", Toast.LENGTH_SHORT).show();
                recognizer.startListening(recognizerListener);

            }
        });

    }



    /**
     * resultJson
     *
     * @param inputJson:Json字符串
     * @return 解析好的字符串
     * */
    private String resultJson(String inputJson) {
        Gson gson = new Gson();
        ResultBean resultBean = gson.fromJson(inputJson, ResultBean.class);
        ArrayList<ResultBean.WS> resultWs = resultBean.ws;

        StringBuilder stringBuilder = new StringBuilder();
        for (ResultBean.WS w : resultWs) {
            String text = w.cw.get(0).w;
            stringBuilder.append(text);
        }
        return stringBuilder.toString().replaceAll("\\p{P}", "");
    }


    /**
    * updateTextView:更新TextView
    * */
    private void updateTextView(String newString) {
        showTxt.setText(newString);
//        showText.append(newString);
    }


}


