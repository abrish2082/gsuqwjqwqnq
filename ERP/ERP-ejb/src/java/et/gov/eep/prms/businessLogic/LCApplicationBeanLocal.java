/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.prms.entity.PrmsContract;
import et.gov.eep.prms.entity.PrmsLcApplication;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import java.math.BigDecimal;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface LCApplicationBeanLocal {

    public List<PrmsLcApplication> searchByLCAppNo(PrmsLcApplication prmsLcApplication);

    public void update(PrmsLcApplication prmsLcApplication);

    public void create(PrmsLcApplication prmsLcApplication);

    public PrmsLcApplication getSelectedLCApp(BigDecimal id);

    public PrmsLcApplication getLastLCAppNo();

    public List<PrmsSupplyProfile> listOfSuppName();
    
    public List<PrmsContract> listOfContractNO();
    
}
