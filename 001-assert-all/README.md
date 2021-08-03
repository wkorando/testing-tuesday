# AssertAll 

[Code](https://github.com/wkorando/testing-tuesday/tree/main/001-assert-all) - [Video](https://youtu.be/C__fAKv0km0) - [Script](https://github.com/wkorando/testing-tuesday/blob/main/001-assert-all/script.srt)

Have a test with multiple assertions and frustrated you have to execute it multiple times to resolve all the assertion errors?

Like in this example where all three assertions would fail: 

```java
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
```

But only the first assertion failure is reported?

```
Failures (1):
  JUnit Jupiter:MultipleAsserts:multipleAsserts()
    MethodSource [className = 'MultipleAsserts', 
    methodName = 'multipleAsserts', methodParameterTypes = '']
    => org.opentest4j.AssertionFailedError: expected: 
    <Tony> but was: <Toni>
```

In JUnit 5 `assertAll` was added, allow for multiple assertions to be wrapped:


```java
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

```

And returning all the wrapped assert failures in a single test execution:

```
Failures (1):
  JUnit Jupiter:AssertAll:withAssertAll()
    MethodSource [className = 'AssertAll', 
    methodName = 'withAssertAll', methodParameterTypes = '']
    => org.opentest4j.MultipleFailuresError: 
    Multiple Failures (3 failures)
	org.opentest4j.AssertionFailedError: 
	expected: <Tony> but was: <Toni>
	org.opentest4j.AssertionFailedError: 
	expected: <Stark> but was: <Stank>
	org.opentest4j.AssertionFailedError: 
	expected: <Ironman> but was: <Irony man>
```

Happy Testing!