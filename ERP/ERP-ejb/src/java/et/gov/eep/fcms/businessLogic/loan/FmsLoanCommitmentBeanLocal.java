/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.loan;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.loan.FmsLoanCommitment;
import java.util.ArrayList;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author Binyam
 */
@Local
public interface FmsLoanCommitmentBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
 public ArrayList<FmsLoanCommitment> fetchCommitment(FmsLoanCommitment fmsLoanCommitment);

    /**
     *
     * @param fmsLoanCommitment
     */
    public void create(FmsLoanCommitment fmsLoanCommitment);

    /**
     *
     * @param fmsLoanCommitment
     */
    public void edit(FmsLoanCommitment fmsLoanCommitment);
    //</editor-fold>
    
   
   
}
