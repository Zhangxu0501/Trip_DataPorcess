package TuNiu_Data_Clean;

import java.util.Date;

public class Trip {
	public String start_place;//��ѡ-----------------
	public String end_place;//��ѡ-----------
	public String traffic;//ѡ----------
	public String hotel;//ѡ-----------(ȥ����û��)
	public int time;//��ѡ-------------
	public String tour_route;//��ѡ----------
	public String title;//��ѡ----------
	public int price;//��ѡ-------------
	public String start_date;//��ѡ------------
	public String url;//��ѡ----------
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
