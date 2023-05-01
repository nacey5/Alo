package com.hzh.alo;

import java.util.*;

public class LRUAlgorithm {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        // 从控制台输入页面序列和页面框的数量
        System.out.print("请输入页面序列：");
        String input = scanner.nextLine();
        System.out.print("请输入页面框数量：");
        int numFrames = scanner.nextInt();

        // 将页面序列转换为整数数组
        String[] pages = input.split(" ");
        int[] pageReferences = new int[pages.length];
        for (int i = 0; i < pages.length; i++) {
            pageReferences[i] = Integer.parseInt(pages[i]);
        }

        // 执行 LRU 算法
        int pageFaults = 0;
        List<Integer> frames = new ArrayList<>();
        Map<Integer, Integer> frameMap = new HashMap<>();
        for (int i = 0; i < pageReferences.length; i++) {
            int page = pageReferences[i];
            if (!frames.contains(page)) {
                pageFaults++;
                if (frames.size() < numFrames) {
                    frames.add(page);
                } else {
                    int lruPage = frames.get(0);
                    int lruIndex = frameMap.get(lruPage);
                    for (int j = 1; j < frames.size(); j++) {
                        int currentPage = frames.get(j);
                        int currentIndex = frameMap.get(currentPage);
                        if (currentIndex < lruIndex) {
                            lruPage = currentPage;
                            lruIndex = currentIndex;
                        }
                    }
                    frames.remove(Integer.valueOf(lruPage));
                    frames.add(page);
                }
            }
            frameMap.put(page, i);
        }

        // 输出 LRU 算法执行结果
        System.out.println("页面故障数: " + pageFaults);
        System.out.println("页面框内容: " + frames);
    }
}

