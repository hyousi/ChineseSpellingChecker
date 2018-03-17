package checker;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.CoreBiGramTableDictionary;
import com.hankcs.hanlp.dictionary.CoreDictionary;
import com.hankcs.hanlp.dictionary.CustomDictionary;
import com.hankcs.hanlp.dictionary.stopword.CoreStopWordDictionary;
import com.hankcs.hanlp.model.perceptron.PerceptronLexicalAnalyzer;
import com.hankcs.hanlp.seg.CRF.CRFSegment;
import com.hankcs.hanlp.seg.Dijkstra.DijkstraSegment;
import com.hankcs.hanlp.seg.Segment;
import com.hankcs.hanlp.seg.common.Term;

import java.io.IOException;
import java.util.*;

public class Detector {
    Segment segment;

    static List<Nature> symbolList = new ArrayList<Nature>(){
        {
            add(Nature.w);
            add(Nature.wb);
            add(Nature.wd);
            add(Nature.wf);
            add(Nature.wh);
            add(Nature.wj);
            add(Nature.wky);
            add(Nature.wkz);
            add(Nature.wm);
            add(Nature.wn);
            add(Nature.wp);
            add(Nature.ws);
            add(Nature.wt);
            add(Nature.ww);
            add(Nature.wyy);
            add(Nature.wyz);
            add(Nature.y);
        }
    };

    public Detector() {
//        segment = new DijkstraSegment();
        segment = new CRFSegment();
        segment.enablePartOfSpeechTagging(true);
    }

    /* 检测分词
     * 连续单个词视为错误
     * @return List<Hanzi>
     */
    List<Cizu> wordDetection(String s){
        List<Term>termList =  segment.seg(s);
        List<Cizu>cizuList = new ArrayList<>();
        for(int i=0;i<termList.size();i++){
            Term term = termList.get(i);
            if(i<termList.size()-1&&term.length()==1&&termList.get(i+1).length()==1){
                cizuList.add(new Cizu(termList.get(i).word,termList.get(i).nature,false));
                cizuList.add(new Cizu(termList.get(i+1).word,termList.get(i+1).nature,false));
                i++;
            }
            else if(term.nature==Nature.nz){
                if(CoreDictionary.contains(term.word)||CustomDictionary.contains(term.word))
                    cizuList.add(new Cizu(termList.get(i).word,termList.get(i).nature,true));
                else{
                    cizuList.add(new Cizu(termList.get(i).word,termList.get(i).nature,false));
                }

            }
            else{
                cizuList.add(new Cizu(termList.get(i).word,termList.get(i).nature,true));
            }
        }
        return cizuList;
    }


    List<Hanzi> characterDetection(List<Cizu> cizuList) {
        List<Hanzi> hanziList = new ArrayList<>();
        for (int i = 0; i < cizuList.size(); i++) {
            if(symbolList.contains(cizuList.get(i).nature)){
                cizuList.get(i).ifright = true;
            }
            hanziList.addAll(cizuList.get(i).toHanziList());
        }
        for (int i=0;i<hanziList.size();i++){
            Hanzi hanzi = hanziList.get(i);
//            System.out.println("current hanzi:"+hanzi.character+"/"+hanzi.ifright);
            if(i<hanziList.size()-1){
                Hanzi nextHanzi = hanziList.get(i+1);
//                System.out.println("next hanzi:"+nextHanzi.character+"/"+nextHanzi.ifright);
                if(hanzi.ifright&&nextHanzi.ifright){
//                    System.out.println("pass\n");
                    continue;
                }
                int bigram = CoreBiGramTableDictionary.getBiFrequency(hanzi.character,nextHanzi.character);
                float p = (float) CoreBiGramTableDictionary.getBiFrequency(hanzi.character,nextHanzi.character)/CoreDictionary.getTermFrequency(hanzi.character);
//                System.out.println("bigram:"+bigram+" P:"+p);
                if(p>0.01){
                    hanzi.ifright = true;
                    nextHanzi.ifright = true;
//                    System.out.println("set "+hanzi.character+" and "+nextHanzi.character+" true");
                }
            }
//            System.out.println();
        }
        return hanziList;
    }

    public List<Integer> detect(String s){
        List<Cizu> cizuList = this.wordDetection(s);
        System.out.println("Segmentation: "+cizuList);
        List<Hanzi> hanziList = this.characterDetection(cizuList);
        List<Integer> detection = new ArrayList<>();
        for (int i=0;i<hanziList.size();i++){
            if(!hanziList.get(i).ifright){
                detection.add(i+1);
            }
        }
        System.out.println("detection:"+detection);
        return detection;
    }


    public static void main(String[] args) throws IOException {
        Detector d = new Detector();
        String s ="";
//        Sentence ss = new Sentence(s);
//        List<Cizu> cizuList = d.wordDetection(new Sentence(s));
//        List<Hanzi> hanziList = d.characterDetection(cizuList);
//        System.out.println(cizuList);
//        System.out.println(hanziList);
        List<Integer>a = d.detect(s);
    }
}

