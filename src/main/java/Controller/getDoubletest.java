package Controller;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Util.getinString;

public class getDoubletest {

	public static void main(String[] args) {
		   String str = "0.8-1万/月";
		   System.out.println(getinString.getDouble(str)[0]);
		}

		

}
