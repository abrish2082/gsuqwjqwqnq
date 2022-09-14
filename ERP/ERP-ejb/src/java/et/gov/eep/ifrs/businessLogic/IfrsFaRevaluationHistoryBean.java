/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.businessLogic;

import et.gov.eep.ifrs.entity.IfrsFaRevaluationHistory;
import et.gov.eep.ifrs.mapper.IfrsFaRevaluationHistoryFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author memube
 */
@Stateless
public class IfrsFaRevaluationHistoryBean implements IfrsFaRevaluationHistoryBeanLocal {

    @EJB
    private IfrsFaRevaluationHistoryFacade ifrsFaRevaluationHistoryFacade;

    @Override
    public void create(IfrsFaRevaluationHistory ifrsFaRevaluationHistory) {
        ifrsFaRevaluationHistoryFacade.create(ifrsFaRevaluationHistory);
    }

    @Override
    public void edit(IfrsFaRevaluationHistory ifrsFaRevaluationHistory) {
        ifrsFaRevaluationHistoryFacade.edit(ifrsFaRevaluationHistory);
    }

    @Override
    public List<IfrsFaRevaluationHistory> findAll() {
        return ifrsFaRevaluationHistoryFacade.findAll();
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
