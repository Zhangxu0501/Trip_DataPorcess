package Qunaer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import TuNiu_Data_Clean.Trip;


public class Spyder {
	
	
	public static void main(String[] args) throws Exception {
	
		//
		BufferedWriter bufw = new BufferedWriter(new FileWriter("C:\\Users\\zx\\Desktop\\result.txt"));
		BufferedReader bufr=new BufferedReader(new FileReader("C:\\Users\\zx\\Desktop\\url.txt"));
		String url=null;
		int count_job=1;
		int count_success=1;
		while((url = bufr.readLine())!=null)
		{
		Trip t = new Trip();
		try
		{
			
		System.out.println("正在执行第"+count_job+++"个任务");
		String title="name:'.*?'";
		Pattern title_p=Pattern.compile(title);
		
		String start_place="<b class=\"t\">出发城市</b>";
		Pattern start_place_p=Pattern.compile(start_place);
		
		String end_place="<div class=\"ct\" id=\"js_des_desc\">.*?</div>";
		Pattern end_place_p=Pattern.compile(end_place);
		
		String time="<div class=\"ct\">.*?天";
		Pattern time_p=Pattern.compile(time);
		
		String start_date=".*?等<a href=\"#js_book_bottom\">查看详细</a>";
		Pattern start_date_p=Pattern.compile(start_date);
		
		String traffic_1="交通信息";//读一行，再读一行，去掉“</div>”
		Pattern traffic_1_p=Pattern.compile(traffic_1);
		
		String price="<em placeholder=\"js_qunar_price\">.*?</em>";
		Pattern price_p=Pattern.compile(price);
		
		String tour_route="<strong class=\"day_v2\">D.*?</strong>";//读一个，第一个，在读一个就是了，用-连接。
		Pattern tour_route_p=Pattern.compile(tour_route);
		
		URLConnection conn = new URL(url).openConnection();
		BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream(),"utf-8"));
		String l = null;
		String [] lines=new String[3000];
		int i=0;
		while((l = in.readLine())!=null&&i<=4999){
			lines[i++]=l;
		}
		
		i=0;
		for(String line=lines[0];i<=4999;i++)
		{	line=lines[i];
			if(line==null)
				break;
			Matcher title_m = title_p.matcher(line);
			Matcher start_place_m = start_place_p.matcher(line);
			Matcher end_place_m = end_place_p.matcher(line);
			Matcher time_m = time_p.matcher(line);
			Matcher  start_date_m=  start_date_p.matcher(line);
			Matcher traffic_1_m = traffic_1_p.matcher(line);
			Matcher price_m = price_p.matcher(line);
			Matcher tour_route_m = tour_route_p.matcher(line);
			
			if(title_m.find())
			{
				String s=title_m.group();
				s=s.replaceAll("name:'", "");
				s=s.replaceAll("'", "");
				s=s.replaceAll(" ", "");
				t.title=s;
			}
			else if(start_place_m.find())
			{
				String s=start_place_m.group();
				s=lines[i+2];
				s=s.replaceAll("&nbsp;出发", "");
				s=s.replaceAll(" ", "");
				s=s.replaceAll("\\t", "");
				t.start_place=s;
			}
			else if(end_place_m.find())
			{
				String s=end_place_m.group();
				s=s.replaceAll("<div class=\"ct\" id=\"js_des_desc\">", "");
				s=s.replaceAll("</div>", "");
				s=s.replaceAll(" ", "");
				s=s.replaceAll("\\t", "");
				t.end_place=s;		}
			else if(time_m.find())
			{
				String s=time_m.group();
				s=s.replaceAll("<div class=\"ct\">", "");
				s=s.replaceAll("天", "");
				s=s.replaceAll(" ", "");
				s=s.replaceAll("\\t", "");
				t.time=Integer.parseInt(s);
			}
			else if(start_date_m.find())
			{
				String s=start_date_m.group();
				s=s.replaceAll("等<a href=\"#js_book_bottom\">查看详细</a>", "");
				s=s.replaceAll(" ", "");
				s=s.replace("月", "/");
				s=s.replace("日", "");
				s=s.replace("、",",");
				s=s.replaceAll("\\t", "");
				t.start_date=s;
			}
			else if(traffic_1_m.find())
			{
				String s = lines[i+2];
				s=s.replaceAll("</div>", "");
				s=s.replaceAll(" ", "");
				s=s.replaceAll("\\t", "");
				t.traffic=s;
			}
			else if(price_m.find())
			{
				String s=price_m.group();
				s=s.replaceAll("<em placeholder=\"js_qunar_price\">", "");
				s=s.replaceAll("</em>", "");
				s=s.replaceAll(" ", "");
				s=s.replaceAll(",", "");
				s=s.replaceAll("\\t", "");
				t.price=Integer.parseInt(s);
			}
			else if(tour_route_m.find())
			{
				String s = lines[i+3];
				s=s.replaceAll(" ", "");
				s=s.replaceAll("\\t", "");
				if(t.tour_route==null)
					t.tour_route=s;
				else
				{
					t.tour_route=t.tour_route+"-"+s;
					t.tour_route=t.tour_route.replaceAll("-", ">");
					t.tour_route=t.tour_route.replaceAll("—", ">");
					
				}
			}
						
			
			t.web="去哪儿网";
			t.url=url;
			
		}
		
		}
		catch(Exception e)
		{
			
		}
		if(t.start_place==null||t.end_place==null|t.traffic==null||t.time==0||t.tour_route==null||t.title==null||t.price==0||t.start_date==null)
			continue;
		System.out.println("第"+count_success+++"个任务成功,有"+(count_job-count_success)+"个任务失败");
		String s = t.start_place+"  ,"+t.end_place+"  ,"+t.traffic+"  ,"+t.time+"  ,"+t.tour_route+"  ,"+t.title+"  ,"+t.price+"  ,"+t.start_date+"  ,"+t.url+"  ,"+t.web;
		System.out.println(s);
		bufw.write(s);
		bufw.newLine();
		bufw.flush();
	}
		bufw.close();
		bufr.close();
	}
}
