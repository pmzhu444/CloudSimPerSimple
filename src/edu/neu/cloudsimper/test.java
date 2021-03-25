package edu.neu.cloudsimper;

import org.apache.commons.math3.distribution.WeibullDistribution;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

public class test {


    public static void main(String[] args) {
        System.out.println(isValid("{}"));
    }
    public static boolean isValid(String s) {
        Deque<Character> stack = new LinkedList<>();
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '(' || s.charAt(i) == '{' || s.charAt(i) == '[') {
                stack.push(s.charAt(i));
            } else {
                if (s.charAt(i) != stack.pop()) {
                    return false;
                }
            }
        }
        if (!stack.isEmpty()) {
            return false;
        }
        return true;
    }
}
