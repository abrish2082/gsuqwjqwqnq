/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.succession;

import et.gov.eep.hrms.entity.employee.HrEmployees;
import et.gov.eep.hrms.entity.succession.HrSmSuccessorEvaluation;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local

public interface SuccessorBeanLocal {

    public void create(HrSmSuccessorEvaluation hrSmSuccessorEvaluation);

    public void findAll();

    public HrSmSuccessorEvaluation getSelectedRequest(int request);

    public List<HrSmSuccessorEvaluation> findJobTitle(String hrJobTypes);

    public void edit(HrSmSuccessorEvaluation hrSmSuccessorEvaluation);

    public boolean checkDuplicate(HrSmSuccessorEvaluation hrSmSuccessorEvaluation);

    public ArrayList<HrEmployees> SearchByEmpId(HrEmployees hrEmployees);

    public HrEmployees getByEmpId(HrEmployees hrEmployees);

    public HrEmployees getPatientByName(HrEmployees hrEmployees);
}
