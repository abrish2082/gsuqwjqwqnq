
package et.gov.eep.hrms.integration;

import et.gov.eep.hrms.entity.organization.HrDepartments;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

@Local
public interface HrDepartmentsIntegrationBeanLocal {

    public ArrayList<HrDepartments> getdepartmetInfoByName(HrDepartments hrDepartments);

    public HrDepartments getdepartmentInformation(HrDepartments hrDepartment);

    public List<HrDepartments> findAllDepartmentInfo();

    public HrDepartments findByDepartmentId(HrDepartments hrDepartments);
    
     public List<HrDepartments> findAll();
    
 
}
