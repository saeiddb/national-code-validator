package ir.saeiddb;

public class NationalCode implements NationalCodeInterface {

  @Override
  public void validate(String nationalCode) throws InvalidNationalCode {
    nationalCode = addIgnoredZeros(nationalCode);

    if (nationalCode.length() != 10) {
      throw new InvalidNationalCode("Invalid national code length.");
    }

    try {
      Long.parseLong(nationalCode);
    } catch (NumberFormatException e) {
      throw new InvalidNationalCode("Notional code is not fully numeric.");
    }

    if (checkSameCharacterString(nationalCode)) {
      throw new InvalidNationalCode("The national code with all same numbers is invalid.");
    }

    if (!hasValidChecksum(nationalCode)) {
      throw new InvalidNationalCode("Invalid national code. Checksum calculation shows that.");
    }
  }

  @Override
  public String addIgnoredZeros(String nationalCode) {
    if (nationalCode.length() < 10) {
      if (nationalCode.length() == 8) {
        return "00" + nationalCode;
      } else if (nationalCode.length() == 9) {
        return "0" + nationalCode;
      }
    }
    return nationalCode;
  }

  private boolean checkSameCharacterString(String input) {
    char firstCharacter = input.charAt(0);
    for (char charInInput : input.toCharArray()) {
      if (firstCharacter != charInInput) {
        return false;
      }
    }
    return true;
  }

  private boolean hasValidChecksum(String nationalCode) {
    String[] nationalCodeNumbers = nationalCode.split("");
    int sum = 0;
    for (int position = 10; position > 1; position--) {
      sum += position * Integer.parseInt(nationalCodeNumbers[10 - position]);
    }
    int checksum = sum % 11;
    if (checksum > 1) {
      checksum = 11 - checksum;
    }

    return Integer.parseInt(nationalCodeNumbers[9]) == checksum;
  }

  public class InvalidNationalCode extends Exception {

    public InvalidNationalCode() {
      super("Invalid national code.");
    }

    public InvalidNationalCode(String message) {
      super(message);
    }
  }


}
