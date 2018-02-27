import java.io.*;
import java.util.*;

import entity.ConvertedWord;
import service.TextService;
import service.impl.TextServiceImpl;

/**
 * @author Damir Ilyasov
 */
public class Main {
  private static final String INPUT_TEXT_FILE_PATH = "D:\\ideaProjects\\nlp\\src\\main\\resources\\InputText.txt";

  public static void main(String[] args) throws IOException {
    TextService textService = new TextServiceImpl();
    String inputText = readFile(new File(INPUT_TEXT_FILE_PATH));
    List<String> proposals = Arrays.asList(inputText.split("[!?.]"));
    List<List<String>> convertedProposals = textService.convert(proposals);
    List<ConvertedWord> resultWords = textService.calculateTfAndIdfAndTFIDF(convertedProposals);
    boolean flag = true;
    while (flag) {
      Scanner scanner = new Scanner(System.in);
      System.out.println("Sort by ... ?");
      System.out.println("1) TF");
      System.out.println("2) IDF");
      System.out.println("3) TF-IDF");
      System.out.println("4) Close program (EXIT)");
      System.out.println("Input 1,2,3 or 4 and press ENTER");
      int choise = scanner.nextInt();
      switch (choise) {
        case 1: {
          resultWords.stream().sorted(Comparator.comparing(ConvertedWord::getTf).reversed()).forEach(word ->
              System.out.println(String.format("%-10s | TF: %-10f | IDF: %-10f | TF-IDF: %-10f",
                  word.getValue(), word.getTf(), word.getIdf(), word.getTfidf())));
          break;
        }
        case 2: {
          resultWords.stream().sorted(Comparator.comparing(ConvertedWord::getIdf).reversed()).forEach(word ->
              System.out.println(String.format("%-10s | TF: %-10f | IDF: %-10f | TF-IDF: %-10f",
                  word.getValue(), word.getTf(), word.getIdf(), word.getIdf())));
          break;
        }
        case 3: {
          resultWords.stream().sorted(Comparator.comparing(ConvertedWord::getTfidf).reversed()).forEach(word ->
              System.out.println(String.format("%-10s | TF: %-10f | IDF: %-10f | TF-IDF: %-10f",
                  word.getValue(), word.getTf(), word.getIdf(), word.getTfidf())));
          break;
        }
        case 4: {
          flag = false;
          break;
        }
        default: {
          System.out.println("Wrong input. Choose 1,2,3 or 4 and press ENTER");
          break;
        }
      }
    }
  }

  private static String readFile(File file) throws IOException {
    FileReader reader = new FileReader(file);
    StringBuilder result = new StringBuilder();
    int symbol;
    while ((symbol = reader.read()) != -1) {
      result.append((char) symbol);
    }
    return result.toString();
  }
}
