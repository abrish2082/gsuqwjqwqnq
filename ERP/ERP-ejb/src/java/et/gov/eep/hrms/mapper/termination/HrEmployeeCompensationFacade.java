/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.termination;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.termination.HrEmployeeCompensation;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author User
 */
@Stateless
public class HrEmployeeCompensationFacade extends AbstractFacade<HrEmployeeCompensation> {
    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public HrEmployeeCompensationFacade() {
        super(HrEmployeeCompensation.class);
    }

    public HrEmployeeCompensation findByEmpId(HrEmployees employee) {
        System.out.println("employee.getId(000)==="+employee.getId());
          Query query = em.createNamedQuery("HrEmployeeCompensation.findByEmployeId",HrEmployeeCompensation.class);
          System.out.println("employee.getId()==="+employee.getId());
          query.setParameter("empid", employee.getId());
      
       
             if(query.getResultList().size()>0){
             HrEmployeeCompensation empcomp=(HrEmployeeCompensation)query.getResultList().get(0);
            System.out.println("--HrEmployeeCompensation--" + empcomp);
            return empcomp;
             }else{
                 return null;
             }
       
    }

  
    
}
