/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.ifrs.businessLogic;

import et.gov.eep.ifrs.entity.IfrsDataType;
import et.gov.eep.ifrs.mapper.IfrsDataTypeFacade;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class DataTypeBean implements DataTypeBeanLocal {
@EJB
IfrsDataTypeFacade ifrsDataTypeFacade;
    @Override
    public List<IfrsDataType> selectAllDataType() {
      return ifrsDataTypeFacade.findAll();
    }
    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
}
