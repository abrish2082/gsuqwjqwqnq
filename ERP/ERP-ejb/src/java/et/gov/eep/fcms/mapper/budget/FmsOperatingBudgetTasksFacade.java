/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.mapper.budget;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsSubsidiaryLedger;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudget1;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetDetail;
import et.gov.eep.fcms.entity.budget.FmsOperatingBudgetTasks;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author PO
 */
@Stateless
public class FmsOperatingBudgetTasksFacade extends AbstractFacade<FmsOperatingBudgetTasks> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public FmsOperatingBudgetTasksFacade() {
        super(FmsOperatingBudgetTasks.class);
    }

    public FmsOperatingBudgetTasks findByOBDtlAndSL(FmsOperatingBudgetDetail budgetDetail, FmsSubsidiaryLedger subsidiaryLedger) {
        Query query = em.createNamedQuery("FmsOperatingBudgetTasks.findByOBDtlAndSL");
        query.setParameter("oBDetailFk", budgetDetail);
        query.setParameter("slId", subsidiaryLedger);
        FmsOperatingBudgetTasks obTasks = null;
        if (query.getResultList().size() > 0) {
            obTasks = (FmsOperatingBudgetTasks) query.getResultList().get(0);
        }
        return obTasks;
    }
    
    public FmsOperatingBudgetTasks fetchOBTask(FmsOperatingBudgetTasks budgetTasks){
        Query query = em.createNamedQuery("FmsOperatingBudgetTasks.findByOBTaskId");
        query.setParameter("oBTaskId", budgetTasks.getOBTaskId());
        FmsOperatingBudgetTasks oprB = (FmsOperatingBudgetTasks) (query.getResultList().get(0));
        return oprB;
    }

    public List<FmsOperatingBudgetTasks> findByOBudgetReqCode(FmsOperatingBudget1 fmsOperatingBudget1) {
        Query query = em.createNamedQuery("FmsOperatingBudgetTasks.findByOBudgetReqCode");
        query.setParameter("requestCode", fmsOperatingBudget1.getRequestCode());
        ArrayList<FmsOperatingBudgetTasks> obTasks = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            obTasks = new ArrayList(query.getResultList());
        }
        return obTasks;
    }

    public List<FmsOperatingBudgetTasks> findByOBudgetDetailId(FmsOperatingBudgetDetail fmsOperatingBudgetDetail) {      
        Query query = em.createNamedQuery("FmsOperatingBudgetTasks.findByOBudgetDetailId");
        query.setParameter("oBDetailFk", fmsOperatingBudgetDetail);
        ArrayList<FmsOperatingBudgetTasks> obTasks = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            obTasks = new ArrayList(query.getResultList());
        }
        return obTasks;
    }
}
