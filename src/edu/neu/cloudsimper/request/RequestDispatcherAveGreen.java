package edu.neu.cloudsimper.request;

import edu.neu.cloudsimper.CloudSimPer;

import java.util.List;

public class RequestDispatcherAveGreen extends RequestDispatcherAbstract implements RequestDispatcher{
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
            double totalReSupply = 0;

            for (int i = 0; i < this.datacenters.size(); i++) {
                if (this.datacenters.get(i).getName().equals("DC_1")) {
                    totalReSupply += (this.datacenters.get(i).getNextReSupply() - 7200) / 7200 * 384;
                }
                if (this.datacenters.get(i).getName().equals("DC_2")) {
                    totalReSupply += (this.datacenters.get(i).getNextReSupply() - 14400) / 14400 * 384 * 2;
                }
                if (this.datacenters.get(i).getName().equals("DC_3")) {
                    totalReSupply += (this.datacenters.get(i).getNextReSupply() - 10800) / 10800 * 384 * 1.5;
                }
            }

            int totalReuqestNum = requests.size();

            for (int i = 0; i < this.datacenters.size(); i++) {
                double reSupply = 0;
                if (this.datacenters.get(i).getName().equals("DC_1")) {
                    reSupply = (this.datacenters.get(i).getNextReSupply() - 7200) / 7200 * 384;
                }
                if (this.datacenters.get(i).getName().equals("DC_2")) {
                    reSupply = (this.datacenters.get(i).getNextReSupply() - 14400) / 14400 * 384 * 2;
                }
                if (this.datacenters.get(i).getName().equals("DC_3")) {
                    reSupply = (this.datacenters.get(i).getNextReSupply() - 10800) / 10800 * 384 * 1.5;
                }
                int num = (int)((reSupply / totalReSupply) * totalReuqestNum);
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
