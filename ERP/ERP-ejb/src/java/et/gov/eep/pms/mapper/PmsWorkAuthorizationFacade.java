/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.pms.mapper;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.fcms.entity.admin.FmsGeneralLedger;
import et.gov.eep.pms.entity.PmsWorkAuthorization;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Binyam
 */
@Stateless
public class PmsWorkAuthorizationFacade extends AbstractFacade<PmsWorkAuthorization> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public PmsWorkAuthorizationFacade() {
        super(PmsWorkAuthorization.class);
    }

    public PmsWorkAuthorization findJobId(PmsWorkAuthorization pmsWorkAuthorization) {
        Query query = em.createNamedQuery("PmsWorkAuthorization.findByJobNo");
        query.setParameter("jobNo", pmsWorkAuthorization.getJobNo());
        try {
            PmsWorkAuthorization jobList = (PmsWorkAuthorization) query.getResultList().get(0);
            return jobList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public PmsWorkAuthorization findByWorkAutId(PmsWorkAuthorization pmsWorkAuthorization) {
        Query query = em.createNamedQuery("PmsWorkAuthorization.findByWorkAuthoId");
        query.setParameter("workAuthoId", pmsWorkAuthorization.getWorkAuthoId());
        try {
            PmsWorkAuthorization jobList = (PmsWorkAuthorization) query.getResultList().get(0);
            return jobList;
        } catch (Exception ex) {
            throw ex;
        }
    }
    
    public List<PmsWorkAuthorization> findJobNoByGL(FmsGeneralLedger fmsGeneralLedger) {
        Query query = em.createNativeQuery("SELECT * FROM PMS_WORK_AUTHORIZATION PMS  where PMS.WORK_AUTHO_ID in "
                + "(select SL.JOBID from FMS_SUBSIDIARY_LEDGER SL where SL.GENERAL_LEDGER_ID = '" + fmsGeneralLedger.getGeneralLedgerId() + "')", PmsWorkAuthorization.class);
        try {
            ArrayList<PmsWorkAuthorization> jobList = new ArrayList<>(query.getResultList());
            return jobList;
        } catch (Exception ex) {
            throw ex;
        }
    }

    public List<PmsWorkAuthorization> findNotInLoan() {
        Query query = em.createNativeQuery("SELECT * FROM PMS_WORK_AUTHORIZATION where PMS_WORK_AUTHORIZATION.WORK_AUTHO_ID NOT IN (SELECT FMS_LOAN.JOB_NO from FMS_LOAN)", PmsWorkAuthorization.class);
        try {
            ArrayList<PmsWorkAuthorization> jobList = new ArrayList<>(query.getResultList());
            return jobList;
        } catch (Exception ex) {
            throw ex;
        }
    }

}
