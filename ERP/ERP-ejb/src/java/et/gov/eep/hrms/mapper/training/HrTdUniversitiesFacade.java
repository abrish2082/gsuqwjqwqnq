/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
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
public class HrTdUniversitiesFacade extends AbstractFacade<HrTdUniversities> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdUniversitiesFacade() {
        super(HrTdUniversities.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrTdUniversities> findByUniversityName(HrTdUniversities hrTdUniversities) {
        Query query = em.createNamedQuery("HrTdUniversities.findByUniversityNameLike");
        query.setParameter("universityName", hrTdUniversities.getUniversityName().toUpperCase() + '%');
        try {
            List<HrTdUniversities> uniList = new ArrayList(query.getResultList());
            return uniList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<HrTdUniversities> findUniversity(String hrTdUniversities) {
        Query query = em.createNamedQuery("HrTdUniversities.findByUniversityName", HrTdUniversities.class);
        query.setParameter("universityName", hrTdUniversities);
        try {
            return (List<HrTdUniversities>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdUniversities> findUniversityName(HrTdUniversities hrTdUniversities) {
        Query query = em.createNamedQuery("HrTdUniversities.findByUniversityName", HrTdUniversities.class);
        query.setParameter("universityName", hrTdUniversities.getUniversityName());
        try {
            return (List<HrTdUniversities>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrTdUniversities> findByUniversityId(HrTdIspStudents hrTdIspStudents) {
        Query query = em.createNamedQuery("HrTdUniversities.insu", HrTdUniversities.class);
        query.setParameter("sid", hrTdIspStudents.getUniversityId().getId());
        try {
            return (List<HrTdUniversities>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean checkDuplicationByName(HrTdUniversities hrTdUniversities) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrTdUniversities.findDuplicationByUniversityName", HrTdUniversities.class);
        query.setParameter("universityName", hrTdUniversities.getUniversityName());
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
