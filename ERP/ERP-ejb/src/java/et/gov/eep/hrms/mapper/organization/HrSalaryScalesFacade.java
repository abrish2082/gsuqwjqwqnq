/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.organization;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.organization.HrSalaryScales;
import java.math.BigDecimal;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Administrator
 */
@Stateless
public class HrSalaryScalesFacade extends AbstractFacade<HrSalaryScales> {

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
    public HrSalaryScalesFacade() {
        super(HrSalaryScales.class);
    }

    /**
     *
     * @param rangId
     * @param stepId
     * @return
     */
    public HrSalaryScales getSalary(BigDecimal rangId, BigDecimal stepId) {
        System.out.println("test=====>   " + rangId + "   " + stepId);
        Query query = em.createNamedQuery("HrSalaryScales.findByRangeStep", HrSalaryScales.class);
        System.out.println("test22  " + query.getSingleResult().toString());
        System.out.println("test q   " + query);
        try {
            query.setParameter("salaryRangeId", rangId);
            query.setParameter("salaryStepId", stepId);
            System.out.println("test2  " + query.getSingleResult().toString());
            HrSalaryScales salary = (HrSalaryScales) (query.getSingleResult());
            //System.out.println("salary moddel " + salary.getSalary());
            return salary;
        } catch (Exception e) {
            return null;
        }

    }

//     public Country getCountryByName(EntityManager em, String name) { 
//         TypedQuery<Country> query = em.createQuery( 
//        "SELECT c FROM Country c WHERE c.name = ?1", Country.class);     return query.setParameter(1, name).getSingleResult();   }  
//
    /**
     *
     * @param hrSalaryScales
     * @return
     */
    public HrSalaryScales findBasicSalary(HrSalaryScales hrSalaryScales) {
        try {
            Query query = em.createQuery("SELECT h FROM HrSalaryScales h where h.salaryRangeId.id=?1 and h.salaryStepId.id=?2");
            query.setParameter(1, hrSalaryScales.getSalaryRangeId().getId());
            query.setParameter(2, hrSalaryScales.getSalaryStepId().getId());
            return (HrSalaryScales) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     *
     * @param hrSalaryScales
     * @return
     */
    public HrSalaryScales checkStepIdRep(HrSalaryScales hrSalaryScales) {
        try {
            Query query = em.createQuery("SELECT h FROM HrSalaryScales h where h.salaryRangeId.id=?1 and h.salaryStepId.id=?2");
            query.setParameter(1, hrSalaryScales.getSalaryRangeId().getId());
            query.setParameter(2, hrSalaryScales.getSalaryStepId().getId());
            return (HrSalaryScales) query.getSingleResult();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

//    @Override
//    public HrSalaryScales searchSalary(String rangId, String stepId) {
//
//        Query query = em.createNativeQuery("SELECT h FROM HrSalaryScales h WHERE h.salaryRangeId.id = :?1 AND h.salaryStepId.id =:?2", HrSalaryScales.class);
//        query.setParameter(1, rangId);
//        query.setParameter(2, stepId);
//
//        try {
//            HrSalaryScales empInfo = (HrSalaryScales) query.getResultList().get(0);
//            System.err.println("sizzzzzzzzzzzzzze" + query.getResultList().size());
//            return empInfo;
//        } catch (Exception ex) {
//            return null;
//        }
//    }
}
