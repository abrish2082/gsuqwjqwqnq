/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdAnnualNeedRequests;
import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.entity.training.HrTdAnnualTrainingNeeds;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Benin
 */
@Local
public interface HrTdAnnualTrainingNeedsBeanLocal {

    public ArrayList<HrTdAnnualTrainingNeeds> SrchAnnTraNeedToBeApproved(HrTdAnnualTrainingNeeds hrTdAnnualTrainingNeeds);
}
