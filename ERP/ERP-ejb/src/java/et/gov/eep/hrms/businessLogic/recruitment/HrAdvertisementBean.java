/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.recruitment;

import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import et.gov.eep.hrms.entity.lookup.HrLuMediaTypes;
import et.gov.eep.hrms.entity.recruitment.HrRecruitmentRequests;
import et.gov.eep.hrms.mapper.lookup.HrLuMediaTypesFacade;
import et.gov.eep.hrms.mapper.recruitment.HrAdvertisementsFacade;
import et.gov.eep.hrms.mapper.recruitment.HrRecruitmentRequestsFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 *
 * @author user
 */
@Stateless
public class HrAdvertisementBean implements HrAdvertisementBeanLocal {

    @EJB
    HrAdvertisementsFacade hrAdvertisementsFacade;
    @EJB
    HrRecruitmentRequestsFacade hrRecruitmentRequestsFacade;
    @EJB
    HrLuMediaTypesFacade mediaTypesFacade;

    /**
     *
     * @param batchCode
     * @return
     */
    @Override
    public List<Object[]> readApprovedRecruitments(String batchCode) {
        return hrAdvertisementsFacade.readApprovedRecruitments(batchCode);
    }

    @Override
    public HrAdvertisements findByBatchCode(String bachCode) {
        return hrAdvertisementsFacade.findByGrade(bachCode);
    }

    /**
     *
     * @return
     */
    @Override
    public List<String> distinctbatchCode() {
        return hrAdvertisementsFacade.distinctBatchCode();
    }

    /**
     *
     * @return
     */
    @Override
    public ArrayList<HrLuMediaTypes> findhrluMedias() {
        return hrAdvertisementsFacade.findhrluMedias();
    }

    /**
     *
     * @param advert
     */
    @Override
    public void save(HrAdvertisements advert) {
        hrAdvertisementsFacade.saveOrUpdate(advert);
    }

    /**
     *
     * @param advert
     */
    @Override
    public void edit(HrAdvertisements advert) {
        hrAdvertisementsFacade.saveOrUpdate(advert);
    }

    @Override
    public void edit(HrRecruitmentRequests hrRecruitmentRequests) {
        hrRecruitmentRequestsFacade.edit(hrRecruitmentRequests);
    }

    /**
     *
     * @param md
     * @return
     */
    @Override
    public HrLuMediaTypes findByMediaName(HrLuMediaTypes md) {
        return hrAdvertisementsFacade.findByMediaName(md);
    }

    @Override
    public HrAdvertisements findByAdvertimnetId(Integer valueOf) {
        return hrAdvertisementsFacade.find(valueOf);
    }

    @Override
    public List<HrAdvertisements> findAllDistinict() {
        return hrAdvertisementsFacade.findAllDistinict();
    }

    @Override
    public List<HrAdvertisements> findAll() {
        return hrAdvertisementsFacade.findAll();
    }

    @Override
    public void findByBatchCodeAndEdit(HrRecruitmentRequests hrRecruitmentRequests) {
        hrAdvertisementsFacade.findByBatchCodeAndEdit(hrRecruitmentRequests);
    }

    @Override
    public void save(HrLuMediaTypes mediaType) {
        mediaTypesFacade.create(mediaType);
    }

    @Override
    public void edit(HrLuMediaTypes mediaType) {
        mediaTypesFacade.edit(mediaType);
    }

    @Override
    public List<HrLuMediaTypes> findall() {
        return mediaTypesFacade.findAll();
    }

    @Override
    public ArrayList<HrLuMediaTypes> searchByMediaType(HrLuMediaTypes hrLuMediaTypes) {
        return hrAdvertisementsFacade.searchByMediaType(hrLuMediaTypes);
    }

    @Override
    public HrLuMediaTypes getByMediaType(HrLuMediaTypes mediaTypes) {
        return hrAdvertisementsFacade.getByMediaType(mediaTypes);
    }

    public boolean isMediaTypeExist(HrLuMediaTypes mediaTypes) {
        return hrAdvertisementsFacade.isMediaTypeExist(mediaTypes);
    }

}
