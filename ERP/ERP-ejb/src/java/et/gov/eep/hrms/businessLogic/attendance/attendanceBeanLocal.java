/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.attendance;

import et.gov.eep.hrms.entity.attendance.HrAttendances;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author meles
 */
@Local
public interface attendanceBeanLocal {

    public List<HrAttendances> findAll();

    public List<SelectItem> Filterstatus();

    public HrAttendances findByabsentdate(String hrattendancesdata);

    public void SaveOrUpdate(HrAttendances hrattendances);

    public List<HrAttendances> loadFiltereddata(int status);

    public List<HrAttendances> findAll(HrAttendances hrattendances);

    public ArrayList findByabsentdate(HrAttendances hrattendances);
    
}
