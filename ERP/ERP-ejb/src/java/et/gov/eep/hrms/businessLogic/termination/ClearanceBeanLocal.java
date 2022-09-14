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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Ob
 */
@Local
public interface ClearanceBeanLocal {

    public List<HrTerminationRequests> findApprovedTermination();

    public List<HrRetirementRequest> findApprovedRetirement();

    public List<HrTransferRequests> findApprovedTransfer();

    public ArrayList<HrClearance> findTerminationeList(int terminationId, int empl, String clearanceType);

    public ArrayList<HrClearance> findRetirementList(int retirementId, int empl, String clearanceType);

    public ArrayList<HrClearance> findTransferList(int transferId, int empl, String clearanceType);

    public void save(HrClearance hrClearance);

    public void edit(HrClearance hrClearance);

    public List<HrClearanceSetting> findClearanceSetting();

}
