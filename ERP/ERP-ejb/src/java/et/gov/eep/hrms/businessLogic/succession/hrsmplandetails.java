/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlanDetails;
import et.gov.eep.hrms.entity.succession.HrSmSuccessionPlans;
import et.gov.eep.hrms.mapper.succession.HrSmSuccessionPlanDetailsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author meles
 */
@Stateless
public class hrsmplandetails implements hrsmplandetailsLocal {

    @EJB
    HrSmSuccessionPlanDetailsFacade hrSmSuccessionPlanDetailsFacade;

    @Override
    public HrSmSuccessionPlanDetails findbyapprovedid(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        return hrSmSuccessionPlanDetailsFacade.findbyapprovedid(hrSmSuccessionPlanDetails);
    }

    @Override
    public List<HrSmSuccessionPlanDetails> findall(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        return hrSmSuccessionPlanDetailsFacade.findAll();
    }

    @Override
    public HrSmSuccessionPlanDetails findbyapprovedid(HrSmSuccessionPlans hrSmSuccessionPlans) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public HrSmSuccessionPlanDetails findbyapprovedida(HrSmSuccessionPlans hrSmSuccessionPlans) {
        return hrSmSuccessionPlanDetailsFacade.findAll(hrSmSuccessionPlans);
    }

    @Override
    public void saveorupdate(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        hrSmSuccessionPlanDetailsFacade.saveOrUpdate(hrSmSuccessionPlanDetails);
    }

    @Override
    public void findallactivesatus(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        hrSmSuccessionPlanDetailsFacade.findAll();
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Filter by Status ---"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load only Active "));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load only Inactive "));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load only Cancelled"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load only Compelete"));
        selectItems.add(new SelectItem(String.valueOf("4"), "Load only Failed"));
        selectItems.add(new SelectItem(String.valueOf("5"), "Load All"));
        return selectItems;
    }

    @Override
    public List<HrSmSuccessionPlanDetails> filteredEvaluationLevel(int status) {
        return hrSmSuccessionPlanDetailsFacade.filteredEvaluationLevel(status);
    }

    @Override
    public HrSmSuccessionPlanDetails getSelectedLevel(int level) {
        return hrSmSuccessionPlanDetailsFacade.getSelectedLevel(level);
    }

    @Override
    public void saveUpdate(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        hrSmSuccessionPlanDetailsFacade.saveOrUpdate(hrSmSuccessionPlanDetails);
    }

    @Override
    public List findAll(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails) {
        return hrSmSuccessionPlanDetailsFacade.findAll();
    }

    @Override
    public List findByStatus(HrSmSuccessionPlanDetails hrSmSuccessionPlanDetails, String status) {
        return hrSmSuccessionPlanDetailsFacade.findByStatus(hrSmSuccessionPlanDetails, status);
    }
}
