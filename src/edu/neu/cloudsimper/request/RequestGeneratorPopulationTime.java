package edu.neu.cloudsimper.request;

import edu.neu.cloudsimper.CloudSimPer;

import java.util.Random;

public class RequestGeneratorPopulationTime extends RequestGeneratorFile implements RequestGenerator {

//google
//	@Override
//	public int nextBatchSize(int duration) {
//
//		int hour = CloudSimPer.getClock() / 3600;
//		int place = (int) ((hour + this.region.getTimeShift() + 720) % 720);
//		int num = (int)(traces[place] * this.region.getPopulation() / (3600 / 600));
//		Random rand = new Random();
//		double ratio = rand.nextDouble() * 0.1 + 0.95;
//		num = (int)(num * ratio);
//		return 0;
//	}
	@Override
	public int nextBatchSize(int duration) {
		int num = traces[CloudSimPer.getClock()/600];
//		System.out.println("total:" + num);
		return num;
	}
	@Override
	protected int parseRequestNum(String line) {
		return Integer.parseInt(line);
	}

}