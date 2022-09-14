/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.fcms.businessLogic;

import et.gov.eep.fcms.mapper.ReportGeneratorFacade;
import java.util.Collection;
import java.util.HashMap;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author Me
 */
@Stateless
public class ReportGeneratorBean {
    @EJB
    private ReportGeneratorFacade reportGeneratorFacade;

    public Collection pentionReport(HashMap hashMap) {
        return reportGeneratorFacade.pentionReport(hashMap);
    }
    
    public Collection pentionSummery(HashMap hashMap) {
        return reportGeneratorFacade.pentionSummery(hashMap);
    }
    
    public Collection incomeTaxAttachment(HashMap hashMap) {
        return reportGeneratorFacade.incomeTaxAtachment(hashMap);
    }
    
    public Collection incomeTaxSummery(HashMap hashMap) {
        return reportGeneratorFacade.incomeTaxSummery(hashMap);
    }
    
    public Collection costSharingAttachmentReport(HashMap hashMap) {
        return reportGeneratorFacade.costSharingAttachmentReport(hashMap);
    }
    
    public Collection costSharingSummeryReport(HashMap hashMap) {
        return reportGeneratorFacade.costSharingSummeryReport(hashMap);
    }
}
