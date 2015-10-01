import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by sunder on 2015/10/1.
 * �ִ�
 */
public class Segment {

    /**
     * ���ļ����зִʣ������д��ָ�����ļ�
     * @param inFilename ��Ҫ�ִʵ��ļ�
     * @param outFilename �ִʺ�д����ļ�
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
            outStr += term.getName() + "\n";
        }
        try {
            Tools.writeToFile(outStr, outFilename);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * ���ļ����е������ļ��ֱ���зִʣ��õ��Ľ������ԭ�����ļ����洢��ָ������ļ�����
     * @param inFolderPath ��Ҫ�ִʵ��ļ���
     * @param outFolderPath �洢������ļ���
     */
    public void cutFolder(String inFolderPath, String outFolderPath){
        if(!(inFolderPath.substring(inFolderPath.length()-1).equals("\\"))){
            inFolderPath += "\\";
        }
        if(!(outFolderPath.substring(outFolderPath.length()-1).equals("\\"))){
            outFolderPath += "\\";
        }
        File folder = new File(inFolderPath);
        String[] filenameList = folder.list();
        for(String filename : filenameList){
            cutFile(inFolderPath+filename, outFolderPath+filename);
        }
    }
}
