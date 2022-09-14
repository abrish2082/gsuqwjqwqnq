/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdUnplanTraParticipant;
import et.gov.eep.hrms.mapper.training.HrTdUnplanTraParticipantFacade;
import java.math.BigDecimal;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Abdi_Pc
 */
@Stateless
public class HrTdUnplanTraParticipantBean implements HrTdUnplanTraParticipantBeanLocal {
    @EJB
    HrTdUnplanTraParticipantFacade hrTdUnplanTraParticipantFacade;

    @Override
    public void saveOrUpdate(HrTdUnplanTraParticipant hrTdUnplanTraParticipant) {
       hrTdUnplanTraParticipantFacade.saveOrUpdate(hrTdUnplanTraParticipant);
    }


    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")

    @Override
    public void edit(HrTdUnplanTraParticipant hrTdUnplanTraParticipant) {
       hrTdUnplanTraParticipantFacade.saveOrUpdate(hrTdUnplanTraParticipant);
    }

    @Override
    public HrTdUnplanTraParticipant getSelectedRequest(BigDecimal id) {
        return  hrTdUnplanTraParticipantFacade.getSelectedRequest(id);
    }
}
