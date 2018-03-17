package checker;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.corpus.tag.Nature;
import com.hankcs.hanlp.dictionary.py.Pinyin;
import com.hankcs.hanlp.seg.common.Term;

import java.util.*;

class Cizu extends Term{
    boolean ifright;
    Cizu(String word,Nature nature,boolean ifright){
        super(word,nature);
        this.ifright = ifright;
    }
    // 将当前词组转换为汉字列表
    List<Hanzi> toHanziList(){
        List<Hanzi> hanziList = new ArrayList<>(); // 返回的汉字列表
        List<Pinyin> pinyinList = HanLP.convertToPinyinList(this.word);
        for (int i=0;i<pinyinList.size();i++){
            hanziList.add(new Hanzi(this.word.substring(i,i+1),pinyinList.get(i).getPinyinWithoutTone(),ifright));
        }
        return hanziList;
    }

    public String toString(){
        return HanLP.Config.ShowTermNature?this.word + ":" + this.ifright + "/" + this.nature : this.word;
    }

}
/* 一个句子中单个汉字的类
 * @param character 汉字
 * @param pinyin 这个汉字对应的无声调的拼音
 * @param ifright 判断这个汉字在这个句子中是否为错字
 */
class Hanzi{
    String character;
    String pinyin;
    boolean ifright;
    Hanzi(String character,String pinyin,boolean ifright)
    {
        this.character = character;
        this.pinyin = pinyin;
        this.ifright = ifright;
    }
    public String toString(){
        return this.character+":"+this.pinyin+"/"+this.ifright;
    }
}

/*
 * 包含句子以及错误信息的结构体
 */
class Sentence{
    String sentence;
    boolean ifright;
    Map<Integer,String> correction;

    Sentence(String s){
        this.sentence = s;
        correction = new HashMap<>();
    }

    // 添加错字位置及正确的字
    void addCorrection(int i,String character){
        correction.put(i,character);
    }

    // 从相应位置找到对应的字
    String getLocCorrection(int i){
        try{
            return correction.get(i);
        }
        catch (Exception e){
            return "";
        }
    }

    // 返回所有错字位置
    Set<Integer> getAllLoc(){
        return correction.keySet();
    }

}

public class Component{
    public static void main(String[] args) {

    }
}