package service.impl;

import java.io.IOException;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import entity.ConvertedWord;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.ru.RussianAnalyzer;
import org.apache.lucene.analysis.tokenattributes.CharTermAttribute;
import org.apache.lucene.util.AttributeSource;
import service.TextService;

/**
 * @author Damir Ilyasov
 */
public class TextServiceImpl implements TextService {
  private Analyzer analyzer = new RussianAnalyzer();

  public List<List<String>> convert(List<String> proposals) {
    List<List<String>> result = new ArrayList<>();
    proposals.forEach(proposal -> {
      TokenStream stream = analyzer.tokenStream("contents", new StringReader(proposal));
      try {
        List<String> convertedProposal = new ArrayList<>();
        stream.reset();
        while (stream.incrementToken()) {
          AttributeSource token = stream.cloneAttributes();
          CharTermAttribute termAttribute = token.addAttribute(CharTermAttribute.class);
          convertedProposal.add(termAttribute.toString());
        }
        result.add(convertedProposal);
        stream.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    });
    return result;
  }

  public List<ConvertedWord> calculateTfAndIdfAndTFIDF(List<List<String>> convertedProposals) {
    int wordsCount = convertedProposals.stream().mapToInt(List::size).sum();
    double proposalsCount = convertedProposals.size();
    Map<String, Integer> countMap = new TreeMap<>();
    List<ConvertedWord> resultConvertedUniqWordsWithTFIDF = new ArrayList<>();
    convertedProposals.forEach(proposal -> {
      proposal.forEach(word -> {
        if (countMap.containsKey(word)) {
          countMap.replace(word, countMap.get(word) + 1);
        }
        else {
          countMap.put(word, 1);
        }
      });
    });
    countMap.forEach((stringValue, wordFrequency) -> {
      double proposeWithThisWordFrequency = convertedProposals.stream().filter(proposal -> proposal.contains(stringValue)).count();
      double tf = (double) wordFrequency / wordsCount;
      double idf = proposalsCount / proposeWithThisWordFrequency;
      double tfidf = tf * idf;
      resultConvertedUniqWordsWithTFIDF.add(new ConvertedWord(stringValue, tf, idf, tfidf));
    });
    return resultConvertedUniqWordsWithTFIDF;
  }
}
