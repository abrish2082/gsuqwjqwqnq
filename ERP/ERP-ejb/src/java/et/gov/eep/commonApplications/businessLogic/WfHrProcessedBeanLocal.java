/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.hrms.entity.leave.HrLeaveRequest;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author insa
 */
@Local
public interface WfHrProcessedBeanLocal {

    public void saveOrUpdate(WfHrProcessed wfHrProcessed);

    public void create(WfHrProcessed wfHrProcessed);

    public List<WfHrProcessed> MassSalWFList(Integer id);

    public WfHrProcessed findByLeaveId(HrLeaveRequest leaveRequest);

    public List<WfHrProcessed> findAlls();

}
