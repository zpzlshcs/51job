package Controller;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import Config.MySQLConfig;
import Util.SQLManager;

public class sqltest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		try{
            //调用Class.forName()方法加载驱动程序
            Class.forName("com.mysql.cj.jdbc.Driver");
            System.out.println("成功加载MySQL驱动！");
        	String url=MySQLConfig.URL;    //JDBC的URL    
            //调用DriverManager对象的getConnection()方法，获得一个Connection对象
            Connection conn;
            conn = DriverManager.getConnection(url,"root",MySQLConfig.Password);
            //创建一个Statement对象
            Statement stmt = conn.createStatement(); //创建Statement对象
            System.out.println("成功连接到数据库！");
            //product为数据库名称
            String sql = "select * from "+"db_type";
            ResultSet rs = stmt.executeQuery(sql);
            while (rs.next()){
                System.out.print(rs.getString(1) + "\t");
                System.out.print(rs.getString(2) + "\t");
                System.out.println();
            }
            //修改数据的代码
//            String change = "update product set content='qweqwe' where pic='a6.jpg'";
//            stmt.executeUpdate(change);
            //增加数据
//            String add="insert into product values('23333','a','b','c','d','a7.jpg','a')";   //SQL语句
//            stmt.executeUpdate(add);         //将sql语句上传至数据库执行
            //删除数据
//            String delete = "delete from product where content='qweqwe'";
//            stmt.executeUpdate(delete);         //将sql语句上传至数据库执行
            rs.close();
            stmt.close();
            conn.close();
        } catch (SQLException e){
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("无法连接数据库驱动");
		}
	}

}
