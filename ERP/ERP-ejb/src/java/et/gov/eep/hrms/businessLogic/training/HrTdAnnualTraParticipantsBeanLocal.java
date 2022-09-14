/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import java.util.ArrayList;
import javax.ejb.Local;

/**
 *
 * @author Benin
 */
@Local
public interface HrTdAnnualTraParticipantsBeanLocal {

    public ArrayList<HrTdAnnualTraParticipants> SearchByAnnTraNeedId(HrTdAnnualTraParticipants hrTdAnnualTraParticipants);

    public ArrayList<HrTdAnnualTraParticipants> SrchAnnTraNeedIdToBeApproved(HrTdAnnualTraParticipants hrTdAnnualTraParticipants);

    public void edit(HrTdAnnualTraParticipants hrTdAnnualTraParticipants);
}
