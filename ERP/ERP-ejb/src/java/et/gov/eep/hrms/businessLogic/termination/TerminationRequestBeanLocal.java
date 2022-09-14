/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import et.gov.eep.hrms.entity.termination.HrTerminationTypes;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Local
public interface TerminationRequestBeanLocal {

    public void save(HrTerminationTypes hrTerminationType);

    public void edit(HrTerminationTypes hrTerminationType);

    public void save(HrTerminationRequests hrTerminationRequest);

    public void edit(HrTerminationRequests hrTerminationRequest);

    public List<HrTerminationTypes> findall();

    public ArrayList<HrTerminationTypes> searchByTerminationName(HrTerminationTypes hrTerminationType);

    public HrTerminationTypes getByTerminationName(HrTerminationTypes hrTerminationType);

    public HrTerminationTypes checkTerminationName(HrTerminationTypes hrTerminationTypes);

    public List<SelectItem> filterByStatus();

    public List<HrTerminationRequests> loadTerminationList(int status);

    public HrTerminationRequests getSelectedRequest(int request);

    public List<HrTerminationRequests> loadTerminationReqList(Status status, int userId);

    public List<HrTerminationRequests> loadTerminationList(Status status, int userId);

    public List<HrTerminationRequests> searchByStatus(int status);

    public List<HrTerminationRequests> loadaApprove(Status status, int userId);

    public List<HrTerminationRequests> loadReject(Status status, int userId);

    public int terminationCountPerYear(String get);

}
