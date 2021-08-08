```java
public class FormValidator {

	public record Form(String fName, String lName, String startDate, 
	String endDate, int selectionChoice) {
	}
	
	public List<String> validateForm(Form form) {
		List<String> messages = new ArrayList<>();
		LocalDate localStartDate = null;
		LocalDate localEndDate = null;
		
		if(form.fName.isBlank()) {
			messages.add(
			"First name is required!");
		} else if(!StringUtils.isAlpha(form.fName)) {
			messages.add(
			"First name needs to be alpha characters only!");
		}
		
		if(form.lName.isBlank()) {
			messages.add(
			"Last name is required!");
		} else if(!StringUtils.isAlpha(form.lName)) {
			messages.add(
			"Last name needs to be alpha characters only!");
		}		
		
		if(!isDateValid(form.startDate)) {
			messages.add(
			"Start Date: " + form.startDate + " must be in YYYY-MM-DD format!");
		} else {
			localStartDate = LocalDate.parse(form.startDate);
		}
		
		if(!isDateValid(form.endDate)) {
			messages.add(
			"End Date: " + form.endDate + " must be in YYYY-MM-DD format!");
		} else {
			localEndDate = LocalDate.parse(form.endDate);
		}
		
		if(localStartDate != null && localEndDate != null) {
			if(localStartDate.isAfter(localEndDate)) {
				messages.add(
				"Start date must be before end date!");
			}
		}
		
		if(!(form.selectionChoice > 0)) {
			messages.add(
			"Selection choice: " + form.selectionChoice + " is not valid!");
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
}
```