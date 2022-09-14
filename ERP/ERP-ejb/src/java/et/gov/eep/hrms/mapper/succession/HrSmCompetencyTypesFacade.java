/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.succession;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.succession.HrSmCompetencyTypes;
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
public class HrSmCompetencyTypesFacade extends AbstractFacade<HrSmCompetencyTypes> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSmCompetencyTypesFacade() {
        super(HrSmCompetencyTypes.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query">
    public List<HrSmCompetencyTypes> findbycompetencyname(HrSmCompetencyTypes hrSmCompetencyTypes) {
        Query query = em.createNamedQuery("HrSmCompetencyTypes.findByCompetencyType");
        query.setParameter("competencyType", hrSmCompetencyTypes.getCompetencyType().toUpperCase() + '%');
        try {
            List<HrSmCompetencyTypes> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmCompetencyTypes> findkmp(String knowledge) {

        Query query = em.createNamedQuery("HrSmCompetencyTypes.findByKnowledge", HrSmCompetencyTypes.class);
        query.setParameter("competencyId", knowledge);
        try {

            List<HrSmCompetencyTypes> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmKnowledgeCompetency> findbykmpiD(HrSmKnowledgeCompetency hrSmKnowledgeCompetency) {
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

    public boolean searchduplicate(HrSmCompetencyTypes competencyTypes) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrSmCompetencyTypes.searchbyduplicate", HrSmCompetencyTypes.class);
        query.setParameter("competencyId", competencyTypes.getCompetencyId().getCompetencyName());
        query.setParameter("comptype", competencyTypes.getCompetencyType());
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

    public List<HrSmCompetencyTypes> findAll(HrSmCompetencyTypes hrSmCompetencyTypes) {
        Query query = em.createNamedQuery("HrSmCompetencyTypes.findAll");
        try {
            ArrayList<HrSmCompetencyTypes> request = new ArrayList<>(query.getResultList());
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

    //</editor-fold>
    //<editor-fold defaultstate="collapsed" desc="Native query">
    public List<Object[]> findbycompet(String skill) {
        Query query = em.createNativeQuery("SELECT HR_SM_COMPETENCY.COMPETENCY_NAME,"
                + "HR_SM_COMPETENCY_TYPES.COMPETENCY_TYPE,"
                + "HR_SM_COMPETENCY_SUBTYPES.COMPETENCY_SUBTYPE, HR_SM_COMPETENCY_TYPES.ID   "
                + "FROM HR_SM_COMPETENCY, HR_SM_COMPETENCY_TYPES,HR_SM_COMPETENCY_SUBTYPES "
                + "WHERE  HR_SM_COMPETENCY_SUBTYPES.COMPETENCY_TYPE_ID=HR_SM_COMPETENCY_TYPES.ID"
                + " AND HR_SM_COMPETENCY_TYPES.COMPETENCY_ID=HR_SM_COMPETENCY.ID"
                + " AND HR_SM_COMPETENCY.COMPETENCY_NAME=?");
        query.setParameter("1", skill);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmCompetencyTypes> findbycompett(String knowledge) {
        Query query = em.createNativeQuery("SELECT HR_SM_COMPETENCY.COMPETENCY_NAME, \n"
                + "               HR_SM_COMPETENCY_TYPES.COMPETENCY_TYPE\n"
                + "                 FROM HR_SM_COMPETENCY, HR_SM_COMPETENCY_TYPES  \n"
                + "                WHERE HR_SM_COMPETENCY.ID=HR_SM_COMPETENCY_TYPES.COMPETENCY_ID \n"
                + "                 AND HR_SM_COMPETENCY.COMPETENCY_NAME='knowledge'");
        query.setParameter("1", knowledge);
        try {
            return query.getResultList();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }
    //</editor-fold>
}
