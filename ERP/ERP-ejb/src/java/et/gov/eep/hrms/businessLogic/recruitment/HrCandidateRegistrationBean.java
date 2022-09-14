/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package et.gov.eep.hrms.businessLogic.recruitment;

import et.gov.eep.commonApplications.entity.ColumnNameResolver;
import et.gov.eep.hrms.entity.lookup.HrLuEducLevels;
import et.gov.eep.hrms.entity.lookup.HrLuEducTypes;
import et.gov.eep.hrms.entity.lookup.HrLuLanguages;
import et.gov.eep.hrms.entity.lookup.HrLuNationalities;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisedJobs;
import et.gov.eep.hrms.entity.recruitment.HrAdvertisements;
import javax.ejb.Stateless;
import et.gov.eep.hrms.entity.recruitment.HrCandidiates;
import et.gov.eep.hrms.mapper.lookup.HrLuEducLevelsFacade;
import et.gov.eep.hrms.mapper.lookup.HrLuEducTypesFacade;
import et.gov.eep.hrms.mapper.recruitment.HrCandidiatesFacade;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.EJB;

/**
 *
 * @author user
 */
@Stateless
public class HrCandidateRegistrationBean implements HrCandidateRegistrationBeanLocal {

    @EJB
    HrCandidiatesFacade hrCandidiatesFaced;
    @EJB
    HrLuEducLevelsFacade hrLuEducLevelsFacade;
    @EJB
    HrLuEducTypesFacade hrLuEducTypesFacade;

    @Override
    public List<HrAdvertisements> batchCodes(String type) {
        return hrCandidiatesFaced.batchCodes(type);
    }

    @Override
    public List<HrAdvertisedJobs> advertizedJobs(int advertId) {
        return hrCandidiatesFaced.advertizedJobs(advertId);
    }

    @Override
    public HrAdvertisements findBatchCode(HrAdvertisements hrAdvertisements) {
        return hrCandidiatesFaced.findBatchCode(hrAdvertisements);
    }

    @Override
    public void save(HrCandidiates candidiates) {
        hrCandidiatesFaced.create(candidiates);

    }

    @Override
    public void edit(HrCandidiates candidiates) {
        hrCandidiatesFaced.edit(candidiates);

    }

    @Override
    public HrLuNationalities findNationality(HrLuNationalities hrLuNationalities) {
        return hrCandidiatesFaced.findNationality(hrLuNationalities);
    }

    @Override
    public ArrayList<HrLuEducTypes> findEducationTypes() {
        return hrCandidiatesFaced.findEducationTypes();
    }

    @Override
    public ArrayList<HrLuEducLevels> findEducationLeves() {
        return hrCandidiatesFaced.findEducationLeves();
    }

    @Override
    public HrCandidiates getByCandidateId(HrCandidiates candidiates) {
        return hrCandidiatesFaced.getByCandidateId(candidiates);
    }

    @Override
    public ArrayList<HrCandidiates> searchByCol_NameAndCol_Value(ColumnNameResolver columnNameResolver, String Col_Value) {
        return hrCandidiatesFaced.searchByCol_NameAndCol_Value(columnNameResolver,Col_Value);
    }
    
    @Override
    public HrCandidiates getByfirstName(HrCandidiates candidiates) {
        return hrCandidiatesFaced.getByfirstName(candidiates);
        
    }

    @Override
    public ArrayList<HrLuLanguages> findAllHrLuLanguages() {
        return hrCandidiatesFaced.findAllHrLuLanguages();
    }

    @Override
    public List<HrCandidiates> readCandidiates(int status1, int status2) {
        return hrCandidiatesFaced.readCandidiates(status1, status2);
    }

    @Override
    public HrLuEducLevels findbyLuEduLevel(HrLuEducLevels hrLuEducLevels) {
        return hrLuEducLevelsFacade.findbyLuEduLevel(hrLuEducLevels);
    }

    @Override
    public HrLuEducTypes findbyLuEduType(HrLuEducTypes hrLuEducTypes) {
        return hrLuEducTypesFacade.findbyLuEduType(hrLuEducTypes);
    }

    @Override
    public List<ColumnNameResolver> findColumns() {
        return hrCandidiatesFaced.findColumns();
    }

   

}
