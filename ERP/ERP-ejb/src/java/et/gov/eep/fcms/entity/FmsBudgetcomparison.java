/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

/**
 *
 * @author mora1
 */

public class FmsBudgetcomparison  {
   
private Integer oBDetailId;
private double approvedAmount;
private double remainingBalance;
private Integer generalLedgerId;
private Integer operatingBudgetId;
private String ACC_DESC;
private String reference;
private String display_conn;
private double DEBIT;
private double CREDIT;
private double balance;
private double TotalDebit;
private double TotalCredit;
private double Utilization;
private double Performance;

    /**
     * @return the date
     */
  
    public String getACC_DESC() {
        return ACC_DESC;
    }

    /**
     * @param ACC_DESC the ACC_DESC to set
     */
    public void setACC_DESC(String ACC_DESC) {
        this.ACC_DESC = ACC_DESC;
    }

    /**
     * @return the DEBIT
     */
    public double getDEBIT() {
        return DEBIT;
    }

    /**
     * @param DEBIT the DEBIT to set
     */
    public void setDEBIT(double DEBIT) {
        this.DEBIT = DEBIT;
    }

    /**
     * @return the CREDIT
     */
    public double getCREDIT() {
        return CREDIT;
    }

    public void setCREDIT(double CREDIT) {
        this.CREDIT = CREDIT;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getTotalDebit() {
        return TotalDebit;
    }

    public void setTotalDebit(double TotalDebit) {
        this.TotalDebit = TotalDebit;
    }

    public double getTotalCredit() {
        return TotalCredit;
    }

    public void setTotalCredit(double TotalCredit) {
        this.TotalCredit = TotalCredit;
    }

    public Integer getoBDetailId() {
        return oBDetailId;
    }

    public void setoBDetailId(Integer oBDetailId) {
        this.oBDetailId = oBDetailId;
    }

    public double getApprovedAmount() {
        return approvedAmount;
    }

    public void setApprovedAmount(double approvedAmount) {
        this.approvedAmount = approvedAmount;
    }

    public double getRemainingBalance() {
        return remainingBalance;
    }

    public void setRemainingBalance(double remainingBalance) {
        this.remainingBalance = remainingBalance;
    }

   

    public Integer getGeneralLedgerId() {
        return generalLedgerId;
    }

    public void setGeneralLedgerId(Integer generalLedgerId) {
        this.generalLedgerId = generalLedgerId;
    }

    public Integer getOperatingBudgetId() {
        return operatingBudgetId;
    }

    public void setOperatingBudgetId(Integer operatingBudgetId) {
        this.operatingBudgetId = operatingBudgetId;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
    }

    public double getUtilization() {
        return Utilization;
    }

    public void setUtilization(double Utilization) {
        this.Utilization = Utilization;
    }

    public double getPerformance() {
        return Performance;
    }

    public void setPerformance(double Performance) {
        this.Performance = Performance;
    }

}
