/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.prms.entity.PrmsActionPlan;
import et.gov.eep.prms.entity.PrmsLuTaskDescription;
import et.gov.eep.prms.entity.PrmsProjectPlan;
import et.gov.eep.prms.entity.PrmsPurchasePlan;
import et.gov.eep.prms.entity.PrmsPurchasePlnDetail;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author user
 */
@Stateless
public class PrmsActionPlanFacade extends AbstractFacade<PrmsActionPlan> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsActionPlanFacade() {
        super(PrmsActionPlan.class);
    }

    public ArrayList<PrmsActionPlan> getActionPlanNo(PrmsActionPlan prmsActionPlan) {

        Query query = em.createNamedQuery("PrmsActionPlan.findByActionPlanNum");
        query.setParameter("actionPlanNo", prmsActionPlan.getActionPlanNo() + '%');

        try {
            ArrayList<PrmsActionPlan> prmsActionPlanLst = new ArrayList<>(query.getResultList());

            return prmsActionPlanLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsLuTaskDescription> getTaskDesc() {
        Query query = em.createNamedQuery("PrmsLuTaskDescription.findAll");
        try {
            List<PrmsLuTaskDescription> taskDescriptions = new ArrayList<>(query.getResultList());
            return taskDescriptions;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsActionPlan getLastPortNo() {

        Query query = em.createNamedQuery("PrmsActionPlan.findByAutoGenetNo");

        try {
            PrmsActionPlan directPurcObj = (PrmsActionPlan) query.getResultList().get(0);

            return directPurcObj;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsActionPlan getSelectedRequest(String id) {
        Query query = em.createNamedQuery("PrmsActionPlan.findById");
        query.setParameter("id", id);
        try {
            PrmsActionPlan idlst = (PrmsActionPlan) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsPurchasePlnDetail getPlanNo(String planNo) {
        System.out.println("------in plan no----" + planNo);
        PrmsPurchasePlnDetail planList = null;
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_PLAN.ID,\n"
                + "  PRMS_PURCHASE_PLN_DETAIL.ID AS ID1,\n"
                + "  PRMS_PURCHASE_PLN_DETAIL.BID_OPENING_DATE,\n"
                + "  PRMS_PURCHASE_PLN_DETAIL.DELIVERY_DATE\n"
                + "FROM PRMS_PURCHASE_PLAN\n"
                + "INNER JOIN PRMS_PURCHASE_PLN_DETAIL\n"
                + "ON PRMS_PURCHASE_PLAN.ID = PRMS_PURCHASE_PLN_DETAIL.PURCHSE_PLAN_ID\n"
                + "WHERE PRMS_PURCHASE_PLAN.PLAN_NO = '" + planNo + "'", PrmsPurchasePlnDetail.class);

        try {
            planList = (PrmsPurchasePlnDetail)query.getResultList().get(0);
            System.out.println("------in plan no----" + planList.getId());

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return planList;
    }

    public List<PrmsProjectPlan> getProjectPlanNo() {
        Query query = em.createNamedQuery("PrmsProjectPlan.findAll");
        try {
            List<PrmsProjectPlan> projectNo = new ArrayList<>(query.getResultList());
            return projectNo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<PrmsPurchasePlan> getPlanNum() {
        List<PrmsPurchasePlan> purchasePlnDetaillst = null;
        Query query = em.createNativeQuery("SELECT PRMS_PURCHASE_PLAN.PLAN_NO,\n"
                + "  PRMS_PURCHASE_PLAN.ID,\n"
                + "  PRMS_PURCHASE_PLN_DETAIL.ID AS ID1\n"
                + "FROM PRMS_PURCHASE_PLAN\n"
                + "INNER JOIN PRMS_PURCHASE_PLN_DETAIL\n"
                + "ON PRMS_PURCHASE_PLAN.ID = PRMS_PURCHASE_PLN_DETAIL.PURCHSE_PLAN_ID", PrmsPurchasePlan.class);
        try {
            purchasePlnDetaillst = new ArrayList<>(query.getResultList());
            System.out.println("wwwwwwwww"+purchasePlnDetaillst.size());
        } catch (Exception ex) {
            ex.printStackTrace();

        }
        return purchasePlnDetaillst;
    }
}
