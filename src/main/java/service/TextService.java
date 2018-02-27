package service;

import java.util.List;

import entity.ConvertedWord;

/**
 * @author Damir Ilyasov
 */
public interface TextService {
  List<List<String>> convert(List<String> proposals);
  List<ConvertedWord> calculateTfAndIdfAndTFIDF(List<List<String>> convertedWords);
}
