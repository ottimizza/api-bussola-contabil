package br.com.ottimizza.dashboard.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	
	public static String formatCnpj(String cnpj){
		String cnpj2 = cnpj.replaceAll("\\D", "");
		cnpj2 = StringUtils.leftPad(cnpj2, 14, "0");

		//feito para evitar a manipulacao por substring
		Pattern pattern = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
		Matcher matcher = pattern.matcher(cnpj2);
		if(matcher.find()){
			return matcher.replaceAll("$1.$2.$3/$4-$5");
		}
		return cnpj;
	}
	
	public static List<String> formatCnpj(List<String> cnpjs){
		
		return cnpjs.stream().map(StringUtil::formatCnpj).collect(Collectors.toList());
		
	}
}
