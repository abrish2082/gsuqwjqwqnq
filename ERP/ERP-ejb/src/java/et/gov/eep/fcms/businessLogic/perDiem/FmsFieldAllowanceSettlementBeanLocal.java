/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.perDiem;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowance;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceSettlement;
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author muller
 */
@Local
public interface FmsFieldAllowanceSettlementBeanLocal {

    public void create(FmsFieldAllowanceSettlement allowanceSettlement);

    public void edit(FmsFieldAllowanceSettlement allowanceSettlement);

    public List<FmsFieldAllowanceSettlement> getEmplloyeId(FmsFieldAllowanceSettlement allowanceSettlement);

    public List<FmsFieldAllowanceSettlement> searchSettlementByParameter(FmsFieldAllowanceSettlement allowanceSettlement);

    public List<FmsFieldAllowanceSettlement> searchAll();

    public List<FmsFieldAllowanceSettlement> searchEmpByEmpName(HrEmployees empEnty);

    public List<FmsFieldAllowanceSettlement> searchEmployeeByEmpId(HrEmployees empEnty);

    public List<FmsFieldAllowanceSettlement> searchAllEmployee();

    public List<FmsFieldAllowanceSettlement> searchSettlementtVocher();

    public List<FmsFieldAllowanceSettlement> findFaByWfStatus(int status);

    public List<FmsFieldAllowanceSettlement> findEmpId(FmsFieldAllowanceSettlement allowanceSettlement);

    public List<FmsFieldAllowance> findByWfStatusApproved(FmsFieldAllowance fmsFieldAllowanceEnty, int wfStatusApproved);

    public FmsFieldAllowance findByempIdAndStatus(FmsFieldAllowance fmsFieldAllowanceEnty);

    public FmsFieldAllowanceSettlement getById(FmsFieldAllowanceSettlement allowanceSettlement);

    public FmsFieldAllowanceSettlement searchSetlementchekeVocher(int id);

    public FmsLuPerdimeRate getPer(FmsLuPerdimeRate fmsLuPerdimeRate);

}
