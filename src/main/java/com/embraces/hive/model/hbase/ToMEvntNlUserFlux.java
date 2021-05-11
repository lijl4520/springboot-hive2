package com.embraces.hive.model.hbase;

import com.fasterxml.jackson.annotation.JsonAutoDetect;

/**
 * @Author Lijl
 * @ClassName ToMEvntNlUserFlux
 * @Description 青海
 * @Date 2021/3/12 14:53
 * @Version 1.0
 */
@JsonAutoDetect(fieldVisibility = JsonAutoDetect.Visibility.ANY,getterVisibility = JsonAutoDetect.Visibility.NONE)
public class ToMEvntNlUserFlux {
    //归属省
    private String home_prov_id;
    //归属市
    private String home_area_id;
    //RAT类型
    private String rat;
    //服务号码
    private String serv_number;
    //小区
    private String cell_id;
    //APN
    private String apn;
    //开始时间
    private String start_time;
    //结束时间
    private String end_time;
    //协议类型
    private String protocol_type;
    //业务小类
    private String app_id;
    //访问的服务器的IPv4地址
    private String server_ipv4;
    //访问的服务器的端口
    private String server_port;
    //上行流量
    private Double up_flux;
    //下行流量
    private Double down_flux;
    //访问域名
    private String host;
    //访问的URI
    private String uri;
    //LAC
    private String lac;
    //终端品牌
    private String trmnl_brand;
    //终端型号
    private String trmnl_model;
    //用户漫游类型
    private String roam_type;

    public String getHome_prov_id() {
        return home_prov_id;
    }

    public void setHome_prov_id(String home_prov_id) {
        this.home_prov_id = home_prov_id;
    }

    public String getHome_area_id() {
        return home_area_id;
    }

    public void setHome_area_id(String home_area_id) {
        this.home_area_id = home_area_id;
    }

    public String getRat() {
        return rat;
    }

    public void setRat(String rat) {
        this.rat = rat;
    }

    public String getServ_number() {
        return serv_number;
    }

    public void setServ_number(String serv_number) {
        this.serv_number = serv_number;
    }

    public String getCell_id() {
        return cell_id;
    }

    public void setCell_id(String cell_id) {
        this.cell_id = cell_id;
    }

    public String getApn() {
        return apn;
    }

    public void setApn(String apn) {
        this.apn = apn;
    }

    public String getStart_time() {
        return start_time;
    }

    public void setStart_time(String start_time) {
        this.start_time = start_time;
    }

    public String getEnd_time() {
        return end_time;
    }

    public void setEnd_time(String end_time) {
        this.end_time = end_time;
    }

    public String getProtocol_type() {
        return protocol_type;
    }

    public void setProtocol_type(String protocol_type) {
        this.protocol_type = protocol_type;
    }

    public String getApp_id() {
        return app_id;
    }

    public void setApp_id(String app_id) {
        this.app_id = app_id;
    }

    public String getServer_ipv4() {
        return server_ipv4;
    }

    public void setServer_ipv4(String server_ipv4) {
        this.server_ipv4 = server_ipv4;
    }

    public String getServer_port() {
        return server_port;
    }

    public void setServer_port(String server_port) {
        this.server_port = server_port;
    }

    public Double getUp_flux() {
        return up_flux;
    }

    public void setUp_flux(Double up_flux) {
        this.up_flux = up_flux;
    }

    public Double getDown_flux() {
        return down_flux;
    }

    public void setDown_flux(Double down_flux) {
        this.down_flux = down_flux;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUri() {
        return uri;
    }

    public void setUri(String uri) {
        this.uri = uri;
    }

    public String getLac() {
        return lac;
    }

    public void setLac(String lac) {
        this.lac = lac;
    }

    public String getTrmnl_brand() {
        return trmnl_brand;
    }

    public void setTrmnl_brand(String trmnl_brand) {
        this.trmnl_brand = trmnl_brand;
    }

    public String getTrmnl_model() {
        return trmnl_model;
    }

    public void setTrmnl_model(String trmnl_model) {
        this.trmnl_model = trmnl_model;
    }

    public String getRoam_type() {
        return roam_type;
    }

    public void setRoam_type(String roam_type) {
        this.roam_type = roam_type;
    }
}
