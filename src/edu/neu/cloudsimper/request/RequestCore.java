package edu.neu.cloudsimper.request;

public class RequestCore {

	private String id;
	private long length;
	private int deadline;

	public RequestCore(int length, int deadline, String id) {
		this.length = length;
		this.deadline = deadline;
		this.id = id;
	}

	public long getLength() {
		return length;
	}

	public int getDeadline() {
		return deadline;
	}

}
