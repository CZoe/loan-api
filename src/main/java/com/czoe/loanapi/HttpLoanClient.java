package com.czoe.loanapi;


import com.google.gson.Gson;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Czoe on 5/17/2021.
 */
public class HttpLoanClient {

  private final CloseableHttpClient httpClient = HttpClients.createDefault();

  public static void main(String [] args) throws Exception {
    HttpLoanClient obj = new HttpLoanClient();

    Loan testLoan = new Loan();
    testLoan.setLoanName("insertTest");
    testLoan.setMonthlyPayment(BigInteger.valueOf(30));
    testLoan.setLoanLength(20);
    testLoan.setInterestRate(BigDecimal.valueOf(1.5));
    testLoan.setAmount(BigInteger.valueOf(45000));
    testLoan.setOwner("Meryann");

    Loan editedTestLoan = new Loan();
    editedTestLoan.setLoanName("insertTest2");
    editedTestLoan.setMonthlyPayment(BigInteger.valueOf(30));
    editedTestLoan.setLoanLength(20);
    editedTestLoan.setInterestRate(BigDecimal.valueOf(1.5));
    editedTestLoan.setAmount(BigInteger.valueOf(400));
    editedTestLoan.setOwner("Merry");


    try {

      /*List<Loan> loanList = obj.getLoans();
      obj.printLoans(loanList);*/

      /*Loan loan7 = obj.getLoanByID(7);
      System.out.println(loan7.toString());*/


      /*obj.createLoan(testLoan);

      obj.editLoan((long) 21, editedTestLoan);*/

    } finally {
      obj.close();
    }

  }

  private void close() throws IOException {
    httpClient.close();
  }

  private List<Loan> getLoans() throws IOException {
    HttpGet request = new HttpGet("http://localhost:8080/api/loans");

    try (CloseableHttpResponse response = httpClient.execute(request)) {
      HttpEntity entity = response.getEntity();

      if (entity == null) {
        System.out.println("No Loans");
      }

      String body = EntityUtils.toString(entity);

      JSONArray array = new JSONArray(body);
      Gson g = new Gson();
      List<Loan> loanList = new ArrayList();
      for(int i = 0; i < array.length(); i++) {
        JSONObject jsonloan = array.getJSONObject(i);
        Loan loan= g.fromJson(jsonloan.toString(), Loan.class);
        loanList.add(loan);
      }

      return loanList;
    }
  }

  private Loan getLoanByID(long id) throws IOException {
    HttpGet request = new HttpGet("http://localhost:8080/api/loans/"+id);

    try (CloseableHttpResponse response = httpClient.execute(request)) {
      HttpEntity entity = response.getEntity();

      if (entity == null) {
        System.out.println("No such loan");
        return null;
      }

      String body = EntityUtils.toString(entity);
      JSONObject jsonloan = new JSONObject(body);
      Gson g = new Gson();
      Loan loan = g.fromJson(jsonloan.toString(), Loan.class);
      return loan;
    }
  }

  private Loan createLoan(Loan loan) throws IOException {
    HttpPost post = new HttpPost("http://localhost:8080/api/loans");

    Gson gson = new Gson();
    String jsonString = gson.toJson(loan);

    StringEntity postingString = new StringEntity(jsonString);
    post.setEntity(postingString);

    post.addHeader("content-type", "application/json");

    try (CloseableHttpClient httpClient = HttpClients.createDefault();
         CloseableHttpResponse response = httpClient.execute(post)) {

      HttpEntity entity = response.getEntity();
      if (entity == null) {
        System.out.println("Loan not created");
        return null;
      }

      String body = EntityUtils.toString(entity);
      JSONObject jsonloan = new JSONObject(body);
      Gson g = new Gson();
      Loan createdLoan = g.fromJson(jsonloan.toString(), Loan.class);
      return createdLoan;
    }
  }

  private Loan editLoan(Long id, Loan loanDetails) throws IOException {
    HttpPut put = new HttpPut("http://localhost:8080/api/loans/" + id);

    Gson gson = new Gson();
    String jsonString = gson.toJson(loanDetails);
    System.out.println(jsonString);
    StringEntity postingString = new StringEntity(jsonString);
    put.setEntity(postingString);
    put.addHeader("content-type", "application/json");

    try (CloseableHttpClient httpClient = HttpClients.createDefault();
         CloseableHttpResponse response = httpClient.execute(put)) {

      HttpEntity entity = response.getEntity();
      if (entity == null) {
        System.out.println("Error editing loan");
        return null;
      }

      String body = EntityUtils.toString(entity);
      JSONObject jsonloan = new JSONObject(body);
      Gson g = new Gson();
      Loan createdLoan = g.fromJson(jsonloan.toString(), Loan.class);
      return createdLoan;
    }

  }

  public void printLoans(List<Loan> loanList) {
    for (Loan loan : loanList) {
      System.out.println(loan.toString());
    }
  }

}
