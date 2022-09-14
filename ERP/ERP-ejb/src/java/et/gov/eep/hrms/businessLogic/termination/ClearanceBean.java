/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.termination.HrClearance;
import et.gov.eep.hrms.entity.termination.HrClearanceSetting;
import et.gov.eep.hrms.entity.termination.HrRetirementRequest;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import et.gov.eep.hrms.entity.transfer.HrTransferRequests;
import et.gov.eep.hrms.mapper.termination.HrClearanceFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Ob
 */
@Stateless
public class ClearanceBean implements ClearanceBeanLocal {

    @EJB
    HrClearanceFacade hrClearanceFacade;

    @Override
    public List<HrTerminationRequests> findApprovedTermination() {
        return hrClearanceFacade.findApprovedTermination();
    }

    @Override
    public List<HrRetirementRequest> findApprovedRetirement() {
        return hrClearanceFacade.findApprovedRetirement();
    }

    @Override
    public List<HrTransferRequests> findApprovedTransfer() {
        return hrClearanceFacade.findApprovedTransfer();
    }

    @Override
    public ArrayList<HrClearance> findTerminationeList(int terminationId, int empl, String clearanceType) {
        return hrClearanceFacade.findTerminationeList(terminationId, empl, clearanceType);
    }

    @Override
    public ArrayList<HrClearance> findRetirementList(int retirementId, int empl, String clearanceType) {
        return hrClearanceFacade.findRetirementList(retirementId, empl, clearanceType);
    }

    @Override
    public ArrayList<HrClearance> findTransferList(int transferId, int empl, String clearanceType) {
        return hrClearanceFacade.findTransferList(transferId, empl, clearanceType);
    }

    @Override
    public void save(HrClearance hrClearance) {
        hrClearanceFacade.create(hrClearance);
}

    @Override
    public void edit(HrClearance hrClearance) {
        hrClearanceFacade.edit(hrClearance);
    }
    
    @Override
    public List<HrClearanceSetting> findClearanceSetting() {
      return hrClearanceFacade.findClearanceSetting();
    }

}
