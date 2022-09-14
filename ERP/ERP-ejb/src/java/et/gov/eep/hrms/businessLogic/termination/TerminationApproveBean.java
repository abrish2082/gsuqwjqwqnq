/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import et.gov.eep.hrms.mapper.termination.HrTerminationRequestsFacade;
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
public class TerminationApproveBean implements TerminationApproveBeanLocal {

    @EJB
    HrTerminationRequestsFacade hrTerminationRequestsFacade;

    @Override
    public void edit(HrTerminationRequests hrTerminationRequest) {
        hrTerminationRequestsFacade.edit(hrTerminationRequest);
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
    public List<HrTerminationRequests> loadTerminationList(int status) {
        return hrTerminationRequestsFacade.loadTerminationList(status);
    }

    @Override
    public HrTerminationRequests getSelectedRequest(int request) {
        return hrTerminationRequestsFacade.getSelectedRequest(request);
    }

    @Override
    public List<HrTerminationRequests> findPreparedList() {
        return hrTerminationRequestsFacade.findPreparedList();
        
    }
    
    @Override
    public List<HrTerminationRequests> searchByStatus(int status) {
        return hrTerminationRequestsFacade.searchByStatus(status);
    }
    
}
