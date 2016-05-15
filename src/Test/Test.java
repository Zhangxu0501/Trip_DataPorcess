package Test;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class Test {

	public static void sop(Object o)
	{
		System.out.println(o);
	}
	/**
	 * @param args
	 * @throws Exception 
	 */
	public static void main(String[] args) throws Exception {
		
		BufferedReader bufr=new BufferedReader(new FileReader("C:\\Users\\zx\\Desktop\\new 1"));
		BufferedReader bufr2=new BufferedReader(new FileReader("C:\\Users\\zx\\Desktop\\new 2"));
		BufferedWriter wr = new BufferedWriter(new FileWriter("C:\\Users\\zx\\Desktop\\Êä³ö½á¹û11.txt"));
		String line=null;
		ArrayList<String> al1 = new ArrayList<String>();
		ArrayList<String> al2 = new ArrayList<String>();
		while((line = bufr.readLine())!=null)
		{
			line=line.replace(" ", "");
			al1.add(line);
		}
		
		while((line = bufr2.readLine())!=null)
		{
			line=line.replace(" ", "");
			al2.add(line);
		}
		
		for(String s:al1)
			for(String ss:al2)
			{
				String l="http://dujia.qunar.com/pqkd/list_"+s+"_all_"+ss;
				sop(l);
				sop(s);
				sop(ss);
				wr.write(l);
				wr.newLine();
				
			}
		wr.flush();
		wr.close();
		
	}

}
