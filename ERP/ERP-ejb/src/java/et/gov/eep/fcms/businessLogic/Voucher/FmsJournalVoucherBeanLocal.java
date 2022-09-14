package et.gov.eep.fcms.businessLogic.Voucher;

   //<editor-fold defaultstate="collapsed" desc="import ">
import java.util.List;
import javax.ejb.Local;
//</editor-fold>

@Local
public interface FmsJournalVoucherBeanLocal {

//<editor-fold defaultstate="collapsed" desc="interface methods ">
    public List<String> getPrepareList(int approveStatus);
    //</editor-fold>

}
