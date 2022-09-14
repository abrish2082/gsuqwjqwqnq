/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrLuTrainingCategory;
import et.gov.eep.hrms.entity.training.HrTdCourses;
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
public class HrTdCoursesFacade extends AbstractFacade<HrTdCourses> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdCoursesFacade() {
        super(HrTdCourses.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrLuTrainingCategory> categories() {
        Query query = em.createNamedQuery("HrLuTrainingCategory.findAll");
        ArrayList<HrLuTrainingCategory> listCate = new ArrayList(query.getResultList());
        return listCate;
    }

    public List<HrLuTrainingCategory> categoriesList() {
        List<HrLuTrainingCategory> luTrainingCategory = null;
        Query query = em.createNamedQuery("HrInternshipUniversities.findAll");
        try {
            luTrainingCategory = (List<HrLuTrainingCategory>) query.getResultList();
            return luTrainingCategory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public HrTdCourses findCourseId(HrTdCourses hrTdCourses) {
        Query query = em.createNamedQuery("HrTdCourses.findById");
        query.setParameter("id", hrTdCourses.getId());
        try {
            HrTdCourses hrTdCourses1 = (HrTdCourses) query.getResultList().get(0);
            return hrTdCourses1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean isExist(HrTdCourses hrTdCourses) {
        boolean isExist;
        Query query = em.createNamedQuery("HrTdCourses.findByCourseName");
        query.setParameter("courseName", hrTdCourses.getCourseName());
        try {
            if (query.getResultList().size() > 0) {
                isExist = true;
            } else {
                isExist = false;
            }
            return isExist;
        } catch (Exception ex) {
            return false;
        }
    }

    public HrLuTrainingCategory checkCatagoryName(HrLuTrainingCategory hrLuTrainingCategory) {
        Query query = em.createNamedQuery("HrLuTrainingCategory.findByCategoryName", HrLuTrainingCategory.class);
        query.setParameter("categoryName", hrLuTrainingCategory.getCategoryName());
        try {
            HrLuTrainingCategory catagory = (HrLuTrainingCategory) query.getResultList();
            return catagory;
        } catch (Exception exce) {
            exce.printStackTrace();
            return null;
        }
    }
    //</editor-fold>
}
