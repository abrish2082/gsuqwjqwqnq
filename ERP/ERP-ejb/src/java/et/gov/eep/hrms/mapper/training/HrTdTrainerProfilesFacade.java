/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.training;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
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
public class HrTdTrainerProfilesFacade extends AbstractFacade<HrTdTrainerProfiles> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrTdTrainerProfilesFacade() {
        super(HrTdTrainerProfiles.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrTdTrainerProfiles> findByInstName(String institutionName) {
        Query query = em.createNamedQuery("HrTdTrainerProfiles.findByInstitutionName");
        query.setParameter("institutionName", institutionName);
        try {
            return (List<HrTdTrainerProfiles>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public ArrayList<HrTdTrainerProfiles> findByIname(HrTdTrainerProfiles hrTdTrainerProfiles) {
        Query query = em.createNamedQuery("HrTdTrainerProfiles.findByInstitutionNameLike");
        query.setParameter("institutionName", hrTdTrainerProfiles.getInstitutionName().toUpperCase() + '%');
        try {
            ArrayList<HrTdTrainerProfiles> empList = new ArrayList(query.getResultList());
            return empList;

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrTdTrainerProfiles findByInameObj(HrTdTrainerProfiles hrTdTrainerProfiles) {
        Query query = em.createNamedQuery("HrTdTrainerProfiles.findByInstitutionName");
        query.setParameter("firstName", hrTdTrainerProfiles.getInstitutionName());
        try {
            HrTdTrainerProfiles emp = (HrTdTrainerProfiles) query.getResultList().get(0);
            return emp;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }

    public HrTdTrainerProfiles findInstituetId(HrTdTrainerProfiles hrTdTrainerProfiles) {
        Query query = em.createNamedQuery("HrTdTrainerProfiles.findById");
        query.setParameter("id", hrTdTrainerProfiles.getId());
        try {
            HrTdTrainerProfiles hrTdTrainerProfiles1 = (HrTdTrainerProfiles) query.getResultList().get(0);
            return hrTdTrainerProfiles1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrTdTrainerProfiles getInstName(HrTdTrainerProfiles tf) {
        HrTdTrainerProfiles companyList = null;
        Query query = em.createNamedQuery("HrTdTrainerProfiles.findByInstitutionName");
        query.setParameter("institutionName", tf.getInstitutionName());
        try {
            if (query.getResultList().size() > 0) {
                companyList = (HrTdTrainerProfiles) query.getResultList().get(0);
            }
            return companyList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean isExist(HrTdTrainerProfiles trainerProfile) {
        boolean isExist;
        Query query = em.createNamedQuery("HrTdTrainerProfiles.findByInstitutionName");
        query.setParameter("institutionName", trainerProfile.getInstitutionName());
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
    //</editor-fold>
}
