/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.businessLogic.hrms;

import et.gov.eep.fcms.entity.FmsLuBudgetYear;
import et.gov.eep.ifrs.entity.HrEmployeeSeverance;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author User
 */
@Local
public interface SeveranceLiabilityLocal {

    public List<HrEmployeeSeverance> findAll();

    public void save(HrEmployeeSeverance hrEmployeeSeverance);

    public List<HrEmployeeSeverance> findBYBudgetYear(FmsLuBudgetYear fmsLuBudgetYear);
    
}
