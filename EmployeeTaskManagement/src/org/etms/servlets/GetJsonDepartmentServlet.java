package org.etms.servlets;

import java.io.*;

import javax.json.*;
import javax.json.stream.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.etms.domain.Department;
import org.etms.domain.DepartmentList;

/**
 * Point your web browser to: http://localhost:8080/bookservice/jsonbooks The
 * servlet implements a web service, it's output is JSON. Note that you need the
 * javax.json-1.0.3.jar file (found in WEB-INF/lib) for the JsonGenerator
 * classes.
 */

@WebServlet({ "/jsondepartments" })
public class GetJsonDepartmentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	static DepartmentList depts = DepartmentList.getDepartmentList();

	/**
	 * Default constructor.
	 */
	public GetJsonDepartmentServlet() {
		// TODO Auto-generated constructor stub
	}

	private void deptToJson(Department department, JsonGenerator jsonGenerator) {

		jsonGenerator.writeStartObject();

		jsonGenerator.write("departmentId", department.getDepartmentId());
		jsonGenerator.write("departmentName", department.getDepartmentName());

		jsonGenerator.writeEnd();
	}

	private void deptListToJson(DepartmentList depts,
			JsonGenerator jsonGenerator) {
		int i;
		Department dept;

		jsonGenerator.writeStartObject(); /* Start of DepartmentList object */
		jsonGenerator.writeStartArray("deptlist");

		for (i = 0; i < depts.numDepts(); i++) {
			dept = depts.getDepartment(i);
			deptToJson(dept, jsonGenerator);
		}

		jsonGenerator.writeEnd(); /* End of DepartmentList Array */
		jsonGenerator.writeEnd(); /* End of DepartmentList object */
	}

	/**
	 * Write the available Department List to a stream in JSON. This servlet
	 * does not return HTML, it returns JSON. The servlet is using the stream
	 * approach (Use JsonGenerator).
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		JsonGeneratorFactory parserFactory = Json.createGeneratorFactory(null);
		JsonGenerator jsonGenerator;

		response.setContentType("application/json"); // Content type will be
														// JSON
		PrintWriter out = response.getWriter();
		jsonGenerator = parserFactory.createGenerator(out);

		deptListToJson(depts, jsonGenerator);
		jsonGenerator.close();
	}

}
