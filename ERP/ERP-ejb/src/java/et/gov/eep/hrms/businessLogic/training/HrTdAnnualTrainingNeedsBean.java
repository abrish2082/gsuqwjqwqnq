/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import et.gov.eep.hrms.mapper.training.HrTdAnnualNeedRequestsFacade;
import et.gov.eep.hrms.mapper.training.HrTdAnnualTrainingNeedsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Benin
 */
@Stateless
public class HrTdAnnualTrainingNeedsBean implements HrTdAnnualTrainingNeedsBeanLocal {

    @EJB
    HrTdAnnualTrainingNeedsFacade hrTdAnnualTrainingNeedsFacade;

    @Override
    public ArrayList<HrTdAnnualTrainingNeeds> SrchAnnTraNeedToBeApproved(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds) {
        return hrTdAnnualTrainingNeedsFacade.SrchAnnTraNeedToBeApproved(hrTdAnnualTrainingNeeds);
    }

    }
