/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.loan;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsLoanRepayment;
import java.util.ArrayList;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author XX
 */
@Local
public interface FmsLoanRepaymentBeanLocal {
    //<editor-fold defaultstate="collapsed" desc="other methods ">
 public void edit(FmsLoanRepayment fmsLoanRepayment);
    public ArrayList<FmsLoanRepayment> searchLoanRepayment(FmsLoan fmsLoan);
    //</editor-fold>
    
   
}
