/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.fcms.entity.FmsLuCurrency;
import et.gov.eep.hrms.entity.address.HrAddresses;
import et.gov.eep.mms.entity.MmsItemRegistration;
import et.gov.eep.prms.entity.PrmsForeignExchange;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsPortEntry;
import javax.ejb.Local;
import java.util.List;

/**
 *
 * @author user
 */
@Local
public interface ForeignExchangeBeanLocal {

    public void create(PrmsForeignExchange prmsForeignExchange);

    public void update(PrmsForeignExchange prmsForeignExchange);

    public PrmsForeignExchange findByfenumberObj(PrmsForeignExchange prmsForeignExchange);

    public List<PrmsForeignExchange> findByfenumber(PrmsForeignExchange prmsForeignExchange);

    public List<PrmsSupplyProfile> VendorName();

    public List<PrmsPortEntry> fromPortEntry();

    public PrmsForeignExchange generateForeignExNo();

    public List<HrAddresses> fromHrAddress();

    public List<MmsItemRegistration> itemNameLists();

    public List<MmsItemRegistration> getItmNameList();

    public HrAddresses addressUpdate(HrAddresses hrAddresses);

    public List<HrAddresses> listofHrAddress();

    public FmsLuCurrency updateCurrencies(FmsLuCurrency freightCurrency);

    public List<PrmsForeignExchange> getForeignExchReqlist();

    public List<PrmsForeignExchange> getParamNameList();
}
