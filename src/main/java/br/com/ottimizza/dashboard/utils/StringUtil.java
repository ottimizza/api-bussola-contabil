package br.com.ottimizza.dashboard.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang3.StringUtils;

public class StringUtil {
	
	public static String formatCpfCnpj(String cpfCnpj){
		cpfCnpj = cpfCnpj.replaceAll("\\D", "");
		
		if (cpfCnpj.length() > 11 && cpfCnpj.length() < 14) {
			cpfCnpj = StringUtils.leftPad(cpfCnpj, 14, "0");
			Pattern pattern = Pattern.compile("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})");
			Matcher matcher = pattern.matcher(cpfCnpj);
			if(matcher.find()) return matcher.replaceAll("$1.$2.$3/$4-$5");
		}
		if (cpfCnpj.length() > 5 && cpfCnpj.length() < 11) {
			cpfCnpj = StringUtils.leftPad(cpfCnpj, 11, "0");
			Pattern pattern = Pattern.compile("(\\d{3})(\\d{3})(\\d{3})(\\d{2})");
			Matcher matcher = pattern.matcher(cpfCnpj);
			if(matcher.find()) return matcher.replaceAll("$1.$2.$3-$4");
		}
		return cpfCnpj;
	}
	
	public static List<String> formatCnpj(List<String> cnpjs){
		return cnpjs.stream().map(StringUtil::formatCpfCnpj).collect(Collectors.toList());
	}
	
	public static String cleanCpfCnpj(String cpfCnpj){
		cpfCnpj = cpfCnpj.replaceAll("\\D", "");
		if (cpfCnpj.length() > 11 && cpfCnpj.length() < 14) StringUtils.leftPad(cpfCnpj, 14, "0");
		if (cpfCnpj.length() > 5 && cpfCnpj.length() < 11) StringUtils.leftPad(cpfCnpj, 11, "0");
		
		return cpfCnpj;
	}
}
