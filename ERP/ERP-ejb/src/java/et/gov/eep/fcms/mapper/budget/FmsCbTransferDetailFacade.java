
package et.gov.eep.fcms.mapper.budget;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.businessLogic.budget.FmsCBTasksBean;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import et.gov.eep.fcms.entity.budget.FmsCbTransferDetail;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
    //</editor-fold>

@Stateless
public class FmsCbTransferDetailFacade extends AbstractFacade<FmsCbTransferDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
//<editor-fold defaultstate="collapsed" desc="other methods ">
     @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsCbTransferDetailFacade() {
        super(FmsCbTransferDetail.class);
    }

    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="NamedQuery">
     public ArrayList<FmsCbTransferDetail> fetchCBFromByCBDTasktlID(FmsCapitalBudgetTasks capitalBudgetTasks) {
        try {
            Query query = em.createNamedQuery("FmsCbTransferDetail.findByFromCbId");
            query.setParameter("fromCbId", capitalBudgetTasks);
            ArrayList<FmsCbTransferDetail> capitalBudgetList = new ArrayList(query.getResultList());
            return capitalBudgetList;
        } catch (NullPointerException nullex) {
            throw nullex;
        }
    }

    public ArrayList<FmsCbTransferDetail> findCBTransReqList() {
        try {
            Query query = em.createNamedQuery("FmsCbTransferDetail.findReqList");
            ArrayList<FmsCbTransferDetail> reqList = new ArrayList(query.getResultList());
            return reqList;
        } catch (NullPointerException nullex) {
            throw nullex;
        }
    }

    public FmsCbTransferDetail findByTrasferId(FmsCbTransferDetail cbTransferDetail) {
        try {
            Query query = em.createNamedQuery("FmsCbTransferDetail.findByTransferDtlId");
            query.setParameter("transferDtlId", cbTransferDetail.getTransferDtlId());
            FmsCbTransferDetail cbTrans = (FmsCbTransferDetail) query.getResultList().get(0);
            return cbTrans;
        } catch (NullPointerException nullex) {
            throw nullex;
        }
    }
    //</editor-fold>
    
    
   
   
}
