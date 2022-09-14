/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import et.gov.eep.hrms.entity.termination.HrTerminationTypes;
import et.gov.eep.hrms.mapper.termination.HrTerminationRequestsFacade;
import et.gov.eep.hrms.mapper.termination.HrTerminationTypesFacade;
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
public class TerminationRequestBean implements TerminationRequestBeanLocal {

    @EJB
    HrTerminationRequestsFacade hrTerminationRequestsFacade;
    @EJB
    HrTerminationTypesFacade hrTerminationTypesFacade;

    @Override
    public void save(HrTerminationTypes hrTerminationType) {
        hrTerminationTypesFacade.create(hrTerminationType);
    }

    @Override
    public void edit(HrTerminationTypes hrTerminationType) {
        hrTerminationTypesFacade.edit(hrTerminationType);
    }

    @Override
    public void save(HrTerminationRequests hrTerminationRequest) {
        hrTerminationRequestsFacade.create(hrTerminationRequest);
    }

    @Override
    public void edit(HrTerminationRequests hrTerminationRequest) {
        hrTerminationRequestsFacade.edit(hrTerminationRequest);
    }

    @Override
    public List<HrTerminationTypes> findall() {
        return hrTerminationTypesFacade.findAll();
    }

    @Override
    public ArrayList<HrTerminationTypes> searchByTerminationName(HrTerminationTypes hrTerminationType) {
        return hrTerminationRequestsFacade.searchByTerminationName(hrTerminationType);
    }

    @Override
    public HrTerminationTypes getByTerminationName(HrTerminationTypes hrTerminationType) {
        return hrTerminationRequestsFacade.getByTerminationName(hrTerminationType);
    }

    @Override
    public HrTerminationTypes checkTerminationName(HrTerminationTypes hrTerminationTypes) {
        return hrTerminationRequestsFacade.checkTerminationName(hrTerminationTypes);
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
    public List<HrTerminationRequests> loadTerminationReqList(Status status, int userId) {
        return hrTerminationRequestsFacade.loadTerminationReqList(status, userId);
    }

    @Override
    public List<HrTerminationRequests> loadTerminationList(Status status, int userId) {
        return hrTerminationRequestsFacade.loadTerminationList(status, userId);
    }

    @Override
    public List<HrTerminationRequests> searchByStatus(int status) {
        return hrTerminationRequestsFacade.searchByStatus(status);
    }
    
     @Override
    public List<HrTerminationRequests> loadaApprove(Status status, int userId) {
     return hrTerminationRequestsFacade.loadaApprove(status, userId);
    }
    
    @Override
    public List<HrTerminationRequests> loadReject(Status status, int userId) {
       return hrTerminationRequestsFacade.loadReject(status, userId);
    }

    @Override
    public int terminationCountPerYear(String get) {
        return hrTerminationRequestsFacade.terminationCountPerYear(get);
    }

}
