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
 * @author Mac
 */
@Stateless
public class HrLuTrainingCategoryFacade extends AbstractFacade<HrLuTrainingCategory> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrLuTrainingCategoryFacade() {
        super(HrLuTrainingCategory.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public ArrayList<HrTdCourses> findByCourseName(HrTdCourses hrTrainingCourses) {
        Query query = em.createNamedQuery("HrTdCourses.findByCourseNameLike");
        query.setParameter("courseName", hrTrainingCourses.getCourseName().toUpperCase() + '%');
        try {
            ArrayList<HrTdCourses> coursesList = new ArrayList(query.getResultList());
            return coursesList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<HrLuTrainingCategory> categories() {
        Query query = em.createNamedQuery("HrLuTrainingCategory.findAll");
        ArrayList<HrLuTrainingCategory> listCate = new ArrayList(query.getResultList());
        return listCate;
    }

    public ArrayList<HrLuTrainingCategory> findAllCategory() {
        Query query = em.createNamedQuery("HrLuTrainingCategory.findAll");
        try {
            ArrayList<HrLuTrainingCategory> categoryList = new ArrayList(query.getResultList());
            return categoryList;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<HrTdCourses> findByCatagory(HrTdCourses hrTrainingCourses) {
        List<HrTdCourses> luTrainingCategory = null;
        Query query = em.createNamedQuery("HrTdCourses.findByCatagoyId");
        query.setParameter("categoryId", hrTrainingCourses.getCategoryId().getId());
        try {
            luTrainingCategory = (List<HrTdCourses>) query.getResultList();
            return luTrainingCategory;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrTdCourses> searchByCatagory(String catagoryName) {
        Query query = em.createNamedQuery("HrTdCourses.findByCatagoy");
        query.setParameter("categoryName", catagoryName);
        try {
            return (List<HrTdCourses>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public HrLuTrainingCategory searchByCategoryName(HrLuTrainingCategory hrTrainingCourses) {
        Query query = em.createNamedQuery("HrLuTrainingCategory.findByCategoryName");
        query.setParameter("categoryName", hrTrainingCourses.getCategoryName());
        try {
            HrLuTrainingCategory coursesList = (HrLuTrainingCategory) query.getResultList().get(0);
            return coursesList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<HrTdCourses> findbyID(HrLuTrainingCategory hrLuTrainingCategory) {
        try {
            Query query = em.createNamedQuery("HrTdCourses.findByCatagoyId", HrTdCourses.class);
            query.setParameter("categoryId", hrLuTrainingCategory.getId());
            return (List<HrTdCourses>) query.getResultList();
        } catch (Exception e) {

            return null;
        }
    }

    public boolean checkCatagoryName(HrLuTrainingCategory hrLuTrainingCategory) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrLuTrainingCategory.findByCategoryName", HrLuTrainingCategory.class);
        query.setParameter("categoryName", hrLuTrainingCategory.getCategoryName());
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
