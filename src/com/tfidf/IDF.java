package com.tfidf;

import com.MyConst;
import com.Tools;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


/**
 * Created by sunder on 2015/10/2.
 * 计算idf
 */
public class IDF {
    // 包含term的文档数量
    private static Map<String, Integer> statistics = new HashMap<>();
    // 总的文档数
    private static int totalDocNumber;

    /**
     * 统计每个词被多少个文档说包含
     * @param inFolder 需要统计的文件夹路径
     * @throws IOException
     */
    public static void doStatistics(String inFolder) throws IOException {
        inFolder = Tools.ensurePath(inFolder);
        File folder = new File(inFolder);
        String[] fileList = folder.list();

        totalDocNumber += fileList.length;

        for(String filename : fileList) {
            Map<String, Integer> contain = new HashMap<>();

            File f = new File(inFolder + filename);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while((line = br.readLine()) != null) {
                String term = line.trim();
                if(!(contain.containsKey(term))){
                    int termDocNumber;
                    if(statistics.containsKey(term)){
                        termDocNumber = statistics.get(term);
                    }else{
                        termDocNumber = 0;
                    }
                    statistics.put(term, termDocNumber + 1);

                    contain.put(term, 1);
                }

            }

        }
    }

    /**
     * 根据统计结果计算idf值
     * @param idfFilename 按照csv标准将idf写入到该文件中
     * @return idf
     * @throws IOException
     */
    public static Map<String, Double> calcIdf(String idfFilename) throws IOException {
        File f = new File(idfFilename);
        BufferedWriter bw = new BufferedWriter(new FileWriter(f));
        Map<String, Double> idf = new HashMap<>();
        for(Map.Entry<String, Integer> entry : statistics.entrySet()){
            String term = entry.getKey();
            int termDocNumber = entry.getValue();

            double termIdf = Math.log(1.0*totalDocNumber/termDocNumber);
            idf.put(term, termIdf);

            bw.write(term + "," + termIdf + MyConst.LINE_SEPARATE);
        }
        bw.close();

        return idf;
    }

    public static Map<String, Double> calcDataBaseIdf(String basePath, String idfFilename) throws IOException {
        basePath = Tools.ensurePath(basePath);
        File termBaseFolder = new File(basePath);
        for(String folderName : termBaseFolder.list()){
                doStatistics(basePath + folderName);
        }

        return calcIdf(idfFilename);
    }
}
