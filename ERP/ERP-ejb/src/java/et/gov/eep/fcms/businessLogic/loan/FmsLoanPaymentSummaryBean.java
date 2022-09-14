/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.loan;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsLoanPaymentSummary;
import et.gov.eep.fcms.mapper.loan.FmsLoanPaymentSummaryFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>


/**
 *
 * @author XX
 */
@Stateless
public class FmsLoanPaymentSummaryBean implements FmsLoanPaymentSummaryBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">
  @EJB
    FmsLoanPaymentSummaryFacade fmsLoanPaymentSummaryFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
 @Override
    public ArrayList<FmsLoanPaymentSummary> fetchLoanPaymentSummary(FmsLoan fmsLoan) {
        return fmsLoanPaymentSummaryFacade.fetchLoanPaymentSummary(fmsLoan);
    }

    @Override
    public void create(FmsLoanPaymentSummary fmsLoanPaymentSummary) {
        fmsLoanPaymentSummaryFacade.create(fmsLoanPaymentSummary);
    }

    @Override
    public void edit(FmsLoanPaymentSummary fmsLoanPaymentSummary) {
        fmsLoanPaymentSummaryFacade.edit(fmsLoanPaymentSummary);
    }

    @Override
    public void deleteLoanPaymentSummary(FmsLoan fmsLoan) {
        fmsLoanPaymentSummaryFacade.deleteLoanPaymentSummary(fmsLoan);
    }
    //</editor-fold>
    
  

   
}
