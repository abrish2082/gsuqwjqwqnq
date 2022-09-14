/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrTdIspStatus;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
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
public class HrTdIspStatusFacade extends AbstractFacade<HrTdIspStatus> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdIspStatusFacade() {
        super(HrTdIspStatus.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public HrTdIspStudentDetails getSelectedRequest(Integer request) {
        Query query = em.createNamedQuery("HrTdIspStudentDetails.findById");
        query.setParameter("id", request);
        try {
            HrTdIspStudentDetails selectrequest = (HrTdIspStudentDetails) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
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

    public List<HrTdIspStudentDetails> searchSemister(HrTdIspStudents hrTdIspStudents) {
        try {
            Query query = em.createNamedQuery("HrTdIspStudentDetails.findByParentID");
            query.setParameter("hrTdIspStudents", hrTdIspStudents);
            return (List<HrTdIspStudentDetails>) query.getResultList();

        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
//</editor-fold>

    public List<HrTdIspStudentDetails> searchBySemister(HrTdIspStudents hrTdIspStudents) {
        try {
            Query query = em.createNativeQuery("select * \n"
                    + "from hr_td_isp_student_details sd Inner join hr_td_isp_students hs \n"
                    + "on hs.ID=sd.internship_student_id\n"
                    + "where hs.semister='" + hrTdIspStudents.getSemister() + "' ", HrTdIspStudents.class);
            return (List<HrTdIspStudentDetails>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }
}
