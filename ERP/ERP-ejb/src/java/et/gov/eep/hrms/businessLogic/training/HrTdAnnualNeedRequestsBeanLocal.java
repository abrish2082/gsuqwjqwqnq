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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author Benin
 */
@Local
public interface HrTdAnnualNeedRequestsBeanLocal {

    public List<HrTdTrainerProfiles> hrTdTrainerProfilesList();

    public void saveOrUpdate(HrTdAnnualNeedRequests hrTdAnnualNeedRequests);

    public ArrayList<HrTdAnnualNeedRequests> searchByBugdetYear(HrTdAnnualNeedRequests hrTdAnnualNeedRequests);

    public void save(HrTdAnnualNeedRequests hrTdAnnualNeedRequests);

    public void edit(HrTdAnnualNeedRequests hrTdAnnualNeedRequests);

    public List<SelectItem> filterByStatus();

    public List<HrTdAnnualNeedRequests> searchAnnualNeed(int status, int budgetYear);

    public boolean isRequstExist(HrTdAnnualNeedRequests hrTdAnnualNeedRequests);

    public void delete(HrTdAnnualTraParticipants hrTdAnnualTraParticipants);

    public List<HrTdAnnualNeedRequests> findRequestForChecker();

    public List<HrTdAnnualNeedRequests> findRequestForApproval();

    public List<HrTdAnnualNeedRequests> loadTrainingRequestList(Status status);

    public List<HrTdAnnualNeedRequests> loadTrainingRequestListForTwo(Status status);

    public List<HrTdAnnualNeedRequests> loadTrainingRequestForChecker(int statusChecker);

    public List<HrTdAnnualNeedRequests> loadTrainingRequest(int status1);

}
