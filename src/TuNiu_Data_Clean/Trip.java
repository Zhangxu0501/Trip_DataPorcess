package TuNiu_Data_Clean;

import java.util.Date;

public class Trip {
	public String start_place;//必选-----------------
	public String end_place;//必选-----------
	public String traffic;//选----------
	public String hotel;//选-----------(去哪网没有)
	public int time;//必选-------------
	public String tour_route;//必选----------
	public String title;//必选----------
	public int price;//必选-------------
	public String start_date;//必选------------
	public String url;//必选----------
	public String web;
	@Override
	public String toString() {
		return "Trip [end_place=" + end_place + ", hotel=" + hotel + ", price="
				+ price + ", start_date=" + start_date + ", start_place="
				+ start_place + ", time=" + time + ", title=" + title
				+ ", tour_route=" + tour_route + ", traffic=" + traffic
				+ ", url=" + url + "]";
	}
	
	

}
