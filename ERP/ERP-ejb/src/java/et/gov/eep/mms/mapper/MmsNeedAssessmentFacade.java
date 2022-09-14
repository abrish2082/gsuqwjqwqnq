/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.mapper;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.mms.entity.MmsNeedAssessment;

/**
 *
 * @author Minab
 */
@Stateless
public class MmsNeedAssessmentFacade extends AbstractFacade<MmsNeedAssessment> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public MmsNeedAssessmentFacade() {
        super(MmsNeedAssessment.class);
    }

    //<editor-fold defaultstate="collapsed" desc="NamedQuery">

    public MmsNeedAssessment getNeededAssessmentinfo(MmsNeedAssessment mmsNeedAssessment) {
        Query query = em.createNamedQuery("MmsNeedAssessment.findByBudyear_Depid_budjtype", MmsNeedAssessment.class);
        query.setParameter("budyear", mmsNeedAssessment.getBudgetYear());
        query.setParameter("budjType", mmsNeedAssessment.getBudgetType());
        query.setParameter("depId", mmsNeedAssessment.getDepartmentId());

        try {
            MmsNeedAssessment NeedAsssessInfo = (MmsNeedAssessment) query.getResultList().get(0);
            return NeedAsssessInfo;
        } catch (Exception ex) {

            return null;
        }
    }

    public ArrayList<MmsNeedAssessment> getNeeddedAssessentByStoreAndYear(MmsNeedAssessment mmsNeedAssessment) {
        Query query = em.createNamedQuery("MmsNeedAssessment.findByBudgetYearAndStore", MmsNeedAssessment.class);

        query.setParameter("budgetYear", mmsNeedAssessment.getBudgetYear());
        query.setParameter("storeId", mmsNeedAssessment.getStoreId());
        try {
            ArrayList<MmsNeedAssessment> NeeddedAssessentList = new ArrayList(query.getResultList());
            return NeeddedAssessentList;
        } catch (Exception ex) {

            return null;
        }
    }

    public ArrayList<MmsNeedAssessment> getNeeddedAssessentByStoreAndYearAndCheckedStatus(MmsNeedAssessment mmsNeedAssessment) {
        Query query = em.createNamedQuery("MmsNeedAssessment.findByBudgetYearAndStoreAndCheckedStatus", MmsNeedAssessment.class);
        System.out.println("=bugetyear=" + mmsNeedAssessment.getBudgetYearId().getLuBudgetYearId());
        System.out.println("=store Id=" + mmsNeedAssessment.getStoreId());
        System.out.println("=checkedStatus=" + mmsNeedAssessment.getCheckedStatus());
        System.out.println("=status=" + mmsNeedAssessment.getStatus());
        query.setParameter("budgetYear", mmsNeedAssessment.getBudgetYearId());
        query.setParameter("storeId", mmsNeedAssessment.getStoreId());
        query.setParameter("checkedStatus", mmsNeedAssessment.getCheckedStatus());
        query.setParameter("status", mmsNeedAssessment.getStatus());
        query.setParameter("purchaseType", mmsNeedAssessment.getPurchaseType());
        try {
            ArrayList<MmsNeedAssessment> NeeddedAssessentList = new ArrayList(query.getResultList());
            return NeeddedAssessentList;
        } catch (Exception ex) {

            return null;
        }
    }

    public ArrayList<MmsNeedAssessment> findByBudgetYearAndCheckedStatus(MmsNeedAssessment mmsNeedAssessment) {
        Query query = em.createNamedQuery("MmsNeedAssessment.findByBudgetYearAndCheckedStatus", MmsNeedAssessment.class);
        System.out.println("=bugetyear=" + mmsNeedAssessment.getBudgetYearId().getLuBudgetYearId());

        System.out.println("=checkedStatus=" + mmsNeedAssessment.getCheckedStatus());
        System.out.println("=status=" + mmsNeedAssessment.getStatus());
        System.out.println("=purchaseType=" + mmsNeedAssessment.getPurchaseType());
        query.setParameter("budgetYear", mmsNeedAssessment.getBudgetYearId());

        query.setParameter("checkedStatus", mmsNeedAssessment.getCheckedStatus());
        query.setParameter("status", mmsNeedAssessment.getStatus());
        query.setParameter("purchaseType", mmsNeedAssessment.getPurchaseType());
        try {
            ArrayList<MmsNeedAssessment> NeeddedAssessentList = new ArrayList(query.getResultList());
            return NeeddedAssessentList;
        } catch (Exception ex) {

            return null;
        }
    }

    public List<MmsNeedAssessment> searchByParameterBudgetTypeAndDepartmentIDAndProcessedBy(MmsNeedAssessment needAssessmentEntity) {

        Query query = em.createNamedQuery("MmsNeedAssessment.findByParameterBudgetTypeAndDepartmentAndProcessedBy", MmsNeedAssessment.class);
        query.setParameter("departmentId", needAssessmentEntity.getDepartmentId());
        query.setParameter("budgetType", needAssessmentEntity.getBudgetType() + '%');
        query.setParameter("processedBy", needAssessmentEntity.getProcessedBy());

        try {
            ArrayList<MmsNeedAssessment> needAssesmentList = new ArrayList(query.getResultList());
            return needAssesmentList;
        } catch (Exception ex) {
            return null;
        }

    }

    ////      public List<MmsNeedAssessment> searchSRByStoreIdAndProcessedBy(MmsNeedAssessment needAssessmentEntity)  {
////      
////        ArrayList<MmsNeedAssessment> result = null;
////        
////        
////        
////        try {
////            Query query1 = em.createNativeQuery("SELECT sr.* , wf.*  " 
////                    + "FROM mms_storereq sr          "
////                    + "INNER JOIN wf_mms_processed wf "
////                    + "ON sr.store_req_id =wf.store_req_id "
////                    + "WHERE sr.request_store ='" + needAssessmentEntity.getAssessmetnId() + "' "
////                    + "AND wf.processed_by = '" + needAssessmentEntity.getProcessedBy() + "'"
////                    + "order BY wf.processed_id DESC",
////                    MmsNeedAssessment.class);
////            return (List<MmsNeedAssessment>) query1.getResultList();
////        } catch (Exception ex) {
////            return null;
////        }  
    public List<MmsNeedAssessment> findNsListByWfStatus(int status) {
        Query query = em.createNamedQuery("MmsNeedAssessment.findNsListByWfStatus", MmsNeedAssessment.class);
        query.setParameter("status", status);
        try {
            ArrayList<MmsNeedAssessment> listofsr = new ArrayList(query.getResultList());
            return listofsr;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsNeedAssessment> findNsListForCheckerByWfStatus(int preparerStatus, int approverRejectStatus) {
        Query query = em.createNamedQuery("MmsNeedAssessment.findNsListForCheckerByWfStatus", MmsNeedAssessment.class);
        query.setParameter("prepared", preparerStatus);
        query.setParameter("approverReject", approverRejectStatus);

        try {
            ArrayList<MmsNeedAssessment> listofsr = new ArrayList(query.getResultList());
            return listofsr;
        } catch (Exception e) {
            return null;
        }
    }

    public List<MmsNeedAssessment> searchByParameterDepatementIdAndProcessedBy(MmsNeedAssessment needAssessmentEntity) {
        Query query = em.createNamedQuery("MmsNeedAssessment.findByParameterDepartmentIdAndProcessedBy", MmsNeedAssessment.class);
        query.setParameter("departmentId", needAssessmentEntity.getDepartmentId());
        query.setParameter("processedBy", needAssessmentEntity.getProcessedBy());

        try {
            ArrayList<MmsNeedAssessment> needAssesmentList = new ArrayList(query.getResultList());
            return needAssesmentList;
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="NativeQuery">

    public List<MmsNeedAssessment> searchByAllParameterAndProcessedBy(MmsNeedAssessment needAssessmentEntity) {

        try {
            Query query1 = em.createNativeQuery("SELECT * , wf.* "
                    + "FROM mms_need_assessment na         "
                    + "INNER JOIN fms_lu_budget_year byear "
                    + "INNER JOIN wf_mms_processed wf "
                    + "ON na.budget_year_id= byear.lu_budget_year_id "
                    + "WHERE na.DEPARTMENT_ID='" + needAssessmentEntity.getDepartmentId().getDepId() + "' "
                    + "AND wf.processed_by = '" + needAssessmentEntity.getProcessedBy() + "%' "
                    + "AND na.BUDGET_TYPE LIKE '" + needAssessmentEntity.getBudgetType() + "%' "
                    + "AND byear.budget_year Like '" + needAssessmentEntity.getBudgetYear() + "%' ",
                    MmsNeedAssessment.class);

            return (List<MmsNeedAssessment>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsNeedAssessment> searchByAllParameter(MmsNeedAssessment needAssessmentEntity) {

        try {
            Query query1 = em.createNativeQuery("SELECT *, wf*  "
                    + "FROM mms_need_assessment na         "
                    + "INNER JOIN fms_lu_budget_year byear "
                    + "INNER JOIN wf_mms_processed wf "
                    + "ON na.budget_year_id= byear.lu_budget_year_id "
                    + "WHERE na.DEPARTMENT_ID='" + needAssessmentEntity.getDepartmentId().getDepId() + "' "
                    + "AND wf.processed_by = '" + needAssessmentEntity.getProcessedBy() + "%' "
                    + "AND byear.budget_year Like '" + needAssessmentEntity.getBudgetYear() + "%' ",
                    MmsNeedAssessment.class);

            return (List<MmsNeedAssessment>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<MmsNeedAssessment> searchByParameterBudgetYearAndDepartmentIDAndProcessedBy(MmsNeedAssessment mmsNeedAssessment) {

        try {
            Query query1 = em.createNativeQuery("SELECT *  "
                    + "FROM mms_need_assessment na         "
                    + "INNER JOIN fms_lu_budget_year byear "
                    + "ON na.budget_year_id= byear.lu_budget_year_id "
                    + "WHERE na.DEPARTMENT_ID='" + mmsNeedAssessment.getDepartmentId().getDepId() + "' "
                    + "AND byear.budget_year Like '" + mmsNeedAssessment.getBudgetYear() + "%' ",
                    MmsNeedAssessment.class);

            return (List<MmsNeedAssessment>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
//</editor-fold>

    }

    public List<String> getMmsNeedColumnNameList() {
        Query query = em.createNativeQuery("SELECT column_name\n"
                + "   FROM USER_TAB_COLUMNS\n"
                + "   WHERE table_name = UPPER('MMS_NEED_ASSESSMENT')\n"
                + "   and COLUMN_NAME NOT IN ('ASSESSMETN_ID','STORE_ID','BUDGET_YEAR_ID','DEPARTMENT_ID')\n"
                + "   ORDER BY column_name ASC");
        try {
            List<String> colNameLists = new ArrayList<>();
            colNameLists = query.getResultList();
            return colNameLists;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
     public List<MmsNeedAssessment> getNeedListsByParameter(ColumnNameResolver columnNameResolver, MmsNeedAssessment mmsNeedAssessment, String columnValue) {
        System.out.println("columnNameResolver.getCol_Name_FromTable()==" + columnNameResolver.getCol_Name_FromTable());
        System.out.println("Col_Value==" + columnValue);
        List<MmsNeedAssessment> colValueLists = new ArrayList<>();
        if (mmsNeedAssessment.getColumnName() != null && !mmsNeedAssessment.getColumnName().equals("")
                && mmsNeedAssessment.getColumnValue() != null && !mmsNeedAssessment.getColumnValue().equals("")) {
            System.out.println("when if"); 

            Query query = em.createNativeQuery("SELECT * FROM MMS_NEED_ASSESSMENT\n"                    
                     + "   WHERE " + mmsNeedAssessment.getColumnName().toLowerCase() + "='" + mmsNeedAssessment.getColumnValue() + "'"
                    + "and " + mmsNeedAssessment.getProcessedBy() + "='" + mmsNeedAssessment.getProcessedBy() + "'", MmsNeedAssessment.class);
            try {
                if (query.getResultList().size() > 0) {
                    colValueLists = query.getResultList();
                }
                return colValueLists;
            } catch (Exception e) {
                e.getMessage();
                return null;
            }
        } else {
            System.out.println("In else " + colValueLists.size());
            Query query = em.createNamedQuery("MmsNeedAssessment.findByProcessedBy");
            query.setParameter("processedBy", mmsNeedAssessment.getProcessedBy());
            colValueLists = query.getResultList();
            System.out.println("list of size " + colValueLists.size());
            return colValueLists;
        }

    }
    
    
}
