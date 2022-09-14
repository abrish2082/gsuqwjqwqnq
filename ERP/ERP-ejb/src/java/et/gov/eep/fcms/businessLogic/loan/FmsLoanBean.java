/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.loan;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.mapper.loan.FmsLoanFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;
    //</editor-fold>


/**
 *
 * @author Binyam
 */
@Stateless
public class FmsLoanBean implements FmsLoanBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">
@EJB
    FmsLoanFacade fmsLoanFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Inject ">
 @Inject
    FmsLoan fmsLoan;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
 @Override
    public void create(FmsLoan fmsLoan) {
        fmsLoanFacade.create(fmsLoan);
    }    

    /**
     *
     * @param fmsLoan
     */
    @Override
    public void edit(FmsLoan fmsLoan) {
         fmsLoanFacade.edit(fmsLoan);
    }    

    /**
     *
     * @param fmsLoan
     * @return
     */
    @Override
     public ArrayList<FmsLoan> searchFmsLoanByLoanNo(FmsLoan fmsLoan) {
      return fmsLoanFacade.searchFmsLoanByLoanNo(fmsLoan);
    }

    /**
     *
     * @param fmsLoan
     * @return
     */
    @Override
    public FmsLoan getFmsLoanInfo(FmsLoan fmsLoan) {
     return fmsLoanFacade.getFmsLoanInfo(fmsLoan);
    }
    
    /**
     *
     * @param fmsLoan
     * @return
     */
    @Override
    public FmsLoan findFmsLoanInfo(FmsLoan fmsLoan) {
     return fmsLoanFacade.fetchFmsLoanInfo(fmsLoan);
    }

    @Override
    public List<FmsLoan> findAll() {
        return fmsLoanFacade.findAll();
    }

    //</editor-fold>
    
    
   
    
    
   
}
