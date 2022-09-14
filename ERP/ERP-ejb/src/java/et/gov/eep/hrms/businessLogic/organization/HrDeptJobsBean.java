/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrDeptJobs;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.mapper.organization.HrDeptJobsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrDeptJobsBean implements HrDeptJobsBeanLocal {

    @EJB
    HrDeptJobsFacade deptJobsFacade;

    /**
     *
     * @param hrDeptJobs
     */
    @Override
    public void create(HrDeptJobs hrDeptJobs) {
        deptJobsFacade.create(hrDeptJobs);
    }

    /**
     *
     * @param hrDeptJobs
     */
    @Override
    public void edit(HrDeptJobs hrDeptJobs) {
        deptJobsFacade.edit(hrDeptJobs);
    }

    /**
     *
     * @param hrDeptJobs
     */
    @Override
    public void remove(HrDeptJobs hrDeptJobs) {
        deptJobsFacade.remove(hrDeptJobs);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrDeptJobs> findAll() {
        return deptJobsFacade.findAll();
    }

    /**
     *
     * @param toString
     * @return
     */
    @Override
    public List<HrDeptJobs> getListOfJobs(String toString) {
        return deptJobsFacade.getListOfJobs(toString);
    }

    @Override
    public Integer numEmployeeNeeded(HrDepartments hrDepartments, HrJobTypes hrJobTypes) {
        return deptJobsFacade.numEmployeeNeeded(hrDepartments, hrJobTypes);
    }

    @Override
    public List<HrDeptJobs> findDebJobsByDept(int deptId) {
        return deptJobsFacade.findDebJobsByDept(deptId);
    }

    @Override
    public List<HrDeptJobs> checkJobDeblication(HrDeptJobs hrDeptJobs) {
        return deptJobsFacade.checkJobDeblication(hrDeptJobs);
    }
}
