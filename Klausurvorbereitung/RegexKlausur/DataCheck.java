package RegexKlausur;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class DataCheck {	
	
	private String regex ="^[Ff][a-z]*e{1}[a-z]*([ ][A-Za-z][a-z]*)*$";
	
	public boolean checkWord(String word) {
		
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(word);
		
		if(matcher.matches()) {
			return true;
		}else {
			return false;
		}
	}
}
