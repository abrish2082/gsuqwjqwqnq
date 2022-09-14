/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.employee;

import et.gov.eep.hrms.entity.employee.HrEmpEducations;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import et.gov.eep.hrms.mapper.employee.HrEmpEducationsFacade;

/**
 *
 * @author Man
 */
@Stateless
public class EmpEducation implements EmpEducationLocal {

   @EJB
   HrEmpEducationsFacade  hrEmpEducationsFacade;
   
    /**
     *
     * @param empEducation
     */
    @Override
   public void save(HrEmpEducations empEducation)
   {
       hrEmpEducationsFacade.create(empEducation);
   }

    /**
     *
     * @param empEducation
     */
    @Override
   public void edit(HrEmpEducations empEducation)
   {
       hrEmpEducationsFacade.edit(empEducation);
   }

    /**
     *
     * @param hrEmpEducations
     */
    @Override
   public void Delete(HrEmpEducations hrEmpEducations)
   {
       hrEmpEducationsFacade.remove(hrEmpEducations);
   }
}
