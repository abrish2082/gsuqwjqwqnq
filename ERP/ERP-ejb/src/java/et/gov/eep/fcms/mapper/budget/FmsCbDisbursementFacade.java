/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.budget;
//<editor-fold defaultstate="collapsed" desc="import ">
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.fcms.entity.admin.FmsCostCenter;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsCapitalBudgetTasks;
import et.gov.eep.fcms.entity.budget.FmsCbDisbursement;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
    //</editor-fold>


@Stateless
public class FmsCbDisbursementFacade extends AbstractFacade<FmsCbDisbursement> {
//<editor-fold defaultstate="collapsed" desc="other methods ">
     @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsCbDisbursementFacade() {
        super(FmsCbDisbursement.class);
    }
    //</editor-fold>
    
    //<editor-fold defaultstate="collapsed" desc="NamedQuery ">
      public ArrayList<FmsCbDisbursement> fetchDisbursedBudget(FmsLuBudgetYear budgetYear, FmsCostCenter fmsCostCenter, PmsWorkAuthorization authorization) {
        Query query = em.createNamedQuery("FmsCbDisbursement.fetchDisbursedBudget");
        query.setParameter("budgetYear", budgetYear.getBudgetYear());
        query.setParameter("costCenterId", fmsCostCenter.getCostCenterId());
        query.setParameter("jobNo", authorization.getJobNo());
        ArrayList<FmsCbDisbursement> disbursedList = new ArrayList(query.getResultList());
        return disbursedList;
    }

    public ArrayList<FmsCbDisbursement> fetchDisbursedCB(FmsCapitalBudgetDetail capitalBudgetDetail) {
        Query query = em.createNamedQuery("FmsCbDisbursement.findByBgtDtl");
        query.setParameter("cBDetailFk", capitalBudgetDetail);
        ArrayList<FmsCbDisbursement> cbDisbursedtList = new ArrayList(query.getResultList());
        return cbDisbursedtList;
    }

    public FmsCbDisbursement fetchCBDisbByTaskId(FmsCapitalBudgetTasks capitalBudgetTasks) {
        Query query = em.createNamedQuery("FmsCbDisbursement.findByTaskId");
        query.setParameter("cbTaskId", capitalBudgetTasks);
        FmsCbDisbursement cbDisbs = null;
        if (query.getResultList().size() > 0) {
            cbDisbs = (FmsCbDisbursement) query.getResultList().get(0);
        }
        return cbDisbs;
    }

    public FmsCbDisbursement fetchCBDisbByID(FmsCbDisbursement cbDisbursement) {
        Query query = em.createNamedQuery("FmsCbDisbursement.findByDisbursementId");
        query.setParameter("disbursementId", cbDisbursement.getDisbursementId());
        FmsCbDisbursement cbDisbs = null;
        if (query.getResultList().size() > 0) {
            cbDisbs = (FmsCbDisbursement) query.getResultList().get(0);
        }
        return cbDisbs;
    }
    //</editor-fold>
    
    
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

   

  
}
