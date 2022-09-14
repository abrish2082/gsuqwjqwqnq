/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.succession.HrSmKmp;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
import et.gov.eep.hrms.mapper.succession.HrSmSuccessorEvaluationFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author Behailu
 */


@Stateless
public class SuccessorEvaluationBean implements SuccessorEvaluationBeanLocal {
    @EJB
    HrSmSuccessorEvaluationFacade hrSmSuccessorEvaluationFacade;
    
    
    
    //<editor-fold defaultstate="collapsed" desc="session bean Method Implementations">
    
    @Override
    public List<HrSmSuccessorEvaluation> findAll() {
        return hrSmSuccessorEvaluationFacade.findAll();
    }
    
    @Override
    public HrSmSuccessorEvaluation readkmpDetail(Integer id) {
        return hrSmSuccessorEvaluationFacade.readkmpDetail(id);
    }
    
    @Override
    public List<HrSmSuccessorEvaluation> findJobTitle(HrJobTypes hrJobTypes){
        return hrSmSuccessorEvaluationFacade.findJobTitle(hrJobTypes);
    }
    @Override
    public List<HrSmSuccessorEvaluation> findEmployeename(HrEmployees hrEmployees){
        return hrSmSuccessorEvaluationFacade.findEmployeename(hrEmployees);
    }
    
    @Override
    public void save(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        hrSmSuccessorEvaluationFacade.create(hrSmSuccessorEvaluation);
    }
    
    @Override
    public void edit(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        hrSmSuccessorEvaluationFacade.edit(hrSmSuccessorEvaluation);
    }
    
    @Override
    public List<HrSmSuccessorEvaluation> findbyposition(HrSmKmp hrSmKmp) {
        return hrSmSuccessorEvaluationFacade.findbyposition(hrSmKmp);
    }
    
    
//</editor-fold>

    @Override
    public void saveorupdate(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
       hrSmSuccessorEvaluationFacade.saveOrUpdate(hrSmSuccessorEvaluation);
    }

    @Override
    public List<HrSmSuccessorEvaluation> findallEvaluatedsuccessors(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
       return hrSmSuccessorEvaluationFacade.findallEvaluatedsuccessors(hrSmSuccessorEvaluation);
    }

    @Override
    public List<HrSmSuccessorEvaluation> findJobTitleforApproval(HrJobTypes hrJobTypes) {
      return hrSmSuccessorEvaluationFacade.findJobTitleforApproval(hrJobTypes);
    }

    @Override
    public List<HrSmSuccessorEvaluation> findEmployeenameforApproval(HrEmployees hrEmployees) {
        return hrSmSuccessorEvaluationFacade.findEmployeenameforApproval(hrEmployees);
    }

    @Override
    public List<HrSmSuccessorEvaluation> findallToBeEvaluated(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
       return hrSmSuccessorEvaluationFacade.findallToBeEvaluated(hrSmSuccessorEvaluation);
    }

    @Override
    public List<HrSmSuccessorEvaluation> findbypositionToEvaluate(HrSmKmp hrSmKmp) {
        return hrSmSuccessorEvaluationFacade.findbypositionToEvaluate(hrSmKmp);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load To be Evaluated Successors List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Evaluated Successors List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load Approved Successors List"));
        return selectItems;
    }

    @Override
    public List<HrSmSuccessorEvaluation> loadEvaluationList(int status) {
        return hrSmSuccessorEvaluationFacade.loadEvaluationList(status);
    }
  

}
