/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.employee;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmpEducations;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Man
 */
@Stateless
public class HrEmpEducationsFacade extends AbstractFacade<HrEmpEducations> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public HrEmpEducationsFacade() {
        super(HrEmpEducations.class);
    }

    public List<HrEmpEducations> findEmpEducation(HrEmployees hrEmployees) { 
        try {
            Query query = em.createNamedQuery("HrEmpEducations.findByEmpID", HrEmpEducations.class);
            query.setParameter("empId", hrEmployees.getId());
            return (List<HrEmpEducations>) query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
