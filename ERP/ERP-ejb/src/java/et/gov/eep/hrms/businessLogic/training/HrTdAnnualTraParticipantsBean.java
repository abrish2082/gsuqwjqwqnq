/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdAnnualTraParticipants;
import et.gov.eep.hrms.mapper.training.HrTdAnnualTraParticipantsFacade;
import java.util.ArrayList;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Benin
 */
@Stateless
public class HrTdAnnualTraParticipantsBean implements HrTdAnnualTraParticipantsBeanLocal {

    @EJB
    HrTdAnnualTraParticipantsFacade hrTdAnnualTraParticipantsFacade;

    public void save(HrTdAnnualTraParticipants hrTdAnnualTraParticipants) {
        hrTdAnnualTraParticipantsFacade.saveOrUpdate(hrTdAnnualTraParticipants);
    }

    @Override
    public ArrayList<HrTdAnnualTraParticipants> SearchByAnnTraNeedId(HrTdAnnualTraParticipants hrTdAnnualTraParticipants) {
        return hrTdAnnualTraParticipantsFacade.SearchByAnnTraNeedId(hrTdAnnualTraParticipants);
    }

    @Override
    public ArrayList<HrTdAnnualTraParticipants> SrchAnnTraNeedIdToBeApproved(HrTdAnnualTraParticipants hrTdAnnualTraParticipants) {
        return hrTdAnnualTraParticipantsFacade.SearchByAnnTraNeedId(hrTdAnnualTraParticipants);
    }

    @Override
    public void edit(HrTdAnnualTraParticipants hrTdAnnualTraParticipants) {
        hrTdAnnualTraParticipantsFacade.edit(hrTdAnnualTraParticipants);
    }
}
