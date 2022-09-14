/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import et.gov.eep.mms.entity.MmsNeedAssessmentDtl;
import et.gov.eep.mms.mapper.MmsNeedAssessmentDtlFacade;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Sadik
 */
@Stateless
public class MmsNeedAssessmentDtlBean implements MmsNeedAssessmentDtlBeanLocal {
    @EJB
    MmsNeedAssessmentDtlFacade needAssessmentDtlFacade;
    @Override
    public void edit(MmsNeedAssessmentDtl needAssessmentDtl) {
        needAssessmentDtlFacade.edit(needAssessmentDtl);
    }

   
}
