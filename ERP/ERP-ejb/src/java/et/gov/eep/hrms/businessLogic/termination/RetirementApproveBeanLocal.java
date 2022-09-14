/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.termination.HrRetirementRequest;
import et.gov.eep.hrms.entity.termination.HrRetirementRequestUpload;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Local
public interface RetirementApproveBeanLocal {

    public void edit(HrRetirementRequest hrRetirementRequest);

    public List<SelectItem> filterByStatus();

    public List<HrRetirementRequest> loadRetirementList(int status);

    public HrRetirementRequest getSelectedRequest(int request);

    public List<HrRetirementRequest> findPreparedList();

    public List<HrRetirementRequest> searchByStatus(int status);

    public void remove(HrRetirementRequestUpload hrRetirementRequestUpload);
    
}
