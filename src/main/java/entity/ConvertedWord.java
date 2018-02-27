package entity;

/**
 * @author Damir Ilyasov
 */
public class ConvertedWord {
  private String value;
  private double tf;
  private double idf;
  private double tfidf;

  public ConvertedWord(String value) {
    this.value = value;
  }

  public ConvertedWord(String value, double tf, double idf) {
    this.value = value;
    this.tf = tf;
    this.idf = idf;
  }

  public double getTfidf() {
    return tfidf;
  }

  public void setTfidf(double tfidf) {
    this.tfidf = tfidf;
  }

  public ConvertedWord(String value, double tf, double idf, double tfidf) {
    this.value = value;
    this.tf = tf;
    this.idf = idf;
    this.tfidf = tfidf;
  }

  public String getValue() {
    return value;
  }

  public void setValue(String value) {
    this.value = value;
  }

  public double getTf() {
    return tf;
  }

  public void setTf(double tf) {
    this.tf = tf;
  }

  public double getIdf() {
    return idf;
  }

  public void setIdf(double idf) {
    this.idf = idf;
  }
}
