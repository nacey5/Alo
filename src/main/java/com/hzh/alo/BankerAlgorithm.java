package com.hzh.alo;

import java.util.Scanner;

public class BankerAlgorithm {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        int numProcesses, numResources;
        int[] available, total, allocated;
        int[][] need;

        // 输入进程数和资源数
        System.out.print("请输入进程数: ");
        numProcesses = scanner.nextInt();
        System.out.print("请输入资源数: ");
        numResources = scanner.nextInt();

        // 初始化数组
        available = new int[numResources];
        total = new int[numResources];
        allocated = new int[numResources];
        need = new int[numProcesses][numResources];

        // 输入资源总量
        System.out.println("请输入各资源的总量: ");
        for (int i = 0; i < numResources; i++) {
            total[i] = scanner.nextInt();
        }

        // 输入已分配资源数量
        System.out.println("请输入各进程已分配的资源数量: ");
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                allocated[j] += scanner.nextInt();
            }
        }

        // 计算可用资源数量
        for (int i = 0; i < numResources; i++) {
            available[i] = total[i] - allocated[i];
        }

        // 输入每个进程还需要的资源数量
        System.out.println("请输入每个进程还需要的资源数量: ");
        for (int i = 0; i < numProcesses; i++) {
            for (int j = 0; j < numResources; j++) {
                need[i][j] = scanner.nextInt();
            }
        }

        // 执行银行家算法
        boolean[] finished = new boolean[numProcesses];
        int[] safeSequence = new int[numProcesses];
        int count = 0;
        while (count < numProcesses) {
            boolean found = false;
            for (int i = 0; i < numProcesses; i++) {
                if (!finished[i]) {
                    boolean canFinish = true;
                    for (int j = 0; j < numResources; j++) {
                        if (need[i][j] > available[j]) {
                            canFinish = false;
                            break;
                        }
                    }
                    if (canFinish) {
                        for (int j = 0; j < numResources; j++) {
                            available[j] += allocated[j];
                        }
                        safeSequence[count++] = i;
                        finished[i] = true;
                        found = true;
                    }
                }
            }
            if (!found) {
                break;
            }
        }

        if (count == numProcesses) {
            System.out.println("系统是安全的。安全序列为:");
            for (int i = 0; i < numProcesses; i++) {
                System.out.print("P" + safeSequence[i]);
                if (i != numProcesses - 1) {
                    System.out.print(" -> ");
                }
            }
            System.out.println();
        } else {
            System.out.println("系统是不安全的。");
        }
    }

}

