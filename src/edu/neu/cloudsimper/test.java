package edu.neu.cloudsimper;

import org.apache.commons.math3.distribution.WeibullDistribution;

import javax.swing.*;
import java.lang.reflect.Array;
import java.util.*;

public class test {
    public static void main(String[] args) {
        int[] a = new int[] {1, 2, 3};
        System.out.println(permute(a));
    }
    public static List<List<Integer>> permute(int[] nums) {
        List<List<Integer>> res = new ArrayList<List<Integer>>();

        List<Integer> output = new ArrayList<Integer>();
        for (int num : nums) {
            output.add(num);
        }

        int n = nums.length;
        backtrack(n, output, res, 0);
        return res;
    }

    public static void backtrack(int n, List<Integer> output, List<List<Integer>> res, int first) {
        // 所有数都填完了
        if (first == n) {
            res.add(output);
        }
        for (int i = first; i < n; i++) {
            if (output.get(first) != output.get(i)) {
                // 动态维护数组
                Collections.swap(output, first, i);
                // 继续递归填下一个数
                backtrack(n, output, res, first + 1);
                // 撤销操作
                Collections.swap(output, first, i);
            }
        }
    }
}
