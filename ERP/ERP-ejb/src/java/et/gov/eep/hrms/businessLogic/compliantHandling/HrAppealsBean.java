/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.compliantHandling;

import et.gov.eep.hrms.entity.compliantHandling.HrAppeals;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import et.gov.eep.hrms.mapper.complaintHandling.HrAppealsFacade;
import et.gov.eep.hrms.mapper.displine.HrDisciplineRequestsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.faces.model.SelectItem;

/**
 *
 * @author Abdi_Pc
 */
@Stateless
public class HrAppealsBean implements HrAppealsBeanLocal {

    @EJB
    HrAppealsFacade appealsFacade;

    @EJB
    HrDisciplineRequestsFacade disciplineRequestsFacade;

    @Override
    public List<HrDisciplineRequests> findAppealRequestList() {
        return disciplineRequestsFacade.readApprovedDisciplinaryAppeals();
    }

    @Override
    public void edit(HrAppeals appeals) {
        appealsFacade.edit(appeals);
    }

    @Override
    public HrAppeals findByCaseId(HrAppeals hrAppeals) {
    return disciplineRequestsFacade.findByCaseId(hrAppeals);
    }
}
