package work.flyergw.smarthome;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.SpeechUtility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Pattern;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeConfig;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;


public class MainActivity extends AppCompatActivity {

    private static String TAG = "===>";
    private EditText ipAddress;
    private Button btnDetect;
    private ImageButton addIpAdres;
    private TextView showTxt;
    private SpeechRecognizer recognizer;    //语音识别对象
    private RecognizerListener recognizerListener;
    private String resultTxt = null;
    private String dstName = null;
    private int dstPort = 1234;
    private String sendString = null;
    private Socket socket;
    private String nowYear, nowMonth, nowDate;
    private String resultCond, resultTmp;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SpeechUtility.createUtility(this, SpeechConstant.APPID + "=5b03f4b7");//讯飞SDK初始化
        HeConfig.init("HE1806181631521200", "5d8f7538f4ec421b8d02945f1b3a5b30");//和风天气SDK初始化
        HeConfig.switchToFreeServerNode();
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
        ipAddress = findViewById(R.id.ip_address);
        addIpAdres = findViewById(R.id.add_ip);

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
                matched(resultTxt);
                connect(sendString);
                updateAllString();
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

        addIpAdres.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getIpAddress();
                getDatTim();
                new Thread() {
                    @Override
                    public void run() {
                        try {
                            socket = new Socket(dstName, dstPort);
                            OutputStream outputStream = socket.getOutputStream();
                            PrintWriter printWriter = new PrintWriter(outputStream);
                            printWriter.write("y" + nowYear + "/" + nowMonth + "/" + nowDate);
                            printWriter.flush();
                            socket.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }.start();
            }
        });
    }

    /**
     * resultJson
     *
     * @param inputJson:Json字符串
     * @return 解析好的字符串
     */
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
     */
    private void updateTextView(String newString) {
        showTxt.setText(newString);
    }

    private void updateAllString() {
        resultTxt = null;
        sendString = null;
    }

    /**
     * @param newSendString:经过正则匹配后给出的指令 TCP通信
     */
    private void connect(final String newSendString) {
        new Thread() {
            @Override
            public void run() {
                try {
                    socket = new Socket(dstName, dstPort);
                    if (newSendString != null) {
                        OutputStream outputStream = socket.getOutputStream();
                        PrintWriter printWriter = new PrintWriter(outputStream);
                        printWriter.write(newSendString);
                        printWriter.flush();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * @param newResultTxt:语音识别获得的字符串 matched:正则匹配获得操控指令
     */
    private void matched(String newResultTxt) {
        String ledOpen = ".*开.*灯*.";
        String ledClose = ".*关.*灯*.";
        String fanOpen = ".*开.*风扇*.";
        String fanClose = ".*关.*风扇*.";
        String speedUp = ".*加.*速*.";
        String speedDown = ".*减.*速*.";
        String nowWeather = ".*天气*.";

        if (newResultTxt != null) {
            if (Pattern.matches(ledOpen, newResultTxt)) {
                sendString = "o1";
            }
            if (Pattern.matches(ledClose, newResultTxt)) {
                sendString = "c1";
            }
            if (Pattern.matches(fanOpen, newResultTxt)) {
                sendString = "o2";
            }
            if (Pattern.matches(fanClose, newResultTxt)) {
                sendString = "c2";
            }
            if (Pattern.matches(speedUp, newResultTxt)) {
                sendString = "fu";
            }
            if (Pattern.matches(speedDown, newResultTxt)) {
                sendString = "fd";
            }
            if (Pattern.matches(nowWeather, newResultTxt)) {
                getWeather();

//               /*
//               * 可通过sleep方法适当进行优化
//               * */
//                try {
//                    Thread.sleep(1000);
//
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }

                if (resultCond != null) {
                    switch (resultCond) {
                        case "雾":
                            sendString = "q1" + resultTmp;
                            Log.d(TAG, "GW:" + sendString);
                            break;
                        case "晴":
                            sendString = "q2" + resultTmp;
                            break;
                        case "阴":
                            sendString = "q3" + resultTmp;
                            break;
                        case "多云":
                            sendString = "q4" + resultTmp;
                            Log.d(TAG, "GW:" + sendString);
                            break;
                    }
                }



            }
        }
    }

    /**
     * getIpAddress:获取ip地址
     */
    private void getIpAddress() {
        //正则匹配有效的ip地址
        String ipMatched = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|[1-9])\\."

                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."

                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\."

                + "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$";
        dstName = ipAddress.getText().toString();
        if (Pattern.matches(ipMatched, dstName)) {
            Toast.makeText(MainActivity.this, "大爷，您可以控制家居啦~~~", Toast.LENGTH_SHORT).show();
        } else {
            dstName = null;
            Toast.makeText(MainActivity.this, "小菜鸡，地址输入错误！", Toast.LENGTH_SHORT).show();
        }
    }


    /**
    * getDatTim:获取时间
    * */
    private void getDatTim() {
        Calendar nowTime = Calendar.getInstance();
        nowYear = String.valueOf(nowTime.get(Calendar.YEAR));
        nowDate = String.valueOf(nowTime.get(Calendar.DATE));

        if ((nowTime.get(Calendar.MONTH) + 1) < 10) {
            nowMonth = "0" + String.valueOf(nowTime.get(Calendar.MONTH) + 1);
        } else {
            nowMonth = String.valueOf(nowTime.get(Calendar.MONTH) + 1);
        }
    }

    /**
     * getWeather:调用API;获取天气Json数据
     * */
    private void getWeather() {
        HeWeather.getWeatherNow(MainActivity.this, "青岛", Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                new HeWeather.OnResultWeatherNowBeanListener() {
                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(List<Now> list) {
                        String wResult = new Gson().toJson(list);
                        wResult = wResult.replace("[", "");
                        wResult = wResult.replace("]", "");
                        weatherJson(wResult);
                    }
                });
    }

    /**
     * weatherJson:和风天气Json数据通过Gson解析
     *
     * @param inputJson:和风天气的Json数据
     * */
    private void weatherJson(String inputJson) {
        Gson gson = new Gson();
        Weather weather = gson.fromJson(inputJson, Weather.class);
        resultCond = weather.getNow().getCond_txt();
        resultTmp = weather.getNow().getTmp();
    }
}


