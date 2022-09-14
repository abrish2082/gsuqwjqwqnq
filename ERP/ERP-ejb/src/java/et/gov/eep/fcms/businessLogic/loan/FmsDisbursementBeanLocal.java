/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.loan;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.fcms.entity.loan.FmsLoanDisbursement;
import java.util.ArrayList;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author Binyam
 */
@Local
public interface FmsDisbursementBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
public void create(FmsLoanDisbursement fmsLoanDisbursement);

    /**
     *
     * @param fmsLoanDisbursement
     */
    public void edit(FmsLoanDisbursement fmsLoanDisbursement);

    /**
     *
     * @param fmsLoanDisbursement
     * @return
     */
    public ArrayList<FmsLoanDisbursement> fetchDisbursement(FmsLoanDisbursement fmsLoanDisbursement);
    //</editor-fold>
    
  
    
}
