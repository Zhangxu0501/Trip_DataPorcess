package Qunaer;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GetUrl {
	public static void sop(Object o)
	{
		System.out.println(o);
	}

	public static void main(String[] args) throws Exception {
		
		String reg = "data-pid=\".*?\"";
		
		Pattern p =Pattern.compile(reg);
		
		BufferedWriter bufw = new BufferedWriter(new FileWriter("C:\\Users\\zx\\Desktop\\url.txt"));
		File file = new File("C:\\Users\\zx\\Desktop\\quna");
		File [] fs = file.listFiles();
		for(File i:fs)
			if(i.isFile())
			{
				String line;
				BufferedReader bufr = new BufferedReader(new FileReader(i));
				while((line=bufr.readLine())!=null)
				{
					Matcher m2 = p.matcher(line);
					if(m2.find())
					{
						line = m2.group();
						line=line.replaceAll("data-pid=\"", "");
						line=line.replaceAll("\"", "");
						line = "http://clqt3.package.qunar.com/user/id="+line+"&abt=a";
						bufw.write(line);
						bufw.newLine();
						
						sop(line);
					}
					
				}
			}
			
			
	bufw.close();
	}
	
}
