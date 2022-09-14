/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.overtime;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.overtime.HrOvertimeRequests;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author meles
 */
@Local
public interface OvertimeReuestBeanLocal {

    public ArrayList findBymonth(HrOvertimeRequests hrOvertimeRequests);

    public void SaveOrUpdate(HrOvertimeRequests hrOvertimeRequests);

    public List<HrOvertimeRequests> findAll();

    public List<HrOvertimeRequests> loadFiltereddata(int status);

    public List<SelectItem> Filterstatus();

    public List<HrOvertimeRequests> populateTableApprove(Status status1, int userId);

    public List<HrOvertimeRequests> loadFiltereddata(Status status1, int userId);

    public List<HrOvertimeRequests> populateTableReject(Status status1, int userId);
}
