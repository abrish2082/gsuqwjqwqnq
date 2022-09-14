/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.succession.HrSmKmp;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrDeptJobs;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface successionBeanLocal {

    public void save(HrSmKmp hrSmKmp);

    public List<HrSmKmp> findAll();

    public void edit(HrSmKmp hrSmKmp);
     
    public List<HrSmKmp>  findAll(HrSmKmp hrSmKmp);
    
    public List<HrDeptJobs> findByDeptId(HrDepartments hrDepartments);
    
    public HrSmKmp readkmpDetail(Integer id);
    
    public boolean searchbyJobAndDep(HrJobTypes hrJobTypes,HrDepartments hrDepartments);
    
    public List<HrSmKmp> findJobTitle(HrJobTypes hrJobTypes);
    
    public List<HrSmKmp> findDepartmentName(HrDepartments hrDepartments);
    
    public List<HrSmKmp> findbyposition(HrSmKmp hrSmKmp);

    public List<HrDepartments> findalldept();

    

  
   

   

}
