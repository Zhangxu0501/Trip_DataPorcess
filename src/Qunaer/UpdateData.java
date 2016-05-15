package Qunaer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

import DAO.HbaseDAO;
import TuNiu_Data_Clean.Trip;

public class UpdateData {


	public static void main(String[] args) throws Exception {
		
		HbaseDAO h = new HbaseDAO();
		BufferedReader bufr=new BufferedReader(new FileReader("C:\\Users\\zx\\Desktop\\result.txt"));
		String line=null;
		int count =1;
		while((line = bufr.readLine())!=null)
		{
			Trip t = new Trip();
			String [] lines=line.split("  ,");
			if(lines.length!=10)
				continue;
			t.start_place=lines[0];
			t.end_place=lines[1];
			t.traffic=lines[2];
			t.time=Integer.parseInt(lines[3]);
			t.tour_route=lines[4];
			t.title=lines[5];
			t.price=Integer.parseInt(lines[6]);
			t.start_date=lines[7];
			t.url=lines[8];
			t.web=lines[9];
			h.insert(t);
			System.out.println("第"+(count++)+"条数据插入成功");
			
		}

	}

}
