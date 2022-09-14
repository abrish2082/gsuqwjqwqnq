/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.loan;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.fcms.entity.loan.FmsLoanDisbursement;
import et.gov.eep.fcms.mapper.loan.FmsLoanDisbursementFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>


/**
 *
 * @author Binyam
 */
@Stateless
public class FmsDisbursementBean implements FmsDisbursementBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB">
@EJB
    FmsLoanDisbursementFacade fmsLoanDisbursementFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
  @Override
    public void create(FmsLoanDisbursement fmsLoanDisbursement) {
        fmsLoanDisbursementFacade.create(fmsLoanDisbursement);
    }
    
    /**
     *
     * @param fmsLoanDisbursement
     */
    @Override
    public void edit(FmsLoanDisbursement fmsLoanDisbursement) {
        fmsLoanDisbursementFacade.edit(fmsLoanDisbursement);
    }

    /**
     *
     * @param fmsLoanDisbursement
     * @return
     */
    
    @Override
    public ArrayList<FmsLoanDisbursement> fetchDisbursement(FmsLoanDisbursement fmsLoanDisbursement) {
        return fmsLoanDisbursementFacade.fetch(fmsLoanDisbursement);
    }
    //</editor-fold>
    
    
    
   
  
}
