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