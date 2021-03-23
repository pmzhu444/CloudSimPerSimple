package edu.neu.cloudsimper.request;

import edu.neu.cloudsimper.CloudSimPer;

import java.util.List;
import java.util.Random;

public class RequestDispatcherMinTime extends RequestDispatcherAbstract implements RequestDispatcher{
    @Override
    public void dispatch(List<RequestCore> requests) {

        int totalCapacity = 0;
        for (int i = 0; i < this.datacenters.size(); i++) {
//            System.out.println(this.datacenters.get(i).getName() + " " + this.datacenters.get(i).getCapacity());
            totalCapacity += this.datacenters.get(i).getCapacity();
        }

        int totalReuqestNum = requests.size();

        for (int i = 0; i < this.datacenters.size(); i++) {
            int num = (int)(((float)this.datacenters.get(i).getCapacity() / totalCapacity) * totalReuqestNum);
//            System.out.println(this.datacenters.get(i).getName() + " " + ((float)this.datacenters.get(i).getCapacity() / totalCapacity) + " " + num);
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
