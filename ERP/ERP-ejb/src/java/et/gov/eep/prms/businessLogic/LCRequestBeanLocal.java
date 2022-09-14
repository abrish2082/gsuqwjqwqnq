/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsLcRequest;
import et.gov.eep.prms.entity.PrmsServiceProvider;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author Dege
 */
@Local
public interface LCRequestBeanLocal {

    public void create(PrmsLcRequest PrmsLcRequest);

    public void update(PrmsLcRequest PrmsLcRequest);

    public List<PrmsLcRequest> findByRequestId(PrmsLcRequest PrmsLcRequest);

//    public PrmsLcRequest findByRequestIdObj(PrmsLcRequest PrmsLcRequest);
//       public int count();

    public PrmsLcRequest getlastLCReqNo();

    public List<PrmsContract> listOfContractNO();

    public List<PrmsServiceProvider> listOfServiceNO();

    public PrmsLcRequest getSelectedRequest(BigDecimal id);

    
}
