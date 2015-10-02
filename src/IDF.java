import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
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

            File f = new File(inFolder + filename);
            BufferedReader br = new BufferedReader(new FileReader(f));
            String line;
            while((line = br.readLine()) != null) {
                String term = line.trim();

                int termDocNumber;
                if(statistics.containsKey(term)){
                    termDocNumber = statistics.get(term);
                }else{
                    termDocNumber = 0;
                }

                statistics.put(term, termDocNumber + 1);
            }
        }
    }

    /**
     * 根据统计结果计算idf值
     * @return idf
     */
    public static Map<String, Double> calcIdf(){
        Map<String, Double> idf = new HashMap<>();
        for(Map.Entry<String, Integer> entry : statistics.entrySet()){
            String term = entry.getKey();
            int termDocNumber = entry.getValue();

            double termIdf = Math.log(1.0*totalDocNumber/termDocNumber);
            idf.put(term, termIdf);
        }

        return idf;
    }
}
