/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.mms.businessLogic;

import javax.ejb.Local;
import et.gov.eep.mms.entity.MmsNeedAssessmentDtl;

/**
 *
 * @author Minab
 */
@Local
public interface MmsNeedAssessmentDtlBeanLocal {

    public void edit(MmsNeedAssessmentDtl needAssessmentDtl);
}
