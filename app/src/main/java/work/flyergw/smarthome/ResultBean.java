package work.flyergw.smarthome;

import java.util.ArrayList;

/**
 * ResultBean
 * 储存解析出来的数据
 *
 * @author 葛伟
 */
public class ResultBean {

    public ArrayList<WS> ws;

    public class WS {
        public ArrayList<CW> cw;
    }

    public class CW {
        public String w;
    }

}
