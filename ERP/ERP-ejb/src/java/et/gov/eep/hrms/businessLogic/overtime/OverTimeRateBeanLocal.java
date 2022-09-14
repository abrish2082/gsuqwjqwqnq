/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.overtime;

import et.gov.eep.hrms.entity.overtime.HrOvertimeRates;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author meles
 */
@Local
public interface OverTimeRateBeanLocal {
 
    public List<HrOvertimeRates> findAll(HrOvertimeRates hrOvertimeRate);

    public List<HrOvertimeRates> findbyRateTypes(HrOvertimeRates hrOvertimeRate);

    public HrOvertimeRates getSelectedRequest(int id);
    public void SaveOrUpdate(HrOvertimeRates hrOvertimeRate);

    public boolean searchdup(HrOvertimeRates hrOvertimeRate);

    public List<HrOvertimeRates> findAll();

    public ArrayList findByRateType(HrOvertimeRates hrOvertimeRate);
    
}
