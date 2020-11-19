package RegexKlausur;



import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class RegexKlausurTest {

	@Test
	void test() {
		
		String[] good = {"fertig", "Ferien haben", "fahre in den Urlaub", "fahren", "fernsehen", "Finsternis", "fies", "fressen"};
		String[] bad = {"in den Urlaub fahren", "Farin Urlaub", "FiEs", "f2es", "frEssen", "fre\nssen", "fre\tssen", "fahren ",
				"fa.hren"};
		
		DataCheck dataCheck = new DataCheck();
		
		for(int i = 0; i<good.length; i++) {
			assert  dataCheck.checkWord(good[i]) == true : "Fehler an Stelle: "+i;
			assertEquals(true, dataCheck.checkWord(good[i]));
			
		}
		
		for(int i = 0; i<bad.length; i++) {
			 assert dataCheck.checkWord(bad[i]) == false : "Fehler an Stelle: "+i;
			 assertEquals(false, dataCheck.checkWord(bad[i]));
		}
		
	}

}
