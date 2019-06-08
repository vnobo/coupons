package com.alex.plugs.similarity;

import com.hankcs.hanlp.HanLP;
import com.hankcs.hanlp.seg.common.Term;

import java.util.List;
import java.util.stream.Collectors;
/**
 * boot-cool-alliance Tokenizer
 * Created by 2019-02-20
 * 中文分词工具类
 * @author Alex bob(https://github.com/vnobo)
 * @byCode https://www.cnblogs.com/qdhxhz/p/9484274.html
 */
public class Tokenizer {
    /**
     * 分词*/
    public static List<Word> segment(String sentence) {

        //1、 采用HanLP中文自然语言处理中标准分词进行分词
        List<Term> termList = HanLP.segment(sentence);

        //2、重新封装到Word对象中（term.word代表分词后的词语，term.nature代表改词的词性）
        return termList.stream().map(term -> new Word(term.word, term.nature.toString())).collect(Collectors.toList());
    }
}
