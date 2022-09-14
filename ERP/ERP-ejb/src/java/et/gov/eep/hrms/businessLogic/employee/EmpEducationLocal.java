/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.employee;

import et.gov.eep.hrms.entity.employee.HrEmpEducations;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface EmpEducationLocal {
    
    /**
     *
     * @param empEducation
     */
    public void save(HrEmpEducations empEducation);
  
    /**
     *
     * @param empEducation
     */
    public void edit(HrEmpEducations empEducation);
   
    /**
     *
     * @param hrEmpEducations
     */
    public void Delete(HrEmpEducations hrEmpEducations);
   
  
}
