package edu.neu.cloudsimper.request;

public class RequestRandomizationSimple implements RequestRandomization {

	@Override
	public int randomized(int value) {
		double result = (Math.random() + 0.5) * value;
		return (int) Math.ceil(result);
	}
}
