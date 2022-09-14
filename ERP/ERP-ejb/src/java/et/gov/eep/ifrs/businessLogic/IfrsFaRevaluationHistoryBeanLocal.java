/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.businessLogic;

import et.gov.eep.ifrs.entity.IfrsFaRevaluationHistory;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author memube
 */
@Local
public interface IfrsFaRevaluationHistoryBeanLocal {

    public void create(IfrsFaRevaluationHistory ifrsFaRevaluationHistory);

    public void edit(IfrsFaRevaluationHistory ifrsFaRevaluationHistory);

    public List<IfrsFaRevaluationHistory> findAll();

}
