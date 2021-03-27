package edu.neu.cloudsimper.request;

import edu.neu.cloudsimper.CloudSimPer;

import java.util.List;
import java.util.Random;

public class RequestDispatcherMinCost extends RequestDispatcherAbstract implements RequestDispatcher{
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
            double totalCost = 0;
            for (int i = 0; i < this.datacenters.size(); i++) {
                totalCost += 1 / (this.datacenters.get(i).getRePrice() * this.datacenters.get(i).getReDcUsage());

            }

            int totalReuqestNum = requests.size();

            for (int i = 0; i < this.datacenters.size(); i++) {
                int num = (int)(((1 / (this.datacenters.get(i).getRePrice() * this.datacenters.get(i).getReDcUsage())) / totalCost) * totalReuqestNum);
                for (int j = 0; j < num; j++) {
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
}
