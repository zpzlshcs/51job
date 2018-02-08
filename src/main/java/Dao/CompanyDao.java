package Dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import Entity.Company;
import Util.DateUtil;
import Util.SQLManager;

public class CompanyDao {
	public static void addCompany(Company company){
		Connection conn = null;
		try {
			//company.setCompany_id(Integer.parseInt(PKUtil.getRandomPk()));
			company.setCompany_addtime(DateUtil.getDate());
			conn = SQLManager.getConnection();
			PreparedStatement stat = conn
					.prepareStatement("insert into db_company(company_id,company_name,company_href,company_type,"
							+ "company_guimo,company_hangye,company_address,company_addtime,company_introduce) VALUES(?,?,?,?,?,?,?,?,?)");
			stat.setInt(1, company.getCompany_id());
			stat.setString(2, company.getCompany_name());
			stat.setString(3, company.getCompany_href());
			stat.setString(4, company.getCompany_type());
			stat.setString(5, company.getCompany_guimo());
			stat.setString(6, company.getCompany_hangye());
			stat.setString(7, company.getCompany_address());
			stat.setString(8, company.getCompany_addtime());
			stat.setString(9, company.getCompany_introduce());
			stat.executeUpdate();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLManager.close(conn);
		}
	}
	public static int searchCompany(int company_id){
		Connection conn = null;
		int havethiscompany = 0;
		try {
			//company.setCompany_id(Integer.parseInt(PKUtil.getRandomPk()));
			conn = SQLManager.getConnection();
			PreparedStatement stat = conn
					.prepareStatement("select * from db_company where company_id=?");
			stat.setInt(1, company_id);
			ResultSet rst = stat.executeQuery();
			if (rst.next()) {
				havethiscompany = 1;
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			SQLManager.close(conn);
		}
		return havethiscompany;
	}
}
