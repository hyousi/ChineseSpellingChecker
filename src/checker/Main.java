package com.company;
import checker.Detector;
import checker.errorSentence;
import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.dictionary.CoreBiGramTableDictionary;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.Other.CommonAhoCorasickDoubleArrayTrieSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.hankcs.hanlp.corpus.dictionary.StringDictionary;
import com.hankcs.hanlp.tokenizer.NotionalTokenizer;
import com.hankcs.hanlp.dictionary.CoreDictionary;

import java.io.*;
import java.util.*;

import static com.hankcs.hanlp.corpus.tag.Nature.nz;




public class Main {

    public static List<String> readTxtFileIntoStringArrList(String filePath)
    {
        List<String> list = new ArrayList<String>();
        try
        {
            String encoding = "utf-8";
            File file = new File(filePath);
            if (file.isFile() && file.exists())
            { // 判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);// 考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;

                while ((lineTxt = bufferedReader.readLine()) != null)
                {
                    list.add(lineTxt);
                }
                bufferedReader.close();
                read.close();
            }
            else
            {
                System.out.println("找不到指定的文件");
            }
        }
        catch (Exception e)
        {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }

        return list;
    }

    public static void main(String[] args)  {
        List<String> file = Main.readTxtFileIntoStringArrList("G:/ChineseSpellingChecker/src/checker/15B2.txt");
//        System.out.println(file.size());
        List<String> sentences = new ArrayList<>();
        List<List<Integer>>locations = new ArrayList<>();
        for(int i=0;i<file.size();i++){
//            System.out.println(file.get(i));
            if(i%2==0){
                sentences.add(file.get(i));
            }
            else{
                String[] tmp = file.get(i).split(",");
                List<Integer> loc_i = new ArrayList<>();
                for(String t:tmp){
                    loc_i.add(Integer.parseInt(t));
                }
                locations.add(loc_i);
            }
        }
        // 统计错误个数
        int errorNum = 0;
        for(int i=0;i<locations.size();i++){
            errorNum +=locations.get(i).size();
        }
        System.out.println("total error:"+errorNum);   // errorNum = 1105

        //统计探测器能探测的错误数量
        Detector d = new Detector();
        int correction = 0;
        for(int i=0;i<sentences.size();i++){
            List<Integer> detecion = d.detect(sentences.get(i));
            System.out.println("gold:"+locations.get(i));
            for(int j:locations.get(i)){
                if(detecion.contains(j)||detecion.contains(j-1)||detecion.contains((j+1))){
                    correction++;
                }
            }
        }
        System.out.println("correction:"+correction);
    }
}



