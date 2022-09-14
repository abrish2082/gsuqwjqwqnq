/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.loan;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.loan.FmsLoanCommitment;
import et.gov.eep.fcms.mapper.loan.FmsLoanCommitmentFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;
    //</editor-fold>


/**
 *
 * @author Binyam
 */
@Stateless
public class FmsLoanCommitmentBean implements FmsLoanCommitmentBeanLocal {
//<editor-fold defaultstate="collapsed" desc="EJB ">
   @EJB
    FmsLoanCommitmentFacade fmsLoanCommitmentFacade;
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="other methods ">
   @Override
    public ArrayList<FmsLoanCommitment> fetchCommitment(FmsLoanCommitment fmsLoanCommitment) {
        return fmsLoanCommitmentFacade.fetchCommitment(fmsLoanCommitment);
    }

    /**
     *
     * @param fmsLoanCommitment
     */
    @Override
    public void create(FmsLoanCommitment fmsLoanCommitment) {
        fmsLoanCommitmentFacade.create(fmsLoanCommitment);
    }
    
    /**
     *
     * @param fmsLoanCommitment
     */
    @Override
    public void edit(FmsLoanCommitment fmsLoanCommitment) {
        fmsLoanCommitmentFacade.edit(fmsLoanCommitment);
    }
    //</editor-fold>
    
 
   
 
}
