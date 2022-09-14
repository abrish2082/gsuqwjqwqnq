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
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface HrDisciplineOffencePenalityBeanLocal {

    public List<HrDisciplineOffencePenality> findAlls();

    public void saveOrUpdate(HrDisciplineOffencePenality OffencePenalty);

    public List<HrDisciplineOffencePenality> findByOffenceName(HrDisciplineOffenceTypes offenceTypes);

    public HrDisciplineOffencePenality FindByOffenceTypeAndRepition(HrDisciplineOffencePenality hrDisciplineOffencePenality);

    public List<HrDisciplineOffencePenality> getOffenceAndPenalityListByName(String offenceName);

    public List<HrDisciplineOffenceTypes> findAllOffnces();

}
