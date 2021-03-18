package edu.neu.cloudsimper;

import java.io.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class Simulation {

	private static final int ZONE_NUM = 13;
	private static final int DC_NUM = 5;
	private static final int TASK_SIZE = 200;
	private static final int TASK_TIME = 1;
	private static final double[] ZONE_SCALES = new double[] { 1.2, 1.5, 0.75, 0.1, 1.0, 1.2, 2.7, 1.125, 0.5, 1.5, 4.5,
			7.5, 1.0 };
	private static final int[] ZONE_TIMES = new int[] { -7, -8, -6, 2, 0, 2, 1, 7, 4, 6, 7, 9, 10 };
	private static final int[] DC_SIZE = new int[] { 100000, 110000, 90000, 120000, 105000 };
	private static final int[] DC_TICK_NUM = new int[DC_NUM];
	private static final int TICK = TASK_TIME * 60 * 10;

	private static final Map<Integer, ArrayList<Integer>> ZONE_DC = new HashMap<Integer, ArrayList<Integer>>();

	private static long clock = 0;
	private static int[] zoneTaskNum = new int[] { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0 };
	private static int[] dcTaskQueue = new int[] { 0, 0, 0, 0, 0 };
	private static List<Double>[] usages = new List[DC_NUM];

	static {
		for (int i = 0; i < DC_NUM; i++) {
			DC_TICK_NUM[i] = DC_SIZE[i] / (TASK_SIZE * TASK_TIME) * TICK;
			usages[i] = new ArrayList<Double>();
		}
		ArrayList<Integer> list = new ArrayList<>();
		list.add(4);
		ZONE_DC.put(1, list);
		ZONE_DC.put(2, list);
		ZONE_DC.put(3, list);

		list = new ArrayList<>();
		list.add(5);
		ZONE_DC.put(4, list);

		list = new ArrayList<>();
		list.add(1);
		list.add(5);
		ZONE_DC.put(5, list);
		ZONE_DC.put(6, list);

		list = new ArrayList<>();
		list.add(1);
		ZONE_DC.put(7, list);
		ZONE_DC.put(9, list);

		list = new ArrayList<>();
		list.add(3);
		list.add(5);
		ZONE_DC.put(8, list);

		list = new ArrayList<>();
		list.add(2);
		ZONE_DC.put(10, list);
		ZONE_DC.put(11, list);

		list = new ArrayList<>();
		list.add(2);
		list.add(3);
		ZONE_DC.put(12, list);
		ZONE_DC.put(13, list);


	}

	public static int genTaskNumPerClock(int i) {

		int[] standardTraceNum = getStandardTraceNum();

		long hour = clock / 3600;
		int place = (int) ((hour + ZONE_TIMES[i] + 720) % 720);

		//long offset = (clock % 3600) / TICK;

		int num = (int)(standardTraceNum[place] * ZONE_SCALES[i] / (3600 /TICK));

		Random rand = new Random();
		double ratio = rand.nextDouble() * 0.1 + 0.95;
		//System.out.println(ratio);
		num = (int)(num * ratio);
		return num;
	}

	public static void genTask() {
		for (int i = 0; i < ZONE_NUM; i++) {
			zoneTaskNum[i] = genTaskNumPerClock(i);
		}
	}

	public static void dispatchTask() {

		//max_power
//		int totalTaskNum = 0;
//		for (int i = 0; i < ZONE_NUM; i++) {
//			totalTaskNum += zoneTaskNum[i];
//		}
//		if (clock == 0) { // the first time rr
//			int result = (int) (totalTaskNum / DC_NUM);
//			for (int i = 0; i < DC_NUM; i++) {
//				dcTaskQueue[i] += result;
//			}
//		} else { // dispatch from large to small according to the usage last tick
//			double[] lastUsage = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
//			for (int i = 0; i < DC_NUM; i++) {
//				lastUsage[i] = usages[i].get(usages[i].size()-1);
//			}
//			int[] Index = Arraysort(lastUsage);
//
//			for (int i = 0; i < DC_NUM; i++) {
//				if (totalTaskNum != 0) {
//					int availableTaskNum = DC_TICK_NUM[Index[i]] - dcTaskQueue[Index[i]];
//					if (totalTaskNum > availableTaskNum) {
//						totalTaskNum -= availableTaskNum;
//						dcTaskQueue[Index[i]] = DC_TICK_NUM[Index[i]];
//					} else {
//						dcTaskQueue[Index[i]] += totalTaskNum;
//						totalTaskNum = 0;
//					}
//				} else {
//					break;
//				}
//			}
//			if (totalTaskNum != 0) {
//				int result = (int) (totalTaskNum / DC_NUM);
//				for (int i = 0; i < DC_NUM; i++) {
//					dcTaskQueue[i] += result;
//				}
//			}
//		}


		//min_time
		int totalTaskNum = 0;
		for (int i = 0; i < ZONE_NUM; i++) {
			totalTaskNum += zoneTaskNum[i];
		}
		if (clock == 0){
			int result = (int) (totalTaskNum / DC_NUM);
			for (int i = 0; i < DC_NUM; i++) {
				dcTaskQueue[i] += result;
			}
		} else {
			double[] lastUsage = new double[]{0.0, 0.0, 0.0, 0.0, 0.0};
			for (int i = 0; i < DC_NUM; i++) {
				lastUsage[i] = usages[i].get(usages[i].size()-1);
			}
			int[] Index = Arraysort(lastUsage);
			for (int i = (DC_NUM-1); i >= 0; i--) {
				if (totalTaskNum != 0) {
					int availableTaskNum = DC_TICK_NUM[Index[i]] - dcTaskQueue[Index[i]];
					availableTaskNum = (int)(availableTaskNum * (new Random().nextDouble() * 0.3 + 0.7));
					if (totalTaskNum > availableTaskNum) {
						totalTaskNum -= availableTaskNum;
						dcTaskQueue[Index[i]] += availableTaskNum;
					} else {
						dcTaskQueue[Index[i]] += totalTaskNum;
						totalTaskNum = 0;
					}
				} else {
					break;
				}
			}
			if (totalTaskNum != 0) {
				int result = (int) (totalTaskNum / DC_NUM);
				for (int i = 0; i < DC_NUM; i++) {
					dcTaskQueue[i] += result;
				}
			}
		}




		//nearest
//		for (int i = 0; i < ZONE_NUM; i++) {
//			int dcNum = ZONE_DC.get(i + 1).size();
//			int taskNum = (int)(zoneTaskNum[i] / dcNum);
//			for (int j = 0; j < dcNum; j++) {
//				dcTaskQueue[ZONE_DC.get(i + 1).get(j) -1] += taskNum;
//			}
//		}

		//random
//		int totalTaskNum = 0;
//		for (int i = 0; i < ZONE_NUM; i++) {
//			totalTaskNum += zoneTaskNum[i];
//		}
//		double[] ratio = new double[] {0, 0, 0, 0, 0};
//		Random random = new Random();
//		DecimalFormat df = new DecimalFormat("#.000");
//		ratio[0] = Double.parseDouble(df.format(random.nextDouble()));
//		ratio[1] = Double.parseDouble(df.format(random.nextDouble() * (1 - ratio[0])));
//		ratio[2] = Double.parseDouble(df.format(random.nextDouble() * (1 - ratio[0] - ratio[1])));
//		ratio[3] = Double.parseDouble(df.format(random.nextDouble() * (1 - ratio[0] - ratio[1] - ratio[2])));
//		ratio[4] = Double.parseDouble(df.format(1 - ratio[0] - ratio[1] - ratio[2] - ratio[3]));
//
//		for (int i = 0; i < DC_NUM; i++) {
//			dcTaskQueue[i] += (int)(totalTaskNum * ratio[i]);
//		}


		//rr
//		int totalTaskNum = 0;
//		for (int i = 0; i < ZONE_NUM; i++) {
//			totalTaskNum += zoneTaskNum[i];
//		}
//		int result = (int) (totalTaskNum / DC_NUM);
//		for (int i = 0; i < DC_NUM; i++) {
//			dcTaskQueue[i] += result;
//		}

	}

	public static void execution() {
		DecimalFormat df = new DecimalFormat("#.000");
		for (int i = 0; i < DC_NUM; i++) {
			int taskNum = dcTaskQueue[i];
			if (taskNum >= DC_TICK_NUM[i]) {
				usages[i].add(1.0);
				dcTaskQueue[i] -= DC_TICK_NUM[i];
			} else {
				int requiredSize = taskNum * TASK_SIZE / TASK_TIME;
				double usage = Double.parseDouble(df.format((float) requiredSize / (DC_SIZE[i] * TICK)));
				usages[i].add(usage);
				dcTaskQueue[i] = 0;
			}
		}
	}

	public static void showUsage() {
		for (int i = 0; i < DC_NUM; i++) {
			System.out.print("DC " + i + " : ");
			for (int j = 0; j < usages[i].size(); j++) {
				System.out.print(usages[i].get(j) + " ");
			}
			System.out.println();
		}
	}

	public static void main(String[] args) {
		int stoptime = 3600 * 24 * 7;
		while (clock < stoptime) {
			genTask();
			dispatchTask();
			execution();
			clock += TICK;
		}
		showUsage();
	}


	private static int[] getStandardTraceNum() {
		int[] standardTraceNum = new int[720];
		File traceFile = new File("src/edu/neu/cloudsimper/conf/trace/RequestNumMonth.txt");
		BufferedReader reader = null;
		try {
			reader = new BufferedReader(new FileReader(traceFile));
			int i = 0;
			String line = reader.readLine();
			while (line != null) {
				standardTraceNum[i] = Integer.parseInt(line);
				i++;
				line = reader.readLine();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return standardTraceNum;

	}

	public static int[] Arraysort(double[]arr)
	{
		double temp;
		int index;
		int k=arr.length;
		int[]Index= new int[k];
		for(int i=0;i<k;i++)
		{
			Index[i]=i;
		}

		for(int i=0;i<arr.length;i++)
		{
			for(int j=0;j<arr.length-i-1;j++)
			{
				if(arr[j]<arr[j+1])
				{
					temp = arr[j];
					arr[j] = arr[j+1];
					arr[j+1] = temp;

					index=Index[j];
					Index[j] = Index[j+1];
					Index[j+1] = index;
				}
			}
		}
		return Index;
	}
}
