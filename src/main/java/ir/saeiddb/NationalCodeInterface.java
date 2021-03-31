package ir.saeiddb;


import ir.saeiddb.NationalCode.InvalidNationalCode;

public interface NationalCodeInterface {

  void validate(String nationalCode) throws InvalidNationalCode;

  String addIgnoredZeros(String nationalCode);
}
