package Controller;

import Util.getinString;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.processor.PageProcessor;

public class test {
	private static Site site = Site.me().setRetryTimes(3).setSleepTime(3000);
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String str = "http://jobs.51job.com/shanghai-sjq/97758935.html?s=07";
		System.out.println(getinString.getInt(str.split("job")[2].split("html")[0]));
	}

}
