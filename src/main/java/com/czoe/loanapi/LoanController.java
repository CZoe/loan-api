package com.czoe.loanapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Czoe on 5/17/2021.
 */

@RestController
@RequestMapping("/api")
public class LoanController {
  @Autowired
  private LoanRepository loanRepository;

  // GET method to get all Loans
  @GetMapping("/loans")
  public List<Loan> getAllLoans() {
    return loanRepository.findAll();
  }

  //GET method to fetch Loan by id
  @GetMapping("/loans/{id}")
  public ResponseEntity<Loan> getLoanById(@PathVariable(value = "id") Long id) throws Exception {
    Loan loan = loanRepository.findById(id)
        .orElseThrow(() ->
            new Exception("Loan " + id + " not found."));
    return ResponseEntity.ok().body(loan);
  }

  // POST method to make Loan
  @PostMapping("/loans")
  public Loan createLoan(@RequestBody Loan loan) {
    return loanRepository.save(loan);
  }

  // PUT to update Loan details
  @PutMapping("/loans/{id}")
  public ResponseEntity<Loan> updateLoan(
      @PathVariable(value = "id") Long id, @RequestBody Loan loanDetails)
      throws Exception {
        Loan loan = loanRepository.findById(id)
            .orElseThrow(()->
                new Exception("Loan " + id + " not found."));

        loan.setLoanName(loanDetails.getLoanName());
        loan.setOwner(loanDetails.getOwner());
        loan.setAmount(loanDetails.getAmount());
        loan.setInterestRate(loanDetails.getInterestRate());
        loan.setLoanLength(loanDetails.getLoanLength());
        loan.setMonthlyPayment(loanDetails.getMonthlyPayment());

        final Loan updatedLoan = loanRepository.save(loan);
        return ResponseEntity.ok(updatedLoan);
  }

  // DELETE all records
  @DeleteMapping("/loans")
  public String deleteAllRecords() {
    loanRepository.deleteAllInBatch();
    return "All records deleted";
  }

  @DeleteMapping("/loans/{id}")
  public String deleteById(@PathVariable(value = "id") Long id) throws Exception {
    loanRepository.deleteById(id);
    return "Loan ID " + id + " deleted.";
  }



}
