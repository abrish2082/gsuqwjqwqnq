/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrTdIspStudentDetails;
import et.gov.eep.hrms.entity.training.HrTdIspStudentPlacement;
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
public class HrTdIspStudentDetailsFacade extends AbstractFacade<HrTdIspStudentDetails> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdIspStudentDetailsFacade() {
        super(HrTdIspStudentDetails.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public ArrayList<HrTdIspStudentPlacement> findByFname(HrTdIspStudentDetails hrTdIspStudentDetails) {
        Query query = em.createNamedQuery("HrTdIspStudentPlacement.findByName");
        query.setParameter("firstName", hrTdIspStudentDetails.getFirstName().toUpperCase() + '%');
        try {
            ArrayList<HrTdIspStudentPlacement> studList = new ArrayList(query.getResultList());
            return studList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public ArrayList<HrTdIspStudentDetails> findBystatus() {
        Query query = em.createNamedQuery("HrTdIspStudentDetails.findByStatusZero");

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

    public HrTdIspStudentDetails findByStudFnameObj(HrTdIspStudentDetails hrTdIspStudentDetails) {
        Query query = em.createNamedQuery("HrTdIspStudentDetails.findByFirstName");
        query.setParameter("firstName", hrTdIspStudentDetails.getFirstName());
        try {
            HrTdIspStudentDetails stud = (HrTdIspStudentDetails) query.getResultList().get(0);
            return stud;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrTdIspStudentDetails findByidObj(HrTdIspStudentDetails hrTdIspStudentDetails) {
        Query query = em.createNamedQuery("HrTdIspStudentDetails.findById");
        query.setParameter("id", hrTdIspStudentDetails.getId());
        try {
            HrTdIspStudentDetails stud = (HrTdIspStudentDetails) query.getResultList().get(0);
            return stud;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public List<HrTdIspStudentDetails> categories() {
        Query query = em.createNamedQuery("HrTdIspStudentDetails.findAll");
        ArrayList<HrTdIspStudentDetails> listCate = new ArrayList(query.getResultList());
        return listCate;
    }

    public List<HrTdIspStudentDetails> categoriesList() {
        List<HrTdIspStudentDetails> internshipStudentDetails = null;
        Query query = em.createNamedQuery("HrLuTrainingCategory.findAll");
        try {
            internshipStudentDetails = (List<HrTdIspStudentDetails>) query.getResultList();
            return internshipStudentDetails;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    //</editor-fold>
}
