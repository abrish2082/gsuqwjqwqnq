
package et.gov.eep.fcms.mapper.Voucher;
//<editor-fold defaultstate="collapsed" desc="import ">

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.Vocher.FmsJournalVoucher;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
//</editor-fold>

@Stateless
public class FmsJournalVoucherFacade extends AbstractFacade<FmsJournalVoucher> {

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
    public FmsJournalVoucherFacade() {
        super(FmsJournalVoucher.class);
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="NamedQuery ">
      public List<String> getPrepareList(int approveStatus) {

        List<String> ChequePaymentList = null;
        try {
            Query query = em.createNamedQuery("FmsJournalVoucher.findByStatus", FmsJournalVoucher.class);
            query.setParameter("status", approveStatus);
            ChequePaymentList = (List<String>) query.getResultList();
            return ChequePaymentList;
        } catch (Exception e) {
            return null;
        }
    }
    //</editor-fold>
 
}
