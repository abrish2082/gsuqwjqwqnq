/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.loan;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.fcms.entity.loan.FmsLoan;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>


/**
 *
 * @author Binyam
 */
@Local
public interface FmsLoanBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
 public void create(FmsLoan fmsLoan);
    public void edit(FmsLoan fmsLoan);
    public List<FmsLoan> findAll();
    public ArrayList<FmsLoan> searchFmsLoanByLoanNo(FmsLoan fmsLoan);
    public FmsLoan getFmsLoanInfo(FmsLoan fmsLoan);
    public FmsLoan findFmsLoanInfo(FmsLoan fmsLoan);
    //</editor-fold>
    
   
}
