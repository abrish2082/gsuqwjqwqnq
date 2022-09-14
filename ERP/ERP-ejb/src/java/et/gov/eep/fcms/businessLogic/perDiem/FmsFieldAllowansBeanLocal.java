/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.perDiem;

import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceForeign;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author muller
 */
@Local
public interface FmsFieldAllowansBeanLocal {

    public void create(FmsFieldAllowance fmsFieldAllowanceEnty);

    public void create(FmsFieldAllowanceForeign allowanceForeign);

    public void edit(FmsFieldAllowance fmsFieldAllowanceEnty);

    public Integer countRow();

    public FmsFieldAllowance findEmploye(FmsFieldAllowance fmsFieldAllowanceEnty);

    public FmsFieldAllowance getById(FmsFieldAllowance fmsFieldAllowanceEnty);

    public FmsFieldAllowance getByrequestno(FmsFieldAllowance fmsFieldAllowanceEnty);

    public FmsFieldAllowance gethFieldAllowance(FmsFieldAllowance fmsFieldAllowanceEnty);

    public ArrayList<HrEmployees> SearchByEmpId(HrEmployees employeeEnty);

    public List<FmsFieldAllowance> getAllEmployee();

    public List<FmsFieldAllowance> searchFieldAllowance(FmsFieldAllowance fmsFieldAllowanceEnty);

    public List<FmsFieldAllowance> searchEmpByEmpName(HrEmployees empEnty);

    public List<FmsFieldAllowance> searchEmployeeByEmpId(HrEmployees empEnty);

    public List<FmsFieldAllowance> searchAllEmployee();

    public List<FmsFieldAllowance> searchnotexitvocher();

    public List<FmsFieldAllowance> searchSetlementchekeVocher();

    public List<FmsFieldAllowance> findFaByWfStatus(int status);

}
