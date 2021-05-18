package com.czoe.loanapi;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.Id;
import javax.persistence.*;
import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Created by Czoe on 5/17/2021.
 */

@Entity
@Table(name = "loans")
@EntityListeners(AuditingEntityListener.class)
public class Loan {

  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  private long id;

  @Column
  private String loanName;

  @Column
  private String owner;

  @Column(nullable = false)
  private BigInteger amount; // stored in cents.

  @Column(nullable = false)
  private BigDecimal interestRate;

  @Column(nullable = false)
  private int loanLength;

  @Column(nullable = false) // stored in cents.
  private BigInteger monthlyPayment;


  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public String getLoanName() {
    return loanName;
  }

  public void setLoanName(String loanName) {
    this.loanName = loanName;
  }

  public String getOwner() {
    return owner;
  }

  public void setOwner(String owner) {
    this.owner = owner;
  }

  public BigInteger getAmount() {
    return amount;
  }

  public void setAmount(BigInteger amount) {
    this.amount = amount;
  }

  public BigDecimal getInterestRate() {
    return interestRate;
  }

  public void setInterestRate(BigDecimal interestRate) {
    this.interestRate = interestRate;
  }

  public int getLoanLength() {
    return loanLength;
  }

  public void setLoanLength(int loanLength) {
    this.loanLength = loanLength;
  }

  public BigInteger getMonthlyPayment() {
    return monthlyPayment;
  }

  public void setMonthlyPayment(BigInteger monthlyPayment) {
    this.monthlyPayment = monthlyPayment;
  }

  @Override
  public String toString() {
    return "Loan{" +
        "id=" + id +
        ", loanName='" + loanName + '\'' +
        ", owner='" + owner + '\'' +
        ", amount=" + amount +
        ", interestRate=" + interestRate +
        ", loanLength=" + loanLength +
        ", monthlyPayment=" + monthlyPayment +
        '}';
  }
}
