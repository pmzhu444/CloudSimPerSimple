package edu.neu.cloudsimper.request;

import edu.neu.cloudsimper.CloudSimPer;

import java.util.List;
import java.util.Random;

public class RequestDispatcherMinTime extends RequestDispatcherAbstract implements RequestDispatcher{
    @Override
    public void dispatch(List<RequestCore> requests) {
        if (CloudSimPer.getClock() == 0) {
            int length = datacenters.size();
            int index = 0;
            while (!requests.isEmpty()) {
                int last = requests.size() - 1;
                if (datacenters.get(index++ % length).
                        accept(requests.get(last))) {
                    requests.remove(last);
                }
            }
        } else {
//            int[] dcMaxNum = new int[this.datacenters.size()];
//            for (int i = 0; i < this.datacenters.size(); i++) {
//                dcMaxNum[i] = this.datacenters.get(i).getCapacity() * 600 / 1000;
//            }
            double[] lastUsage = new double[this.datacenters.size()];
            for (int i = 0; i < lastUsage.length; i++) {
                lastUsage[i] = this.datacenters.get(i).getDcUsage();
            }
            int[] Index = Arraysort(lastUsage);

//            int[] dcRequestNum = new int[this.datacenters.size()];
//            for (int i = 0; i < dcRequestNum.length; i++) {
//                dcRequestNum[i] = 0;
//            }

            int totalRequestNum = requests.size();
            for (int i = this.datacenters.size() - 1; i >= 0; i--) {
                if (totalRequestNum != 0) {
                    //int availableRequestNum = dcMaxNum[Index[i]] - this.datacenters.get(Index[i]).getQueueSize();
                    int availableRequestNum = this.datacenters.get(Index[i]).getCapacity() * 600 / 10000 - this.datacenters.get(Index[i]).getQueueSize()
                            - this.datacenters.get(Index[i]).getNextRequestNum();
                    if (availableRequestNum > 0) {
                        availableRequestNum = (int)(availableRequestNum * (new Random().nextDouble() * 0.3 + 0.7));
                        if (totalRequestNum > availableRequestNum) {
                            totalRequestNum -= availableRequestNum;
                            this.datacenters.get(Index[i]).setNextRequestNum(this.datacenters.get(Index[i]).getNextRequestNum() + availableRequestNum);
                        } else {
                            this.datacenters.get(Index[i]).setNextRequestNum(this.datacenters.get(Index[i]).getNextRequestNum() + totalRequestNum);
                            totalRequestNum =0;
                        }
                    }
                } else {
                    break;
                }
            }
            if (totalRequestNum != 0) {
                int result = (int) Math.ceil((double)totalRequestNum / this.datacenters.size());
                for (int i = 0; i < this.datacenters.size(); i++) {
                    this.datacenters.get(i).setNextRequestNum(this.datacenters.get(i).getNextRequestNum() + result);
                }
            }

            for (int i = 0; i < this.datacenters.size(); i++) {
                for (int j = 0; j < this.datacenters.get(i).getNextRequestNum(); j++) {
                    if (!requests.isEmpty()) {
                        int last = requests.size() -1;
                        this.datacenters.get(i).accept(requests.get(last));
                        requests.remove(last);
                    } else {
                        break;
                    }

                }
            }

        }
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
