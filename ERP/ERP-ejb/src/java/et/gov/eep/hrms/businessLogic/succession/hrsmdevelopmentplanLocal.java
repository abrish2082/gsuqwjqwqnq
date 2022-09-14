/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlanDetails;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlans;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author meles
 */
@Local
public interface hrsmdevelopmentplanLocal {

    public List<HrSmSuccessionPlans> FindAll(HrSmSuccessionPlans hrSmSuccessionPlans);

    public void Findall(HrSmSuccessionPlans hrSmSuccessionPlans);

    public HrSmSuccessionPlans findByPlanId(int planId);

    public void saveOrUpdate(HrSmSuccessionPlans hrSmSuccessionPlans);

    public HrSmSuccessionPlans findbyapprovedida(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails);
    public List<SelectItem> filterByStatus();

    public List<HrSmSuccessionPlanDetails> findemp(HrEmployees hrEmployees);
}
