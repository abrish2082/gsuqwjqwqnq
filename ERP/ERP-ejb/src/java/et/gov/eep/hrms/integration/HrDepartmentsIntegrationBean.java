
package et.gov.eep.hrms.integration;

import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;


@Stateless
public class HrDepartmentsIntegrationBean implements HrDepartmentsIntegrationBeanLocal {

    @EJB
    HrDepartmentsFacade hrDepartmentsFacade;

    @Override
    public ArrayList<HrDepartments> getdepartmetInfoByName(HrDepartments hrDepartments) {
        return hrDepartmentsFacade.getdepartmetInfoByName(hrDepartments);
    }

    @Override
    public HrDepartments getdepartmentInformation(HrDepartments hrDepartment) {
        return hrDepartmentsFacade.getdepartmentInformation(hrDepartment);
    }
    @Override
    public List<HrDepartments> findAllDepartmentInfo() {
        return hrDepartmentsFacade.findAllDepartment();
    }

    @Override
    public HrDepartments findByDepartmentId(HrDepartments hrDepartments) {
        return hrDepartmentsFacade.findByDepartmentId(hrDepartments);
    }

    @Override
    public List<HrDepartments> findAll() {
        return hrDepartmentsFacade.findAll();
    }

    }
