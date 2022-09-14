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
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;
import javax.faces.model.SelectItem;

/**
 *
 * @author user
 */
@Local
public interface HrAppealRequestsBeanLocal {

    public List<SelectItem> filterByStatus();

    public ArrayList<SelectItem> readPromotionCases(int applicantId);

    public HrPromotionRequests findByPromotionId(int promotionId);

    public ArrayList<SelectItem> readApprovedDisciplineCases(int applicantId);

    public HrDisciplineRequests findByDisciplineId(int disciplineId);

    public void saveAppealRequest(HrAppeals hrAppeals);

    public void updateAppealRequest(HrAppeals hrAppeals);

    public List<HrAppeals> loadAppealList(int status);

    /*
     methods for appeal approve pages
     */
    public void approveAppeal(HrAppeals hrAppeals);

    public List<HrAppeals> loadPenalityRequestList(Status status);

    public List<HrAppeals> loadPenalityRequestListForTwo(Status status);

    public List<HrAppeals> findRequestForApproval();

    public List<HrDisciplineRequests> findAllApprovedList(int applicantId);

}
