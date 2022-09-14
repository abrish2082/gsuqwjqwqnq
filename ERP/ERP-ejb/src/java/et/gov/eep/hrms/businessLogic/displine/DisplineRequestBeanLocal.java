/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.displine;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.compliantHandling.HrAppeals;
import et.gov.eep.hrms.entity.displine.HrDisciplineFileUpload;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.entity.displine.HrEmpDisciplines;
import et.gov.eep.hrms.entity.employee.HrEmployees;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.DataModel;
import javax.faces.model.SelectItem;

/**
 *
 * @author user
 */
@Local
public interface DisplineRequestBeanLocal {

    public List<HrDisciplineRequests> findRequests();

    public HrDisciplineRequests displayAllRequests(String toString);

    public List<HrDisciplineRequests> findRequestsApproved();

    public List<HrDisciplineRequests> findApprovedDisciplineCases(int applicantId);

    public HrDisciplineRequests findByDisciplineId(HrDisciplineRequests hrDisciplineRequests);

    public HrDisciplineRequests findById(String toString);

    public List<HrDisciplineRequests> searchEmployeeByName(HrEmployees hrEmployeesRequester);

    public void saveOrUpdate(HrDisciplineRequests disciplineRequests);

    public List<HrDisciplineRequests> searchEmployeeByOffenderName(HrEmployees hrEmployeesAccuser);

    public List<HrDisciplineRequests> searchEmployeeByOffenderAndRequesterName(HrEmployees hrEmployeesAccuser, HrEmployees hrEmployeesRequester);

    public List<HrDisciplineRequests> getByStatus(HrDisciplineRequests disciplineRequests);

    public void edit(HrDisciplineRequests disciplineRequests);

    public List<HrDisciplineRequests> loadPenalityRequest(int status);

    public List<HrDisciplineRequests> findByOffenderId(HrDisciplineRequests disciplineRequests);

    public HrDisciplineRequests findDisciplineReqByOffenderIdAndOffenceType(HrDisciplineRequests disciplineRequests);

    public HrDisciplineRequests findDisciplineReqRecordByOffenderIdAndOffenceType(HrDisciplineRequests disciplineRequests);

    public HrAppeals findByCaseId(HrAppeals hrAppeals);

    public void edit(HrAppeals appals);

    public void create(HrDisciplineFileUpload hrDisciplineFileUpload);

    public List<HrDisciplineFileUpload> findDocumentId(HrDisciplineRequests disciplineRequests);

    public HrDisciplineFileUpload findFileUploadId(HrDisciplineRequests disciplineRequests);

    public void remove(HrDisciplineFileUpload hrDisciplineFileUpload);

//    public List<HrDisciplineRequests> loadPenalityRequestList(String status, String userId);
    public List<HrDisciplineRequests> loadPenalityRequestList(Status status);

    public List<HrDisciplineRequests> findRequestForApproval();

    public List<HrDisciplineRequests> loadPenalityRequestListForTwo(Status status);

    public HrDisciplineRequests findByIds(HrDisciplineRequests disciplineRequests);

    //<editor-fold defaultstate="collapsed" desc="adjucate disciplinary appeal">
    public List<HrDisciplineRequests> readApprovedDisciplinaryAppeals();
//</editor-fold>

    public List<HrEmpDisciplines> findDisciplinByOffenderId(Integer offenderId);

    public List<HrDisciplineRequests> findPhaseOutList();

    public List<HrDisciplineFileUpload> getFileListByDiciplineId(HrDisciplineRequests disciplineRequests);

}
