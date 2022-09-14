/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.organization;

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
public interface HrDeptJobsBeanLocal {

    /**
     *
     * @param hrDeptJobs
     */
    public void create(HrDeptJobs hrDeptJobs);

    /**
     *
     * @param hrDeptJobs
     */
    void edit(HrDeptJobs hrDeptJobs);

    /**
     *
     * @param hrDeptJobs
     */
    void remove(HrDeptJobs hrDeptJobs);

    /**
     *
     * @return
     */
    public List<HrDeptJobs> findAll();

    /**
     *
     * @param toString
     * @return
     */
    public List<HrDeptJobs> getListOfJobs(String toString);

    public Integer numEmployeeNeeded(HrDepartments hrDepartments, HrJobTypes hrJobTypes);

    public List<HrDeptJobs> findDebJobsByDept(int deptId);

    public List<HrDeptJobs> checkJobDeblication(HrDeptJobs hrDeptJobs);
}
