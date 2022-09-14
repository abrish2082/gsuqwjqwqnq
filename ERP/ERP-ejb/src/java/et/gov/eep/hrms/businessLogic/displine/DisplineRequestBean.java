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
import et.gov.eep.hrms.mapper.complaintHandling.HrAppealsFacade;
import et.gov.eep.hrms.mapper.displine.HrDisciplineFileUploadFacade;
import et.gov.eep.hrms.mapper.displine.HrDisciplineRequestsFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class DisplineRequestBean implements DisplineRequestBeanLocal {

    @EJB
    HrDisciplineRequestsFacade disciplineRequestsFacade;

    @EJB
    HrAppealsFacade appealsFacade;

    @EJB
    HrDisciplineFileUploadFacade hrDisciplineFileUploadFacade;

    @Override
    public HrDisciplineRequests findByDisciplineId(HrDisciplineRequests hrDisciplineRequests) {
        return disciplineRequestsFacade.findByDisciplineId(hrDisciplineRequests);
    }

    @Override
    public HrDisciplineRequests findById(String toString) {
        return disciplineRequestsFacade.find(Integer.valueOf(toString));
    }

    @Override
    public List<HrDisciplineRequests> findRequests() {
        return disciplineRequestsFacade.displayRequests();

    }

    @Override
    public List<HrDisciplineRequests> findRequestsApproved() {
        return disciplineRequestsFacade.displayRequestsApprove();
    }

    @Override
    public HrDisciplineRequests displayAllRequests(String toString) {
        return disciplineRequestsFacade.find(Integer.valueOf(toString));
    }

    @Override
    public List<HrDisciplineRequests> findApprovedDisciplineCases(int applicantId) {
        return disciplineRequestsFacade.findApprovedDisciplineCases(applicantId);
    }

    @Override
    public List<HrDisciplineRequests> searchEmployeeByName(HrEmployees hrEmployeesRequester) {
        return disciplineRequestsFacade.searchEmployeeByName(hrEmployeesRequester);
    }

    @Override
    public void saveOrUpdate(HrDisciplineRequests disciplineRequests) {
        disciplineRequestsFacade.saveOrUpdate(disciplineRequests);
    }

    @Override
    public List<HrDisciplineRequests> searchEmployeeByOffenderName(HrEmployees hrEmployeesAccuser) {
        return disciplineRequestsFacade.searchEmployeeByOffenderName(hrEmployeesAccuser);
    }

    @Override
    public List<HrDisciplineRequests> searchEmployeeByOffenderAndRequesterName(HrEmployees hrEmployeesAccuser, HrEmployees hrEmployeesRequester) {
        return disciplineRequestsFacade.searchEmployeeByOffenderAndRequesterName(hrEmployeesRequester, hrEmployeesAccuser);
    }

    @Override
    public List<HrDisciplineRequests> getByStatus(HrDisciplineRequests disciplineRequests) {
        return disciplineRequestsFacade.getByStatus(disciplineRequests);
    }

    @Override
    public void edit(HrDisciplineRequests disciplineRequests) {
        disciplineRequestsFacade.edit(disciplineRequests);
    }

    @Override
    public List<HrDisciplineRequests> loadPenalityRequest(int status) {
        return disciplineRequestsFacade.loadPenalityRequest(status);
    }

    @Override
    public List<HrDisciplineRequests> findByOffenderId(HrDisciplineRequests disciplineRequests) {
        return disciplineRequestsFacade.findByOffenderId(disciplineRequests);
    }

    @Override
    public HrDisciplineRequests findDisciplineReqByOffenderIdAndOffenceType(HrDisciplineRequests disciplineRequests) {
        return disciplineRequestsFacade.findDisciplineReqByOffenderIdAndOffenceType(disciplineRequests);
    }

    @Override
    public HrDisciplineRequests findDisciplineReqRecordByOffenderIdAndOffenceType(HrDisciplineRequests disciplineRequests) {
        return disciplineRequestsFacade.findDisciplineReqRecordByOffenderIdAndOffenceType(disciplineRequests);
    }

    @Override
    public HrAppeals findByCaseId(HrAppeals hrAppeals) {
        return disciplineRequestsFacade.findByCaseId(hrAppeals);
    }

    @Override
    public void edit(HrAppeals appals) {
        appealsFacade.edit(appals);
    }

    @Override
    public void create(HrDisciplineFileUpload hrDisciplineFileUpload) {
        hrDisciplineFileUploadFacade.create(hrDisciplineFileUpload);
    }

    @Override
    public List<HrDisciplineFileUpload> findDocumentId(HrDisciplineRequests disciplineRequests) {
        return disciplineRequestsFacade.findDocumentId(disciplineRequests);
    }

    @Override
    public HrDisciplineFileUpload findFileUploadId(HrDisciplineRequests disciplineRequests) {
        return disciplineRequestsFacade.findFileUploadId(disciplineRequests);
    }

    @Override
    public void remove(HrDisciplineFileUpload hrDisciplineFileUpload) {
        hrDisciplineFileUploadFacade.remove(hrDisciplineFileUpload);
    }

//    @Override
//    public List<HrDisciplineRequests> loadPenalityRequestList(String status, String userId) {
//        return disciplineRequestsFacade.loadPenalityRequestList(status, userId);
//    }
    @Override
    public List<HrDisciplineRequests> loadPenalityRequestList(Status status) {
        return disciplineRequestsFacade.loadPenalityRequestList(status);
    }

    @Override
    public List<HrDisciplineRequests> findRequestForApproval() {
        return disciplineRequestsFacade.findRequestForApproval();
    }

    @Override
    public List<HrDisciplineRequests> loadPenalityRequestListForTwo(Status status) {
        return disciplineRequestsFacade.loadPenalityRequestListForTwo(status);
    }

    @Override
    public HrDisciplineRequests findByIds(HrDisciplineRequests disciplineRequests) {
        return disciplineRequestsFacade.findByIds(disciplineRequests);
    }

    //<editor-fold defaultstate="collapsed" desc="adjudicate disciplinary appeal">
    @Override
    public List<HrDisciplineRequests> readApprovedDisciplinaryAppeals() {
        return disciplineRequestsFacade.readApprovedDisciplinaryAppeals();
    }
//</editor-fold>

    @Override
    public List<HrEmpDisciplines> findDisciplinByOffenderId(Integer offenderId) {
        return disciplineRequestsFacade.findDisciplinByOffenderId(offenderId);
    }

    @Override
    public List<HrDisciplineRequests> findPhaseOutList() {
       return disciplineRequestsFacade.findPhaseOutList();
    }

    @Override
    public List<HrDisciplineFileUpload> getFileListByDiciplineId(HrDisciplineRequests disciplineRequests) {
        return disciplineRequestsFacade.getFileListByDiciplineId(disciplineRequests);
    }
}
