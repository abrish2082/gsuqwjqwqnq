/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlanDetails;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlans;
import et.gov.eep.hrms.mapper.succession.HrSmSuccessionPlansFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author meles
 */
@Stateless
public class hrsmdevelopmentplan implements hrsmdevelopmentplanLocal {
@EJB
HrSmSuccessionPlansFacade hrSmSuccessionPlansFacade;

    @Override
    public List<HrSmSuccessionPlans> FindAll(HrSmSuccessionPlans hrSmSuccessionPlans) {
  return  hrSmSuccessionPlansFacade.findstatusactive();
    }

    @Override
    public void Findall(HrSmSuccessionPlans hrSmSuccessionPlans) {
     hrSmSuccessionPlansFacade.findaall();
    }

    @Override
    public HrSmSuccessionPlans findByPlanId(int planId) {
    return hrSmSuccessionPlansFacade.findByPlanId(planId);

    }

    @Override
    public void saveOrUpdate(HrSmSuccessionPlans hrSmSuccessionPlans) {
   hrSmSuccessionPlansFacade.saveOrUpdate(hrSmSuccessionPlans);}

    @Override
    public HrSmSuccessionPlans findbyapprovedida(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        return  hrSmSuccessionPlansFacade.findAll(hrSmSuccessionPlanDetails);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public List<HrSmSuccessionPlanDetails> findemp(HrEmployees hrEmployees) {
        return  hrSmSuccessionPlansFacade.findemp(hrEmployees);
    }
}
