```java
@Test
public void withAssertAll() {
	record Superhero(String fName, String lName, String alias) {};

	Superhero superhero 
	= new Superhero("Toni", "Stank", "Irony man");

	assertAll(
      () -> assertEquals("Tony", person.fName),//Will fail
      () -> assertEquals("Stark", person.lName), //Will fail
      () -> assertEquals("Ironman", person.alias) //Will fail
	);
}
```

# Result: 

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