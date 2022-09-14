/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.commonApplications.mapper;

import et.gov.eep.commonApplications.entity.ComLuCountry;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author mubejbl
 */
@Stateless
public class ComLuCountryFacade extends AbstractFacade<ComLuCountry> {

    @PersistenceContext(unitName = "ERP-ejbPU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public ComLuCountryFacade() {
        super(ComLuCountry.class);
    }

    // <editor-fold defaultstate="collapsed" desc="Named Queries">
    public ComLuCountry findByCountryId(ComLuCountry luCountry) {
        Query query = em.createNamedQuery("ComLuCountry.findById");
        query.setParameter("id", luCountry.getId());
        try {
            ComLuCountry comLuCountry = (ComLuCountry) (query.getResultList().get(0));
            return comLuCountry;
        } catch (Exception ex) {
            System.out.println("not fund");
            return null;
        }
    }

    public ArrayList<ComLuCountry> getCountryName() {
        try {
            Query query = em.createNamedQuery("ComLuCountry.findAll", ComLuCountry.class);
            ArrayList<ComLuCountry> comLuCountryList = new ArrayList(query.getResultList());
            return comLuCountryList;
        } catch (Exception e) {
            e.printStackTrace();;
            return null;
        }
    }

    public List<ComLuCountry> getCountries() {
        Query query = em.createNamedQuery("ComLuCountry.findCountryLists");
        try {
            List<ComLuCountry> countriesList = new ArrayList<>(query.getResultList());
            return countriesList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    // </editor-fold>

}
