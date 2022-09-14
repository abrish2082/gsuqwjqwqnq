/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.attendance;

import et.gov.eep.hrms.entity.attendance.HrAttendances;
import et.gov.eep.hrms.mapper.attendace.HrAttendancesFacade;
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
public class attendanceBean implements attendanceBeanLocal {

    @EJB
    HrAttendancesFacade hrAttendancesFacade;
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public List<HrAttendances> findAll() {
        return hrAttendancesFacade.findAll();
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
    public HrAttendances findByabsentdate(String hrattendancesdata) {
        return hrAttendancesFacade.findByabsentdate(hrattendancesdata);
    }

    @Override
    public void SaveOrUpdate(HrAttendances hrattendances) {
        hrAttendancesFacade.saveOrUpdate(hrattendances);
    }

    @Override
    public List<HrAttendances> loadFiltereddata(int status) {
        return hrAttendancesFacade.loadFiltereddata(status);
    }

    @Override
    public List<HrAttendances> findAll(HrAttendances hrattendances) {
        return hrAttendancesFacade.findAll(hrattendances);
    }

    @Override
    public ArrayList findByabsentdate(HrAttendances hrattendances) {
        return hrAttendancesFacade.findabsentdated(hrattendances);
    }
}
