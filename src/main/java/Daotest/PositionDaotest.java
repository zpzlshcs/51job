package Daotest;

import org.jsoup.nodes.Element;

import Dao.PostitionDao;
import Entity.Position;
import Util.getinString;

public class PositionDaotest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Position position = new Position();
		position.setPosition_id(123123);
		position.setPosition_name(1+"");
		position.setPosition_href(1+"");
		position.setPosition_address(1+"");
		position.setPosition_company_id(183879);
		position.setPosition_minexperience(1+"");
		position.setPosition_maxexperience("1");
		position.setPosition_education(1+"");
		position.setPosition_getnum(1);
		position.setPosition_posttime(1+"");
		position.setPosition_lauguage(1+"");
		position.setPosition_other(1+"");
		
		position.setPosition_maxage(1);
		position.setPosition_minage(1);
		position.setPosition_maxware(1);
		position.setPosition_minware(1);
		position.setPosition_nature(1+"");
		position.setPosition_sex(1);
		position.setPosition_type("1");
		PostitionDao.addposition(position);
	}

}
