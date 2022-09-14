/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.loan;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsLoanPaymentSummary;
import java.util.ArrayList;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author XX
 */
@Local
public interface FmsLoanPaymentSummaryBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
public ArrayList<FmsLoanPaymentSummary> fetchLoanPaymentSummary(FmsLoan fmsLoan);

    public void create(FmsLoanPaymentSummary fmsLoanPaymentSummary);

    public void edit(FmsLoanPaymentSummary fmsLoanPaymentSummary);
    
    public void deleteLoanPaymentSummary(FmsLoan fmsLoan);
    //</editor-fold>
    
    
    
}
