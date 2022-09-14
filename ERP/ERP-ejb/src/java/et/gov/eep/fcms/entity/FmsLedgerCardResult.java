/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.entity;

public class FmsLedgerCardResult {

private String date;
private String voucherType;
private String reference;
private String display_conn;
private String ACC_DESC;
private double DEBIT;
private double CREDIT;
private double balance;
private double TotalDebit;
private double TotalCredit;

    /**
     * @return the date
     */
    public String getDate() {
        return date;
    }

    /**
     * @param date the date to set
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * @return the reference
     */
    public String getReference() {
        return reference;
    }

    /**
     * @param reference the reference to set
     */
    public void setReference(String reference) {
        this.reference = reference;
    }

    /**
     * @return the ACC_DESC
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

    public String getVoucherType() {
        return voucherType;
    }

    public void setVoucherType(String voucherType) {
        this.voucherType = voucherType;
    }

    public String getDisplay_conn() {
        return display_conn;
    }

    public void setDisplay_conn(String display_conn) {
        this.display_conn = display_conn;
    }

   
}
