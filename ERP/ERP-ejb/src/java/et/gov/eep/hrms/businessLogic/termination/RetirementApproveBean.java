/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.termination.HrRetirementRequest;
import et.gov.eep.hrms.entity.termination.HrRetirementRequestUpload;
import et.gov.eep.hrms.mapper.termination.HrRetirementRequestFacade;
import et.gov.eep.hrms.mapper.termination.HrRetirementRequestUploadFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Stateless
public class RetirementApproveBean implements RetirementApproveBeanLocal {

    @EJB
    HrRetirementRequestFacade hrRetirementRequestFacade;
    @EJB
    HrRetirementRequestUploadFacade hrRetirementRequestUploadFacade;

    @Override
    public void edit(HrRetirementRequest hrRetirementRequest) {
        hrRetirementRequestFacade.edit(hrRetirementRequest);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Rejected List"));
        return selectItems;
    }

    @Override
    public List<HrRetirementRequest> loadRetirementList(int status) {
        return hrRetirementRequestFacade.loadRetirementList(status);
    }

    @Override
    public HrRetirementRequest getSelectedRequest(int request) {
        return hrRetirementRequestFacade.getSelectedRequest(request);
    }

    @Override
    public List<HrRetirementRequest> findPreparedList() {
        return hrRetirementRequestFacade.findPreparedList();
    }

    @Override
    public List<HrRetirementRequest> searchByStatus(int status) {
        return hrRetirementRequestFacade.searchByStatus(status);
    }

    @Override
    public void remove(HrRetirementRequestUpload hrRetirementRequestUpload) {
        hrRetirementRequestUploadFacade.remove(hrRetirementRequestUpload);
    }

}
