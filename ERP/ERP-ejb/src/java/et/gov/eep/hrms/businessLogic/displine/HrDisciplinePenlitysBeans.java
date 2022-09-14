/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.displine;

import et.gov.eep.hrms.entity.displine.HrDisciplineOffenceTypes;
import et.gov.eep.hrms.entity.displine.HrDisciplinePenlitys;
import et.gov.eep.hrms.mapper.displine.HrDisciplinePenlitysFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrDisciplinePenlitysBeans implements HrDisciplinePenlitysBeansLocal {

    @EJB
    HrDisciplinePenlitysFacade hrDisciplinePenlitysFacade;

    @Override
    public List<HrDisciplinePenlitys> findByOffenceId(HrDisciplineOffenceTypes offenceTypes) {
        return hrDisciplinePenlitysFacade.findByOffenceId(offenceTypes);
    }
}
