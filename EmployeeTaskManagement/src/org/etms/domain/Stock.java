package org.etms.domain;


public class Stock {
	private String firstName;
	private String lastName;
	private String stock;


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getStock() {
		return stock;
	}

	public void setStock(String stock) {
		this.stock = stock;
	}


	public String toString() {
		String str = "\tFirst Name: "
				+ firstName + "\n\tLast Name: " + lastName +  "\n\tStock: " + stock  ;
		return str;
	}
}
