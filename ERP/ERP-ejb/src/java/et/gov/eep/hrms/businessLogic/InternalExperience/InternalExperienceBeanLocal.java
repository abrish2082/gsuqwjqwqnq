/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.InternalExperience;

import et.gov.eep.hrms.entity.transfer.HrEmpInternalHistory;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author meles
 */
@Local
public interface InternalExperienceBeanLocal {

    public void save(HrEmpInternalHistory hrEmpInternalHistory);
    
    public List<HrEmpInternalHistory> findByEmpId(HrEmpInternalHistory hrEmpInternalHistory, String empID);
}
