/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudget1;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
    //</editor-fold>

@Stateless
public class FmsCapitalBudgetTasksFacade extends AbstractFacade<FmsCapitalBudgetTasks> {
//<editor-fold defaultstate="collapsed" desc="other methods ">
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsCapitalBudgetTasksFacade() {
        super(FmsCapitalBudgetTasks.class);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="NamedQuery ">
     public List<FmsCapitalBudgetTasks> findByCBudgetReqCode(FmsCapitalBudget1 capitalBudget1) {
        Query query = em.createNamedQuery("FmsCapitalBudgetTasks.findByCBudgetReqCode");
        query.setParameter("requestCode", capitalBudget1.getRequestCode());
        ArrayList<FmsCapitalBudgetTasks> cbTasks = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            cbTasks = new ArrayList(query.getResultList());
        }
        return cbTasks;
    }

    public List<FmsCapitalBudgetTasks> findByCBudgetDetail(FmsCapitalBudgetDetail capitalBudgetDetail) {
        Query query = em.createNamedQuery("FmsCapitalBudgetTasks.findByBgtDtl");
        query.setParameter("cBDetailFk", capitalBudgetDetail);
        ArrayList<FmsCapitalBudgetTasks> cbTasks = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            cbTasks = new ArrayList(query.getResultList());
        }
        return cbTasks;
    }

    public FmsCapitalBudget1 findByRequestCodeOnRequest(FmsCapitalBudget1 capitalBudget1) {
        Query query = em.createNamedQuery("FmsCapitalBudget1.findByRequestCodeOnRequest");
        query.setParameter("requestCode", capitalBudget1.getRequestCode());
        FmsCapitalBudget1 capitalBudget = (FmsCapitalBudget1) query.getResultList().get(0);
        return capitalBudget;
    }

    public FmsCapitalBudgetTasks fetchCBTaskData(FmsCapitalBudgetDetail capitalBudgetDetail, FmsSubsidiaryLedger fmsSubsidiaryLedger) {
        Query query = em.createNamedQuery("FmsCapitalBudgetTasks.findByBgtDtlAndSL");
        query.setParameter("cBDetailFk", capitalBudgetDetail);
        query.setParameter("slId", fmsSubsidiaryLedger);
        FmsCapitalBudgetTasks cbTask = new FmsCapitalBudgetTasks();
        if (query.getResultList().size() > 0) {
            cbTask = (FmsCapitalBudgetTasks) query.getResultList().get(0);
        }
        return cbTask;
    }

    public FmsCapitalBudgetTasks fetchCBTask(FmsCapitalBudgetTasks budgetTasks) {
        Query query = em.createNamedQuery("FmsCapitalBudgetTasks.findByCBTasksId");
        query.setParameter("cBTasksId", budgetTasks.getCBTasksId());
        FmsCapitalBudgetTasks capB = (FmsCapitalBudgetTasks) (query.getResultList().get(0));
        return capB;
    }
    //</editor-fold>
    
    
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    

   
}
