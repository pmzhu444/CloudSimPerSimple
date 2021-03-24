package edu.neu.cloudsimper;

import org.apache.commons.math3.distribution.WeibullDistribution;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

public class test {


    public static void main(String[] args) {
        double a = 5.0, b = 3, c = 2;
        double sum = 1 / a + 1 / b + 1 / c;
        System.out.println(sum);
        System.out.println((1 / a) / sum);
    }
}
