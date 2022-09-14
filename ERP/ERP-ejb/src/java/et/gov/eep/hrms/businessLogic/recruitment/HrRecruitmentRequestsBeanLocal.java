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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author user
 */
@Local
public interface HrRecruitmentRequestsBeanLocal {

    public void save(HrRecruitmentRequests hrRecruitmentRequests);

    public void edit(HrRecruitmentRequests hrRecruitmentRequests);

    public HrDepartments getSelectDepartement(int departmentid);

    public HrRecruitmentRequests getSelectRecruitmentRequest(int recruitmentRequestId);

    public HrRecruitmentRequests getSelectRecruitmentRequestToApprove(int recruitmentRequestId);

    public ArrayList<HrRecruitmentRequests> getRecruitmentRequesttList();

    public ArrayList<HrRecruitmentRequests> findListByDate(HrRecruitmentRequests hrRecruitmentRequests);

    public ArrayList<HrRecruitmentRequests> findListReqToApproveByDate(HrRecruitmentRequests hrRecruitmentRequests);

    public ArrayList<HrRecruitmentRequests> findListByDepartment(HrDepartments hrDepartments);

    public HrJobTypes findByName(int jobid);

    List<HrJobTypes> listOfJobType(int departmentId);

    ArrayList<String> getRecruitmentBatchCodes();

    public ArrayList<HrRecruitmentRequests> getRequstesByBachCode(String batchCode);

    public HrRecruitmentRequests getRequstesByBachCodes(String batchCode);

    public String totalNoAllowedJob(HrDepartments department, HrJobTypes job);

    public int totalNoEmployeesInDepJob(HrDepartments department, HrJobTypes job);

    public HrRecruitmentRequests findListByDate1(HrRecruitmentRequests hrRecruitmentRequests);

    public ArrayList<HrRecruitmentRequests> findList();

    public ArrayList<HrRecruitmentRequests> findByDetAndDate(HrDepartments hrDepartments, HrRecruitmentRequests hrRecruitmentRequests);

    public List<HrRecruitmentRequests> findListByDateAndDeptId(HrRecruitmentRequests hrRecruitmentRequests);

    public List<HrDepartments> findaadepts();

    public List<HrHrpRecruitmentRequest> findbyHowTbeFilld(String howToBeFilled);

    public HrRecruitmentRequests getByRecruitmentReqstID(String recruitmentReqstID);

    public List<SelectItem> filterByStatus();

    public List<HrRecruitmentRequests> loadRecruitmentList(int status);

    public List<HrRecruitmentRequests> loadRecLists(int status);

    public List<HrRecruitmentRequests> findRecPreparedList();

    public List<HrRecruitmentRequests> findRecCheckedList();

    public List<HrRecruitmentRequests> findWfToModify();

    public List<HrRecruitmentRequests> findWfToApprove();

    public List<HrRecruitmentRequests> loadToApproveInvoiceList(Status status, int userId);

    public List<HrRecruitmentRequests> loadToModifyInvoiceList(Status status, int userId);

    public List<HrRecruitmentRequests> loadRequestList(Status status, int userId);

    public List<HrRecruitmentRequests> loadRecuitmentList(Status status, int userId);
}
