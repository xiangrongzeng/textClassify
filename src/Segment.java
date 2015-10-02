import org.ansj.domain.Term;
import org.ansj.splitWord.analysis.BaseAnalysis;
import org.ansj.util.FilterModifWord;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sunder on 2015/10/1.
 * �ִ�
 */
public class Segment {
    ArrayList<String> stopWords = new ArrayList<>();
    public Segment(String stopFilename){
        File f = new File(stopFilename);
        try (BufferedReader br = new BufferedReader(new FileReader(f))) {
            String line;

            while((line = br.readLine()) != null) {
                String word = line.trim();
                stopWords.add(word);
            }
        }catch (IOException e) {
            e.printStackTrace();
        }
    }

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
            if(!(stopWords.contains(term.getName()))
                    && !term.getName().matches("\\pP")
                    && !term.getName().matches("\\pZ")){
                outStr += term.getName() + MyConst.LINE_SEPARATE;
            }
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
        inFolderPath = Tools.ensurePath(inFolderPath);
        outFolderPath = Tools.ensurePath(outFolderPath);

        File folder = new File(inFolderPath);
        String[] filenameList = folder.list();
        for(String filename : filenameList){
            cutFile(inFolderPath+filename, outFolderPath+filename);
        }
    }
}
