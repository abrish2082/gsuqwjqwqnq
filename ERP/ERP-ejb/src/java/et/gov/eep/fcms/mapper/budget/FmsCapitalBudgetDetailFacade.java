
package et.gov.eep.fcms.mapper.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.budget.FmsBudgetCode;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

    //</editor-fold>

@Stateless
public class FmsCapitalBudgetDetailFacade extends AbstractFacade<FmsCapitalBudgetDetail> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
//<editor-fold defaultstate="collapsed" desc="other methods ">

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsCapitalBudgetDetailFacade() {
        super(FmsCapitalBudgetDetail.class);
    }
    //</editor-fold>

//<editor-fold defaultstate="collapsed" desc="NamedQuery ">
    public List<FmsCapitalBudgetDetail> fetchSelectedCBRequest(FmsCapitalBudget1 capitalBudget1) {
        Query query = em.createNamedQuery("FmsCapitalBudgetDetail.findByCBId");
        query.setParameter("capitalBudgetId", capitalBudget1);
        ArrayList<FmsCapitalBudgetDetail> capitalBudgetDetailList = new ArrayList(query.getResultList());
        return capitalBudgetDetailList;
    }

    public List<FmsCapitalBudgetDetail> getCapBudgetDetailList(FmsLuBudgetYear fmsLuBudgetYear, FmsCapitalBudget1 capitalBudget1, FmsBudgetCode budgetCode) {
        Query query = em.createNamedQuery("FmsCapitalBudgetDetail.getCapBudgetDetail");
        query.setParameter("ccSsJunction", capitalBudget1.getCcSsJunction());
        query.setParameter("budgetYear", fmsLuBudgetYear.getBudgetYear());
        query.setParameter("budgetCode", budgetCode.getBudgetCode());
        ArrayList<FmsCapitalBudgetDetail> capitalBudgetDetailList = new ArrayList(query.getResultList());
        return capitalBudgetDetailList;
    }

    public List<FmsCapitalBudgetDetail> fetchCBDetail(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter) {
        Query query = em.createNamedQuery("FmsCapitalBudgetDetail.fetchCBDetail");
        query.setParameter("budgetYear", fmsLuBudgetYear.getBudgetYear());
        query.setParameter("costCenterId", fmsCostCenter.getCostCenterId());
        ArrayList<FmsCapitalBudgetDetail> capBList = new ArrayList<>(query.getResultList());
        return capBList;
    }

    public FmsCapitalBudgetDetail fetchCapBudgetDetail(FmsLuBudgetYear fmsLuBudgetYear, FmsCostCenter fmsCostCenter, FmsBudgetCode budgetCode) {
        Query query = em.createNamedQuery("FmsCapitalBudgetDetail.fetchCapBudgetDetail");
        query.setParameter("budgetYear", fmsLuBudgetYear.getBudgetYear());
        query.setParameter("costCenterId", fmsCostCenter.getCostCenterId());
        query.setParameter("budgetCode", budgetCode.getBudgetCode());
        FmsCapitalBudgetDetail capB = (FmsCapitalBudgetDetail) (query.getResultList().get(0));
        return capB;
    }

    public FmsCapitalBudgetDetail getCapBudgetDetail(FmsCapitalBudgetDetail fmsCapitalBudgetDetail) {
        Query query = em.createNamedQuery("FmsCapitalBudgetDetail.findByCbDetailId");
        query.setParameter("cbDetailId", fmsCapitalBudgetDetail.getCbDetailId());
        FmsCapitalBudgetDetail capB = (FmsCapitalBudgetDetail) (query.getResultList().get(0));
        return capB;
    }
    //</editor-fold>
    
}
