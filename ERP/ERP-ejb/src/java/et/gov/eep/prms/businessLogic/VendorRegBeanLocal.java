/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author user
 */
@Local
public interface VendorRegBeanLocal {

    /**
     *
     * @param prmsSupplyProfile
     */
    public void save(PrmsSupplyProfile prmsSupplyProfile);

    /**
     *
     * @param prmsSupplyProfile
     */
    public void update(PrmsSupplyProfile prmsSupplyProfile);

    /**
     *
     * @param prmsSupplyProfile
     * @return
     */
    public List<PrmsSupplyProfile> searchvendorName(PrmsSupplyProfile vendorName);

    /**
     *
     * @param prmsSupplyProfile
     * @return
     */
    public PrmsSupplyProfile getVdrCode(PrmsSupplyProfile prmsSupplyProfile);

    /**
     *
     * @return
     */
    public List<PrmsSupplyProfile> getVondorName();

    boolean checkVendorReg(PrmsSupplyProfile prmsSupplyProfile);

    public List<PrmsSupplyProfile> searchBySupplProfCode(PrmsSupplyProfile eepVendorReg);

    public PrmsSupplyProfile getSelectedRequest(String id);

    public PrmsSupplyProfile generatelastSuppNo();

    public List<PrmsSupplyProfile> getSuppByProfileId(String prefix, String currentYear);

    public List<ComLuCountry> getCountryList();

    public PrmsSupplyProfile getSupId(PrmsSupplyProfile prmsSupplyProfile);

    public List<PrmsLuVatTypeLookup> findVatType();

    public String getSupOrConOrConsNo(String profileType);

    public List<PrmsSupplyProfile> getParamNameList();
  
    public List<String> findAllSupplierStatuses();

    public int ConutBYSupplierType(String get);
}
