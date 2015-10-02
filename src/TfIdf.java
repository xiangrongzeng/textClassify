import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.security.KeyException;
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

    /**
     * 统计一个文件夹（类别）下每个文件各个词出现的次数。
     * @param inFolder 需要统计的文件夹路径
     * @throws IOException
     */
    public static void doStatistics(String inFolder) throws IOException {
        String[] seg = inFolder.split("\\\\");
        String className = seg[seg.length -1];

        inFolder = Tools.ensurePath(inFolder);
        File folder = new File(inFolder);
        String[] fileList = folder.list();
        for(String filename : fileList){

            File f = new File(inFolder + filename);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while ((line = br.readLine()) != null){
                String term = line.trim();

                Map<String, Map<String, Integer>> thisTermStatistics;
                try{
                    thisTermStatistics = termStatistics.get(term);
                }catch (Exception e){
                    thisTermStatistics = new HashMap<>();
                }

                Map<String, Integer> thisClassStatistics;
                try{
                    thisClassStatistics = thisTermStatistics.get(className);
                }catch (Exception e){
                    thisClassStatistics = new HashMap<>();
                }

                int thisFileTermCount;
                try{
                    thisFileTermCount = thisClassStatistics.get(filename);
                }catch (Exception e){
                    thisFileTermCount = 0;
                }

                thisClassStatistics.put(filename, thisFileTermCount+1);
                thisTermStatistics.put(className, thisClassStatistics);
                termStatistics.put(term, thisTermStatistics);
            }
        }

    }
}
