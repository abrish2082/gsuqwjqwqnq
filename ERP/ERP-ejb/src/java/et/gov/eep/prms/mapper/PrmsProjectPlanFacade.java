/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.pms.entity.PmsCreateProjects;
import et.gov.eep.prms.entity.PrmsProjectPlan;
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
public class PrmsProjectPlanFacade extends AbstractFacade<PrmsProjectPlan> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PrmsProjectPlanFacade() {
        super(PrmsProjectPlan.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named(Static) Queries">
    /**
     * *************************************************************************
     *
     * @param prmsProjectPlan
     * @return
     * ************************************************************************
     */
    public List<PrmsProjectPlan> getProjectPlanNo(
            PrmsProjectPlan prmsProjectPlan) {
        List<PrmsProjectPlan> prmsProjectPlanLst = new ArrayList();
        if (prmsProjectPlan.getColumnName() != null && !prmsProjectPlan.getColumnName().equals("")
                && prmsProjectPlan.getColumnValue() != null && !prmsProjectPlan.getColumnValue().equals("")) {

            Query query = em.createNativeQuery("SELECT * FROM PRMS_PROJECT_PLAN\n"
                    + "where " + prmsProjectPlan.getColumnName().toLowerCase() + " = '" + prmsProjectPlan.getColumnValue() + "'"
                    + "and " + prmsProjectPlan.getPreparedBy() + "='" + prmsProjectPlan.getPreparedBy() + "'", PrmsProjectPlan.class);
            try {
                if (query.getResultList().size() > 0) {
                    prmsProjectPlanLst = query.getResultList();
                }
                return prmsProjectPlanLst;
            } catch (NumberFormatException nfe) {
                nfe.getMessage();
                return null;
            }
        } else {
            Query query = em.createNamedQuery("PrmsProjectPlan.findByPreparedBy");
            query.setParameter("preparedBy", prmsProjectPlan.getPreparedBy());
            prmsProjectPlanLst = query.getResultList();
            return prmsProjectPlanLst;
        }
    }

    /**
     * *************************************************************************
     *
     * @param id
     * @return
     * ************************************************************************
     */
    public PrmsProjectPlan getProjectPlanId(String id) {

        Query query = em.createNamedQuery("PrmsProjectPlan.findById");
        query.setParameter("id", id);

        try {
            PrmsProjectPlan idlst = (PrmsProjectPlan) query.getResultList().get(0);
            return idlst;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @return
     * ***********************************************************************
     */
    public PrmsProjectPlan getProjectPlanNo() {

        Query query = em.createNamedQuery("PrmsProjectPlan.findAutoGenerat");

        try {
            PrmsProjectPlan projectPlanObject = new PrmsProjectPlan();
            if (query.getResultList().size() > 0) {
                projectPlanObject = (PrmsProjectPlan) query.getResultList().get(0);
            }

            return projectPlanObject;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public ArrayList<PrmsProjectPlan> getProjectPlanNo_() {

        Query query = em.createNamedQuery("PrmsProjectPlan.searchByProjectPlanNoStatus");

        try {
            ArrayList<PrmsProjectPlan> prmsProjectPlanList
                    = new ArrayList<>(query.getResultList());
            System.out.println("--plan no --no-" + prmsProjectPlanList.size());

            return prmsProjectPlanList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     * *************************************************************************
     *
     * @return
     * ************************************************************************
     */
    public List<PmsCreateProjects> getProject() {
        Query query = em.createNamedQuery("PmsCreateProjects.findAll");//"SELECT * FROM PMS_CREATE_PROJECTS", PmsCreateProjects.class);

        try {
            List<PmsCreateProjects> projectList = new ArrayList();
            if (query.getResultList().size() > 0) {
                projectList = query.getResultList();
            }
            System.err.println("KOKO Size testing :--" + projectList.size());
            return projectList;

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param prefix
     * @param eYear
     * @return prmsProjectPlansList
     */
    public List<PrmsProjectPlan> getGoodsOrServiceOrWorkProjectSeqNo(String prefix, String eYear) {
        Query query = em.createNamedQuery("PrmsProjectPlan.findByProjectPlanNoLike");
        query.setParameter("projectPlanNo", prefix + "-" + "%" + "/" + eYear);
        List<PrmsProjectPlan> prmsProjectPlansList = new ArrayList<>();
        if (query.getResultList().size() > 0) {
            prmsProjectPlansList = query.getResultList();
        }
        return prmsProjectPlansList;
    }
     // </editor-fold>

    // <editor-fold defaultstate="collapsed" desc="Native Queries">
    /**
     * *************************************************************************
     *
     * @param status
     * @param UserId
     * @return
     * ************************************************************************
     */
    public ArrayList<PrmsProjectPlan> getProjectPlanNo(int status, int UserId) {

        Query query = em.createNativeQuery("select * from PRMS_PROJECT_PLAN pp INNER JOIN WF_PRMS_PROCESSED wf on pp.id=wf.PROJECT_PROCUREMENT_PLAN_ID "
                + "where pp.status='" + status + "' " + "and wf.PROCESSED_BY='" + UserId + "' ", PrmsProjectPlan.class);
        ArrayList<PrmsProjectPlan> projectPlanLst = new ArrayList<>(query.getResultList());

        return projectPlanLst;
    }
    // </editor-fold>

    public List<PrmsProjectPlan> getParamNameList() {
        Query query = em.createNativeQuery("SELECT column_name FROM user_tab_columns \n"
                + "where table_name = UPPER('PRMS_PROJECT_PLAN') \n"
                + "and column_name not in ('ID','FULL_PROCUREMENT_DESCRIPTION','STATUS')");
        try {
            List<PrmsProjectPlan> columnNameList = new ArrayList<>();
            columnNameList = query.getResultList();
            return columnNameList;
        } catch (Exception e) {
            e.getMessage();
            return null;
        }
    }
}
