/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.displine;

import et.gov.eep.hrms.entity.displine.HrDisciplineOffencePenality;
import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenaltyTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.mapper.displine.HrDisciplineOffencePenalityFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrDisciplineOffencePenalityBean implements HrDisciplineOffencePenalityBeanLocal {

    @EJB
    HrDisciplineOffencePenalityFacade disciplineOffencePenaltyFacade;

    @Override
    public List<HrDisciplineOffencePenality> findAlls() {
        return disciplineOffencePenaltyFacade.findAll();

    }

    @Override
    public void saveOrUpdate(HrDisciplineOffencePenality OffencePenalty) {
        disciplineOffencePenaltyFacade.saveOrUpdate(OffencePenalty);
    }

    @Override
    public List<HrDisciplineOffencePenality> findByOffenceName(HrDisciplineOffenceTypes offenceTypes) {
        return disciplineOffencePenaltyFacade.findByOffenceName(offenceTypes);
    }

    @Override
    public HrDisciplineOffencePenality FindByOffenceTypeAndRepition(HrDisciplineOffencePenality hrDisciplineOffencePenality) {
        return disciplineOffencePenaltyFacade.FindByOffenceTypeAndRepition(hrDisciplineOffencePenality);
    }

    @Override
    public List<HrDisciplineOffencePenality> getOffenceAndPenalityListByName(String offenceName) {
        return disciplineOffencePenaltyFacade.getOffenceAndPenalityListByName(offenceName);
    }

    @Override
    public List<HrDisciplineOffenceTypes> findAllOffnces() {
        return disciplineOffencePenaltyFacade.findAllOffnces();
    }
}
