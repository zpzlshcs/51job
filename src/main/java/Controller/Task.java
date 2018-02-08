package Controller;

import java.util.HashMap;
import java.util.List;
import java.util.TimerTask;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import Dao.CompanyDao;
import Dao.PostitionDao;
import Entity.Company;
import Entity.Position;
import Util.getinString;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;
import us.codecraft.webmagic.selector.Html;

public class Task extends TimerTask{
	private static Site site = Site.me().setRetryTimes(3).setSleepTime(3000);
	@Override
	public void run() {
		// TODO Auto-generated method stub
		String startUrl = "http://search.51job.com/list/000000,000000,0000,00,9,99,%2B,2,2.html";
		String companyurl = "http://jobs.51job.com/all/co4035810.html#syzw";
		Request request = new Request(startUrl);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("type", "1");
		request.setExtras(map);
		Spider spidercontent = new Spider(new PageProcessor() {

			public void process(Page page) {
				// TODO Auto-generated method stub
				String type = page.getRequest().getExtra("type").toString();
				if(type.equals("1"))getCompany(page);
				else if(type.equals("2"))getCompanyContent(page);
				else if(type.equals("3"))getPositionContent(page);
			}

			public Site getSite() {
				// TODO Auto-generated method stub
				return site;
			}
		});
		spidercontent.addRequest(request).thread(20).run();
	}
	public static void getCompany(Page page){
		List<String> list = page.getHtml().links().all();
		for(String href :list){
			if(href.matches("(http://jobs.51job.com/+(\\w+)+/co+(\\w+).html)")){
				Request request1 = new Request(href);
				HashMap<String, Object> map1 = new HashMap<String, Object>();
				map1.put("type", "2");
				map1.put("company_id", getinString.getInt(href.split("job")[2]));
				request1.setExtras(map1);
				page.addTargetRequest(request1);
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
				map.put("type", "1");
				request.setExtras(map);
				page.addTargetRequest(request);
			}
		}
	}
	public static void getCompanyContent(Page page){
		int id = (int)page.getRequest().getExtra("company_id");
		if(new CompanyDao().searchCompany(id)==0){
			addCompany(page, id);
		}
		List<String> list = page.getHtml().links().all();
		for(String href :list){
			if(href.matches("(http://jobs.51job.com/+(\\w+)+/+(\\w+).html\\?s=04)")){
				Request request = new Request(href);
				HashMap<String, Object> map = new HashMap<String, Object>();
				map.put("company_id", id);
				map.put("type", "3");
				request.setExtras(map);
				page.addTargetRequest(request);
			}
		}
		
	}
	public static void addCompany(Page page,int id){
		Company company = new Company();
		company.setCompany_id(id);
		company.setCompany_address(page.getHtml()
				.xpath("/html/body/div[2]/div[2]/div[3]/div[2]/div/p/span/text()").toString());
		
	    company.setCompany_type(page.getHtml().xpath("/html/body/div[2]/div[2]/div[2]/div/p[1]/text()").toString().split("\\|")[0]);
	    String str = page.getHtml().xpath("/html/body/div[2]/div[2]/div[2]/div/p[1]/text()").toString().split("\\|")[1];
	    if(getinString.getInt(str) == 99){
	    	company.setCompany_guimo("0");
		    company.setCompany_hangye(page.getHtml().xpath("/html/body/div[2]/div[2]/div[2]/div/p[1]/text()").toString().split("\\|")[1]);
	    }else{
	    	company.setCompany_guimo(page.getHtml().xpath("/html/body/div[2]/div[2]/div[2]/div/p[1]/text()").toString().split("\\|")[1]);
		    company.setCompany_hangye(page.getHtml().xpath("/html/body/div[2]/div[2]/div[2]/div/p[1]/text()").toString().split("\\|")[2]);
	    }
	    //company.setCompany_introduce(page.getHtml().xpath("/html/body/div[2]/div[2]/div[3]/div[1]/div/div[1]/div[1]/p/text()").toString().split("\\|")[1]);
	    company.setCompany_address(page.getHtml().xpath("/html/body/div[2]/div[2]/div[3]/div[2]/div/p[1]/text()").toString());
	    company.setCompany_href(page.getUrl().toString());
	    company.setCompany_name(page.getHtml().xpath("/html/body/div[2]/div[2]/div[2]/div/h1/text()").toString());
	    new CompanyDao().addCompany(company);
	}
	public  static void getPositionContent(Page page) {
		Html html = page.getHtml();
		Element doc = Jsoup.parse(page.getHtml().toString()).select("div[class=\"tCompany_center clearfix\"]").get(0);
		Position position = new Position();
		position.setPosition_id(getinString.getInt(page.getUrl().toString().split("job")[2].split("html")[0]));
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
		String[] strl = page.getHtml().xpath("/html/body/div[3]/div[2]/div[2]/div/div[1]/p[2]/text(").toString().split("|");
		position.setPosition_type(strl[strl.length-1]);
		PostitionDao.SearchaddPosition(position);
		
	}
}
