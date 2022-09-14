
package et.gov.eep.fcms.businessLogic.Voucher;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.fcms.mapper.Voucher.FmsJournalVoucherFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
//</editor-fold>

@Stateless
public class FmsJournalVoucherBean implements FmsJournalVoucherBeanLocal {

    //<editor-fold defaultstate="collapsed" desc="EJB ">
    @EJB
    FmsJournalVoucherFacade journalVoucherFacade;
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="other methods ">

    @Override
    public List<String> getPrepareList(int approveStatus) {
        return journalVoucherFacade.getPrepareList(approveStatus);
    }
    //</editor-fold>

}
