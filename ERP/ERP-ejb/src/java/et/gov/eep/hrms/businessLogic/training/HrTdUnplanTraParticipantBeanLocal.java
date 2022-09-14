/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.training;

import et.gov.eep.hrms.entity.training.HrTdUnplanTraParticipant;
import java.math.BigDecimal;
import javax.ejb.Local;

/**
 *
 * @author Abdi_Pc
 */
@Local
public interface HrTdUnplanTraParticipantBeanLocal {

    public void saveOrUpdate(HrTdUnplanTraParticipant hrTdUnplanTraParticipant);

    public void edit(HrTdUnplanTraParticipant hrTdUnplanTraParticipant);

    public HrTdUnplanTraParticipant getSelectedRequest(BigDecimal id);

   
    
}
