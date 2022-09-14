/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.compliantHandling;

import et.gov.eep.hrms.entity.compliantHandling.HrAppeals;
import et.gov.eep.hrms.entity.displine.HrDisciplineRequests;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Abdi_Pc
 */
@Local
public interface HrAppealsBeanLocal {

    public List<HrDisciplineRequests> findAppealRequestList();

    public void edit(HrAppeals appeals);

    public HrAppeals findByCaseId(HrAppeals hrAppeals);

}
