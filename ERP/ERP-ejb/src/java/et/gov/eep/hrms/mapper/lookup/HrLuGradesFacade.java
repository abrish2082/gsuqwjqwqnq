/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.lookup;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.lookup.HrLuGrades;
import et.gov.eep.hrms.entity.termination.HrTerminationTypes;
import java.util.ArrayList;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class HrLuGradesFacade extends AbstractFacade<HrLuGrades> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public HrLuGradesFacade() {
        super(HrLuGrades.class);
    }
    
    public ArrayList<HrLuGrades> searchByGrade(HrLuGrades hrLuGrades) {
        Query query = em.createNamedQuery("HrLuGrades.findByGradeLike");
        query.setParameter("grade", hrLuGrades.getGrade().toUpperCase() + '%');
        try {
            ArrayList<HrLuGrades> gradeList = new ArrayList(query.getResultList());
            return gradeList;
        } catch (Exception ex) {
            return null;
        }
    }

    public HrLuGrades getByGrade(HrLuGrades hrLuGrades) {
        Query query = em.createNamedQuery("HrLuGrades.findByGrade");
        query.setParameter("grade", hrLuGrades.getGrade());
        try {
            HrLuGrades gradeList = (HrLuGrades) query.getResultList().get(0);
            return gradeList;
        } catch (Exception ex) {
            return null;
        }
    }

}
