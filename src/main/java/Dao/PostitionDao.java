package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Entity.Position;
import Util.DateUtil;
import Util.SQLManager;

public class PostitionDao {
	public static void addposition(Position position){
		Connection conn = null;
		try {
			//position.setposition_id(Integer.parseInt(PKUtil.getRandomPk()));
			position.setPosition_addtime(DateUtil.getDate());
			conn = SQLManager.getConnection();
			PreparedStatement stat = conn
					.prepareStatement("insert into db_position("
							+ "position_id,position_name,position_company_id,position_nature,position_minware,"
							+ "position_maxware,position_address,position_posttime,position_education,position_getnum,position_minexperience,"
							+ "position_maxexperience,position_type,position_sex,position_minage,position_maxage,position_lauguage,"
							+ "position_other,position_addtime,position_href"
							+ ") VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
			stat.setInt(1, position.getPosition_id());
			stat.setString(2, position.getPosition_name());
			stat.setInt(3, position.getPosition_company_id());
			stat.setString(4, position.getPosition_nature());
			stat.setDouble(5, position.getPosition_minware());
			stat.setDouble(6, position.getPosition_maxware());
			stat.setString(7, position.getPosition_address());
			stat.setString(8, position.getPosition_posttime());
			stat.setString(9, position.getPosition_education());
			stat.setInt(10, position.getPosition_getnum());
			stat.setString(11, position.getPosition_minexperience());
			stat.setString(12, position.getPosition_maxexperience());
			stat.setString(13, position.getPosition_type());
			stat.setInt(14, position.getPosition_sex());
			stat.setInt(15, position.getPosition_minage());
			stat.setInt(16, position.getPosition_maxage());
			stat.setString(17, position.getPosition_lauguage());
			stat.setString(18, position.getPosition_other());
			stat.setString(19, position.getPosition_addtime());
			stat.setString(20, position.getPosition_href());
			stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLManager.close(conn);
		}
	}
	public static boolean searchPosition(int position_id){
		Connection conn = null;
		try {
			conn = SQLManager.getConnection();
			PreparedStatement stat = conn
					.prepareStatement("select * from db_position where position_id=?");
			stat.setInt(1, position_id);
			ResultSet rst = stat.executeQuery();
			if (rst.next()) {
				return true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLManager.close(conn);
		}
		return false;
	}
	public static void updatePosition(Position position){
		Connection conn = null;
		try {
			position.setPosition_addtime(DateUtil.getDate());
			conn = SQLManager.getConnection();
			PreparedStatement stat = conn
					.prepareStatement("update db_position set "
							+ "position_name=?,position_company_id=?,position_nature=?,position_minware=?,"
							+ "position_maxware=?,position_address=?,position_posttime=?,position_education=?,position_getnum=?,position_minexperience=?,"
							+ "position_maxexperience=?,position_type=?,position_sex=?,position_minage=?,position_maxage=?,position_lauguage=?,"
							+ "position_other=?,position_addtime=?,position_href=?"
							+ " where position_id=?");
			
			stat.setString(1, position.getPosition_name());
			stat.setInt(2, position.getPosition_company_id());
			stat.setString(3, position.getPosition_nature());
			stat.setDouble(4, position.getPosition_minware());
			stat.setDouble(5, position.getPosition_maxware());
			stat.setString(6, position.getPosition_address());
			stat.setString(7, position.getPosition_posttime());
			stat.setString(8, position.getPosition_education());
			stat.setInt(9, position.getPosition_getnum());
			stat.setString(10, position.getPosition_minexperience());
			stat.setString(11, position.getPosition_maxexperience());
			stat.setString(12, position.getPosition_type());
			stat.setInt(13, position.getPosition_sex());
			stat.setInt(14, position.getPosition_minage());
			stat.setInt(15, position.getPosition_maxage());
			stat.setString(16, position.getPosition_lauguage());
			stat.setString(17, position.getPosition_other());
			stat.setString(18, position.getPosition_addtime());
			stat.setString(19, position.getPosition_href());
			stat.setInt(20, position.getPosition_id());
			stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLManager.close(conn);
		}
	}
	public static void SearchaddPosition(Position position){
		if(searchPosition(position.getPosition_id())){
			updatePosition(position);
		}else{
			addposition(position);
		}
	}
}
