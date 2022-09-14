/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.allowance;

import et.gov.eep.hrms.entity.allowance.HrAllowanceTypes;
import et.gov.eep.hrms.mapper.allowance.HrAllowanceTypesFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Abdi
 */
@Stateless
public class HrAllowanceTypesBean implements HrAllowanceTypesBeanLocal {

    @EJB
    HrAllowanceTypesFacade hrAllowanceTypesFacade;

    @Override
    public List<HrAllowanceTypes> findAll() {
        return hrAllowanceTypesFacade.findAll();
    }
}
