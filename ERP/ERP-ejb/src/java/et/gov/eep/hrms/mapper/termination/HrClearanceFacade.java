/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.termination;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.termination.HrClearance;
import et.gov.eep.hrms.entity.termination.HrClearanceSetting;
import et.gov.eep.hrms.entity.termination.HrRetirementRequest;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import et.gov.eep.hrms.entity.transfer.HrTransferRequests;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ob
 */
@Stateless
public class HrClearanceFacade extends AbstractFacade<HrClearance> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrClearanceFacade() {
        super(HrClearance.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">

    public ArrayList<HrTerminationRequests> getTermination(int status) {
        Query query = em.createNamedQuery("HrClearance.findByTerminationStatus", HrTerminationRequests.class);
        try {
            ArrayList<HrTerminationRequests> termination = new ArrayList(query.getResultList());
            return termination;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrClearance> findTerminationeList(int terminationId, int empl, String clearanceType) {
        Query query = em.createNamedQuery("HrClearance.findByTerminationEmp");
        query.setParameter("terId", terminationId);
        query.setParameter("emp", empl);
        query.setParameter("clearanceType", clearanceType);
        try {
            ArrayList<HrClearance> clearanceList = new ArrayList(query.getResultList());
            return clearanceList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrClearance> findRetirementList(int retirementId, int empl, String clearanceType) {
        Query query = em.createNamedQuery("HrClearance.findByRetirementEmp");
        query.setParameter("retId", retirementId);
        query.setParameter("emp", empl);
        query.setParameter("clearanceType", clearanceType);
        try {
            ArrayList<HrClearance> clearanceList = new ArrayList(query.getResultList());
            return clearanceList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrClearance> findTransferList(int transferId, int empl, String clearanceType) {
        Query query = em.createNamedQuery("HrClearance.findByTransferEmp");
        query.setParameter("transferId", transferId);
        query.setParameter("emp", empl);
        query.setParameter("clearanceType", clearanceType);
        try {
            ArrayList<HrClearance> clearanceList = new ArrayList(query.getResultList());
            return clearanceList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrClearanceSetting> findClearanceSetting() {
        Query query = em.createNamedQuery("HrClearance.findByPriority");
        try {
            ArrayList<HrClearanceSetting> clearanceSetting = new ArrayList(query.getResultList());
            return clearanceSetting;
        } catch (Exception ex) {
            return null;
        }
    }
//</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">

    public List<HrTerminationRequests> findApprovedTermination() {
        Query query = em.createNativeQuery("SELECT * FROM HR_TERMINATION_REQUESTS WHERE STATUS = 1"
                + "ORDER BY REQUEST_DATE DESC", HrTerminationRequests.class);
        try {
            return (List<HrTerminationRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrRetirementRequest> findApprovedRetirement() {
        Query query = em.createNativeQuery("SELECT * FROM HR_RETIREMENT_REQUEST WHERE STATUS = 1"
                + "ORDER BY REQUEST_DATE DESC", HrRetirementRequest.class);
        try {
            return (List<HrRetirementRequest>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTransferRequests> findApprovedTransfer() {
        Query query = em.createNativeQuery("SELECT * FROM HR_TRANSFER_REQUESTS WHERE STATUS = 1"
                + "ORDER BY REQUEST_DATE DESC", HrTransferRequests.class);
        try {
            return (List<HrTransferRequests>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>
}
