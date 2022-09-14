/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.succession;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.succession.HrSmCompetencySubtypes;
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
public class HrSmCompetencySubtypesFacade extends AbstractFacade<HrSmCompetencySubtypes> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrSmCompetencySubtypesFacade() {
        super(HrSmCompetencySubtypes.class);
    }

    //<editor-fold defaultstate="collapsed" desc="Named query"> 
    public List<HrSmCompetencySubtypes> findbycompetencysubtypes(HrSmCompetencySubtypes hrSmCompetencySubtypes) {
        Query query = em.createNamedQuery("HrSmCompetencySubtypes.findByCompetencySubtype");
        query.setParameter("competencySubtype", hrSmCompetencySubtypes.getCompetencySubtype().toUpperCase() + '%');
        try {
            List<HrSmCompetencySubtypes> compList = new ArrayList(query.getResultList());
            return compList;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public List<HrSmCompetencySubtypes> kmpskill(String skill) {
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

    public boolean searchduplicate(HrSmCompetencySubtypes hrSmCompetencySubtypes) {
        boolean duplicaton;
        Query query = em.createNamedQuery("HrSmCompetencySubtypes.searchbyduplicate", HrSmCompetencySubtypes.class);
        query.setParameter("competencyId", hrSmCompetencySubtypes.getCompetencyTypeId().getCompetencyType());
        query.setParameter("comptype", hrSmCompetencySubtypes.getCompetencySubtype());
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

    public List<HrSmCompetencySubtypes> findAll(HrSmCompetencySubtypes hrSmCompetencySubtypes) {
        Query query = em.createNamedQuery("HrSmCompetencySubtypes.findAll");
        try {
            ArrayList<HrSmCompetencySubtypes> request = new ArrayList<>(query.getResultList());
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
}
