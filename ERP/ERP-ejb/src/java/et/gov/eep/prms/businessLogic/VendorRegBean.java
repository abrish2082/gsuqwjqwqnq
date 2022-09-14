/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.prms.businessLogic;

import et.gov.eep.commonApplications.businessLogic.EthiopianCalendarBeanLocal;
import et.gov.eep.commonApplications.entity.ComLuCountry;
import et.gov.eep.commonApplications.mapper.ComLuCountryFacade;
import et.gov.eep.prms.entity.PrmsSupplyProfile;
import et.gov.eep.prms.entity.PrmsLuVatTypeLookup;
import et.gov.eep.prms.mapper.PrmsVatTypeLookupFacade;
import et.gov.eep.prms.mapper.PrmsSupplyProfileFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class VendorRegBean implements VendorRegBeanLocal {

    // <editor-fold defaultstate="collapsed" desc="Inject EJBs">
    @EJB
    PrmsSupplyProfileFacade eepVendorRegFacade;
    @EJB
    ComLuCountryFacade comLuCountryFacade;
    @EJB
    PrmsVatTypeLookupFacade vatTypeLookupFacade;
    @EJB
    EthiopianCalendarBeanLocal ethiopianCalendarBeanLocal;
    // </editor-fold>

    /**
     *
     * @param prmsSupplyProfile
     */
    @Override
    public void save(PrmsSupplyProfile prmsSupplyProfile) {
        eepVendorRegFacade.create(prmsSupplyProfile);
    }

    /**
     *
     * @param prmsSupplyProfile
     * @return
     */
    @Override
    public List<PrmsSupplyProfile> searchvendorName(PrmsSupplyProfile vendorName) {
        return eepVendorRegFacade.searchvendName(vendorName);
    }

    /**
     *
     * @param prmsSupplyProfile
     * @return
     */
    @Override
    public PrmsSupplyProfile getVdrCode(PrmsSupplyProfile prmsSupplyProfile) {
        return eepVendorRegFacade.searchvdCode(prmsSupplyProfile);
    }

    /**
     *
     * @return
     */
    @Override
    public List<PrmsSupplyProfile> getVondorName() {
        return eepVendorRegFacade.findSuppliers();
    }

    /**
     *
     * @param prmsSupplyProfile
     */
    @Override
    public void update(PrmsSupplyProfile prmsSupplyProfile) {
        eepVendorRegFacade.edit(prmsSupplyProfile);
    }

    @Override
    public boolean checkVendorReg(PrmsSupplyProfile prmsSupplyProfile) {
        return eepVendorRegFacade.checkVendorRegByNamCode(prmsSupplyProfile);
    }

    @Override
    public List<PrmsSupplyProfile> searchBySupplProfCode(PrmsSupplyProfile eepVendorReg) {
        return eepVendorRegFacade.getSuppByProfCode(eepVendorReg);
    }

    @Override
    public PrmsSupplyProfile getSelectedRequest(String id) {
        return eepVendorRegFacade.getSelectedId(id);
    }

    @Override
    public PrmsSupplyProfile generatelastSuppNo() {
        return eepVendorRegFacade.getlastSupNo();
    }

    @Override
    public List<PrmsSupplyProfile> getSuppByProfileId(String prefix, String currentYear) {
        return eepVendorRegFacade.getSuppByProfileId(prefix, currentYear);
    }

    @Override
    public List<ComLuCountry> getCountryList() {
        return comLuCountryFacade.getCountries();
    }

    @Override
    public PrmsSupplyProfile getSupId(PrmsSupplyProfile prmsSupplyProfile) {
        return eepVendorRegFacade.getBySuppId(prmsSupplyProfile);
    }

    @Override
    public List<PrmsLuVatTypeLookup> findVatType() {
        return vatTypeLookupFacade.findVatType();
    }

    @Override
    public String getSupOrConOrConsNo(String profileType) {
        String eYear = ethiopianCalendarBeanLocal.getEthiopianCurrentDate();
        String supplierId = null;
        String prefix = null;
        int maxNo = 0;
        if (profileType.equalsIgnoreCase("Supplier")) {
            prefix = "SP";
        } else if (profileType.equalsIgnoreCase("Contractor")) {
            prefix = "CON";
        } else if (profileType.equalsIgnoreCase("Consultant")) {
            prefix = "CNS";
        }
        List<PrmsSupplyProfile> supplyProfiles = eepVendorRegFacade.getSuppByProfileId(prefix, eYear);
        for (int i = 0; i < supplyProfiles.size(); i++) {
            supplierId = supplyProfiles.get(i).getVendorCode();
            String[] lastInspNos = supplierId.split("-");
            String lastDatesPatern = lastInspNos[1];
            String[] lastDatesPaterns = lastDatesPatern.split("/");
            int increment = Integer.parseInt(lastDatesPaterns[0]);
            if (maxNo < increment) {
                maxNo = increment;
            }
        }
        maxNo = maxNo + 1;
        supplierId = prefix + "-" + maxNo + "/" + eYear;
        return supplierId;
    }

    @Override
    public List<PrmsSupplyProfile> getParamNameList() {
        return eepVendorRegFacade.getParamNameList();
    }

    @Override
    public List<String> findAllSupplierStatuses() {
        return eepVendorRegFacade.findAllSupplierStatuses();
    }

    @Override
    public int ConutBYSupplierType(String get) {
        return eepVendorRegFacade.ConutBYSupplierType(get);
    }

}
