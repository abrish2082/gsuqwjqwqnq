/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.termination.HrClearanceSetting;
import et.gov.eep.hrms.mapper.termination.HrClearanceSettingFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ob
 */
@Stateless
public class ClearanceSettingBean implements ClearanceSettingBeanLocal {

    @EJB
    HrClearanceSettingFacade hrClearanceSettingFacade;

    @Override
    public void save(HrClearanceSetting hrClearanceSetting) {
        hrClearanceSettingFacade.create(hrClearanceSetting);
    }

    @Override
    public void edit(HrClearanceSetting hrClearanceSetting) {
        hrClearanceSettingFacade.edit(hrClearanceSetting);
    }

    @Override
    public List<HrClearanceSetting> findAll() {
        return hrClearanceSettingFacade.findAll();
    }

    @Override
     public ArrayList<HrClearanceSetting> getSelectedDepartmentsList() {
       return hrClearanceSettingFacade.getSelectedDepartmentsList();
     }
    
}
