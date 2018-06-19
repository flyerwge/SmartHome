package work.flyergw.smarthome;

import android.util.Log;

import com.google.gson.Gson;

/**
 * WeatherJson
 * 解析天气Json数据
 *
 * @author flyerwge
 * */

public class WeatherJson {
    public String resultCond,resultTmp;
    public void weatherJson(String inputJson){
        Gson gson = new Gson();
        Weather weather = gson.fromJson(inputJson, Weather.class);
        resultCond = weather.getNow().getCond_txt();
        resultTmp = weather.getNow().getTmp();
    }
}
