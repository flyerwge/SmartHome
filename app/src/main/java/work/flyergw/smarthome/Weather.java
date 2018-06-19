package work.flyergw.smarthome;

/**
 * Weather
 * 天气Json数据的JavaBean
 *
 * GsonFormat生成
 *
 * @author flyerwge
 * */

public class Weather {
    /**
     * now : {"cloud":"0","cond_code":"501","cond_txt":"雾","fl":"22","hum":"75","pcpn":"0.0","pres":"1002","tmp":"21","vis":"10","wind_deg":"188","wind_dir":"南风","wind_sc":"2","wind_spd":"6"}
     * basic : {"admin_area":"山东","cid":"CN101120201","cnty":"中国","lat":"36.08298111","location":"青岛","lon":"120.3551712","parent_city":"青岛","tz":"+8.00"}
     * status : ok
     * update : {"loc":"2018-06-19 15:49","utc":"2018-06-19 07:49"}
     */

    private NowBean now;
    private BasicBean basic;
    private String status;
    private UpdateBean update;

    public NowBean getNow() {
        return now;
    }

    public void setNow(NowBean now) {
        this.now = now;
    }

    public BasicBean getBasic() {
        return basic;
    }

    public void setBasic(BasicBean basic) {
        this.basic = basic;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public UpdateBean getUpdate() {
        return update;
    }

    public void setUpdate(UpdateBean update) {
        this.update = update;
    }

    public static class NowBean {
        /**
         * cloud : 0
         * cond_code : 501
         * cond_txt : 雾
         * fl : 22
         * hum : 75
         * pcpn : 0.0
         * pres : 1002
         * tmp : 21
         * vis : 10
         * wind_deg : 188
         * wind_dir : 南风
         * wind_sc : 2
         * wind_spd : 6
         */

        private String cloud;
        private String cond_code;
        private String cond_txt;
        private String fl;
        private String hum;
        private String pcpn;
        private String pres;
        private String tmp;
        private String vis;
        private String wind_deg;
        private String wind_dir;
        private String wind_sc;
        private String wind_spd;

        public String getCloud() {
            return cloud;
        }

        public void setCloud(String cloud) {
            this.cloud = cloud;
        }

        public String getCond_code() {
            return cond_code;
        }

        public void setCond_code(String cond_code) {
            this.cond_code = cond_code;
        }

        public String getCond_txt() {
            return cond_txt;
        }

        public void setCond_txt(String cond_txt) {
            this.cond_txt = cond_txt;
        }

        public String getFl() {
            return fl;
        }

        public void setFl(String fl) {
            this.fl = fl;
        }

        public String getHum() {
            return hum;
        }

        public void setHum(String hum) {
            this.hum = hum;
        }

        public String getPcpn() {
            return pcpn;
        }

        public void setPcpn(String pcpn) {
            this.pcpn = pcpn;
        }

        public String getPres() {
            return pres;
        }

        public void setPres(String pres) {
            this.pres = pres;
        }

        public String getTmp() {
            return tmp;
        }

        public void setTmp(String tmp) {
            this.tmp = tmp;
        }

        public String getVis() {
            return vis;
        }

        public void setVis(String vis) {
            this.vis = vis;
        }

        public String getWind_deg() {
            return wind_deg;
        }

        public void setWind_deg(String wind_deg) {
            this.wind_deg = wind_deg;
        }

        public String getWind_dir() {
            return wind_dir;
        }

        public void setWind_dir(String wind_dir) {
            this.wind_dir = wind_dir;
        }

        public String getWind_sc() {
            return wind_sc;
        }

        public void setWind_sc(String wind_sc) {
            this.wind_sc = wind_sc;
        }

        public String getWind_spd() {
            return wind_spd;
        }

        public void setWind_spd(String wind_spd) {
            this.wind_spd = wind_spd;
        }
    }

    public static class BasicBean {
        /**
         * admin_area : 山东
         * cid : CN101120201
         * cnty : 中国
         * lat : 36.08298111
         * location : 青岛
         * lon : 120.3551712
         * parent_city : 青岛
         * tz : +8.00
         */

        private String admin_area;
        private String cid;
        private String cnty;
        private String lat;
        private String location;
        private String lon;
        private String parent_city;
        private String tz;

        public String getAdmin_area() {
            return admin_area;
        }

        public void setAdmin_area(String admin_area) {
            this.admin_area = admin_area;
        }

        public String getCid() {
            return cid;
        }

        public void setCid(String cid) {
            this.cid = cid;
        }

        public String getCnty() {
            return cnty;
        }

        public void setCnty(String cnty) {
            this.cnty = cnty;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getLocation() {
            return location;
        }

        public void setLocation(String location) {
            this.location = location;
        }

        public String getLon() {
            return lon;
        }

        public void setLon(String lon) {
            this.lon = lon;
        }

        public String getParent_city() {
            return parent_city;
        }

        public void setParent_city(String parent_city) {
            this.parent_city = parent_city;
        }

        public String getTz() {
            return tz;
        }

        public void setTz(String tz) {
            this.tz = tz;
        }
    }

    public static class UpdateBean {
        /**
         * loc : 2018-06-19 15:49
         * utc : 2018-06-19 07:49
         */

        private String loc;
        private String utc;

        public String getLoc() {
            return loc;
        }

        public void setLoc(String loc) {
            this.loc = loc;
        }

        public String getUtc() {
            return utc;
        }

        public void setUtc(String utc) {
            this.utc = utc;
        }
    }
}
