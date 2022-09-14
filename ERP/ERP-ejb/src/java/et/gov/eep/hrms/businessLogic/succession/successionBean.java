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
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import et.gov.eep.hrms.mapper.succession.HrSmKmpFacade;
import et.gov.eep.hrms.mapper.organization.HrDeptJobsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Behailu
 */
@Stateless
public class successionBean implements successionBeanLocal {

    @EJB
    HrSmKmpFacade hrSmKmpFacade;
    @EJB
    HrDeptJobsFacade hrDeptJobsFacade;
    @EJB
    HrDepartmentsFacade hrDepartmentsFacade;

    @Override
    public void save(HrSmKmp hrSmKmp) {
        hrSmKmpFacade.create(hrSmKmp);
    }

    @Override
    public List<HrSmKmp> findAll() {
        return hrSmKmpFacade.findAll();
    }

    @Override
    public void edit(HrSmKmp hrSmKmp) {
        hrSmKmpFacade.edit(hrSmKmp);
    }

    @Override
    public List<HrDeptJobs> findByDeptId(HrDepartments hrDepartments) {
        return hrSmKmpFacade.findByDeptId(hrDepartments);
    }

    @Override
    public HrSmKmp readkmpDetail(Integer id) {
        return hrSmKmpFacade.readkmpDetail(id);
    }

    @Override
    public boolean searchbyJobAndDep(HrJobTypes hrJobTypes,HrDepartments hrDepartments) {
        return hrSmKmpFacade.searchbyJobAndDep(hrJobTypes,hrDepartments);
    }

    @Override
    public List<HrSmKmp> findJobTitle(HrJobTypes hrJobTypes) {
        return hrSmKmpFacade.findJobTitle(hrJobTypes);
    }

    @Override
    public List<HrSmKmp> findDepartmentName(HrDepartments hrDepartments) {
        return hrSmKmpFacade.findDepartmentName(hrDepartments);
    }

  

    @Override
    public List<HrSmKmp>  findAll(HrSmKmp hrSmKmp) {
      return hrSmKmpFacade.findAll();
    }

   

    @Override
    public List<HrSmKmp> findbyposition(HrSmKmp hrSmKmp) {
        return hrSmKmpFacade.findbyposition(hrSmKmp);
    }

    @Override
    public List<HrDepartments> findalldept() {
        return hrDepartmentsFacade.findAll();
    }

    

    
    

   

}
