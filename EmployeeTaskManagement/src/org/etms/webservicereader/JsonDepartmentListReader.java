package org.etms.webservicereader;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;

import javax.json.*;
import javax.json.stream.*;
import javax.json.stream.JsonParser.*;

import org.etms.domain.Department;

public class JsonDepartmentListReader {
	static final String servletUrlStr = "http://localhost:8080/EmployeeTaskManagement/jsondepartments";

	private static void addPropertyToDepartment(Department dept,
			String property, String value) {

		if (property.equalsIgnoreCase("departmentId")) {

			dept.setDepartmentId(value);
			return;
		}

		if (property.equalsIgnoreCase("departmentName")) {
			dept.setDepartmentName(value);
			return;
		}

		System.out.println("Error: Unexpected property");
	}

	private static Department parseDepartment(JsonParser parser) {
		JsonParser.Event event = Event.VALUE_NULL;
		String curName = null, curValue;
		Department dept = null;

		/* Assume START_OBJECT event already parsed */

		dept = new Department();
		while (parser.hasNext()) {
			event = parser.next();
			switch (event) {
			case START_OBJECT:
				System.out.println("Error: Unexpected token type: "
						+ event.toString());
				break;
			case END_OBJECT:
				return dept; /* finished parsing the department */
			case VALUE_FALSE:
			case VALUE_NULL:
			case VALUE_TRUE:
				System.out.println("Error: Unexpected token type: "
						+ event.toString());
				break;
			case KEY_NAME:
				curName = parser.getString();
				break;
			case VALUE_STRING:
			case VALUE_NUMBER:
				curValue = parser.getString();
				addPropertyToDepartment(dept, curName, curValue);
				break;
			default:
				System.out.println("Error: Unexpected token type: "
						+ event.toString());
				break;
			}
		}

		return dept;
	}

	private static ArrayList<Department> parseDepartmentList(JsonParser parser) {
		JsonParser.Event event = Event.VALUE_NULL;
		Department dept;
		String name = null;
		ArrayList<Department> deptList = new ArrayList<Department>();

		if (parser.hasNext())
			event = parser.next();
		if (event != Event.START_OBJECT)
			return null; /* Start of DepartmentList object */
		if (parser.hasNext())
			event = parser.next();
		if (event != Event.KEY_NAME)
			return null; /* Name is Department list, Value should be an array */
		name = parser.getString();
		if (name == null || !name.equalsIgnoreCase("deptlist"))
			return null;
		if (parser.hasNext())
			event = parser.next();
		if (event != Event.START_ARRAY)
			return null;

		while (parser.hasNext()) {
			event = parser.next();
			if (event != Event.START_OBJECT)
				break; /* If not start of book object, exit loop */
			dept = parseDepartment(parser);
			deptList.add(dept);
		}

		if (event != Event.END_ARRAY)
			return null; /* End of Book array */
		if (parser.hasNext())
			event = parser.next(); /* End of BookList Object */
		if (event != Event.END_OBJECT)
			return null;

		return deptList;
	}

	private static void printDeptList(ArrayList<Department> deptList) {

		System.out.println("The Department List (from JSON web service) is: \n");
		for (Department curDept : deptList) {
			System.out.println(curDept + "\n");
		}
	}

	public ArrayList<Department> getDeptListFromWebService() {
		ArrayList<Department> deptList = new ArrayList<Department>();

		try {
			URL jsonBookListUrl = new URL(servletUrlStr);
			InputStream urlInStrm = jsonBookListUrl.openConnection()
					.getInputStream();

			JsonParserFactory factory = Json.createParserFactory(null);
			JsonParser parser = factory.createParser(urlInStrm);

			deptList = parseDepartmentList(parser);
			return deptList;
			// printDeptList(deptList);
		} catch (Exception ex) {
			System.out.println("Problem accessing JSON Servlet: " + ex);
		}
		return deptList;
	}

}
