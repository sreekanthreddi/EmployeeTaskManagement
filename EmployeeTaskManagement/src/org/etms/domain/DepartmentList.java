package org.etms.domain;

import java.util.*;

// This class uses the Singleton design pattern -- only
// one DepartmentList object can ever be created
public class DepartmentList {
	private ArrayList<Department> depts = new ArrayList<Department>(10);
	private static DepartmentList deptList = null;

	private DepartmentList() {
		init();
	}

	static public DepartmentList getDepartmentList() {
		if (deptList == null) {
			deptList = new DepartmentList();
		}
		return deptList;
	}

	public ArrayList<Department> getDeptArray() {
		return depts;
	}

	public int numDepts() {
		return depts.size();
	}

	public Department getDepartment(int itemIdx) {
		Department book = depts.get(itemIdx);
		return book;
	}

	/*
	 * public Department getBookDepartment(String prodCode) { int i; Book
	 * tstBook;
	 * 
	 * for (i=0; i<books.size(); i++) { tstBook = books.get(i); if
	 * (tstBook.hasCode(prodCode)) { return tstBook; } }
	 * 
	 * return null; }
	 * 
	 * public void addBook(Book book) { books.add(book); }
	 * 
	 * public void removeBook(int itemIdx) { }
	 */
	
	public void addDepartment(Department book) {  }
	
	
	private void init() {
		// Usually the data would come from a database
		Department newDepartment;

		newDepartment = new Department();
		newDepartment.setDepartmentId("1");
		newDepartment.setDepartmentName("Product Engineering Service");
		depts.add(newDepartment);
		
		newDepartment = new Department();
		newDepartment.setDepartmentId("2");
		newDepartment.setDepartmentName("Embedded");
		depts.add(newDepartment);
		
		newDepartment = new Department();
		newDepartment.setDepartmentId("3");
		newDepartment.setDepartmentName("Software");
		depts.add(newDepartment);
		
		newDepartment = new Department();
		newDepartment.setDepartmentId("4");
		newDepartment.setDepartmentName("Sales and Marketing");
		depts.add(newDepartment);
		
		newDepartment = new Department();
		newDepartment.setDepartmentId("5");
		newDepartment.setDepartmentName("Admin");
		depts.add(newDepartment);
		
		newDepartment = new Department();
		newDepartment.setDepartmentId("6");
		newDepartment.setDepartmentName("Human Resource");
		depts.add(newDepartment);
		
		
	}
}
