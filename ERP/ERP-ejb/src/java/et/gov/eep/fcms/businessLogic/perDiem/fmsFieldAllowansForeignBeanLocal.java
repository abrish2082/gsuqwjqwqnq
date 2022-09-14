/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic.perDiem;

import java.util.List;
import javax.ejb.Local;
import et.gov.eep.fcms.entity.perDiem.FmsFieldAllowanceForeign;
import et.gov.eep.fcms.entity.perDiem.FmsGoodWillingPayment;
import et.gov.eep.fcms.entity.perDiem.FmsLuAdditionalAmount;
import et.gov.eep.fcms.entity.perDiem.FmsLuPerdimeRate;
import et.gov.eep.hrms.entity.employee.HrEmployees;

/**
 *
 * @author muller
 */
@Local
public interface fmsFieldAllowansForeignBeanLocal {

    public void create(FmsFieldAllowanceForeign allowanceForeign);

    public void edit(FmsFieldAllowanceForeign allowanceForeign);

    public FmsLuAdditionalAmount searchAdd(String levelId);

    public FmsGoodWillingPayment searchGoodW(FmsGoodWillingPayment fmsGoodWillingPayment);

    public FmsLuPerdimeRate getPer(Integer levId);

    public FmsFieldAllowanceForeign findEmploye(FmsFieldAllowanceForeign allowanceForeign);

    public FmsFieldAllowanceForeign getById(FmsFieldAllowanceForeign allowanceForeign);

    public FmsFieldAllowanceForeign getAllEmployees(FmsFieldAllowanceForeign allowanceForeign);

    public Integer countRow();

    public List<FmsFieldAllowanceForeign> SearchEmpName(FmsFieldAllowanceForeign allowanceForeign);

    public List<FmsFieldAllowanceForeign> searchEmpByEmpName(HrEmployees empEnty);

    public List<FmsFieldAllowanceForeign> searchEmployeeByEmpId(HrEmployees empEnty);

    public List<FmsFieldAllowanceForeign> searchAll();

    public List<FmsFieldAllowanceForeign> findFaByWfStatus(int status);

}
