package org.etms.exceptions;

public class EmployeeDbFailure extends Exception {
	private static final long serialVersionUID = 1L;
	public static final int STMT_FAILED = 0;
	public static final int BAD_EMP_ID = 1;
	public static final int RETRY = 2;
	public static final int RETRY_LIMIT_EXCEEDED = 3;
	public static final int UNIQUE_EMAIL = 4;
	public static final int NAMMING_EX = 5;
			
	private int failureReason;

	public EmployeeDbFailure(int failureReason) {
		this.failureReason = failureReason;
	}

	public int getFailureReason() {
		return failureReason;
	}

	public String getReasonStr() {
		switch (failureReason) {
		case STMT_FAILED:
			return "Failure Executing process. Try again!";
		case UNIQUE_EMAIL:
			return "Email Id already exist.";
		case BAD_EMP_ID:
			return "Invalid employee Id";
		case RETRY:
			return "Operation rolled back, retry";
		case RETRY_LIMIT_EXCEEDED:
			return "Multiple attempts to perform the statement failed";
		case NAMMING_EX:
			return "Namming Exception from database connectivity.";
		default:
			return "Unknown Reason";
		}
	}

}
