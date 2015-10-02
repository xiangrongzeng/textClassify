import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by sunder on 2015/10/1.
 * 分词
 */
public class Segment {

    /**
     * 对文件进行分词，将结果写入指定的文件
     * @param inFilename 需要分词的文件
     * @param outFilename 分词后写入的文件
     */
    public void cutFile(String inFilename, String outFilename){
        System.out.println("cut file: " + inFilename);

        String content = "";
        try {
            content = Tools.readWholeFile(inFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
        List<Term> parses = BaseAnalysis.parse(content);

        String outStr = "";
        for(Term term : parses){
            outStr += term.getName() + MyConst.LINE_SEPARATE;
        }
        try {
            Tools.writeToFile(outStr, outFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 对文件夹中的所有文件分别进行分词，得到的结果按照原来的文件名存储在指定输出文件夹中
     * @param inFolderPath 需要分词的文件夹
     * @param outFolderPath 存储结果的文件夹
     */
    public void cutFolder(String inFolderPath, String outFolderPath){
        inFolderPath = Tools.ensurePath(inFolderPath);
        outFolderPath = Tools.ensurePath(outFolderPath);

        File folder = new File(inFolderPath);
        String[] filenameList = folder.list();
        for(String filename : filenameList){
            cutFile(inFolderPath+filename, outFolderPath+filename);
        }
    }
}
