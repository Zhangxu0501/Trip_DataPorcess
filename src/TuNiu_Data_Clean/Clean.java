package TuNiu_Data_Clean;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.UnsupportedEncodingException;
import java.util.Date;

import org.json.JSONException;
import org.json.JSONObject;

import DAO.HbaseDAO;

public class Clean {
	public static void sop(Object o)
	{
		System.out.println(o);
	}
	 public static Trip toMap(String jsonString) throws JSONException, UnsupportedEncodingException {

	        JSONObject jsonObject = new JSONObject(jsonString);
	        
	        Trip t = new Trip();
	        String [] lines;
	          
	        
	        
            String start_place= jsonObject.getString("start_place");  //必须有
            if(start_place.equals("[]"))
            {
            	return null; 
            }
            lines =start_place.split("\"");
            start_place=lines[1];
            start_place= start_place.replace("出发", "");
            t.start_place=start_place;
            sop(start_place);
           
            
            String end_place= jsonObject.getString("end_place");//必须有
            t.end_place=java.net.URLDecoder.decode(end_place,"utf-8");
           
            
            String duration = jsonObject.getString("duration");
            lines =duration.split(",");
            for(String s:lines)
            {
            	if(s.contains("宿："))//可选
            	{
            	
            		s=s.replace("[\"住　　宿：\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t", "");
            		s=s.replace("[\"住　　宿：\\n\\t\\t\\t\\t\\t\\t\\t\\t", "");
            		s=s.replace("\\t\\t\\t\\t\\t\\t\\t\\t\"", "");   		
            		t.hotel=s;
            	}
            		
            	if(s.contains("通："))//可选
            	{
            		s=s.replace("\"交　　通：\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t", "");
            		s=s.replace("\\t\\t\\t\\t\\t\\t\\t\\t\"", ""); 
            		s=s.replace("\"交　　通：\\n\\t\\t\\t\\t\\t\\t\\t\\t", "");
            		s=s.replace("[", "");
            		t.traffic=s;
            	}
           
            		
            	if(s.contains("行程"))//必须有
            	{
            		s=s.replace("\"行程天数：", "");
            		s=s.replace("天\"]", "");   
            		s=s.replace("[", "");
            		t.time=Integer.parseInt(s);
            	}
            		
            }
            
            
            String tour_route = jsonObject.getString("tour_route");//必有
            tour_route=tour_route.replace("[\"行程概览：", "");
            tour_route=tour_route.replace("\"]", "");
            t.tour_route=tour_route;
            
            
            String title = jsonObject.getString("item_name");//必有
            title=title.replace("[\"<", "");
            title=title.replace("\"]", "");
            t.title=title;
           
            
            String price= jsonObject.getString("price");//必有
            price=price.replace("\"]", "");
            price=price.replace("[\"", "");
            if(price.equals("[]"))
            {
            	return null; 
            }
            t.price=Integer.parseInt(price);
            
            String url=jsonObject.getString("url");
            url=url.replace("[\"", "");
            url=url.replace("\"]", "");
            t.url=url;
        
            
            String start_date=jsonObject.getString("start_date");
            start_date=start_date.replace("[\",", "");
            start_date=start_date.replace("[\"", "");
            start_date=start_date.replace("\"]", "");
            start_date=start_date.replace(" \",\"", "");
            start_date=start_date.replace("天天发团", "");
            start_date=start_date.replace(" ", "");
            if(start_date.charAt(0)==',');
            start_date=start_date.substring(1);
            t.start_date=start_date;
            t.web="途牛网";
	         return t;
	 }
	public static void main(String[] args) throws Exception{
		int count=1;
		HbaseDAO h = new HbaseDAO();
		sop(new Date().getTime());  
		BufferedReader bufr=new BufferedReader(new FileReader("C:\\Users\\zx\\Desktop\\demo.jsonlines"));
		String line=null;
		while((line = bufr.readLine())!=null)
		{
			Trip t = toMap(line);
			try
			{
				
			
			String rowkey = t.web+"-"+t.start_place+"-"+t.time+"-"+t.end_place+"-"+new Date().getTime();
			sop(rowkey);
			h.insert(t);
			sop("正在插入第"+(count++)+"个数据");
			}
			catch(Exception e)
			{
				
			}
		
		}
	}
	

}
