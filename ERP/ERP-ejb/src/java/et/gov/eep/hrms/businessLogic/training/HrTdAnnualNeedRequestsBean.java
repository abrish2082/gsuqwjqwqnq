/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.commonApplications.entity.Status;
import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdTrainerProfiles;
import et.gov.eep.hrms.mapper.training.HrTdAnnualNeedRequestsFacade;
import et.gov.eep.hrms.mapper.training.HrTdAnnualTraParticipantsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author Benin
 */
@Stateless
public class HrTdAnnualNeedRequestsBean implements HrTdAnnualNeedRequestsBeanLocal {

    @EJB
    HrTdAnnualNeedRequestsFacade hrTdAnnualNeedRequestsFacade;
    @EJB
    HrTdAnnualTraParticipantsFacade hrTdAnnualTraParticipantsFacade;

    @Override
    public void save(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        hrTdAnnualNeedRequestsFacade.create(hrTdAnnualNeedRequests);
    }

    @Override
    public void edit(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        hrTdAnnualNeedRequestsFacade.edit(hrTdAnnualNeedRequests);
    }

    @Override
    public void saveOrUpdate(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        hrTdAnnualNeedRequestsFacade.saveOrUpdate(hrTdAnnualNeedRequests);
    }

    @Override
    public List<HrTdTrainerProfiles> hrTdTrainerProfilesList() {
        return hrTdAnnualNeedRequestsFacade.hrTdTrainerProfilesList();
    }

    @Override
    public ArrayList<HrTdAnnualNeedRequests> searchByBugdetYear(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        return hrTdAnnualNeedRequestsFacade.searchByBugdetYear(hrTdAnnualNeedRequests);
    }

    @Override
    public List<SelectItem> filterByStatus() {
        List<SelectItem> selectItems = new ArrayList<>();
        selectItems.add(new SelectItem("null", "--- Filter by Status ---"));
        selectItems.add(new SelectItem(String.valueOf("0"), "Load Request List"));
        selectItems.add(new SelectItem(String.valueOf("1"), "Load Approved List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Rejectd  List"));
        selectItems.add(new SelectItem(String.valueOf("2"), "Load Resumitted  List"));
        return selectItems;
    }

    @Override
    public List<HrTdAnnualNeedRequests> searchAnnualNeed(int status, int budgetYear) {
        return hrTdAnnualNeedRequestsFacade.searchAnnualNeed(status, budgetYear);
    }

    @Override
    public boolean isRequstExist(HrTdAnnualNeedRequests hrTdAnnualNeedRequests) {
        return hrTdAnnualNeedRequestsFacade.isRequstExist(hrTdAnnualNeedRequests);
    }

    @Override
    public void delete(HrTdAnnualTraParticipants hrTdAnnualTraParticipants) {
        hrTdAnnualTraParticipantsFacade.remove(hrTdAnnualTraParticipants);
    }

    @Override
    public List<HrTdAnnualNeedRequests> findRequestForChecker() {
        return hrTdAnnualNeedRequestsFacade.findRequestForChecker();
    }

    @Override
    public List<HrTdAnnualNeedRequests> findRequestForApproval() {
        return hrTdAnnualNeedRequestsFacade.findRequestForApproval();
    }

    @Override
    public List<HrTdAnnualNeedRequests> loadTrainingRequestList(Status status) {
        return hrTdAnnualNeedRequestsFacade.loadTrainingRequestList(status);
    }

    @Override
    public List<HrTdAnnualNeedRequests> loadTrainingRequestListForTwo(Status status) {
        return hrTdAnnualNeedRequestsFacade.loadTrainingRequestListForTwo(status);
    }

    @Override
    public List<HrTdAnnualNeedRequests> loadTrainingRequestForChecker(int statusChecker) {
        return hrTdAnnualNeedRequestsFacade.loadTrainingRequestForChecker(statusChecker);
    }

    @Override
    public List<HrTdAnnualNeedRequests> loadTrainingRequest(int status1) {
        return hrTdAnnualNeedRequestsFacade.loadTrainingRequest(status1);
    }

}
