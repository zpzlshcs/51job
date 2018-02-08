package Util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class getinString {

	/**
	 * 2012.6.2
	 */

	public static int getInt(String str) {
		char[] charlist = str.toCharArray();
		String num = "";
		for(char c :charlist){
			if(Character.isDigit(c)){
				num = num+c;
			}
		}
		if(num.length()==0){
			return 99;
		}
		return Integer.parseInt(num);
	}
	public static double[] getDouble(String ptCasinoMsg){
		char[] charlist = ptCasinoMsg.toCharArray();
		List<String> doublelist = new ArrayList<>();
		String num = "";
		for(char c :charlist){
			if(Character.isDigit(c)){
				num = num+c;
			}
			else if(c == '.'){
				num = num+c;
			}
			else{

				if(num.length()!=0){
					doublelist.add(num);
					num = "";
				}
			}
		}
		double doubleresult[] = new double[doublelist.size()];
		for(int i = 0;i<doublelist.size();i++){
			doubleresult[i] = Double.parseDouble(doublelist.get(i));
		}
		return doubleresult;
	}

}