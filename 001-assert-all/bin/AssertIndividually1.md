```java
@Test
public void multipleAsserts() {
	record Superhero(String fName, String lName, String alias) {};

	Superhero superhero = 
	new Superhero("Toni", "Stank", "Irony man");

	assertEquals("Tony", person.fName); // Will fail
	assertEquals("Stark", person.lName); // Will fail
	assertEquals("Ironman", person.alias); // Will fail
}
```

# Result:

```
Failures (1):
  JUnit Jupiter:MultipleAsserts:multipleAsserts()
    MethodSource [className = 'MultipleAsserts', 
    methodName = 'multipleAsserts', methodParameterTypes = '']
    => org.opentest4j.AssertionFailedError: expected: 
    <Tony> but was: <Toni>
```