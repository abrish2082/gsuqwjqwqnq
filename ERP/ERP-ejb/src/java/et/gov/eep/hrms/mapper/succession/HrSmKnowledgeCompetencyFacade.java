/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.succession;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.succession.HrSmKnowledgeCompetency;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author meles
 */
@Stateless
public class HrSmKnowledgeCompetencyFacade extends AbstractFacade<HrSmKnowledgeCompetency> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSmKnowledgeCompetencyFacade() {
        super(HrSmKnowledgeCompetency.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrSmKnowledgeCompetency> findbykmpid(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        Query query = em.createNamedQuery("HrSmKnowledgeCompetency.findByKmpId");
        query.setParameter("kmpId", hrSmKnowledgeCompetency.getKmpId().getId());
        try {
            List<HrSmKnowledgeCompetency> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public HrSmKnowledgeCompetency findbyId(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        Query query = em.createNamedQuery("HrSmKnowledgeCompetency.findById");
        query.setParameter("id", hrSmKnowledgeCompetency.getId());
        try {
            HrSmKnowledgeCompetency comp = (HrSmKnowledgeCompetency) (query.getResultList().get(0));
            return comp;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmKnowledgeCompetency> findbyKmpId(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        Query query = em.createNamedQuery("HrSmKnowledgeCompetency.findByKmpId");
        query.setParameter("kmpId", hrSmKnowledgeCompetency.getKmpId().getId());
        try {
            List<HrSmKnowledgeCompetency> comp = new ArrayList(query.getResultList());
            return comp;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmKnowledgeCompetency> findAllKmpId(HrJobTypes hrJobTypes) {
        Query query = em.createNamedQuery("HrSmKnowledgeCompetency.findBykmpIdLike");
        query.setParameter("jobTitle", hrJobTypes.getJobTitle().toUpperCase());
        try {
            List<HrSmKnowledgeCompetency> comp = new ArrayList(query.getResultList());
            return comp;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmKnowledgeCompetency> findAll(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        Query query = em.createNamedQuery("HrSmKnowledgeCompetency.findAll");
        try {
            ArrayList<HrSmKnowledgeCompetency> request = new ArrayList<>(query.getResultList());
            if (query.getResultList().isEmpty()) {
            } else if (query.getResultList().size() > 0) {
                return request;
            }
            return null;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public boolean searchduplicatecompetency(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrSmKnowledgeCompetency.searchduplicatecompetency", HrSmKnowledgeCompetency.class);
        query.setParameter("kmpId", hrSmKnowledgeCompetency.getKmpId());
        query.setParameter("competencyId", hrSmKnowledgeCompetency.getCompetencyTypeId());
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
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<HrSmKnowledgeCompetency> findJobTitle(HrJobTypes hrJobTypes) {
        try {
            Query query1 = em.createNativeQuery("SELECT * FROM hr_sm_knowledge_competency kc "
                    + "INNER JOIN hr_sm_kmp kmp "
                    + "on kc.kmp_id=kmp.id "
                    + "INNER JOIN hr_job_types hj "
                    + "on hj.id = kmp.job_id "
                    + "where lower (hj.job_title) Like lower ('" + hrJobTypes.getJobTitle() + "%')", HrSmKnowledgeCompetency.class);
            return (List<HrSmKnowledgeCompetency>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }

    }

    public boolean searchduplicate(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
        boolean duplicaton;
        Query query1 = em.createNativeQuery("select * from hr_sm_knowledge_competency k "
                + "where k.kmp_id='" + hrSmKnowledgeCompetency.getKmpId() + "' "
                + "and k.competency_type_id ='" + hrSmKnowledgeCompetency.getCompetencyTypeId() + "' ");
        try {
            if (query1.getResultList().size() > 0) {
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
