/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudents;
import et.gov.eep.hrms.entity.training.HrTdUniversities;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Benin
 */
@Stateless
public class HrTdIspStudentsFacade extends AbstractFacade<HrTdIspStudents> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdIspStudentsFacade() {
        super(HrTdIspStudents.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrTdUniversities> universities() {
        Query query = em.createNamedQuery("HrTdUniversities.findAll");
        ArrayList<HrTdUniversities> listUniv = new ArrayList(query.getResultList());
        return listUniv;
    }

    public List<HrTdUniversities> universitiesList() {
        List<HrTdUniversities> internshipUniversitie = null;
        Query query = em.createNamedQuery("HrTdUniversities.findAll");
        try {
            internshipUniversitie = (List<HrTdUniversities>) query.getResultList();
            return internshipUniversitie;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrTdIspStudents> findByFirstName(HrTdIspStudents hrTdIspStudents) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public List<HrTdIspStudents> findByYearSemister(HrTdIspStudents hrTdIspStudents) {
        List<HrTdIspStudents> internshipUniversitie = null;
        Query query = em.createNamedQuery("HrTdIspStudents.findByYearSemister", HrTdIspStudents.class);
        query.setParameter("year", hrTdIspStudents.getYear());
        query.setParameter("semister", hrTdIspStudents.getSemister());
        try {
            internshipUniversitie = (List<HrTdIspStudents>) query.getResultList();
            return internshipUniversitie;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HrTdUniversities findByUniversityId(int UnId) {
        try {
            Query query = em.createNamedQuery("HrTdUniversities.findById", HrTdUniversities.class);
            query.setParameter("id", UnId);
            return (HrTdUniversities) query.getResultList().get(0);
        } catch (Exception e) {
            return null;
        }
    }

    public List<HrTdUniversities> findByUniversityId(HrTdIspStudents hrTdIspStudents) {
        try {
            Query query = em.createNamedQuery("HrTdUniversities.insu", HrTdUniversities.class);
            query.setParameter("sid", hrTdIspStudents.getUniversityId());
            return (List<HrTdUniversities>) query.getResultList();
        } catch (Exception e) {
            return null;
        }
    }

    public HrTdIspStudents getSelectedRequest(Integer request) {
        Query query = em.createNamedQuery("HrTdIspStudents.findById");
        query.setParameter("id", request);
        try {
            HrTdIspStudents selectrequest = (HrTdIspStudents) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean checkDuplicate(HrTdIspStudentDetails hrTdIspStudentDetails) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrTdIspStudentDetails.findByDuplicate", HrTdIspStudentDetails.class);
        query.setParameter("studentId", hrTdIspStudentDetails.getStudentId());
        query.setParameter("internId", hrTdIspStudentDetails.getInternshipStudentId());
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
    //</editor-fold>

}
