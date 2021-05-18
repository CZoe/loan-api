package com.czoe.loanapi;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Created by Czoe on 5/17/2021.
 */
public interface LoanRepository extends JpaRepository<Loan, Long>{

}
