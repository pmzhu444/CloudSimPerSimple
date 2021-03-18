package edu.neu.cloudsimper.request;

public class RequestRandomizationEmpty implements RequestRandomization {

	@Override
	public int randomized(int value) {
		return value;
	}

}
