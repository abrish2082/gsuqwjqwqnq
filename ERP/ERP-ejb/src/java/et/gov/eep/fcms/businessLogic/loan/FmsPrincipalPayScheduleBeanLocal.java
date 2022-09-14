
package et.gov.eep.fcms.businessLogic.loan;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.fcms.entity.loan.FmsLoan;
import et.gov.eep.fcms.entity.loan.FmsPrincipalPayment;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
    //</editor-fold>

@Local
public interface FmsPrincipalPayScheduleBeanLocal {
//<editor-fold defaultstate="collapsed" desc="other methods ">
 public void create (FmsPrincipalPayment fmsPrincipalPayment);
    public void delete (FmsPrincipalPayment fmsPrincipalPayment);

    /**
     *
     * @param fmsPrincipalPayment
     */
    public void edit (FmsPrincipalPayment fmsPrincipalPayment);

    /**
     *
     * @param fmsPrincipalPayment
     * @return
     */
    public ArrayList <FmsPrincipalPayment> fetchschedule(FmsPrincipalPayment fmsPrincipalPayment);

    /**
     *
     * @param fmsPrincipalPayment
     * @return
     */
    public ArrayList <FmsPrincipalPayment> listschedule(FmsPrincipalPayment fmsPrincipalPayment);
    
    
    public List<FmsPrincipalPayment> fetchPricipalPayment (FmsLoan fmsLoan);
    //</editor-fold>
      
   
}
