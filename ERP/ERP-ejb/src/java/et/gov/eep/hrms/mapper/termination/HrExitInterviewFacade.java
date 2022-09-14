/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.termination;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.termination.HrClearance;
import et.gov.eep.hrms.entity.termination.HrExitInterview;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Ob
 */
@Stateless
public class HrExitInterviewFacade extends AbstractFacade<HrExitInterview> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrExitInterviewFacade() {
        super(HrExitInterview.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">

    public ArrayList<HrTerminationRequests> findAllList() {
        Query query = em.createNamedQuery("HrClearance.findAllDittinictName");
        try {
            ArrayList<HrTerminationRequests> request = new ArrayList<HrTerminationRequests>(query.getResultList());
            return request;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public HrTerminationRequests findById(HrTerminationRequests hrExitInterview) {
        Query query = em.createNamedQuery("HrTerminationRequests.findById");
        query.setParameter("id", hrExitInterview.getId());
        try {
            return (HrTerminationRequests) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrClearance findByEmpId(String hrClearance) {
        try {
            Query query = em.createNamedQuery("HrClearance.findById", HrClearance.class);
            query.setParameter("id", hrClearance);
            return (HrClearance) query.getResultList().get(0);
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<HrExitInterview> findAll() {
        Query query = em.createNamedQuery("HrExitInterview.findAll");
        try {
            ArrayList<HrExitInterview> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrExitInterview> findByEmpId(HrEmployees empId) {
        Query query = em.createNamedQuery("HrExitInterview.findByEmpId");
        query.setParameter("empId", empId.getEmpId().toUpperCase() + '%');
        try {
            ArrayList<HrExitInterview> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrExitInterview> findByName(HrEmployees empName) {
        Query query = em.createNamedQuery("HrExitInterview.findByName");
        query.setParameter("firstName", empName.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrExitInterview> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrExitInterview> findByEmpIdAndName(HrEmployees empId, HrEmployees empName) {
        Query query = em.createNamedQuery("HrExitInterview.findByEmpIdAndName");
        query.setParameter("empId", empId.getEmpId().toUpperCase() + '%');
        query.setParameter("firstName", empName.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrExitInterview> result = new ArrayList<>(query.getResultList());
            return result;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrClearance findByEmpFirstName(String toString) {
        try {
            Query query = em.createNamedQuery("HrClearance.findByFirstName");
            query.setParameter("firstName", toString);
            System.out.println("---HrClearance--" + (HrClearance) query.getSingleResult());
            return (HrClearance) query.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrExitInterview getSelectedExitInterview(int exitInterview) {
        Query query = em.createNamedQuery("HrExitInterview.findById");
        query.setParameter("id", exitInterview);
        try {
            HrExitInterview selectresult = (HrExitInterview) query.getResultList().get(0);
            return selectresult;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean checkDuplicate(HrExitInterview hrExitInterview) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrExitInterview.findToCheckDuplicate", HrExitInterview.class);
        query.setParameter("empId", hrExitInterview.getTerminationRequestId().getEmpId());
        query.setParameter("leaveReason", hrExitInterview.getLeaveReason());
        query.setParameter("otherReason", hrExitInterview.getOtherReason());
        query.setParameter("preparedOn", hrExitInterview.getPreparedOn());
        query.setParameter("remark", hrExitInterview.getRemark());
        try {
            if (query.getResultList().size() > 0) {
                duplicaton = true;
            } else {
                duplicaton = false;
            }
            return duplicaton;
        } catch (Exception ex) {
            return false;
        }
    }

    public ArrayList<HrTerminationRequests> findApprovedTerminationByTerminationType() {
        Query query = em.createNamedQuery("HrTerminationRequests.findByTerminationType");
        try {
            ArrayList<HrTerminationRequests> terminationRequest = new ArrayList(query.getResultList());
            return terminationRequest;
        } catch (Exception ex) {
            return null;
        }
    }
    //</editor-fold>

}
