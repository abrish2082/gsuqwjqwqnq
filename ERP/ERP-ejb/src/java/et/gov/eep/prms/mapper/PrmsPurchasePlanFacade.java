/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsCategory;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.mms.entity.MmsNeedAssessment;
import et.gov.eep.mms.entity.MmsNeedAssessmentDtl;
import et.gov.eep.mms.entity.MmsNeedAssessmentService;
import et.gov.eep.prms.entity.PrmsPurchasePlan;
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
public class PrmsPurchasePlanFacade extends AbstractFacade<PrmsPurchasePlan> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsPurchasePlanFacade() {
        super(PrmsPurchasePlan.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    public ArrayList<PrmsPurchasePlan> searchPlnNo(PrmsPurchasePlan eepPurchasePlan) {

        Query query = em.createNamedQuery("PrmsPurchasePlan.findByPlanNo");
        query.setParameter("planNo", eepPurchasePlan.getPlanNo() + '%');
        try {
            ArrayList<PrmsPurchasePlan> eepPurchasePlanLst = new ArrayList(query.getResultList());

            return eepPurchasePlanLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsPurchasePlan searchEvent(PrmsPurchasePlan eepPurchasePlan) {
        Query query = em.createNamedQuery("PrmsPurchasePlan.findByPlanNos");
        query.setParameter("planNo", eepPurchasePlan.getPlanNo());
        try {
            PrmsPurchasePlan eepPurchasePlanList = (PrmsPurchasePlan) (query.getResultList().get(0));

            return eepPurchasePlanList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<MmsItemRegistration> getListOfCodeName(String toString) {
        ArrayList<MmsItemRegistration> deptJobs = null;
        try {
            Query query = em.createNamedQuery("MmsItemRegistration.findAll", MmsItemRegistration.class);

            deptJobs = (ArrayList<MmsItemRegistration>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deptJobs;
    }

    public List<MmsNeedAssessment> getBudgetYears() {
        List<MmsNeedAssessment> budgetYrsLst = null;
        try {
            Query query = em.createNamedQuery("MmsNeedAssessment.findAll", MmsNeedAssessment.class);
            budgetYrsLst = (List<MmsNeedAssessment>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return budgetYrsLst;
    }

    public ArrayList<MmsNeedAssessment> getBudgetList(MmsNeedAssessment string) {
        ArrayList<MmsNeedAssessment> budgetYrsLst = null;
        try {
            Query query = em.createNamedQuery("MmsNeedAssessment.findByBudgetYear", MmsNeedAssessment.class);
            query.setParameter("budgetYear", string.getBudgetYear());
            budgetYrsLst = (ArrayList<MmsNeedAssessment>) query.getResultList();

        } catch (Exception e) {
            e.printStackTrace();
        }
        return budgetYrsLst;
    }

    public ArrayList<PrmsPurchasePlan> getAPlanNo(PrmsPurchasePlan prmsPurchasePlan) {
        Query query = em.createNamedQuery("PrmsPurchasePlan.findByPlanNo");
        query.setParameter("planNo", prmsPurchasePlan.getPlanNo() + '%');
        query.setParameter("preparedBy", prmsPurchasePlan.getPreparedBy());
        try {
            ArrayList<PrmsPurchasePlan> purchasePlanLst = new ArrayList<>(query.getResultList());
            return purchasePlanLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<PrmsPurchasePlan> getAPlanNo_() {
        Query query = em.createNamedQuery("PrmsPurchasePlan.findByPlanNoStatus");
        try {
            ArrayList<PrmsPurchasePlan> purchasePlanLst = new ArrayList<>(query.getResultList());
            return purchasePlanLst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsPurchasePlan getLastPlnNo() {
        Query query = em.createNamedQuery("PrmsPurchasePlan.findByMaxNo");
        PrmsPurchasePlan purchasePlanNo = null;
        try {
            if (query.getResultList().size() > 0) {
                purchasePlanNo = (PrmsPurchasePlan) query.getResultList().get(0);
            }
            return purchasePlanNo;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsPurchasePlan getplanId(String id) {
        Query query = em.createNamedQuery("PrmsPurchasePlan.findById");
        query.setParameter("id", id);
        try {
            PrmsPurchasePlan idlst = (PrmsPurchasePlan) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public PrmsPurchasePlan findById(String id) {

        Query query = em.createNamedQuery("PrmsPurchasePlan.findById");
        query.setParameter("id", id);
        PrmsPurchasePlan result = (PrmsPurchasePlan) query.getResultList().get(0);

        return result;
    }

    public List<MmsCategory> findItemsCategoriesList() {
        Query query = em.createNamedQuery("MmsCategory.findAll");
        List<MmsCategory> itemCatNameList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            itemCatNameList = query.getResultList();
        }
        return itemCatNameList;
    }

    public List<PrmsPurchasePlan> getAnnualPlanNo(String prefix, String ethYear) {
        Query q = em.createNamedQuery("PrmsPurchasePlan.findByPlanNoLike");
        q.setParameter("planNo", prefix + "-" + '%' + "/" + ethYear);
        List<PrmsPurchasePlan> purchasePlanLists = new ArrayList<>();
        if (q.getResultList().size() > 0) {
            purchasePlanLists = q.getResultList();
        }
        return purchasePlanLists;
    }

    public List<MmsNeedAssessment> getPurchaseType(String purchaseType) {
        List<MmsNeedAssessment> needAssessments = null;
        try {
            Query query = em.createNamedQuery("MmsNeedAssessment.findByBudgetYear", MmsNeedAssessment.class);
            query.setParameter("budgetYears", purchaseType);
            needAssessments = (List<MmsNeedAssessment>) query.getResultList();
            return needAssessments;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return needAssessments;
    }
    // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    public List<MmsNeedAssessmentService> getServByYears(
            PrmsPurchasePlan selectYear, String purchaseType) {

        List<MmsNeedAssessmentService> budgetYearList = null;
        Query query = em.createNativeQuery("SELECT * FROM MMS_NEED_ASSESSMENT_SERVICE ns "
                + "inner join MMS_NEED_ASSESSMENT n "
                + "on n.ASSESSMETN_ID = ns.ASSESSMENT_ID where n.BUDGET_YEAR_ID ="
                + selectYear.getBudgetYearId().getLuBudgetYearId()
                + " and n.PURCHASE_TYPE like '" + purchaseType + "' ", MmsNeedAssessmentService.class);
        budgetYearList = (List<MmsNeedAssessmentService>) query.getResultList();

        return budgetYearList;
    }

    public List<PrmsPurchasePlan> getAPlanNo(int status, int UserId, PrmsPurchasePlan eepPurchasePlan) {
        List<PrmsPurchasePlan> annualPlanlist = new ArrayList();
        if (eepPurchasePlan.getColumnName() != null && !eepPurchasePlan.getColumnName().equals("")
                && eepPurchasePlan.getColumnValue() != null && !eepPurchasePlan.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_PURCHASE_PLAN\n"
                    + "where " + eepPurchasePlan.getColumnName().toLowerCase() + " = '" + eepPurchasePlan.getColumnValue() + "'"
                    + "and " + eepPurchasePlan.getPreparedBy() + "='" + eepPurchasePlan.getPreparedBy() + "'", PrmsPurchasePlan.class);
            try {
                if (query.getResultList().size() > 0) {
                    annualPlanlist = query.getResultList();
                }
                return annualPlanlist;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsPurchasePlan.findByPreparedBy");
            query.setParameter("preparedBy", eepPurchasePlan.getPreparedBy());
            annualPlanlist = query.getResultList();
            return annualPlanlist;
        }
    }

    public List<MmsNeedAssessmentDtl> getByYear(PrmsPurchasePlan selectYear, int needAssStatus) {

        List<MmsNeedAssessmentDtl> budgetYearList = null;
        Query query = em.createNativeQuery("SELECT * FROM MMS_NEED_ASSESSMENT_DTL ns "
                + "inner join MMS_NEED_ASSESSMENT n on n.ASSESSMETN_ID = "
                + "ns.ASSESSMET_ID where n.BUDGET_YEAR_ID ="
                + selectYear.getBudgetYearId().getLuBudgetYearId() + "and n.PURCHASE_TYPE='"
                + selectYear.getPurchaseType() + "' " + " and ns.STATUS <> " + needAssStatus, MmsNeedAssessmentDtl.class);
        budgetYearList = (List<MmsNeedAssessmentDtl>) query.getResultList();

        return budgetYearList;
    }
    // </editor-fold>

    public List<PrmsPurchasePlan> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_PURCHASE_PLAN') \n"
                + "and column_name not in ('ID','NEED_ASSMNT_ID','ANNUAL_PLAN_DESC','REMARK','STATUS')");
        try {
            List<PrmsPurchasePlan> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
