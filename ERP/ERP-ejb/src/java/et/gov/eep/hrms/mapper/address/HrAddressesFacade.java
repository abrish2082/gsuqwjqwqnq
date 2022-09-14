/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.mapper.address;

import et.gov.eep.commonApplications.mapper.AbstractFacade;
import et.gov.eep.hrms.entity.address.HrAddresses;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author munir
 */
@Stateless
public class HrAddressesFacade extends AbstractFacade<HrAddresses> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    /**
     *
     * @return
     */
    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    /**
     *
     */
    public HrAddressesFacade() {
        super(HrAddresses.class);
    }

    /**
     *
     * @return
     */
    @Override
    public List<HrAddresses> findAll() {
        Query query = em.createNamedQuery("HrAddresses.findAll");
        try {
            List<HrAddresses> getAllAddress = (List<HrAddresses>) query.getResultList();
            return getAllAddress;
        } catch (Exception ex) {
            return null;
        }
    }

    public List<HrAddresses> findAllCountryFromDescription() {
        Query queries = em.createNamedQuery("HrAddresses.findAllCountryFromDescription");

        try {
            List<HrAddresses> countries = new ArrayList<>(queries.getResultList());
            return countries;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     *
     * @return
     */
    public List<HrAddresses> findAllAddressUnit() {
        Query query = em.createNativeQuery("SELECT HR_ADDRESSES.*, "
                + "LEVEL, LPAD(' ', 5* LEVEL - 1)|| ADDRESS_DESCRIPTION || '    ' "
                + "FROM HR_ADDRESSES "
                + "WHERE (PARENT_ID != 0 OR ADDRESS_ID = 1) AND STATUS = 1 "
                + "START WITH ADDRESS_ID = 1 "
                + "CONNECT BY nocycle "
                + "PRIOR ADDRESS_ID = PARENT_ID", HrAddresses.class);
        try {
            return (List<HrAddresses>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @return
     */
    public List<HrAddresses> findAllAddressUnitAndCountries() {
        Query query = em.createNativeQuery("SELECT HR_ADDRESSES.*, "
                + "LEVEL, LPAD(' ', 5* LEVEL - 1)|| ADDRESS_DESCRIPTION || '    ' "
                + "FROM HR_ADDRESSES "
                + "START WITH ADDRESS_ID = 0 "
                + "CONNECT BY nocycle "
                + "PRIOR ADDRESS_ID = PARENT_ID", HrAddresses.class);
        try {
            return (List<HrAddresses>) query.getResultList();
        } catch (Exception ex) {
            return null;
        }
    }

    /**
     *
     * @param hrAddresses
     * @return
     */
    public HrAddresses getAddressInfoByAddressId(HrAddresses hrAddresses) {
        Query query = em.createNamedQuery("HrAddresses.findByAddressId");
        query.setParameter("addressId", hrAddresses.getAddressId());
        try {
            HrAddresses address = null;
            if (query.getResultList().size() > 0) {
                address = (HrAddresses) query.getResultList().get(0);
            }
            return address;
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    /**
     *
     * @param hrAddresses
     * @return
     */
    public HrAddresses findAllAddressUnitAsOne(HrAddresses hrAddresses) {
        Query query = em.createNativeQuery("SELECT HR_ADDRESSES.ADDRESS_ID, SUBSTR(sys_connect_by_path( "
                + "HR_ADDRESSES.ADDRESS_NAME, ' , ' ), 3) as ADDRESS_NAME "
                + "FROM HR_ADDRESSES "
                + "WHERE ADDRESS_ID = ? "
                + "START WITH HR_ADDRESSES.PARENT_ID = 0 "
                + "CONNECT BY PRIOR ADDRESS_ID = PARENT_ID", HrAddresses.class);
        query.setParameter(1, hrAddresses.getAddressId());
        try {
            return (HrAddresses) query.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

}
