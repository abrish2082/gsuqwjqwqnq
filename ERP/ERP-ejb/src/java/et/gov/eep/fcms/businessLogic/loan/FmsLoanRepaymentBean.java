/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.loan;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsLoanDisbursement;
import et.gov.eep.fcms.entity.loan.FmsLoanRepayment;
import et.gov.eep.fcms.mapper.loan.FmsLoanRepaymentFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>


/**
 *
 * @author XX
 */
@Stateless
public class FmsLoanRepaymentBean implements FmsLoanRepaymentBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">
 @EJB
    FmsLoanRepaymentFacade fmsLoanRepaymentFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
 @Override
    public void edit(FmsLoanRepayment fmsLoanRepayment) {
        fmsLoanRepaymentFacade.edit(fmsLoanRepayment);
    }

    @Override
    public ArrayList<FmsLoanRepayment> searchLoanRepayment(FmsLoan fmsLoan) {
        return fmsLoanRepaymentFacade.searchLoanRepayment(fmsLoan);
    }
    //</editor-fold>
    
   

   
}
