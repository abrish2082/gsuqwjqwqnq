/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.training.HrTdPsvcTraineeDetails;
import et.gov.eep.hrms.entity.training.HrTdPsvcTrainees;
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
public class HrTdPsvcTraineesFacade extends AbstractFacade<HrTdPsvcTrainees> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdPsvcTraineesFacade() {
        super(HrTdPsvcTrainees.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrLuEducLevels> findByLevel() {
        Query query = em.createNamedQuery("HrLuEducLevels.findAll");
        try {
            List<HrLuEducLevels> levellist = (List<HrLuEducLevels>) query.getResultList();
            return levellist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<HrLuEducTypes> findByDepartment() {
        Query query = em.createNamedQuery("HrLuEducTypes.findAll");
        try {
            List<HrLuEducTypes> levellist = (List<HrLuEducTypes>) query.getResultList();
            return levellist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public ArrayList<HrTdPsvcTrainees> findByyear1(HrTdPsvcTrainees hrTdPsvcTrainees) {
        Query queryReq = em.createNamedQuery("HrTdPsvcTrainees.findByYear");
        try {
            queryReq.setParameter("yearOfTraining", hrTdPsvcTrainees.getYearOfTraining());
            ArrayList<HrTdPsvcTrainees> trainList = new ArrayList(queryReq.getResultList());
            return trainList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrTdPsvcTraineeDetails searchIMGById(HrTdPsvcTraineeDetails id) {
        try {
            Query query = em.createNamedQuery("HrTdPsvcTraineeDetails.findById");
            query.setParameter("id", id.getId());

            return (HrTdPsvcTraineeDetails) query.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
//</editor-fold>

    public List<HrTdPsvcTrainees> findByAllyear() {
        Query query = em.createNativeQuery("SELECT DISTINCT YEAR_OF_TRAINING FROM HR_TD_PSVC_TRAINEES ORDER BY YEAR_OF_TRAINING DESC");
        try {
            List<HrTdPsvcTrainees> levellist = (List<HrTdPsvcTrainees>) query.getResultList();
            return levellist;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
