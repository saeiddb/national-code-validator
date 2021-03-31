package ir.saeiddb;

import ir.saeiddb.NationalCode.InvalidNationalCode;
import java.io.Console;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

  private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);

  private static class NationalCodeValidationItem {

    private final String nationalCode;
    private final boolean isValid;
    private final String validationMessage;
    private final Date logTime;

    private NationalCodeValidationItem(String nationalCode, boolean isValid,
        String validationMessage, Date logTime) {
      this.nationalCode = nationalCode;
      this.isValid = isValid;
      this.validationMessage = validationMessage;
      this.logTime = logTime;
    }

    public String getNationalCode() {
      return nationalCode;
    }

    public boolean isValid() {
      return isValid;
    }

    public String getValidationMessage() {
      return validationMessage;
    }

    public Date getLogTime() {
      return logTime;
    }

    @Override
    public String toString() {
      return "{" +
          "nationalCode='" + nationalCode + '\'' +
          ", isValid=" + isValid +
          ", validationMessage='" + validationMessage + '\'' +
          ", logTime=" + logTime +
          '}';
    }
  }

  public static void main(String[] args) {

    Console console = System.console();

    if (console == null) {
      LOGGER.error("No console available");
      return;
    }

    NationalCode nationalCode = new NationalCode();
    List<NationalCodeValidationItem> nationalCodeValidationItems = new ArrayList<>();

    String input = "";
    while (true) {
      input = console.readLine("Enter string as a national code: ");
      if (input.equals("exit")) {
        break;
      }

      boolean isValid = true;
      String message = "";
      try {
        nationalCode.validate(input);
      } catch (InvalidNationalCode invalidNationalCode) {
        isValid = false;
        message = invalidNationalCode.getMessage();
      }
      nationalCodeValidationItems
          .add(new NationalCodeValidationItem(input, isValid, message, new Date()));
    }

    Collections.sort(nationalCodeValidationItems,
        Comparator.comparing(NationalCodeValidationItem::getNationalCode));

    for (int i = 0; i < nationalCodeValidationItems.size(); i++) {
      LOGGER.info("{}- {}", i, nationalCodeValidationItems);
    }
  }
}
