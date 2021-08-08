# Parameterized Tests 

[Code](https://github.com/wkorando/testing-tuesday/tree/main/002-parameterized-tests) - [Video]() - [Script](https://github.com/wkorando/testing-tuesday/blob/main/002-parameterized-tests/script.srt)

When writing automated tests, there is frequently the scenario when the method being tested can have many branches. A common case would be when validating user submitted form, like in this example below:

```java
public List<String> validateForm(Form form) {
	List<String> messages = new ArrayList<>();
	LocalDate localStartDate = null;
	LocalDate localEndDate = null;
	
	if(form.fName()!= null && form.fName().isBlank()) {
		messages.add("First name is required!");
	} else if(!StringUtils.isAlpha(form.fName())) {
		messages.add("First name needs to be alpha characters only!");
	}
	
	if(form.lName()!= null && form.lName().isBlank()) {
		messages.add("Last name is required!");
	} else if(!StringUtils.isAlpha(form.lName())) {
		messages.add("Last name needs to be alpha characters only!");
	}		
	
	if(!isDateValid(form.startDate())) {
		messages.add("Start Date: " + form.startDate() + " must be in YYYY-MM-DD format!");
	} else {
		localStartDate = LocalDate.parse(form.startDate());
	}
	
	if(!isDateValid(form.endDate())) {
		messages.add("End Date: " + form.endDate() + " must be in YYYY-MM-DD format!");
	} else {
		localEndDate = LocalDate.parse(form.endDate());
	}
	
	if(localStartDate != null && localEndDate != null) {
		if(localStartDate.isAfter(localEndDate)) {
			messages.add("Start date must be before end date!");
		}
	}
	
	if(!(form.selectionChoice() > 0)) {
		messages.add("Selection choice: " + form.selectionChoice() + " is not valid!");
	}
	
	return messages;
}

private boolean isDateValid(String date) {
	try {
		LocalDate.parse(date);
		return true;
	} catch(Exception e) {
		return false;
	}
}
```

In the above there is a dozen or so different paths through the code. 

The initial thought to when approaching writing tests for this block of code would be to re-use (i.e. copy and paste) a common format like here: 

```java
FormValidator formValidator = new FormValidator();

@Test
public void hasBlankFirstName() {
	Form form = new Form("", "", "", "", 0);	
	
	List<String> errorMessages = formValidator.validateForm(form);
	
	assertTrue(errorMessages.contains(
	"First name is required!"));
}

@Test
public void hasNonAlphaFirstName() {
	Form form = new Form("1a", "", "", "", 0);	
	
	List<String> errorMessages = formValidator.validateForm(form);
	
	assertTrue(errorMessages.contains(
	"First name needs to be alpha characters only!"));
}


@Test
public void hasBlankLastName() {
	Form form = new Form("", "", "", "", 0);	
	
	List<String> errorMessages = formValidator.validateForm(form);
	
	assertTrue(errorMessages.contains(
	"Last name is required!"));
}

@Test
public void hasNonAlphaLastName() {
	Form form = new Form("", "1a", "", "", 0);	
	
	List<String> errorMessages = formValidator.validateForm(form);
	
	assertTrue(errorMessages.contains(
	"Last name needs to be alpha characters only!"));
}

@Test
public void hasInvalidStartDate() {
	Form form = new Form("", "", "08/06/2021", "", 0);	
	
	List<String> errorMessages = formValidator.validateForm(form);
	
	assertTrue(errorMessages.contains(
	"Start Date: 08/06/2021 must be in YYYY-MM-DD format!"));
}

@Test
public void hasInvalidEndDate() {
	Form form = new Form("", "", "", "08/06/2021", 0);	
	
	List<String> errorMessages = formValidator.validateForm(form);
	
	assertTrue(errorMessages.contains(
	"End Date: 08/06/2021 must be in YYYY-MM-DD format!"));
}

@Test
public void hasEndDateBeforeStartDate() {
	Form form = new Form("", "", "2021-08-11", "2021-08-06", 0);	
	
	List<String> errorMessages = formValidator.validateForm(form);
	
	assertTrue(errorMessages.contains(
	"Start date must be before end date!"));
}
```

This approach can be difficult to maintain, as many tests might need to be updated with every change to the block of code being tested. 

Instead consider writing a [Parameterized Test](https://junit.org/junit5/docs/current/user-guide/#writing-tests-parameterized-tests):

```java
FormValidator formValidator = new FormValidator();
@ParameterizedTest
@MethodSource
public void formValidation(FormValidationTestCase testCase) {
	List<String> errorMessages = formValidator.validateForm(testCase.form());

	assertTrue(errorMessages.contains(testCase.errorMessage()));
}

static Stream<FormValidationTestCase> formValidation() {
	return Stream.of(
			new FormValidationTestCase(
					"hasBlankFirstName",
					new Form("", "", "", "", 0),
					"First name is required!"),	                                              
			new FormValidationTestCase(	
					"hasNonAlphaFirstName",
					new Form("1a", "", "", "", 0),
					"First name needs to be alpha characters only!"),                                                                               
			new FormValidationTestCase(	
					"hasBlankLastName",
					new Form("", "", "", "", 0), 
					"Last name is required!"),                                                 
			new FormValidationTestCase(	
					"hasNonAlphaLastName", 
					new Form("", "1a", "", "", 0),
					"Last name needs to be alpha characters only!"),                      
			new FormValidationTestCase(	
					"hasInvalidStartDate()",
					new Form("", "", "08/06/2021", "", 0), 
					"Start Date: 08/06/2021 must be in YYYY-MM-DD format!"),   
			new FormValidationTestCase(
					"hasInvalidEndDate",
					new Form("", "", "", "08/06/2021", 0),
					"End Date: 08/06/2021 must be in YYYY-MM-DD format!"),          
			new FormValidationTestCase(	
					"hasEndDateBeforeStartDate",
					new Form("", "", "2021-08-11", "2021-08-06", 0),
					"Start date must be before end date!"));      
}

record FormValidationTestCase(String testName, Form form, 
String errorMessage) {
	@Override
	public String toString() {
		return testName;
	}
}
```

With a parameterized test. You write a single test case, and then define a source that will pass data into the test case for executing different test scenarios. 

Parameterized tests are typically easier to maintain and update when there are changes to the code being tested, as most changes need to be made to a single area. They are also easier to expand, as the cost to defining an additional scenario is low. 

There are several other different source types that can be used with parameterized tests:

* CsvFileSource
* CsvSource
* EmptySource
* EnumSource
* MethodSource
* NullSource
* ValueSource

Happy Testing!