package work.flyergw.smarthome;

import android.util.Log;

import com.google.gson.Gson;

import java.util.List;

import interfaces.heweather.com.interfacesmodule.bean.Lang;
import interfaces.heweather.com.interfacesmodule.bean.Unit;
import interfaces.heweather.com.interfacesmodule.bean.weather.now.Now;
import interfaces.heweather.com.interfacesmodule.view.HeWeather;

/**
 * Getweather
 *获取天气Json数据
 *
 * @author flyerwge
 * */
public class GetWeather {
    public String wResult = "1";

    public void getWeather(){
        HeWeather.getWeatherNow(null, "青岛", Lang.CHINESE_SIMPLIFIED, Unit.METRIC,
                new HeWeather.OnResultWeatherNowBeanListener() {
                    @Override
                    public void onError(Throwable throwable) {

                    }

                    @Override
                    public void onSuccess(List<Now> list) {
                        Log.d("=====", "sss:" + new Gson().toJson(list));
                        wResult = new Gson().toJson(list);
                        wResult = wResult.replace("[", "");
                        wResult = wResult.replace("]", "");
                        wResult = "2";
                    }
                });
    }
}
