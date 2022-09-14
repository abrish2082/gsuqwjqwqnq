package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
import et.gov.eep.hrms.mapper.employee.HrEmployeesFacade;
import et.gov.eep.hrms.mapper.succession.HrSmSuccessorFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author insa
 */
@Stateless
public class SuccessorBean implements SuccessorBeanLocal {

    @Inject
    HrSmSuccessorEvaluation hrSmSuccessorEvaluation;

    @EJB
    HrSmSuccessorFacade hrSmSuccessorEvaluationFacade;
    @EJB
    HrEmployeesFacade hrEmployeesFacade;

    @Override
    public void create(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        hrSmSuccessorEvaluationFacade.create(hrSmSuccessorEvaluation);
    }

    @Override
    public HrSmSuccessorEvaluation getSelectedRequest(int request) {
        return hrSmSuccessorEvaluationFacade.getSelectedRequest(request);
    }

    @Override
    public List<HrSmSuccessorEvaluation> findJobTitle(String hrJobTypes) {
        return hrSmSuccessorEvaluationFacade.findJobTitle(hrJobTypes);
    }

    @Override
    public void findAll() {
        hrSmSuccessorEvaluationFacade.findAll();
    }

    @Override
    public void edit(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        hrSmSuccessorEvaluationFacade.edit(hrSmSuccessorEvaluation);
    }

    @Override
    public boolean checkDuplicate(HrSmSuccessorEvaluation hrSmSuccessorEvaluation) {
        return hrSmSuccessorEvaluationFacade.checkDuplicate(hrSmSuccessorEvaluation);

    }

    @Override
    public ArrayList<HrEmployees> SearchByEmpId(HrEmployees hrEmployees) {
        return hrSmSuccessorEvaluationFacade.SearchByEmpId(hrEmployees);
    }

    @Override
    public HrEmployees getByEmpId(HrEmployees hrEmployees) {
        return hrSmSuccessorEvaluationFacade.getByEmpId(hrEmployees);
    }

    @Override
    public HrEmployees getPatientByName(HrEmployees hrEmployees) {
       return hrSmSuccessorEvaluationFacade.getByName(hrEmployees);
    }
}
