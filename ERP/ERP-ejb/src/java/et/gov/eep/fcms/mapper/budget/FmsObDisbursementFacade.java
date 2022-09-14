/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.budget;
//<editor-fold defaultstate="collapsed" desc="import">
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.budget.FmsObDisbursement;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
    //</editor-fold>

@Stateless
public class FmsObDisbursementFacade extends AbstractFacade<FmsObDisbursement> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;
//<editor-fold defaultstate="collapsed" desc="other methods ">
     @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsObDisbursementFacade() {
        super(FmsObDisbursement.class);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="NamedQuery ">
     public ArrayList<FmsObDisbursement> fetchDisbursedOB(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {
        Query query = em.createNamedQuery("FmsObDisbursement.findByOperatingBudgetId");
        query.setParameter("oBDetailFk", fmsOperatingBudgetDetail);
        ArrayList<FmsObDisbursement> obDisbursedtList = new ArrayList(query.getResultList());
        return obDisbursedtList;
    }

    public FmsObDisbursement fetchOBDisbByTaskId(FmsOperatingBudgetTasks fmsOperatingBudgetTasks) {
        Query query = em.createNamedQuery("FmsObDisbursement.findByTaskId");
        query.setParameter("obTaskId", fmsOperatingBudgetTasks);
        FmsObDisbursement obDisbs = null;
        if (query.getResultList().size() > 0) {
            obDisbs = (FmsObDisbursement) query.getResultList().get(0);
        }
        return obDisbs;
    }
    
    public FmsObDisbursement fetchOBDisbByID(FmsObDisbursement fmsObDisbursement) {
        Query query = em.createNamedQuery("FmsObDisbursement.findByDisbursementId");
        query.setParameter("disbursementId", fmsObDisbursement.getDisbursementId());
        FmsObDisbursement obDisbs = null;
        if (query.getResultList().size() > 0) {
            obDisbs = (FmsObDisbursement) query.getResultList().get(0);
        }
        return obDisbs;
    }
    //</editor-fold>
    
    
   

   
}
