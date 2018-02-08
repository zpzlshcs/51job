package Controller;

import java.util.HashMap;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;

import Config.Httpseed;
import Dao.PostitionDao;
import Entity.Position;
import Util.SearchPositionContent;
import Util.getinString;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class getContenttest {

	private static Site site = Site.me().setRetryTimes(3).setSleepTime(3000);

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Request request = new Request("http://jobs.51job.com/wuhan-hsq/95315088.html?s=01&t=0");
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("type", 1);
		request.setExtras(map);
		Spider spidercontent = new Spider(new PageProcessor() {

			public void process(Page page) {
				// TODO Auto-generated method stub
				getContent(page);
			}

			public Site getSite() {
				// TODO Auto-generated method stub
				return site;
			}
		});
		spidercontent.addRequest(request).thread(100).run();
	}

	public static void getContent(Page page) {
		Html html = page.getHtml();
		Element doc = Jsoup.parse(page.getHtml().toString()).select("div[class=\"tCompany_center clearfix\"]").get(0);
		Position position = new Position();
		position.setPosition_name(html.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/h1/text()").toString());
		position.setPosition_href(page.getUrl().toString());
		position.setPosition_address(html.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/span/text()").toString());
		//position.setPosition_company_id(html.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/p[1]/a/text()").toString());

		Element title = doc.select("div[class=\"t1\"]").get(0);
		for (Element e : title.select("span")) {
			switch (e.select("em").attr("class").toString()) {
			case "i1":
				position.setPosition_minexperience(e.text());
				break;
			case "i2":
				position.setPosition_education(e.text());
				break;
			case "i3":
				position.setPosition_getnum(getinString.getInt(e.text()));
				break;
			case "i4":
				position.setPosition_posttime(e.text());
				break;
			case "i5":
				position.setPosition_lauguage(e.text());
				break;
			default:
				break;
			}
		}
		position.setPosition_other("");
		for (Element e : doc.select("p[class=\"t2\"]").select("span")) {
			position.setPosition_other(position.getPosition_other() + "," + e.text());
		}

		// position.setPosition_maxage(position_maxage);
		// position.setPosition_minage(position_minage);
		String ware = html.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/strong/text()").toString();
		position.setPosition_maxware(getinString.getDouble(ware)[0]);
		position.setPosition_minware(getinString.getDouble(ware)[1]);
		position.setPosition_nature(html.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/p[2]/text()").toString());
		// position.setPosition_sex(position_sex);
		// position.setPosition_type(position_type);
		PostitionDao.addposition(position);
		
	}
}
