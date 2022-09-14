/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrLuTdPsvcCourseTypes;
import et.gov.eep.hrms.entity.training.HrTdPsvcCourses;
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
public class HrTdPsvcCoursesFacade extends AbstractFacade<HrTdPsvcCourses> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdPsvcCoursesFacade() {
        super(HrTdPsvcCourses.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public HrTdPsvcCourses getSelectedRequest(Integer request) {
        Query query = em.createNamedQuery("HrTdPsvcCourses.findById");
        query.setParameter("id", request);
        try {
            HrTdPsvcCourses selectrequest = (HrTdPsvcCourses) query.getResultList().get(0);
            return selectrequest;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrLuTdPsvcCourseTypes> categoriesList() {
        Query query = em.createNamedQuery("HrLuTdPsvcCourseTypes.findAll");
        try {
            ArrayList<HrLuTdPsvcCourseTypes> courseList = new ArrayList(query.getResultList());
            return courseList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrTdPsvcCourses> findByCourse(HrTdPsvcCourses hrTdPsvcCourses) {
        List<HrTdPsvcCourses> course = null;
        Query query = em.createNamedQuery("HrTdPsvcCourses.findbycourse");
        query.setParameter("id", hrTdPsvcCourses.getCourseTypeId().getId());
        try {
            course = (List<HrTdPsvcCourses>) query.getResultList();
            return course;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //</editor-fold>
}
