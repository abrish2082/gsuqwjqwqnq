/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.termination.HrClearanceSetting;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ob
 */
@Local
public interface ClearanceSettingBeanLocal {

    public void save(HrClearanceSetting hrClearanceSetting);

    public void edit(HrClearanceSetting hrClearanceSetting);

    public List<HrClearanceSetting> findAll();

    public ArrayList<HrClearanceSetting> getSelectedDepartmentsList();

}
