/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.overtime;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.overtime.HrOvertimeRequests;
import et.gov.eep.hrms.mapper.overtime.HrOvertimeRequestsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author meles
 */
@Stateless
public class OvertimeReuestBean implements OvertimeReuestBeanLocal {
    @EJB
    HrOvertimeRequestsFacade hrOvertimeRequestsFacade;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public ArrayList findBymonth(HrOvertimeRequests hrOvertimeRequests) {
       return hrOvertimeRequestsFacade.findBymonth(hrOvertimeRequests);
    }

    @Override
    public void SaveOrUpdate(HrOvertimeRequests hrOvertimeRequests) {
       hrOvertimeRequestsFacade.saveOrUpdate(hrOvertimeRequests);
    }

    @Override
    public List<HrOvertimeRequests> findAll() {
   return hrOvertimeRequestsFacade.findAll();
    }

    @Override
    public List<HrOvertimeRequests> loadFiltereddata(int status) {
         return hrOvertimeRequestsFacade.loadFiltereddata(status);
    }

    @Override
    public List<SelectItem> Filterstatus() {
       List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Requested List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("4"), "Load Rejected List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load all List"));
        return selectItems;
    }

    @Override
    public List<HrOvertimeRequests> loadFiltereddata(Status status1, int userId) {
        return hrOvertimeRequestsFacade.loadFiltereddata(status1, userId);
    }
    @Override
    public List<HrOvertimeRequests> populateTableApprove(Status status1, int userId) {
        return hrOvertimeRequestsFacade.populateTableApprove(status1, userId);
    }

    @Override
    public List<HrOvertimeRequests> populateTableReject(Status status1, int userId) {
       return hrOvertimeRequestsFacade.populateTableReject(status1, userId);
    }
}
