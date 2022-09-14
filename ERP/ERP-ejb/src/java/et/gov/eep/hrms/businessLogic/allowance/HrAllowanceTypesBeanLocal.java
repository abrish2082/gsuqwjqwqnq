/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.allowance;

import et.gov.eep.hrms.entity.allowance.HrAllowanceTypes;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdi
 */
@Local
public interface HrAllowanceTypesBeanLocal {

    public List<HrAllowanceTypes> findAll();
    
}
