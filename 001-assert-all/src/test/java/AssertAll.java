import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

public class AssertAll {

	@Test
	public void withAssertAll() {
		record Superhero(String fName, String lName, String alias) {
		};

		Superhero person = new Superhero("Toni", "Stank", "Irony man");

		assertAll(
			() -> assertEquals("Tony", person.fName),//Will fail
			() -> assertEquals("Stark", person.lName), //Will fail
			() -> assertEquals("Ironman", person.alias) //Will fail
		);
	}
}
