package edu.neu.cloudsimper;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 10; i++) {
            System.out.println(P_rand(2));
        }
    }
    public static double P_rand(double Lamda){     // 泊松分布
        double x=0,b=1,c=Math.exp(-Lamda),u;
        do {
            u=Math.random();
            b *=u;
            if(b>=c)
                x++;
        }while(b>=c);
        return x;
    }
}
