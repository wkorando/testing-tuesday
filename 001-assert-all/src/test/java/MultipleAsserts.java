import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class MultipleAsserts {

	@Test
	public void multipleAsserts() {
		record Superhero(String fName, String lName, String alias) {
		}
		;

		Superhero person = new Superhero("Tony", "Stark", "Irony man");

		assertEquals("Tony", person.fName); // Will fail
		assertEquals("Stark", person.lName); // Will fail
		assertEquals("Ironman", person.alias); // Will fail
	}

}