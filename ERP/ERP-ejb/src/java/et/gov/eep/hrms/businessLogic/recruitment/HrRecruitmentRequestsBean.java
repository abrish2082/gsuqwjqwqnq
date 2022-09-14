/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.recruitment;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.organization.HrDepartments;
import et.gov.eep.hrms.entity.organization.HrJobTypes;
import et.gov.eep.hrms.entity.planning.HrHrpRecruitmentRequest;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import et.gov.eep.hrms.mapper.organization.HrDepartmentsFacade;
import et.gov.eep.hrms.mapper.recruitment.HrRecruitmentRequestsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author Biniyam Mathewos
 */
@Stateless
public class HrRecruitmentRequestsBean implements HrRecruitmentRequestsBeanLocal {

    @EJB
    HrRecruitmentRequestsFacade hrRecruitmentRequestsFacade;

    /**
     *
     * @param hrRecruitmentRequests
     */
    @Override
    public void save(HrRecruitmentRequests hrRecruitmentRequests) {
        hrRecruitmentRequestsFacade.create(hrRecruitmentRequests);
    }
    @EJB
    HrDepartmentsFacade hrDepartmentsFacade;

    /**
     *
     * @param hrRecruitmentRequests
     */
    @Override
    public void edit(HrRecruitmentRequests hrRecruitmentRequests) {
        hrRecruitmentRequestsFacade.edit(hrRecruitmentRequests);
    }

    /**
     *
     * @param departmentid
     * @return
     */
    @Override
    public HrDepartments getSelectDepartement(int departmentid) {
        return hrRecruitmentRequestsFacade.getSelectDepartement(departmentid);
    }

    /**
     *
     * @param recruitmentRequestId
     * @return
     */
    @Override
    public HrRecruitmentRequests getSelectRecruitmentRequest(int recruitmentRequestId) {
        return hrRecruitmentRequestsFacade.getSelectRequest(recruitmentRequestId);
    }

    /**
     *
     * @param recruitmentRequestId
     * @return
     */
    @Override
    public HrRecruitmentRequests getSelectRecruitmentRequestToApprove(int recruitmentRequestId) {
        return hrRecruitmentRequestsFacade.getSelectRequestToApprove(recruitmentRequestId);
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<HrRecruitmentRequests> getRecruitmentRequesttList() {
        return hrRecruitmentRequestsFacade.findAll();
    }

    @Override
    public ArrayList<HrRecruitmentRequests> findListByDate(HrRecruitmentRequests hrRecruitmentRequests) {
        return hrRecruitmentRequestsFacade.findListByDate(hrRecruitmentRequests);
    }

    @Override
    public ArrayList<HrRecruitmentRequests> findListReqToApproveByDate(HrRecruitmentRequests hrRecruitmentRequests) {
        return hrRecruitmentRequestsFacade.findListReqToApproveByDate(hrRecruitmentRequests);
    }

    @Override
    public ArrayList<HrRecruitmentRequests> findListByDepartment(HrDepartments hrDepartments) {
        return hrRecruitmentRequestsFacade.findListByDepartment(hrDepartments);
    }

    @Override
    public ArrayList<HrRecruitmentRequests> findByDetAndDate(HrDepartments hrDepartments, HrRecruitmentRequests hrRecruitmentRequests) {
        return hrRecruitmentRequestsFacade.findByDetAndDate(hrDepartments, hrRecruitmentRequests);
    }

    @Override
    public HrRecruitmentRequests findListByDate1(HrRecruitmentRequests hrRecruitmentRequests) {
        return hrRecruitmentRequestsFacade.findListByDate1(hrRecruitmentRequests);
    }

    @Override
    public ArrayList<HrRecruitmentRequests> findList() {
        return hrRecruitmentRequestsFacade.findList();
    }

    /**
     *
     * @param jobid
     * @return
     */
    @Override
    public HrJobTypes findByName(int jobid) {
        return hrRecruitmentRequestsFacade.findByName(jobid);
    }
//    @Override
//    public HrJobEducQualifications findByJobId(HrJobTypes name){
//         return  hrRecruitmentRequestsFacade.findByJobId(name);
//    }

    /**
     *
     * @param departmentId
     * @return
     */
    @Override
    public List<HrJobTypes> listOfJobType(int departmentId) {
        return hrRecruitmentRequestsFacade.listOfJobType(departmentId); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<String> getRecruitmentBatchCodes() {
        return hrRecruitmentRequestsFacade.findBatchCode();
    }

    /**
     *
     * @param batchCode
     * @return
     */
    @Override
    public ArrayList<HrRecruitmentRequests> getRequstesByBachCode(String batchCode) {
        return hrRecruitmentRequestsFacade.getRequstesByBachCode(batchCode);
    }

    /**
     *
     * @param batchCode
     * @return
     */
    @Override
    public HrRecruitmentRequests getRequstesByBachCodes(String batchCode) {
        return hrRecruitmentRequestsFacade.getRequstesByBachCodes(batchCode);
    }

    /**
     *
     * @param department
     * @param job
     * @return
     */
    @Override
    public String totalNoAllowedJob(HrDepartments department, HrJobTypes job) {
        return hrRecruitmentRequestsFacade.totalNoAllowedJob(department, job);
    }

    /**
     *
     * @param department
     * @param job
     * @return
     */
    @Override
    public int totalNoEmployeesInDepJob(HrDepartments department, HrJobTypes job) {
        return hrRecruitmentRequestsFacade.totalNoEmployeesInDepJob(department, job);
    }

    @Override
    public List<HrRecruitmentRequests> findListByDateAndDeptId(HrRecruitmentRequests hrRecruitmentRequests) {
        return hrRecruitmentRequestsFacade.findListByDateAndDeptId(hrRecruitmentRequests);
    }

    @Override
    public List<HrDepartments> findaadepts() {
        return hrDepartmentsFacade.findAll();
    }

    @Override
    public List<HrHrpRecruitmentRequest> findbyHowTbeFilld(String howToBeFilled) {
        return hrRecruitmentRequestsFacade.findByHowToBeFilled(howToBeFilled);
    }

    @Override
    public HrRecruitmentRequests getByRecruitmentReqstID(String recruitmentReqstID) {
        return hrRecruitmentRequestsFacade.getByRecruitmentReqstID(recruitmentReqstID);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf(0), "Load request list"));
        selectItems.add(new SelectItem(String.valueOf(1), "Load approved list"));
        selectItems.add(new SelectItem(String.valueOf(2), "Load rejected list"));
        return selectItems;
    }

    @Override
    public List<HrRecruitmentRequests> loadRecruitmentList(int status) {
        return hrRecruitmentRequestsFacade.loadRecruitmentList(status);
    }

    @Override
    public List<HrRecruitmentRequests> loadRecLists(int status) {
        return hrRecruitmentRequestsFacade.loadRecLists(status);
    }

    @Override
    public List<HrRecruitmentRequests> findRecPreparedList() {
        return hrRecruitmentRequestsFacade.findRecPreparedList();
    }

    @Override
    public List<HrRecruitmentRequests> findRecCheckedList() {
        return hrRecruitmentRequestsFacade.findRecCheckedList();
    }

    @Override
    public List<HrRecruitmentRequests> findWfToModify() {
        return hrRecruitmentRequestsFacade.findWfToModify();
    }

    @Override
    public List<HrRecruitmentRequests> findWfToApprove() {
        return hrRecruitmentRequestsFacade.findWfToApprove();
    }

    @Override
    public List<HrRecruitmentRequests> loadToApproveInvoiceList(Status status, int userId) {
        return hrRecruitmentRequestsFacade.loadToApproveInvoiceList(status, userId);
    }

    @Override
    public List<HrRecruitmentRequests> loadToModifyInvoiceList(Status status, int userId) {
        return hrRecruitmentRequestsFacade.loadToModifyInvoiceList(status, userId);
    }

    @Override
    public List<HrRecruitmentRequests> loadRequestList(Status status, int userId) {
        return hrRecruitmentRequestsFacade.loadRequestList(status, userId);
    }

    @Override
    public List<HrRecruitmentRequests> loadRecuitmentList(Status status, int userId) {
        return hrRecruitmentRequestsFacade.loadRecuitmentList(status, userId);
    }
}
