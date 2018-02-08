package Util;

import java.util.HashMap;
import java.util.List;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Dao.PostitionDao;
import Entity.Position;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class SearchPositionContent implements PageProcessor{

	Site site;
	public SearchPositionContent(Site site){
		this.site  = site;
	}
	public void process(Page page) {
		// TODO Auto-generated method stub
		if(page.getRequest().getExtras().get("type").equals(1)){
			getContent(page);
		}
		else{
			List<String> list = page.getHtml().links().all();
			for(String href :list){
				if(href.matches("(http://jobs.51job.com/+(\\w+)+/+(\\w+).html\\?s=01&t=0)")){
					Request request = new Request(href);
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("type_name", page.getRequest().getExtras().get("type_name"));
					map.put("type", 1);
					request.setExtras(map);
					page.addTargetRequest(request);
				}
			}
			List<String> pagelist = page.getHtml().xpath("//*[@id=\"resultList\"]/div/div/div/div/ul/li/a").all();
			for(String str :pagelist){
				Document doc = Jsoup.parse(str);
				String text = doc.text().toString();
				String href = doc.select("a").attr("href");
				if(text.equals("下一页")){
					Request request = new Request(href);
					HashMap<String, Object> map = new HashMap<String, Object>();
					map.put("type", 2);
					map.put("type_name", page.getRequest().getExtras().get("type_name"));
					request.setExtras(map);
					page.addTargetRequest(request);
				}
			}
		}
		
	}
	
	public Site getSite() {
		// TODO Auto-generated method stub
		return site;
	}
	public  void getContent(Page page) {
		Html html = page.getHtml();
		Element doc = Jsoup.parse(page.getHtml().toString()).select("div[class=\"tCompany_center clearfix\"]").get(0);
		Position position = new Position();
		position.setPosition_name(html.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/h1/text()").toString());
		position.setPosition_href(page.getUrl().toString());
		position.setPosition_address(html.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/span/text()").toString());
		position.setPosition_company_id((int)page.getRequest().getExtra("company_id"));

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
		try{
			String ware = html.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/strong/text()").toString();
			position.setPosition_maxware(getinString.getDouble(ware)[1]);
			position.setPosition_minware(getinString.getDouble(ware)[0]);
		}catch(ArrayIndexOutOfBoundsException e){
			position.setPosition_maxware(0);
			position.setPosition_minware(0);
		}
		position.setPosition_nature(html.xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/p[2]/text()").toString());
		// position.setPosition_sex(position_sex);
		position.setPosition_type(page.getRequest().getExtras().get("type_name").toString());
		PostitionDao.addposition(position);
		
	}

}
