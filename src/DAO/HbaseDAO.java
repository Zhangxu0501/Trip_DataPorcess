package DAO;

import java.util.Date;

import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HColumnDescriptor;
import org.apache.hadoop.hbase.HTableDescriptor;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.HTable;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.util.Bytes;
import org.junit.Test;

import TuNiu_Data_Clean.Trip;



public class HbaseDAO {
	
private static org.apache.hadoop.conf.Configuration conf = null;
	
	static{
		conf = HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "zx0:2181");
		}
	
	
	@Test
	public  void insert(Trip t)throws Exception
	{
		HTable table = new HTable(conf,"Trip1");
		
		if(t==null)
			return ;
		
		if(t.web.equals("Í¾Å£Íø"))
		{
			
		
		String rowkey = t.web+"-"+t.start_place+"-"+t.time+"-"+t.end_place+"-"+new Date().getTime();
		Put p = new Put(rowkey.getBytes());
		
		if(t.traffic!=null)
		p.add(Bytes.toBytes("bi"),Bytes.toBytes("traffic"),Bytes.toBytes(t.traffic));
		if(t.hotel!=null)
		p.add(Bytes.toBytes("bi"),Bytes.toBytes("hotel"),Bytes.toBytes(t.hotel));
		p.add(Bytes.toBytes("bi"),Bytes.toBytes("tour_route"),Bytes.toBytes(t.tour_route));
		p.add(Bytes.toBytes("bi"),Bytes.toBytes("title"),Bytes.toBytes(t.title));
		p.add(Bytes.toBytes("bi"),Bytes.toBytes("price"),Bytes.toBytes(t.price));
		p.add(Bytes.toBytes("bi"),Bytes.toBytes("start_data"),Bytes.toBytes(t.start_date));
		p.add(Bytes.toBytes("bi"),Bytes.toBytes("url"),Bytes.toBytes(t.url));
		p.add(Bytes.toBytes("bi"),Bytes.toBytes("web"),Bytes.toBytes(t.web));
		p.add(Bytes.toBytes("bi"),Bytes.toBytes("start_place"),Bytes.toBytes(t.start_place));
		p.add(Bytes.toBytes("bi"),Bytes.toBytes("end_place"),Bytes.toBytes(t.end_place));
		p.add(Bytes.toBytes("bi"),Bytes.toBytes("time"),Bytes.toBytes(t.time));
		table.put(p);
		System.out.println(rowkey);
		}
		else
		{

			String rowkey = t.web+"-"+t.start_place+"-"+t.time+"-"+new Date().getTime();
			Put p = new Put(rowkey.getBytes());
			
			p.add(Bytes.toBytes("bi"),Bytes.toBytes("traffic"),Bytes.toBytes(t.traffic));
			p.add(Bytes.toBytes("bi"),Bytes.toBytes("tour_route"),Bytes.toBytes(t.tour_route));
			p.add(Bytes.toBytes("bi"),Bytes.toBytes("title"),Bytes.toBytes(t.title));
			p.add(Bytes.toBytes("bi"),Bytes.toBytes("price"),Bytes.toBytes(t.price));
			p.add(Bytes.toBytes("bi"),Bytes.toBytes("start_data"),Bytes.toBytes(t.start_date));
			p.add(Bytes.toBytes("bi"),Bytes.toBytes("url"),Bytes.toBytes(t.url));
			p.add(Bytes.toBytes("bi"),Bytes.toBytes("web"),Bytes.toBytes(t.web));
			p.add(Bytes.toBytes("bi"),Bytes.toBytes("end_place"),Bytes.toBytes(t.end_place));
			p.add(Bytes.toBytes("bi"),Bytes.toBytes("start_place"),Bytes.toBytes(t.start_place));
			p.add(Bytes.toBytes("bi"),Bytes.toBytes("time"),Bytes.toBytes(t.time));
			System.out.println(rowkey);
			table.put(p);
		}
		
		System.out.println(t);
	}
	
	@Test
	public  void createTable() throws Exception {

		
		
		HBaseAdmin admin = new HBaseAdmin(conf);
		
		TableName name = TableName.valueOf("Trip1");
		
		
		HTableDescriptor desc = new HTableDescriptor(name);
		
		
		HColumnDescriptor base_info = new HColumnDescriptor("bi");
		
		base_info.setMaxVersions(5);

		desc.addFamily(base_info);
	
		
		admin.createTable(desc);
	}
	
	/*
	@Test
	public void insertTest() throws Exception{
		HTable nvshen = new HTable(conf, "Tirrp");
		Put name = new Put(Bytes.toBytes("rk0001"));
		name.add(Bytes.toBytes("base_info"), Bytes.toBytes("name"), Bytes.toBytes("angelababy"));

		Put age = new Put(Bytes.toBytes("rk0001"));
		age.add(Bytes.toBytes("base_info"), Bytes.toBytes("age"), Bytes.toBytes(18));
		
		ArrayList<Put> puts = new ArrayList<Put>();
		puts.add(name);
		puts.add(age);
		nvshen.put(puts);
		
	}

	
	
	public static void main(String[] args) throws Exception {

		Configuration conf =  HBaseConfiguration.create();
		conf.set("hbase.zookeeper.quorum", "itcast01:2181");
		
		HBaseAdmin admin = new HBaseAdmin(conf);
		
		TableName name = TableName.valueOf("Trrip");
		
		
		HTableDescriptor desc = new HTableDescriptor(name);
		
		
		HColumnDescriptor base_info = new HColumnDescriptor("bi");
		HColumnDescriptor extra_info = new HColumnDescriptor("ei");
		base_info.setMaxVersions(5);
		extra_info.setMaxVersions(5);
		desc.addFamily(base_info);
		desc.addFamily(extra_info);
		
		admin.createTable(desc);
	}
	@Test
	public void testDropTable()throws Exception
	{
	HBaseAdmin admin = new HBaseAdmin(conf);
	admin.disableTable("Trrip");
	admin.deleteTable("Trrip");
	admin.close();
	}
	
	@Test
	public void testPut()throws Exception
	{
	
		
	}
	

	@Test
	public void testGet()throws Exception
	{
	
		HTable table = new HTable(conf,"Trip");
		Get get = new Get(Bytes.toBytes("xiecheng017261"));
		get.setMaxVersions(5);
		Result result =table.get(get);
		List<Cell> cells = result.listCells();
		
		for(KeyValue kv : result.list()){
			String family = new String(kv.getFamily());
			System.out.println(family);
			String qualifier = new String(kv.getQualifier());
			System.out.println(qualifier);
			System.out.println(new String(kv.getValue()));
			System.out.println("-----------------------------");
			
		}
	}
	
	@Test
	public void testScan()throws Exception
	{
		HTable table = new HTable(conf,"Trip");
		Scan scan = new Scan();
		
		//Ç°×º¹ýÂËÆ÷£¬Õë¶ÔÐÐ¼ü
		Filter filter = new PrefixFilter(Bytes.toBytes("xiecheng0000"));
		//ÐÐ¹ýÂËÆ÷
		ByteArrayComparable  rowComparator=new BinaryComparator(Bytes.toBytes("xiecheng000011"));
		RowFilter rf = new RowFilter(CompareOp.LESS_OR_EQUAL, rowComparator);
		
		//scan.setFilter(rf);
		
		scan.setFilter(rf);
		scan.setFilter(filter);
		ResultScanner res = table.getScanner(scan);
		
		
		int a=1;
		for(Result r:res)
		{
	
			System.out.println(new String(r.getRow()));
			a++;
			System.out.println(new String((r.getValue(Bytes.toBytes("bi"), Bytes.toBytes("place")))));
		}
		System.out.println(a);
	}
	
	
	public void sop()
	{
		System.out.println("Spring and hbase and SpringMVC worked");
	}
	public ArrayList<Place> Search (String start,String date,String end) throws IOException
	{

		HTable table = new HTable(conf,"Trip");
		Scan scan = new Scan();
		ArrayList<Filter> filters = new ArrayList<Filter>();
		
		SubstringComparator comparator_s = new SubstringComparator(start);
		Filter f_s=new SingleColumnValueFilter("bi".getBytes(), "start".getBytes(), CompareOp.EQUAL, comparator_s);
		filters.add(f_s);
		
		SubstringComparator comparator_e = new SubstringComparator(end);
		Filter f_e=new SingleColumnValueFilter("bi".getBytes(), "end".getBytes(), CompareOp.EQUAL, comparator_e);
		filters.add(f_e);
		
		SubstringComparator comparator_d = new SubstringComparator(date);
		Filter f_d =new SingleColumnValueFilter("bi".getBytes(), "data".getBytes(), CompareOp.EQUAL, comparator_d);
		filters.add(f_d);
		
		Filter filter = new FilterList(FilterList.Operator.MUST_PASS_ALL,filters);
		scan.setFilter(filter);
		ResultScanner res = table.getScanner(scan);
		
		ArrayList<Place> ls = new ArrayList<Place>();
		
		int a=1;
		for(Result r:res)
		{
			String row =new String(r.getRow());
			String start1=new String((r.getValue(Bytes.toBytes("bi"), Bytes.toBytes("start"))));
			String end1=new String((r.getValue(Bytes.toBytes("bi"), Bytes.toBytes("end"))));
			String price=new String((r.getValue(Bytes.toBytes("bi"), Bytes.toBytes("price"))));
			String place=new String((r.getValue(Bytes.toBytes("bi"), Bytes.toBytes("place"))));
			String data=new String((r.getValue(Bytes.toBytes("bi"), Bytes.toBytes("data"))));
			Place p = new Place();
			p.setData(data);
			p.setEnd(end1);
			p.setPlace(place);
			p.setPrice(price);
			p.setRow(row);
			p.setStart(start1);
			ls.add(p);
		}
		
		
		System.out.println(a);
		
		
		table.close();
		
		return ls;
		
	}
	*/
}
