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
	          
	        
	        
            String start_place= jsonObject.getString("start_place");  //������
            if(start_place.equals("[]"))
            {
            	return null; 
            }
            lines =start_place.split("\"");
            start_place=lines[1];
            start_place= start_place.replace("����", "");
            t.start_place=start_place;
            sop(start_place);
           
            
            String end_place= jsonObject.getString("end_place");//������
            t.end_place=java.net.URLDecoder.decode(end_place,"utf-8");
           
            
            String duration = jsonObject.getString("duration");
            lines =duration.split(",");
            for(String s:lines)
            {
            	if(s.contains("�ޣ�"))//��ѡ
            	{
            	
            		s=s.replace("[\"ס�����ޣ�\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t", "");
            		s=s.replace("[\"ס�����ޣ�\\n\\t\\t\\t\\t\\t\\t\\t\\t", "");
            		s=s.replace("\\t\\t\\t\\t\\t\\t\\t\\t\"", "");   		
            		t.hotel=s;
            	}
            		
            	if(s.contains("ͨ��"))//��ѡ
            	{
            		s=s.replace("\"������ͨ��\\r\\n\\t\\t\\t\\t\\t\\t\\t\\t", "");
            		s=s.replace("\\t\\t\\t\\t\\t\\t\\t\\t\"", ""); 
            		s=s.replace("\"������ͨ��\\n\\t\\t\\t\\t\\t\\t\\t\\t", "");
            		s=s.replace("[", "");
            		t.traffic=s;
            	}
           
            		
            	if(s.contains("�г�"))//������
            	{
            		s=s.replace("\"�г�������", "");
            		s=s.replace("��\"]", "");   
            		s=s.replace("[", "");
            		t.time=Integer.parseInt(s);
            	}
            		
            }
            
            
            String tour_route = jsonObject.getString("tour_route");//����
            tour_route=tour_route.replace("[\"�г̸�����", "");
            tour_route=tour_route.replace("\"]", "");
            t.tour_route=tour_route;
            
            
            String title = jsonObject.getString("item_name");//����
            title=title.replace("[\"<", "");
            title=title.replace("\"]", "");
            t.title=title;
           
            
            String price= jsonObject.getString("price");//����
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
            start_date=start_date.replace("���췢��", "");
            start_date=start_date.replace(" ", "");
            if(start_date.charAt(0)==',');
            start_date=start_date.substring(1);
            t.start_date=start_date;
            t.web=";ţ��";
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
			sop("���ڲ����"+(count++)+"������");
			}
			catch(Exception e)
			{
				
			}
		
		}
	}
	

}
