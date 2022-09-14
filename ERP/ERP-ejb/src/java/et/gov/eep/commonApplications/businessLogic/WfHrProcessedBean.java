/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.businessLogic;

import et.gov.eep.commonApplications.entity.WfHrProcessed;
import et.gov.eep.commonApplications.mapper.WfHrProcessedFacade;
import et.gov.eep.hrms.entity.leave.HrLeaveRequest;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author insa
 */
@Stateless
public class WfHrProcessedBean implements WfHrProcessedBeanLocal {

    @EJB
    WfHrProcessedFacade wfHrProcessedFacade;

    @Override
    public void saveOrUpdate(WfHrProcessed wfHrProcessed) {
        wfHrProcessedFacade.saveOrUpdate(wfHrProcessed);
    }

    @Override
    public void create(WfHrProcessed wfHrProcessed) {
        wfHrProcessedFacade.create(wfHrProcessed);
    }

    @Override
    public List<WfHrProcessed> MassSalWFList(Integer id) {
        return wfHrProcessedFacade.MassSalWFList(id);
    }

    @Override
    public WfHrProcessed findByLeaveId(HrLeaveRequest leaveRequest) {
        return wfHrProcessedFacade.findByLeaveId(leaveRequest);
    }

    @Override
    public List<WfHrProcessed> findAlls() {
        return wfHrProcessedFacade.findAll();
    }
}
