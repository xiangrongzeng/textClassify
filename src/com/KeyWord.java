package com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

/**
 * Created by sunder on 2015/10/3.
 * 选取关键词
 */
public class KeyWord {

    /**
     * 从训练集中的词中挑选出关键词
     * @param idf 每个词的idf值，根据idf的大小来选择关键词
     * @param number 设定关键词的个数
     * @param filename 关键词写入的文件
     * @return 关键词列表
     * @throws IOException
     */
    public static ArrayList<String> select(Map<String, Double> idf, int number, String filename) throws IOException {
        // 排序
        List<Map.Entry<String, Double>> sortedIdf = new ArrayList<>(idf.entrySet());
        Collections.sort(sortedIdf, new Comparator<Map.Entry<String, Double>>() {
            public int compare(Map.Entry<String, Double> o1,
                               Map.Entry<String, Double> o2) {
                return (o2.getValue().compareTo(o1.getValue()));
            }
        });

        // 选择前number个关键词并写入文件
        File f = new File(filename);
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        ArrayList<String> keyWords = new ArrayList<>();
        for (int i = 0; i < sortedIdf.size() && i < number; i++) {
            String keyWord = sortedIdf.get(i).getKey();
            keyWords.add(keyWord);
            bw.write(keyWord);
            bw.newLine();
        }
        bw.close();
        return keyWords;
    }
}
