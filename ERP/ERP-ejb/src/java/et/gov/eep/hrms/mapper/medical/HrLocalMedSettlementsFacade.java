/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.medical;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.medical.HrLocalMedSettlements;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author INSA
 */
@Stateless
public class HrLocalMedSettlementsFacade extends AbstractFacade<HrLocalMedSettlements> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLocalMedSettlementsFacade() {
        super(HrLocalMedSettlements.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    @Override
    public ArrayList<HrLocalMedSettlements> findAll() {
        Query query = em.createNamedQuery("HrLocalMedSettlements.findAll");
        try {
            ArrayList<HrLocalMedSettlements> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrLocalMedSettlements> findByName(HrEmployees empName) {
        Query query = em.createNamedQuery("HrLocalMedSettlements.findByName");
        query.setParameter("firstName", empName.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrLocalMedSettlements> request = new ArrayList<>(query.getResultList());
            return request;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrLocalMedSettlements getSelectedRequest(int request) {
        Query query = em.createNamedQuery("HrLocalMedSettlements.findById");
        query.setParameter("id", request);
        try {
            HrLocalMedSettlements selectedRequest = (HrLocalMedSettlements) query.getResultList().get(0);
            return selectedRequest;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrLocalMedSettlements fetchSettlement(String refNo) {
        Query query = em.createNamedQuery("HrLocalMedSettlements.findByReferenceNo");
        query.setParameter("referenceNo", refNo);
        HrLocalMedSettlements settlement = (HrLocalMedSettlements) query.getResultList().get(0);
        return settlement;
    }

    public List<HrLocalMedSettlements> findPreparedList() {
        Query query = em.createNamedQuery("HrLocalMedSettlements.findPreparedList");
        try {
            List<HrLocalMedSettlements> preparedList = query.getResultList();
            return preparedList;
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">

    public List<HrLocalMedSettlements> loadMedicaCashRefund(int status) {
        String statusQuery = " WHERE STATUS='" + status + "' ";
        if (status == 0) {
            statusQuery = " WHERE (STATUS='0')";
        } else if (status == 1) {
            statusQuery = " WHERE (STATUS='1' OR STATUS='3')";
        } else if (status == 2) {
            statusQuery = " WHERE (STATUS='2' OR STATUS='4')";
        }
        Query query = em.createNativeQuery("SELECT * FROM HR_LOCAL_MED_SETTLEMENTS "
                + statusQuery
                + "ORDER BY REQUEST_DATE DESC", HrLocalMedSettlements.class);
        try {
            return (List<HrLocalMedSettlements>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrLocalMedSettlements> loadMedSettlementList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_LOCAL_MED_SETTLEMENTS rq"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "' OR"
                    + " rq.status='" + status.getStatus3() + "') AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrLocalMedSettlements.class);
            return (List<HrLocalMedSettlements>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrLocalMedSettlements> loadSettlementList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_LOCAL_MED_SETTLEMENTS rq"
                    + " WHERE (rq.status='" + status.getStatus1() + "' OR"
                    + " rq.status='" + status.getStatus2() + "') AND\n"
                    + "rq.PREPARED_BY  ='" + userId + "'", HrLocalMedSettlements.class);
            return (List<HrLocalMedSettlements>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrLocalMedSettlements> loadPrepaMedSettlementList(HrLocalMedSettlements hrLocalMedSettlements) {
        Query query = em.createNativeQuery("SELECT * from HR_LOCAL_MED_SETTLEMENTS WHERE\n"
                + "HR_LOCAL_MED_SETTLEMENTS.STATUS = '0' OR HR_LOCAL_MED_SETTLEMENTS.STATUS = '2' OR HR_LOCAL_MED_SETTLEMENTS.STATUS = '4'\n"
                + "AND HR_LOCAL_MED_SETTLEMENTS.PREPARED_BY =' " + hrLocalMedSettlements.getPreparedBy() + " ' ", HrLocalMedSettlements.class);
        ArrayList<HrLocalMedSettlements> medSettlementList = new ArrayList(query.getResultList());
        return medSettlementList;
    }

    public List<HrLocalMedSettlements> loadReqMedSettList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_LOCAL_MED_SETTLEMENTS medSettlement"
                    + " WHERE medSettlement.STATUS ='" + status.getStatus1() + "' AND"
                    + " medSettlement.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY medSettlement.REQUEST_DATE DESC", HrLocalMedSettlements.class);
            return (List<HrLocalMedSettlements>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrLocalMedSettlements> loadApproveMedSettList(Status status, int userId) {
        try {
            Query query = em.createNativeQuery("SELECT * FROM HR_LOCAL_MED_SETTLEMENTS medSettlement"
                    + " WHERE (medSettlement.status ='" + status.getStatus1() + "' OR"
                    + " medSettlement.status ='" + status.getStatus2() + "') AND"
                    + " medSettlement.PREPARED_BY ='" + userId + "'"
                    + " ORDER BY medSettlement.REQUEST_DATE DESC", HrLocalMedSettlements.class);
            return (List<HrLocalMedSettlements>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

}
