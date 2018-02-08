package Controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import Util.DateUtil;
import Util.SQLManager;

public class typetest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Element doc = Jsoup.parse(readString2());
		for(Element e:doc.select("em")){
			Connection conn = null;
			try {
				conn = SQLManager.getConnection();
				PreparedStatement stat = conn
						.prepareStatement("insert into db_type(type_name,type_num) VALUES(?,?)");
				stat.setString(1, e.text());
				stat.setString(2, e.attr("data-value"));
				stat.executeUpdate();
			} catch (Exception eee) {
				eee.printStackTrace();
			} finally {
				SQLManager.close(conn);
			}
			
		}
	}
	private static String readString2()

	{
	    String str="";

	    File file=new File("D:\\Desktop\\type.txt");

	    try {

	        FileInputStream in=new FileInputStream(file);

	        // size  为字串的长度 ，这里一次性读完

	        int size=in.available();

	        byte[] buffer=new byte[size];

	        in.read(buffer);

	        in.close();

	        str=new String(buffer,"GB2312");

	    } catch (IOException e) {

	        // TODO Auto-generated catch block


	        e.printStackTrace();

	        return null;

	    }

	    return str;

	}
}
