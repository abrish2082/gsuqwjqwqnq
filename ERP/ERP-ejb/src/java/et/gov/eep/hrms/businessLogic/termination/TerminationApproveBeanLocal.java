/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.termination;

import et.gov.eep.hrms.entity.termination.HrTerminationRequests;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author INSA
 */
@Local
public interface TerminationApproveBeanLocal {

    public void edit(HrTerminationRequests hrTerminationRequest);

    public List<SelectItem> filterByStatus();

    public List<HrTerminationRequests> loadTerminationList(int status);

    public HrTerminationRequests getSelectedRequest(int request);

    public List<HrTerminationRequests> findPreparedList();

    public List<HrTerminationRequests> searchByStatus(int status);

}
