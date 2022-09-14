
package et.gov.eep.fcms.mapper.Voucher;

//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Vocher.FmsCashPaymentOrder;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//</editor-fold>

@Stateless
public class FmsCashPaymentOrderFacade extends AbstractFacade<FmsCashPaymentOrder> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
   
    //<editor-fold defaultstate="collapsed" desc="other methods ">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public FmsCashPaymentOrderFacade() {
        super(FmsCashPaymentOrder.class);
    }
    //</editor-fold>

    //<editor-fold defaultstate="collapsed" desc="Named Queries">
    /**
     *
     * @param approveStatus
     * @return
     */
    public List<String> getPrepareList(int approveStatus) {

        List<String> ChequePaymentList = null;
        try {
            Query query = em.createNamedQuery("FmsCashReceiptVoucher.findByStatus");
            query.setParameter("status", approveStatus);
            ChequePaymentList = (List<String>) query.getResultList();
            return ChequePaymentList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<FmsCashPaymentOrder> getPreparedCPO() {
        try {
            Query query = em.createNamedQuery("FmsCashPaymentOrder.findByStatus");
            query.setParameter("status", "1");
            ArrayList<FmsCashPaymentOrder> cashPaymentList = new ArrayList(query.getResultList());
            return cashPaymentList;
        } catch (Exception e) {
            throw e;
        }
    }

    public FmsCashPaymentOrder getCashPaymentOrder(FmsCashPaymentOrder fmsCashPaymentOrder) {
        try {
            Query query = em.createNamedQuery("FmsCashPaymentOrder.findByVoucherId");
            query.setParameter("fmsVOUCHERID", fmsCashPaymentOrder.getFmsVOUCHERID());
            FmsCashPaymentOrder checkList = (FmsCashPaymentOrder) query.getResultList().get(0);
            return checkList;
        } catch (Exception e) {
            throw e;
        }
    }
    //</editor-fold>

}
