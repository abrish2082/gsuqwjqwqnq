/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.compliantHandling;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.compliantHandling.HrAppeals;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.entity.promotion.HrPromotionRequests;
import et.gov.eep.hrms.mapper.complaintHandling.HrAppealsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author user
 */
@Stateless
public class HrAppealRequestsBean implements HrAppealRequestsBeanLocal {

    @EJB
    HrAppealsFacade appealsFacade;

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Select ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Rejected List"));
        selectItems.add(new SelectItem(String.valueOf("3"), "Load Resubmit List"));
        return selectItems;
    }

    @Override
    public ArrayList<SelectItem> readPromotionCases(int applicantId) {
        ArrayList<SelectItem> readPromotionCases = new ArrayList<>();
        List<HrPromotionRequests> getPromotionCases = appealsFacade.readPromotionCases(applicantId);
        if (getPromotionCases != null && !getPromotionCases.isEmpty()) {
            for (HrPromotionRequests hrPromotionRequests : getPromotionCases) {
                readPromotionCases.add(new SelectItem(
                        hrPromotionRequests.getId(),
                        hrPromotionRequests.getAdvertJobId().getJobId().getJobTitle()));
            }
            readPromotionCases.add(0, new SelectItem(null, "-- Select Promotion Case --"));
        }
        return readPromotionCases;
    }

    @Override
    public HrPromotionRequests findByPromotionId(int promotionId) {
        return appealsFacade.findByPromotionId(promotionId);
    }

    @Override
    public ArrayList<SelectItem> readApprovedDisciplineCases(int applicantId) {
        ArrayList<SelectItem> readDisciplineCases = new ArrayList<>();
        List<HrDisciplineRequests> getDisciplineCases = appealsFacade.readApprovedDisciplineCases(applicantId);
        if (getDisciplineCases != null && !getDisciplineCases.isEmpty()) {
            for (HrDisciplineRequests hrDisciplineRequests : getDisciplineCases) {
                readDisciplineCases.add(new SelectItem(
                        hrDisciplineRequests.getId(),
                        hrDisciplineRequests.getOffenceTypeId().getOffenceName()));
            }
            readDisciplineCases.add(0, new SelectItem(null, "-- Select Discipline Case --"));
        }
        return readDisciplineCases;
    }

    @Override
    public HrDisciplineRequests findByDisciplineId(int disciplineId) {
        return appealsFacade.findByDisciplineId(disciplineId);
    }

    @Override
    public void saveAppealRequest(HrAppeals hrAppeals) {
        appealsFacade.create(hrAppeals);
    }

    @Override
    public void updateAppealRequest(HrAppeals hrAppeals) {
        appealsFacade.edit(hrAppeals);
    }

    @Override
    public List<HrAppeals> loadAppealList(int status) {
        return appealsFacade.loadAppealList(status);

    }

    /*
     methods for appeal approve pages
     */
    @Override
    public void approveAppeal(HrAppeals hrAppeals) {
        appealsFacade.edit(hrAppeals);
    }

    @Override
    public List<HrAppeals> loadPenalityRequestList(Status status) {
        return appealsFacade.loadPenalityRequestList(status);
    }

    @Override
    public List<HrAppeals> loadPenalityRequestListForTwo(Status status) {
        return appealsFacade.loadPenalityRequestListForTwo(status);
    }

    @Override
    public List<HrAppeals> findRequestForApproval() {
        return appealsFacade.findRequestForApproval();
    }


    @Override
    public List<HrDisciplineRequests> findAllApprovedList(int applicantId) {
       return appealsFacade.findAllApprovedList(applicantId);
    }
}
