/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

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
public interface hrsmplandetailsLocal {

    public HrSmSuccessionPlanDetails findbyapprovedid(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails);

    public List<HrSmSuccessionPlanDetails> findall(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails);

    public HrSmSuccessionPlanDetails findbyapprovedid(HrSmSuccessionPlans hrSmSuccessionPlans);

    public HrSmSuccessionPlanDetails findbyapprovedida(HrSmSuccessionPlans hrSmSuccessionPlans);

    public void saveorupdate(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails);

    public void findallactivesatus(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails);

    public List<SelectItem> filterByStatus();

    public HrSmSuccessionPlanDetails getSelectedLevel(int level);

    public List<HrSmSuccessionPlanDetails> filteredEvaluationLevel(int status);

    public void saveUpdate(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails);

    public List findAll(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails);

    public List findByStatus(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails, String status);

}
