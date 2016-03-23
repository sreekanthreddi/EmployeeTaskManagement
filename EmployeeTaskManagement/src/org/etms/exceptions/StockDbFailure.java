package org.etms.exceptions;

public class StockDbFailure extends Exception {
	private static final long serialVersionUID = 1L;
	public static final int STMT_FAILED = 0;
	public static final int BAD_EMP_ID = 1;
	public static final int RETRY = 2;
	public static final int RETRY_LIMIT_EXCEEDED = 3;
	public static final int UNIQUE_EMAIL = 4;
	public static final int NAMMING_EX = 5;
			
	private int failureReason;

	public StockDbFailure(int failureReason) {
		this.failureReason = failureReason;
	}
	public int getFailureReason() {
		return failureReason;
	}

}