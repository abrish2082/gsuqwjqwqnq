/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.succession;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.succession.HrSmCompetencySubtypes;
import et.gov.eep.hrms.entity.succession.HrSmSkillCompetency;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.succession.HrSmKnowledgeCompetency;
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
public class HrSmSkillCompetencyFacade extends AbstractFacade<HrSmSkillCompetency> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSmSkillCompetencyFacade() {
        super(HrSmSkillCompetency.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrSmCompetencySubtypes> findkmpskill(String skill) {
        Query query = em.createNamedQuery("HrSmCompetencySubtypes.findbyskill", HrSmCompetencySubtypes.class);
        query.setParameter("competencyId", skill);
        try {
            List<HrSmCompetencySubtypes> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmSkillCompetency> findbykmpiD(HrSmSkillCompetency hrSmSkillCompetency) {
        Query query = em.createNamedQuery("HrSmSkillCompetency.findByKmpid");
        query.setParameter("Kmpid", hrSmSkillCompetency.getKmpId().getId());
        try {
            List<HrSmSkillCompetency> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmSkillCompetency> add(HrSmSkillCompetency hrSmSkillCompetency) {
        Query query = em.createNamedQuery("HrSmSkillCompetency.findByKmpid");
        query.setParameter("Kmpid", hrSmSkillCompetency.getKmpId());
        try {
            List<HrSmSkillCompetency> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmSkillCompetency> add() {
        Query query = em.createNamedQuery("HrSmSkillCompetency.findByKmpid");
        try {
            List<HrSmSkillCompetency> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmSkillCompetency> findbyKmpId(HrSmSkillCompetency hrSmSkillCompetency) {
        Query query = em.createNamedQuery("HrSmSkillCompetency.findByKmpId");
        query.setParameter("kmpId", hrSmSkillCompetency.getKmpId().getId());
        try {
            List<HrSmSkillCompetency> comp = new ArrayList(query.getResultList());
            return comp;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmSkillCompetency> findAll(HrSmSkillCompetency hrSmSkillCompetency) {
        Query query = em.createNamedQuery("HrSmSkillCompetency.findAll");
        try {
            ArrayList<HrSmSkillCompetency> request = new ArrayList<>(query.getResultList());
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

    public boolean searchduplicate(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
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

    public boolean searchduplicatexist(HrSmSkillCompetency hrSmSkillCompetency) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrSmSkillCompetency.searchduplicatecompetency", HrSmKnowledgeCompetency.class);
        query.setParameter("Kmpid", hrSmSkillCompetency.getKmpId());
        query.setParameter("competencyId", hrSmSkillCompetency.getCompetencySubtypeId());
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
    public List<HrSmSkillCompetency> kmpskill(String skill) {
        Query query = em.createNativeQuery("select * from hr_sm_competency cm "
                + "left join hr_sm_competency_types ct on ct.competency_id=cm.id \n"
                + "left join hr_sm_competency_subtypes cst"
                + " on cst.competency_type_id=ct.id\n"
                + "left join hr_sm_skill_competency"
                + " sk on sk.competency_subtype_id=cst.id\n"
                + "where cm.competency_name=?", HrSmSkillCompetency.class);
        query.setParameter("1", skill);
        try {
            return (List<HrSmSkillCompetency>) query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmSkillCompetency> findJobTitle(HrJobTypes hrJobTypes) {
        try {
            Query query1 = em.createNativeQuery("SELECT * FROM hr_sm_skill_competency kc "
                    + "INNER JOIN hr_sm_kmp kmp "
                    + "on kc.kmp_id=kmp.id "
                    + "INNER JOIN hr_job_types hj "
                    + "on hj.id = kmp.job_id "
                    + "where lower (hj.job_title) Like lower ('" + hrJobTypes.getJobTitle() + "%')", HrSmSkillCompetency.class);
            return (List<HrSmSkillCompetency>) query1.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    public boolean searchduplicate(HrSmSkillCompetency hrSmSkillCompetency) {
        boolean duplicaton;
        Query query1 = em.createNativeQuery("select * from hr_sm_knowledge_competency k "
                + "where k.kmp_id='" + hrSmSkillCompetency.getKmpId() + "' "
                + "and k.competency_type_id ='" + hrSmSkillCompetency.getCompetencySubtypeId() + "' ");
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
