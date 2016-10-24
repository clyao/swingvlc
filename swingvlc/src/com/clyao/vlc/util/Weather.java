package com.clyao.vlc.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * @author clyao
 * @time 2015-11-16 15:00
 * @version 1.0v
 * @description 获取百度天气
 */
public class Weather {
	
	//从百度获取天气信息
	public String getWeatherForBaidu() {

		String result = "";
		BufferedReader in = null;
		try {
			URL url = new URL(
					"http://api.map.baidu.com/telematics/v3/weather?location=%E5%B9%BF%E5%B7%9E&output=json&ak=6WuRMDGsQx0cEGIKR8y060yy");
			// 打开和URL之间的连接
			URLConnection connection = url.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent",
					"Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream(), "UTF-8"));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
			System.out.println("成功连接了百度天气api.........");
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}
	
	public Weather resolveJSON(){
		Weather w = new Weather();
		JSONObject jsonObject  = JSONObject.fromObject(getWeatherForBaidu());
        JSONArray jsonArray = jsonObject.getJSONArray("results");
        for (int i = 0; i < jsonArray.size(); i++){
        	 JSONObject weatherJson = JSONObject.fromObject(jsonArray.get(i));
        	 JSONArray weatherJsonArray = weatherJson.getJSONArray("weather_data");
        	 JSONObject weatherJsons = JSONObject.fromObject(weatherJsonArray.get(0));
        	 String[] str = weatherJsons.getString("date").toString().split(" ");
        	 w.setWeek(str[0]);
        	 w.setDate(str[1]);
        	 w.setWind(weatherJsons.getString("wind"));
        	 w.setTemperature(weatherJsons.getString("temperature"));
        	 w.setWeather(weatherJsons.getString("weather"));
        	 w.setRealWeather(str[2]);
        }
        return w;
	}
	
	private String week;
	private String date;
	private String temperature;
	private String wind;
	private String weather;
	private String realWeather;

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}
	
	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}
	
	public String getWind() {
		return wind;
	}

	public void setWind(String wind) {
		this.wind = wind;
	}

	public String getWeather() {
		return weather;
	}

	public void setWeather(String weather) {
		this.weather = weather;
	}

	public String getRealWeather() {
		return realWeather;
	}

	public void setRealWeather(String realWeather) {
		this.realWeather = realWeather;
	}
	
}
