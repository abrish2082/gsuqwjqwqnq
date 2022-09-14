/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrTdIspPayments;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudentPlacement;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author insa
 */
@Stateless
public class HrTdIspPaymentFacade extends AbstractFacade<HrTdIspPayments> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdIspPaymentFacade() {
        super(HrTdIspPayments.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public HrTdIspPayments getSelectedRequest(Integer request) {
        Query query = em.createNamedQuery("HrTdIspPayments.findById");
        query.setParameter("id", request);
        try {
            HrTdIspPayments selectrequest = (HrTdIspPayments) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdIspStudentDetails> findBystatus() {
        Query query = em.createNamedQuery("HrTdIspStudentDetails.findByStatusAll");
        try {
            ArrayList<HrTdIspStudentDetails> studList = new ArrayList(query.getResultList());
            return studList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrTdIspStudentDetails> findBystatusp() {
        Query query = em.createNamedQuery("HrTdIspStudentDetails.findByStatusAllplaced");
        try {
            ArrayList<HrTdIspStudentDetails> studList = new ArrayList(query.getResultList());
            return studList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrTdIspStudentDetails findByidObj(int hrTdIspStudentDetails) {
        Query query = em.createNamedQuery("HrTdIspStudentDetails.findById");
        query.setParameter("id", hrTdIspStudentDetails);
        try {
            HrTdIspStudentDetails stud = (HrTdIspStudentDetails) query.getResultList().get(0);
            return stud;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<HrTdIspStudents> findByYear(Integer years) {
        Query query = em.createNamedQuery("HrTdIspStudents.findByYear", HrTdIspStudents.class);
        query.setParameter("year", years);
        try {
            List<HrTdIspStudents> internshipPayment = (List<HrTdIspStudents>) query.getResultList();
            return internshipPayment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrTdIspStudents> findByYear(HrTdIspStudents hrTdIspStudents) {
        Query query = em.createNamedQuery("HrTdIspStudents.findById");
        query.setParameter("id", hrTdIspStudents.getId());
        try {
            List< HrTdIspStudents> studId = (List<HrTdIspStudents>) query.getResultList().get(0);
            return studId;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdIspStudents> findyears() {
        Query query = em.createNamedQuery("HrTdIspStudents.findAll", HrTdIspStudents.class);
        try {
            List<HrTdIspStudents> internshipPayment = (List<HrTdIspStudents>) query.getResultList();
            return internshipPayment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HrTdIspStudents findById(HrTdIspStudents hrTdIspStudents) {
        Query query = em.createNamedQuery("HrTdIspStudents.findById");
        query.setParameter("id", hrTdIspStudents.getId());
        try {
            return (HrTdIspStudents) query.getResultList().get(0);
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdIspStudentDetails> findAlls() {
        Query query = em.createNamedQuery("HrTdIspStudentDetails.findAll");
        try {
            List<HrTdIspStudentDetails> Intpayments = (List<HrTdIspStudentDetails>) query.getResultList();
            return Intpayments;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrTdIspPayments> findByLitter() {
        Query query = em.createNamedQuery("HrTdIspPayments.findAll", HrTdIspStudents.class);
        try {
            List<HrTdIspPayments> internshipPayment = (List<HrTdIspPayments>) query.getResultList();
            return internshipPayment;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrTdIspPayments> findByLetter(String refereletter) {
        Query query = em.createNamedQuery("HrTdIspPayments.findByReferenceLetter", HrTdIspPayments.class);
        query.setParameter("referenceLetter", refereletter);
        try {
            return (List<HrTdIspPayments>) query.getResultList();
        } catch (Exception ex) {
            return null;

        }
    }

    //</editor-fold>

    public HrTdIspStudentPlacement searchDepartment(HrTdIspStudentDetails hrTdIspStudentDetails) {
        Query query = em.createNativeQuery("SELECT H.*  FROM HR_TD_ISP_STUDENT_PLACEMENT H  WHERE h.INTERNSHIP_STUDENT_DETAILS_ID=?1 ", HrTdIspStudentPlacement.class);
        query.setParameter(1, hrTdIspStudentDetails.getId());
        try {
            HrTdIspStudentPlacement empInfo = (HrTdIspStudentPlacement) query.getResultList().get(0);
            return empInfo;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
}
