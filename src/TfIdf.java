import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sunder on 2015/10/2.
 * 计算term的tf和idf
 */
public class TfIdf {
    // term, 类，文档， 次数
    private static Map<String, Map<String, Map<String, Integer>>> termStatistics
            = new HashMap<>();
    // 类别，文档数
    private static Map<String, Integer> classDocNumberStatistics = new HashMap<>();
    // term，idf值
    private static Map<String, Double> idf = new HashMap<>();

    /**
     * 统计一个文件夹（类别）下每个文件各个词出现的次数。
     * @param inFolder 需要统计的文件夹路径
     * @throws IOException
     */
    public static void doStatistics(String inFolder) throws IOException {
        inFolder = Tools.ensurePath(inFolder);
        File folder = new File(inFolder);
        String[] fileList = folder.list();

        String[] seg = inFolder.split("\\\\");
        String className = seg[seg.length -1];
        classDocNumberStatistics.put(className, fileList.length);

        for(String filename : fileList){

            File f = new File(inFolder + filename);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null){
                String term = line.trim();

                Map<String, Map<String, Integer>> thisTermStatistics;
                if(termStatistics.containsKey(term)){
                    thisTermStatistics = termStatistics.get(term);
                }else{
                    thisTermStatistics = new HashMap<>();
                }

                Map<String, Integer> thisClassStatistics;
                if(thisTermStatistics.containsKey(className)){
                    thisClassStatistics = thisTermStatistics.get(className);
                }else{
                    thisClassStatistics = new HashMap<>();
                }

                int thisFileTermCount;
                if(thisClassStatistics.containsKey(filename)){
                    thisFileTermCount = thisClassStatistics.get(filename);
                }else{
                    thisFileTermCount = 0;
                }

                thisClassStatistics.put(filename, thisFileTermCount+1);
                thisTermStatistics.put(className, thisClassStatistics);
                termStatistics.put(term, thisTermStatistics);
            }
        }

    }

    public static Map<String, Map<String, Map<String, Integer>>> getTermStatistics(){
        return termStatistics;
    }

    public static Map<String, Double> getIdf(){
        return idf;
    }

    /**
     * 计算每个词的idf值
     */
    public static void calcIdf(){
        int docNumber = 0;
        for(int number : classDocNumberStatistics.values()){
            docNumber += number;
        }

        for(Map.Entry<String, Map<String, Map<String, Integer>>> thisItemEntry : termStatistics.entrySet()){
            String term = thisItemEntry.getKey();
            int termDocNumber = 0;
            for(Map.Entry<String, Map<String, Integer>> thisClassEntry : thisItemEntry.getValue().entrySet()){
                termDocNumber += thisClassEntry.getValue().keySet().size();
            }

            double termIdf = 1.0*docNumber/termDocNumber;
            idf.put(term, termIdf);
        }
    }
}
